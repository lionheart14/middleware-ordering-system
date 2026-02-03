package com.mos.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ordering.OrderRequest;
import ordering.OrderResponse;
import ordering.OrderingSystemGrpc;

import java.io.IOException;

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
        
        @Override
        public void placeOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
            System.out.println("Neue Bestellung von: " + request.getCustomerId());

            // Anforderung c) Firmenkunden-Check (RBAC Simulation)
            if (request.getIsCompanyOrder() && !request.getCustomerId().startsWith("FIRMA")) {
                responseObserver.onNext(OrderResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("ZUGRIFF VERWEIGERT: Nur Firmenkunden erlaubt.")
                    .build());
                responseObserver.onCompleted();
                return;
            }

            // Anforderung b) Rabatt-Logik & Validierung
            double basePrice = 100.0; // Beispielwert
            double discount = 0.0;
            if (request.getCustomerId().contains("VIP")) {
                discount = 20.0; // 20% Rabatt für VIPs
            }

            // Anforderung a) & f) Transaktions-Simulation (Ganz oder gar nicht)
            boolean storageAvailable = true; // Hier würde die Lager-Prüfung stehen
            
            if (storageAvailable) {
                OrderResponse response = OrderResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Bestellung erfolgreich verarbeitet.")
                    .setTotalPriceAfterDiscount(basePrice - discount)
                    .setTransactionId("TX-" + System.currentTimeMillis())
                    .build();
                
                responseObserver.onNext(response);
            } else {
                responseObserver.onNext(OrderResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Fehler: Lager leer. Transaktion abgebrochen.")
                    .build());
            }
            responseObserver.onCompleted();
        }
    }
}