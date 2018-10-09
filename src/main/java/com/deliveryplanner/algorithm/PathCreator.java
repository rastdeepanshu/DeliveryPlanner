package com.deliveryplanner.algorithm;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.RankedDelivery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathCreator {

    private DistanceMatrixCreator distanceMatrixCreator;

    public PathCreator(DistanceMatrixCreator distanceMatrixCreator) {
        this.distanceMatrixCreator = distanceMatrixCreator;
    }

    public List<RankedDelivery> createPath(DeliveryDto start, List<DeliveryDto> deliveries) {
        deliveries.add(0, start);
        double[][] distanceMatrix = distanceMatrixCreator.create(deliveries);

        return null;
    }
}
