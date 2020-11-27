package service;

import model.Customer;
import model.validators.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerServiceTest {

    private IRepository<Long, Customer> customerRepository;
    private CustomerValidator customerValidator;
    private CustomerService customerService;

    @BeforeEach
    public void setup() {

        this.customerValidator = new CustomerValidator();
        this.customerRepository = new InMemoryRepository<>(customerValidator);

        this.customerService = new CustomerService(customerRepository, customerValidator);
    }

    @Test
    void addingAValidCustomer_should_saveThatCustomerToRepository() {

        Customer customer = new Customer(1l, "firstName1", "lastName1", "0744556688", "Cluj-Napoca", "Florilor", "1A");
        customerService.addCustomerService(customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getCity(), customer.getStreet(), customer.getNumber());

        assertEquals(1, StreamSupport.stream(customerRepository.findAll().spliterator(), false).count());
    }

    @Test
    void getAll() {

        Customer customer = new Customer(1l, "firstName1", "lastName1", "0744556688", "Cluj-Napoca", "Florilor", "1A");
        customerService.addCustomerService(customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getCity(), customer.getStreet(), customer.getNumber());

        Iterable<Customer> customers = customerService.getAll();
        assertEquals(1, StreamSupport.stream(customers.spliterator(), false).count());
    }

    @Test
    void gettingAllTheCustomers_should_showCustomersFromASpecificCity() {

        String city = "Cluj-Napoca";
        Customer customer1 = new Customer(1l, "firstName1", "lastName1", "0744556688", "Cluj-Napoca", "Florilor", "1A");
        Customer customer2 = new Customer(2l, "firstName2", "lastName2", "0744556688", "Oradea", "Florilor", "2A");
        Customer customer3 = new Customer(3l, "firstName3", "lastName3", "0744556688", "Cluj-Napoca", "Florilor", "3A");

        customerService.addCustomerService(customer1.getFirstName(), customer1.getLastName(), customer1.getPhone(), customer1.getCity(), customer1.getStreet(), customer1.getNumber());
        customerService.addCustomerService(customer2.getFirstName(), customer2.getLastName(), customer2.getPhone(), customer2.getCity(), customer2.getStreet(), customer2.getNumber());
        customerService.addCustomerService(customer3.getFirstName(), customer3.getLastName(), customer3.getPhone(), customer3.getCity(), customer3.getStreet(), customer3.getNumber());

        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(customer1);
        expectedCustomers.add(customer3);

        assertEquals(2, customerService.showCustomersFromASpecificCity(city).size());
    }

}