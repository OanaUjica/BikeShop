package repository;

import model.Bike;
import model.BikeType;
import model.validators.BikeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.xmlFileRepositories.BikeXmlRepository;

import static org.junit.jupiter.api.Assertions.*;

class BikeXmlRepositoryTest {

    private BikeValidator bikeValidator;
    private IRepository<Long, Bike> bikeRepository;

    @BeforeEach
    public void setup() {

        this.bikeValidator = new BikeValidator();
        this.bikeRepository = new BikeXmlRepository(bikeValidator, "src/test/resources/bikes_test.xml");
    }

    @Test
    void save() {

        Bike bike2 = new Bike(2l, "bike2", BikeType.ELECTRICBIKE, 9000.00);
        this.bikeRepository.save(bike2);

        assertEquals(bike2, this.bikeRepository.findOne(bike2.getId()).get());

    }

    @Test
    void delete() {

        Bike bike3 = new Bike(3l, "bike3", BikeType.MOUNTAINBIKE, 5500);
        this.bikeRepository.save(bike3);

        this.bikeRepository.delete(bike3.getId());

        assertFalse(this.bikeRepository.findOne(bike3.getId()).isPresent());
    }

    @Test
    void update() {

        Bike bike2 = new Bike(2l, "bike2", BikeType.ELECTRICBIKE, 9200.00);
        this.bikeRepository.update(bike2);

        assertEquals(9200, this.bikeRepository.findOne(bike2.getId()).get().getPrice());
    }
}