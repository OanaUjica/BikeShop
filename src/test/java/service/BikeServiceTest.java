package service;

import model.Bike;
import model.BikeType;
import model.validators.BikeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class BikeServiceTest {

    private IRepository<Long, Bike> bikeRepository;
    private BikeValidator bikeValidator;
    private BikeService bikeService;

    @BeforeEach
    public void setup() {

        this.bikeValidator = new BikeValidator();
        this.bikeRepository = new InMemoryRepository<>(bikeValidator);

        this.bikeService = new BikeService(bikeRepository, bikeValidator);
    }

    @Test
    void addingAValidBike_should_saveThatBikeToRepository() {

        Bike bike = new Bike(1l, "bike1", BikeType.ELECTRICBIKE, 7800.00);
        bikeService.addBikeService(bike.getName(), bike.getType(), bike.getPrice());

        assertEquals(1, StreamSupport.stream(bikeRepository.findAll().spliterator(), false).count());
    }

    @Test
    void getAll() {

        Bike bike = new Bike(2l, "Bike2", BikeType.MOUNTAINBIKE, 3500.00);
        bikeService.addBikeService(bike.getName(), bike.getType(), bike.getPrice());

        Iterable<Bike> bikes = bikeService.getAll();
        assertEquals(1, StreamSupport.stream(bikes.spliterator(), false).count());
    }

    @Test
    void gettingAllTheBikes_should_showBikeWithMaxPrice() {

        Bike bike1 = new Bike(1l, "Bike1", BikeType.MOUNTAINBIKE, 3900.00);
        Bike bike2 = new Bike(2l, "Bike2", BikeType.CITYBIKE, 2200.00);
        Bike bike3 = new Bike(3l, "Bike3", BikeType.MOUNTAINBIKE, 3900.00);

        bikeService.addBikeService(bike1.getName(), bike1.getType(), bike1.getPrice());
        bikeService.addBikeService(bike2.getName(), bike2.getType(), bike2.getPrice());
        bikeService.addBikeService(bike3.getName(), bike3.getType(), bike3.getPrice());

        List<Bike> expectedBikes = new ArrayList<>();
        expectedBikes.add(bike1);
        expectedBikes.add(bike3);

        assertEquals(2, bikeService.showBikeWithMaxPrice().size());
    }

}