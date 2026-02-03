import grpc
import ordering_pb2
import ordering_pb2_grpc

def run():
    # Verbindung zum Server aufbauen (Im Docker-Netzwerk heißt der Host 'server')
    with grpc.insecure_channel('server:50051') as channel:
        stub = ordering_pb2_grpc.OrderingSystemStub(channel)

        print("--- Test 1: Normaler Kunde ---")
        response = stub.PlaceOrder(ordering_pb2.OrderRequest(
            customer_id="Kunde_123",
            item_ids=["Laptop", "Maus"],
            payment_method="Kreditkarte",
            is_company_order=False
        ))
        print(f"Status: {response.success}, Nachricht: {response.message}, Preis: {response.total_price_after_discount}€")

        print("\n--- Test 2: VIP Kunde (Rabatt Check Anforderung b) ---")
        response = stub.PlaceOrder(ordering_pb2.OrderRequest(
            customer_id="VIP_Leon",
            item_ids=["Monitor"],
            payment_method="PayPal",
            is_company_order=False
        ))
        print(f"Status: {response.success}, Nachricht: {response.message}, Preis: {response.total_price_after_discount}€")

        print("\n--- Test 3: Firmenkunden-Check (Anforderung c) ---")
        # Wir versuchen als Privatkunde eine Firmenbestellung zu schicken
        response = stub.PlaceOrder(ordering_pb2.OrderRequest(
            customer_id="Kunde_123",
            item_ids=["Server-Rack"],
            payment_method="Rechnung",
            is_company_order=True
        ))
        print(f"Status: {response.success}, Nachricht: {response.message}")

if __name__ == '__main__':
    run()