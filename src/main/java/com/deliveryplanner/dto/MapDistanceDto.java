package com.deliveryplanner.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapDistanceDto {

    private List<RowObject> rows;
    private String status;

    @Data
    public static class RowObject {
        private List<Element> elements;
    }

    @Data
    public static class Element {
        private String status;
        private ValueObject distance;
    }

    @Data
    public static class ValueObject {
        private String text;
        private long value;
    }
}
