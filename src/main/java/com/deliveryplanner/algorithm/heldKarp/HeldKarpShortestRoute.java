package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.NodePath;
import com.deliveryplanner.algorithm.heldKarp.object.OrderedDelivery;
import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.RankedDeliveryDto;
import com.deliveryplanner.service.ShortestRoute;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class HeldKarpShortestRoute implements ShortestRoute {

    private DistanceMatrixCreator distanceMatrixCreator;
    private PathCreator pathCreator;

    public HeldKarpShortestRoute(DistanceMatrixCreator distanceMatrixCreator, PathCreator pathCreator) {
        this.distanceMatrixCreator = distanceMatrixCreator;
        this.pathCreator = pathCreator;
    }

    public List<RankedDeliveryDto> createRoute(DeliveryDto start, List<DeliveryDto> deliveries) {
        List<OrderedDelivery> orderedDeliveries = getOrderedDeliveries(start, deliveries);

        double[][] distanceMatrix = distanceMatrixCreator.create(orderedDeliveries);

        List<Set<OrderedDelivery>> nodes = generateSubsets(orderedDeliveries);
        List<NodePath> nodePaths = createAllPossibleNodePaths(nodes, orderedDeliveries);

        return pathCreator.create(distanceMatrix, nodePaths, orderedDeliveries);
    }

    private List<OrderedDelivery> getOrderedDeliveries(DeliveryDto start, List<DeliveryDto> deliveries) {
        List<OrderedDelivery> orderedDeliveries = new ArrayList<>();
        orderedDeliveries.add(new OrderedDelivery(0, start.getLatitude(), start.getLongitude()));

        AtomicInteger order = new AtomicInteger(1);
        orderedDeliveries.addAll(deliveries.stream()
                .map(d-> new OrderedDelivery(order.getAndIncrement(), d.getLatitude(), d.getLongitude()))
                .collect(Collectors.toList()));
        return orderedDeliveries;
    }

    /**
     *
     * @return All the subsets of nodes except the starting node.
     */
    List<Set<OrderedDelivery>> generateSubsets(List<OrderedDelivery> deliveries) {
        List<Set<OrderedDelivery>> nodes = new ArrayList<>();
        generateSubsets(deliveries, 1, nodes, new ArrayList<>());

        return nodes;
    }

    private void generateSubsets(List<OrderedDelivery> deliveries, int start, List<Set<OrderedDelivery>> nodes,
                                 List<OrderedDelivery> temp) {

        for(int i = start; i < deliveries.size(); i++) {
            temp.add(deliveries.get(i));
            nodes.add(new LinkedHashSet<>(temp));
            generateSubsets(deliveries, i + 1, nodes, temp);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     *
     * @return List of Nodepaths ordered by the number of elements they have in "via".
     */
    private List<NodePath> createAllPossibleNodePaths(List<Set<OrderedDelivery>> nodes,
                                                      List<OrderedDelivery> deliveries) {
        List<NodePath> result = new ArrayList<>();

        deliveries.stream()
                .skip(1)
                .forEach(d -> createNodePathForDelivery(d, nodes, result));

        result.sort(Comparator.comparingInt(nodePath -> nodePath.getVia().size()));
        return result;
    }

    private void createNodePathForDelivery(OrderedDelivery delivery, List<Set<OrderedDelivery>> nodes,
                                           List<NodePath> result) {
        result.add(new NodePath(delivery, new HashSet<>())); // reach destination directly from start point
        nodes.forEach(node -> {
            if (!node.contains(delivery)) {
                result.add(new NodePath(delivery, node));
            }
        });
    }
}
