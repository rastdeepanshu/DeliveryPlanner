package com.deliveryplanner.service;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.RankedDeliveryDto;

import java.util.List;

public interface PathCreator {

    List<RankedDeliveryDto> createPath(DeliveryDto start, List<DeliveryDto> deliveries);
}
