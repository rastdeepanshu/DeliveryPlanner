package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.NodePath;
import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.RankedDelivery;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OptimalPath {

    public List<RankedDelivery> create (double[][] distanceMatrix, List<NodePath> nodePaths) {
        Map<NodePath, Double> distanceMap = new HashMap<>();
        Map<NodePath, DeliveryDto> parentMap = new HashMap<>();

        return null;
    }
}
