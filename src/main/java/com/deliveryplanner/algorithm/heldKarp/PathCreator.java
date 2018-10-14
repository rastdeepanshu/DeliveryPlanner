package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.NodePath;
import com.deliveryplanner.algorithm.heldKarp.object.OrderedDelivery;
import com.deliveryplanner.dto.RankedDeliveryDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class PathCreator {

    public List<RankedDeliveryDto> create (double[][] distanceMatrix, List<NodePath> nodePaths,
                                           List<OrderedDelivery> deliveries) {
        Map<NodePath, Double> distanceMap = new HashMap<>();
        Map<NodePath, OrderedDelivery> parentMap = new HashMap<>();

        nodePaths.forEach(path -> findPathCost(path, distanceMatrix, distanceMap, parentMap, deliveries));

        NodePath startNodePath = new NodePath(deliveries.get(0), new HashSet<>(deliveries.subList(1, deliveries.size())));
        findPathCost(startNodePath, distanceMatrix, distanceMap, parentMap, deliveries);

        List<RankedDeliveryDto> result = new ArrayList<>();
        getRankedDeliveries(parentMap, startNodePath, result);
        return result;
    }

    private void findPathCost(NodePath path, double[][] distanceMatrix, Map<NodePath, Double> distanceMap,
                              Map<NodePath, OrderedDelivery> parentMap, List<OrderedDelivery> deliveries) {
        if (path.getVia().size() == 0) {
            distanceMap.put(path, distanceMatrix[0][path.getDestination().getIndex()]);
            parentMap.put(path, deliveries.get(0));
        } else {
            double minCost = Double.MAX_VALUE;
            OrderedDelivery parent = null;
            for (OrderedDelivery od : path.getVia()) {
                Set<OrderedDelivery> copyOfVia = new HashSet<>(path.getVia());
                copyOfVia.remove(od);
                NodePath reducedNodePath = new NodePath(od, copyOfVia);
                double cost = distanceMatrix[od.getIndex()][path.getDestination().getIndex()] +
                        distanceMap.get(reducedNodePath);

                if (cost < minCost) {
                    minCost = cost;
                    parent = od;
                }
            }

            distanceMap.put(path, minCost);
            parentMap.put(path, parent);
        }
    }

    private void getRankedDeliveries(Map<NodePath, OrderedDelivery> parentMap,
                                     NodePath nodePath, List<RankedDeliveryDto> result) {

        if (nodePath.getVia().size() == 0) {
            return;
        }
        OrderedDelivery destinationDelivery = parentMap.get(nodePath);
        result.add(new RankedDeliveryDto(nodePath.getVia().size(), destinationDelivery.getLatitude(),
                destinationDelivery.getLongitude()));

        nodePath.getVia().remove(destinationDelivery);
        NodePath prevNodePath = new NodePath(destinationDelivery, new HashSet<>(nodePath.getVia()));

        getRankedDeliveries(parentMap, prevNodePath, result);
    }
}
