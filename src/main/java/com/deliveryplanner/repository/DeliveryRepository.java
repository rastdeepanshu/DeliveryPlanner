package com.deliveryplanner.repository;

import com.deliveryplanner.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByPathIdAndRank(String pathId, int rank);
}
