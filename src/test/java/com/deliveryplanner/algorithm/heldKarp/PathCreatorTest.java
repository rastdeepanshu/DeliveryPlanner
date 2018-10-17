package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.NodePath;
import com.deliveryplanner.algorithm.heldKarp.object.OrderedDelivery;
import com.deliveryplanner.dto.RankedDeliveryDto;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class PathCreatorTest {

    @Autowired
    PathCreator pathCreator;

    @Test
    public void createTest() {
        long[][] distanceMatrix = {{0, 1, 2}, {3, 0, 4}, {5, 6, 0}};

        OrderedDelivery od1 = new OrderedDelivery(0, 11.11, 11.11);
        OrderedDelivery od2 = new OrderedDelivery(1, 22.22, 22.22);
        OrderedDelivery od3 = new OrderedDelivery(2, 33.33, 33.33);
        List<OrderedDelivery> orderedDeliveries = Arrays.asList(od1, od2, od3);

        List<NodePath> nodePaths = Arrays.asList(
                new NodePath(od2, new HashSet<>()),
                new NodePath(od3, new HashSet<>()),
                new NodePath(od2, new HashSet<>(Collections.singletonList(od3))),
                new NodePath(od3, new HashSet<>(Collections.singletonList(od2)))
        );

        List<RankedDeliveryDto> rankedDeliveries = pathCreator.create(distanceMatrix, nodePaths, orderedDeliveries);

        // With the given distanceMatrix, the optimal path turns out to be 0 -> 1 -> 2 -> 0

        Assert.assertEquals(2, rankedDeliveries.size());

        rankedDeliveries.forEach(rd -> {
            if (rd.getRank() == 1) {
                Assert.assertEquals(22.22, rd.getLatitude(), 0);
            } else if (rd.getRank() == 2) {
                Assert.assertEquals(33.33, rd.getLatitude(), 0);
            }
        });
    }
}
