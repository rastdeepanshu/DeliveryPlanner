package com.deliveryplanner.service;

public interface DistanceService {

    long calculateDistance(double startLat, double startLon, double endLat, double endLon);
}
