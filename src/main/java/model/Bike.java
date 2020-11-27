package model;

import model.customAnnotations.BikeTypeAnnotation;
import model.customAnnotations.abc;

import java.util.Objects;

public class Bike extends Entity<Long> {

    private String name;
    //@BikeTypeAnnotation(anyOf = {BikeType.CITYBIKE, BikeType.ELECTRICBIKE, BikeType.MOUNTAINBIKE}, message = "must be any of {anyOf}")
    //@abc(regexp = "CITYBIKE|ELECTRICBIKE|MOUNTAINBIKE")
    private BikeType type;
    private double price;

    public Bike() {
    }

    public Bike(String name, BikeType type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Bike(Long id, String name, BikeType type, double price) {
        super.setId(id);
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BikeType getType() {

        return type;
    }

    public void setType(BikeType type) {

        this.type = type;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    @Override
    public String toString() {
        return "Bike{id='" + super.getId() + "\' " +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Double.compare(bike.price, price) == 0 &&
                Objects.equals(name, bike.name) &&
                Objects.equals(type, bike.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), name, type, price);
    }
}


