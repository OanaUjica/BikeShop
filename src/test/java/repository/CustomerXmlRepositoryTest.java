package repository;

import model.Customer;
import model.validators.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.xmlFileRepositories.CustomerXmlRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CustomerXmlRepositoryTest {

    private CustomerValidator customerValidator;
    private IRepository<Long, Customer> customerRepository;

    @BeforeEach
    public void setup() {

        this.customerValidator = new CustomerValidator();
        this.customerRepository = new CustomerXmlRepository(customerValidator, "src/test/resources/customers_test.xml");
    }

    @Test
    void save() {

        Customer customer2 = new Customer(
                2l,
                "firstName2",
                "lastName2",
                "9911228732",
                "Sibiu",
                "Florilor",
                "10B");

        this.customerRepository.save(customer2);

        assertEquals(customer2, this.customerRepository.findOne(customer2.getId()).get());
    }

    @Test
    void delete() {

        Customer customer3 = new Customer(
                3l,
                "firstName3",
                "lastName3",
                "9911298732",
                "Brasov",
                "Florilor",
                "10B");
        this.customerRepository.save(customer3);

        this.customerRepository.delete(customer3.getId());

        assertFalse(this.customerRepository.findOne(customer3.getId()).isPresent());
    }

    @Test
    void update() {

        Customer customer2 = new Customer(
                2l,
                "firstName2",
                "lastName2",
                "9911228732",
                "Sibiu",
                "Lalelelor",
                "10B");
        this.customerRepository.update(customer2);

        assertEquals("Lalelelor", this.customerRepository.findOne(customer2.getId()).get().getStreet());
    }
}