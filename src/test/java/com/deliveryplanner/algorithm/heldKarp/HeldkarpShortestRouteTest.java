package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.NodePath;
import com.deliveryplanner.algorithm.heldKarp.object.OrderedDelivery;
import com.deliveryplanner.dto.DeliveryDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeldkarpShortestRouteTest {

    @Autowired
    private HeldKarpShortestRoute heldKarpShortestRoute;

    @Test
    public void getOrderedDeliveriesTest() {
        DeliveryDto start = new DeliveryDto(11.11, 22.22);
        List<DeliveryDto> deliveries = Arrays.asList(
                new DeliveryDto(33.33, 44.44),
                new DeliveryDto(55.55, 66.66)
        );

        List<OrderedDelivery> orderedDeliveries = heldKarpShortestRoute.getOrderedDeliveries(start, deliveries);

        Assert.assertEquals(11.11, orderedDeliveries.get(0).getLatitude(), 0d);
        Assert.assertEquals(0, orderedDeliveries.get(0).getIndex());

        Assert.assertEquals(33.33, orderedDeliveries.get(1).getLatitude(), 0d);
        Assert.assertEquals(1, orderedDeliveries.get(1).getIndex());

        Assert.assertEquals(55.55, orderedDeliveries.get(2).getLatitude(), 0d);
        Assert.assertEquals(2, orderedDeliveries.get(2).getIndex());
    }

    @Test
    public void generateSubsetsTest() {
        OrderedDelivery od1 = new OrderedDelivery(0, 11.11, 11.11);
        OrderedDelivery od2 = new OrderedDelivery(1, 22.22, 22.22);
        OrderedDelivery od3 = new OrderedDelivery(2, 33.33, 33.33);
        List<OrderedDelivery> orderedDeliveries = Arrays.asList(od1, od2, od3);

        List<Set<OrderedDelivery>> subsets = heldKarpShortestRoute.generateSubsets(orderedDeliveries);

        Assert.assertEquals(3, subsets.size());

        Assert.assertEquals(1, subsets.get(0).size());
        Assert.assertEquals(true, subsets.get(0).contains(od2));

        Assert.assertEquals(2, subsets.get(1).size());
        Assert.assertEquals(true, subsets.get(1).containsAll(Arrays.asList(od2, od3)));

        Assert.assertEquals(1, subsets.get(2).size());
        Assert.assertEquals(true, subsets.get(2).contains(od3));
    }

    @Test
    public void createNodePathForDeliveryTest() {
        OrderedDelivery od1 = new OrderedDelivery(0, 11.11, 11.11);
        OrderedDelivery od2 = new OrderedDelivery(1, 22.22, 22.22);
        OrderedDelivery od3 = new OrderedDelivery(2, 33.33, 33.33);
        List<OrderedDelivery> orderedDeliveries = Arrays.asList(od1, od2, od3);

        List<Set<OrderedDelivery>> subsets = Arrays.asList(
                new HashSet<>(Collections.singletonList(od2)),
                new HashSet<>(Arrays.asList(od2, od3)),
                new HashSet<>(Collections.singletonList(od3))
        );

        List<NodePath> nodepaths = heldKarpShortestRoute.createAllPossibleNodePaths(subsets, orderedDeliveries);

        Assert.assertEquals(4, nodepaths.size());

        Assert.assertEquals(od2, nodepaths.get(0).getDestination());
        Assert.assertEquals(0, nodepaths.get(0).getVia().size());

        Assert.assertEquals(od3, nodepaths.get(1).getDestination());
        Assert.assertEquals(0, nodepaths.get(1).getVia().size());

        Assert.assertEquals(od2, nodepaths.get(2).getDestination());
        Assert.assertEquals(1, nodepaths.get(2).getVia().size());
        Assert.assertEquals(true, nodepaths.get(2).getVia().contains(od3));

        Assert.assertEquals(od3, nodepaths.get(3).getDestination());
        Assert.assertEquals(1, nodepaths.get(3).getVia().size());
        Assert.assertEquals(true, nodepaths.get(3).getVia().contains(od2));
    }
}
