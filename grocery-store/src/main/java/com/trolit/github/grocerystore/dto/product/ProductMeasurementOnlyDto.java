package com.trolit.github.grocerystore.dto.product;

public class ProductMeasurementOnlyDto {

    private String measurement;

    public ProductMeasurementOnlyDto(String measurement) {
        this.measurement = measurement;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
