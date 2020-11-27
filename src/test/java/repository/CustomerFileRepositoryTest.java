package repository;

import model.Customer;
import model.validators.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.csvFileRepositories.CustomerFileRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CustomerFileRepositoryTest {

    private CustomerValidator customerValidator;
    private IRepository<Long, Customer> customerFileRepository;

    @BeforeEach
    public void setup() {

        this.customerValidator = new CustomerValidator();
        this.customerFileRepository = new CustomerFileRepository(customerValidator, "src/test/resources/customers_test.csv");
    }

    @Test
    void save() {

        Customer customer1 =
                new Customer(1l,
                        "firstName1",
                        "lastName1",
                        "7890234223",
                        "Brasov",
                        "Plopilor",
                        "13A");
//        customer1.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        this.customerFileRepository.save(customer1);

        Customer customer2 =
                new Customer(2l,
                        "firstName2",
                        "lastName2",
                        "7890334223",
                        "Brasov",
                        "Plopilor",
                        "13A");

//        customer2.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        this.customerFileRepository.save(customer2);

        assertEquals(customer1, this.customerFileRepository.findOne(customer1.getId()).get());

    }

    @Test
    void delete() {

        Customer customer3 =
                new Customer(3l,
                        "firstName3",
                        "lastName3",
                        "7890334883",
                        "Cluj-Napoca",
                        "Plopilor",
                        "15A");
//        customer3.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        this.customerFileRepository.save(customer3);

        this.customerFileRepository.delete(customer3.getId());

        assertFalse(this.customerFileRepository.findOne(customer3.getId()).isPresent());

    }

}