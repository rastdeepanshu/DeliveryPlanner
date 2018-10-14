package com.deliveryplanner.rest;

import com.deliveryplanner.dto.DeliveryDto;
import com.deliveryplanner.dto.PathDto;
import com.deliveryplanner.exception.ServiceException;
import com.deliveryplanner.service.RouteService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping(path = "/delivery-planner/api")
public class RouteController {

    private RouteService routeService;

    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @RequestMapping(value = {"/paths"}, method = RequestMethod.POST)
    public @ResponseBody PathDto getDeliveriesByRank(
            @RequestParam("slat") double startLat,
            @RequestParam("slon") double startLon,
            @RequestBody List<DeliveryDto> deliveries) {

        return routeService.getDeliveriesByRank(startLat, startLon, deliveries);
    }

    @RequestMapping(value = {"/paths/{pathId}/deliveries/{currentDelivery}"}, method = RequestMethod.GET)
    public DeliveryDto getNextDelivery(
            @PathVariable("pathId") String pathId,
            @PathVariable("currentDelivery") int currentDelivery) throws InvalidParameterException {
        DeliveryDto deliveryDto = null;
        try {
            deliveryDto = routeService.getNextDelivery(pathId, currentDelivery);
        } catch (ServiceException e) {
            throw new InvalidParameterException(e.getMessage());
        }

        return deliveryDto;
    }
}
