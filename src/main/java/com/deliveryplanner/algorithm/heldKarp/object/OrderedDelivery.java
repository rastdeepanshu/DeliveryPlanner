package com.deliveryplanner.algorithm.heldKarp.object;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedDelivery {
    private int index;
    private double latitude, longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedDelivery delivery = (OrderedDelivery) o;
        return index == delivery.index &&
                Double.compare(delivery.latitude, latitude) == 0 &&
                Double.compare(delivery.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(index, latitude, longitude);
    }
}
