package com.deliveryplanner.mapper;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.entity.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryToDeliveryDtoMapper {
    public DeliveryDto map(Delivery delivery) {
        return new DeliveryDto(delivery.getLatitude(), delivery.getLongitude());
    }
}
