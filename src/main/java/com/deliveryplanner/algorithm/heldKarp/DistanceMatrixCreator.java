package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.OrderedDelivery;
import com.deliveryplanner.service.DistanceService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistanceMatrixCreator {

    private DistanceService distanceService;

    public DistanceMatrixCreator(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    public long[][] create(List<OrderedDelivery> deliveries) {
        long[][] distanceMatrix = new long[deliveries.size()][deliveries.size()];

        for(int i = 0; i < deliveries.size(); i++) {
            for (int j = 0; j < deliveries.size(); j++) {
                if (i != j) {
                    OrderedDelivery start = deliveries.get(i);
                    OrderedDelivery end = deliveries.get(j);
                    distanceMatrix[i][j] = distanceService.calculateDistance(
                            start.getLatitude(), start.getLongitude(),
                            end.getLatitude(), end.getLongitude());
                }
            }
        }

        return distanceMatrix;
    }
}
