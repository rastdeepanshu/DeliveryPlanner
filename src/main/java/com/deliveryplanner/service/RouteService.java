package com.deliveryplanner.service;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.PathDto;
import com.deliveryplanner.dto.RankedDelivery;
import com.deliveryplanner.entity.Delivery;
import com.deliveryplanner.exception.ServiceException;
import com.deliveryplanner.mapper.DeliveryToDeliveryDtoMapper;
import com.deliveryplanner.mapper.PathDtoToDeliveryMapper;
import com.deliveryplanner.repository.DeliveryRepository;
import com.deliveryplanner.algorithm.PathCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class RouteService {

    private DeliveryRepository deliveryRepository;
    private PathCreator pathCreator;
    private PathDtoToDeliveryMapper pathToDeliveryMapper;
    private DeliveryToDeliveryDtoMapper deliveryDtoMapper;

    public RouteService(DeliveryRepository deliveryRepository, PathCreator pathCreator,
                        PathDtoToDeliveryMapper pathToDeliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.pathCreator = pathCreator;
        this.pathToDeliveryMapper = pathToDeliveryMapper;
    }

    @Transactional
    public PathDto getDeliveriesByRank(double startLat, double startLon, double endLat, double endLon,
                                       List<DeliveryDto> deliveries) {
        String pathId = calculatePathId();
        List<RankedDelivery> rankedDeliveries = pathCreator.createPath(new DeliveryDto(startLat, startLon),
                new DeliveryDto(endLat, endLon), deliveries);
        Collections.sort(rankedDeliveries, Comparator.comparingInt(RankedDelivery::getRank));

        PathDto pathDto = new PathDto(pathId, rankedDeliveries);
        deliveryRepository.save(pathToDeliveryMapper.convert(pathDto));
        return pathDto;
    }

    private String calculatePathId() {
        return UUID.randomUUID().toString();
    }

    public DeliveryDto getNextDelivery(String pathId, int currentDelivery) throws ServiceException {
        if (currentDelivery < 0) {
            throw new ServiceException("Invalid delivery");
        }
        Delivery delivery =  deliveryRepository.findByPathIdAndRank(pathId, currentDelivery + 1);
        if (delivery == null) {
            throw new ServiceException("No more deliveries");
        }
        return deliveryDtoMapper.map(delivery);
    }
}
