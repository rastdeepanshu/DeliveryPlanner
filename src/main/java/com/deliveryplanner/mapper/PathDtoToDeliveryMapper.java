package com.deliveryplanner.mapper;

import com.deliveryplanner.dto.PathDto;
import com.deliveryplanner.entity.Delivery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PathDtoToDeliveryMapper {

    public List<Delivery> convert(PathDto pathDto) {
        return pathDto.getRankedDeliveries().stream()
                .map(rankedDelivery -> new Delivery(0,
                        pathDto.getPathId(),
                        rankedDelivery.getRank(),
                        rankedDelivery.getDeliveryDto().getLatitude(),
                        rankedDelivery.getDeliveryDto().getLongitude()))
                .collect(Collectors.toList());
    }
}
