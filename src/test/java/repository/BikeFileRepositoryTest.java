package repository;

import model.Bike;
import model.BikeType;
import model.validators.BikeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.csvFileRepositories.BikeFileRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BikeFileRepositoryTest {

    private BikeValidator bikeValidator;
    private IRepository<Long, Bike> bikeFileRepository;

    @BeforeEach
    public void setup() {

        this.bikeValidator = new BikeValidator();
        this.bikeFileRepository = new BikeFileRepository(bikeValidator, "src/test/resources/bikes_test.csv");
    }

    @Test
    void save() {

        Bike bike1 = new Bike(1l, "bike1", BikeType.MOUNTAINBIKE, 3400.00);
//        bike1.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        this.bikeFileRepository.save(bike1);

        Bike bike2 = new Bike(2l, "bike2", BikeType.CITYBIKE, 2500.00);
//        bike2.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        this.bikeFileRepository.save(bike2);

        assertEquals(bike1, this.bikeFileRepository.findOne(bike1.getId()).get());

    }

    @Test
    void delete() {

        Bike bike3 = new Bike(1l, "bike3", BikeType.ELECTRICBIKE, 8400.00);
        bike3.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        this.bikeFileRepository.save(bike3);

        this.bikeFileRepository.delete(bike3.getId());

        assertFalse(this.bikeFileRepository.findOne(bike3.getId()).isPresent());

    }
}