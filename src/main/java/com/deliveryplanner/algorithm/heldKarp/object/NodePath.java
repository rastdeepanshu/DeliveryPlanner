package com.deliveryplanner.algorithm.heldKarp.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NodePath {
    private OrderedDelivery destination;
    private Set<OrderedDelivery> via;
}
