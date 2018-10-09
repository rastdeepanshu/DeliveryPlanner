package com.deliveryplanner.algorithm;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.service.DistanceService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistanceMatrixCreator {

    private DistanceService distanceService;

    public DistanceMatrixCreator(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    public double[][] create(List<DeliveryDto> deliveries) {
        double[][] distanceMatrix = new double[deliveries.size()][deliveries.size()];

        for(int i = 0; i < deliveries.size(); i++) {
            for (int j = 0; j < deliveries.size(); j++) {
                if (i != j) {
                    DeliveryDto start = deliveries.get(i);
                    DeliveryDto end = deliveries.get(j);
                    distanceMatrix[i][j] = distanceService.calculateDistance(
                            start.getLatitude(), start.getLongitude(),
                            end.getLatitude(), end.getLongitude());
                }
            }
        }

        return distanceMatrix;
    }
}
