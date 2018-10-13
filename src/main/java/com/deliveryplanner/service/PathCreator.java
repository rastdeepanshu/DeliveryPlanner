package com.deliveryplanner.service;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.RankedDelivery;

import java.util.List;

public interface PathCreator {

    List<RankedDelivery> createPath(DeliveryDto start, List<DeliveryDto> deliveries);
}
