package com.deliveryplanner.algorithm.heldKarp.object;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NodePath {
    private OrderedDelivery destination;
    private Set<OrderedDelivery> via;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodePath nodePath = (NodePath) o;
        return Objects.equals(destination, nodePath.destination) &&
                compareSets(nodePath);
    }

    private boolean compareSets(NodePath nodePath) {
        return via.containsAll(nodePath.via);
    }

    @Override
    public int hashCode() {

        return Objects.hash(destination, via);
    }
}
