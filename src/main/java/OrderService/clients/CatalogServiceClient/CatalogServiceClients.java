package OrderService.clients.CatalogServiceClient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import proto.Catalog;


@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class CatalogServiceClients {
    private final String grpcServerHost;
    private final int grpcServerPort;

    public Catalog.GetMenuItemsResponse getMenuItems(int restaurantId) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .build();

            CatalogServiceGrpc.CatalogServiceBlockingStub stub = CatalogServiceGrpc.newBlockingStub(channel);
            Catalog.GetMenuItemsRequest request = Catalog.GetMenuItemsRequest.newBuilder()
                    .setRestaurantId(restaurantId)
                    .build();
            Catalog.GetMenuItemsResponse response = stub.getMenuItems(request);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error calling gRPC service", e);
        }
    }
}
