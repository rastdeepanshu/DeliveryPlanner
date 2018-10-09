package com.deliveryplanner.service;

import com.deliveryplanner.dto.MapDistanceDto;
import com.deliveryplanner.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class GoogleMapsDistanceService implements DistanceService {

    @Value("${google_api.url}")
    private String routesApiUrl;

    @Value("${google_api.key}")
    private String apiKey;

    private RestTemplate restTemplate;

    public GoogleMapsDistanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public long calculateDistance(double startLat, double startLon, double endLat, double endLon) throws ServiceException {
        MapDistanceDto mapDistanceDto = restTemplate.getForObject(routesApiUrl, MapDistanceDto.class, startLat, startLon, endLat, endLon);
        if (!"OK".equalsIgnoreCase(mapDistanceDto.getStatus())) {
            throw new ServiceException("Could not make a successful call to google api");
        }
        return mapDistanceDto.getRows().get(0).getElements().get(0).getDistance().getValue();
    }
}
