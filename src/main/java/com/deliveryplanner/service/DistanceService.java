package com.deliveryplanner.service;

import com.deliveryplanner.exception.ServiceException;

public interface DistanceService {

    long calculateDistance(double startLat, double startLon, double endLat, double endLon) throws ServiceException;
}
