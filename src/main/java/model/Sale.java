package model;

import java.time.LocalDate;
import java.util.Objects;

public class Sale extends Entity<Long> {

    private Bike bike;
    private Customer customer;
    private LocalDate saleDate;

    public Sale() {
    }

    public Sale(Bike bike, Customer customer, LocalDate saleDate) {
        this.bike = bike;
        this.customer = customer;
        this.saleDate = saleDate;
    }

    public Sale(Long id, Bike bike, Customer customer, LocalDate saleDate) {
        super.setId(id);
        this.bike = bike;
        this.customer = customer;
        this.saleDate = saleDate;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "Sale{id='" + super.getId() + "\' " +
                "bike=" + bike + "\n" +
                ", customer=" + customer + "\n" +
                ", saleDate=" + saleDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(bike, sale.bike) &&
                Objects.equals(customer, sale.customer) &&
                Objects.equals(saleDate, sale.saleDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bike, customer, saleDate);
    }
}
