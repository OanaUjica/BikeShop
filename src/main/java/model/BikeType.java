package model;

import model.customAnnotations.BikeTypeAnnotation;

//@BikeTypeAnnotation(anyOf = {BikeType.CITYBIKE, BikeType.ELECTRICBIKE, BikeType.MOUNTAINBIKE}, message = "must be any of {anyOf}")
public enum BikeType {

    CITYBIKE("CityBike"), MOUNTAINBIKE("MountainBike"), ELECTRICBIKE("ElectricBike");

    private String type;

    BikeType(String type) {

        this.type = type;
    }

    public String getBikeType() {

        return this.type;
    }
}
