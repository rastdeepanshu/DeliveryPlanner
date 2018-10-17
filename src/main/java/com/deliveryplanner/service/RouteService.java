package com.deliveryplanner.service;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.PathDto;
import com.deliveryplanner.dto.RankedDeliveryDto;
import com.deliveryplanner.entity.Delivery;
import com.deliveryplanner.exception.ServiceException;
import com.deliveryplanner.mapper.DeliveryToDeliveryDtoMapper;
import com.deliveryplanner.mapper.PathDtoToDeliveryMapper;
import com.deliveryplanner.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class RouteService {

    private DeliveryRepository deliveryRepository;
    private ShortestRoute shortestRoute;
    private PathDtoToDeliveryMapper pathToDeliveryMapper;
    private DeliveryToDeliveryDtoMapper deliveryDtoMapper;

    public RouteService(DeliveryRepository deliveryRepository, ShortestRoute shortestRoute,
                        PathDtoToDeliveryMapper pathToDeliveryMapper,
                        DeliveryToDeliveryDtoMapper deliveryDtoMapper) {
        this.deliveryRepository = deliveryRepository;
        this.shortestRoute = shortestRoute;
        this.pathToDeliveryMapper = pathToDeliveryMapper;
        this.deliveryDtoMapper = deliveryDtoMapper;
    }

    @Transactional
    public PathDto getDeliveriesByRank(double startLat, double startLon, List<DeliveryDto> deliveries) {
        String pathId = generatePathId();
        List<RankedDeliveryDto> rankedDeliveries = shortestRoute.createRoute(new DeliveryDto(startLat, startLon), deliveries);
        Collections.sort(rankedDeliveries, Comparator.comparingInt(RankedDeliveryDto::getRank));

        PathDto pathDto = new PathDto(pathId, rankedDeliveries);
        deliveryRepository.save(pathToDeliveryMapper.convert(pathDto));
        return pathDto;
    }

    private String generatePathId() {
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
