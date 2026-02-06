package com.mos.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ordering.*; // Generierte Klassen

import java.io.IOException;
import java.sql.*;

public class OrderingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new OrderingServiceImpl())
                .build();

        System.out.println("Middleware Server gestartet auf Port 50051...");
        server.start();
        server.awaitTermination();
    }

    static class OrderingServiceImpl extends OrderingSystemGrpc.OrderingSystemImplBase {
        private Connection conn;

        public OrderingServiceImpl() {
            try {
                // Verbindung zur Postgres-DB (Anforderung f)
                String url = "jdbc:postgresql://db:5432/ordering_db";
                conn = DriverManager.getConnection(url, "admin", "mos_password");
                
                // Tabelle initialisieren
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                             "id SERIAL PRIMARY KEY, " +
                             "customer_id TEXT, " +
                             "total_price DOUBLE PRECISION, " +
                             "transaction_id TEXT)");
            } catch (SQLException e) {
                System.err.println("DB-Verbindung fehlgeschlagen: " + e.getMessage());
            }
        }

        @Override
        public void placeOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
            System.out.println("Verarbeite Bestellung für: " + request.getCustomerId());

            // 1. Firmenkunden-Check (Anforderung c)
            if (request.getItemIdsList().contains("PRO_TOOL") && !request.getIsCompanyOrder()) {
                responseObserver.onNext(OrderResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("ZUGRIFF VERWEIGERT: Nur Firmenkunden erlaubt.")
                        .build());
                responseObserver.onCompleted();
                return;
            }

            // 2. Rabatt-Logik (Anforderung b)
            double basePrice = request.getItemIdsCount() * 50.0;
            double finalPrice = request.getCustomerId().startsWith("VIP") ? basePrice * 0.8 : basePrice;

            String txId = "TX-" + System.currentTimeMillis();

            try {
                // 3. Persistenz & Transaktion (Anforderung a & f)
                conn.setAutoCommit(false); // Start Transaktion
                
                PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO orders (customer_id, total_price, transaction_id) VALUES (?, ?, ?)");
                pstmt.setString(1, request.getCustomerId());
                pstmt.setDouble(2, finalPrice);
                pstmt.setString(3, txId);
                pstmt.executeUpdate();
                
                conn.commit(); // Abschluss Transaktion

                responseObserver.onNext(OrderResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Bestellung erfolgreich verarbeitet.")
                        .setTotalPriceAfterDiscount(finalPrice)
                        .setTransactionId(txId)
                        .build());
            } catch (SQLException e) {
                try { conn.rollback(); } catch (SQLException rb) { rb.printStackTrace(); }
                responseObserver.onNext(OrderResponse.newBuilder().setSuccess(false).setMessage("Datenbankfehler").build());
            }
            responseObserver.onCompleted();
        }
    }
}