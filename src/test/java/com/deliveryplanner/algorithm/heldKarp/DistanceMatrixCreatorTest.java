package com.deliveryplanner.algorithm.heldKarp;

import com.deliveryplanner.algorithm.heldKarp.object.OrderedDelivery;
import com.deliveryplanner.service.DistanceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistanceMatrixCreatorTest {

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private DistanceMatrixCreator distanceMatrixCreator;

    @Test
    public void testCreate() {
        List<OrderedDelivery> deliveries = createDeliveries();
        Mockito.when(distanceService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(1L)
                .thenReturn(2L);

        long[][] matrix = distanceMatrixCreator.create(deliveries);

        Assert.assertEquals(0L, matrix[0][0], 0d);
        Assert.assertEquals(1L, matrix[0][1], 0d);
        Assert.assertEquals(2L, matrix[1][0], 0d);
        Assert.assertEquals(0L, matrix[1][1], 0d);
    }

    private List<OrderedDelivery> createDeliveries() {
        List<OrderedDelivery> deliveries = new ArrayList<>();
        deliveries.add(new OrderedDelivery(1, 10d, 10d));
        deliveries.add(new OrderedDelivery(2, 20d, 20d));

        return deliveries;
    }
}
