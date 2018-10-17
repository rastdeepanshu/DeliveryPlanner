package com.deliveryplanner.repository;

import com.deliveryplanner.entity.Delivery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void testFindByPathIdAndRank() {
        Delivery delivery = new Delivery(0, "pathid", 1, 1D, 10D);
        deliveryRepository.save(delivery);

        Delivery returnedDelivery = deliveryRepository.findByPathIdAndRank("pathid", 1);

        Assert.assertEquals(1D, returnedDelivery.getLatitude(), 0D);
        Assert.assertEquals(10D, returnedDelivery.getLongitude(), 0D);
    }
}
