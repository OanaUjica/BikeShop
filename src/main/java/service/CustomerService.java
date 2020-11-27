package service;

import model.Bike;
import model.Customer;
import model.validators.Validator;
import repository.IRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomerService {

    private IRepository<Long, Customer> customerRepository;
    private Validator<Customer> customerValidator;

    public CustomerService(IRepository<Long, Customer> customerRepository, Validator<Customer> customerValidator) {

        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    /**
     * Adds a customer to the repository
     *
     * @param firstName the given firstName of the customer
     * @param lastName  the given lastName of the customer
     * @param phone     the given phone of the customer
     * @param city      the given city of the customer
     * @param street    the given street of the customer
     * @param number    the given number of the customer
     */
    public void addCustomerService(String firstName, String lastName, String phone, String city, String street, String number) {

        Customer customer = new Customer(firstName, lastName, phone, city, street, number);

        this.customerValidator.validate(customer);

//        customer.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        this.customerRepository.save(customer);
    }

    /**
     * Gets a customer from the repository
     * @param id the given id of the customer
     * @return the customer
     */
    public Optional<Customer> findOneCustomer(Long id) {

        Optional<Customer> customerOptional = this.customerRepository.findOne(id);

        if(customerOptional.isEmpty()) {

            throw new IllegalArgumentException("The id does not exist");
        }

        return this.customerRepository.findOne(id);
    }

    /**
     * Gets all the customers from the repository
     *
     * @return the customers
     */
    public Iterable<Customer> getAll() {

        return this.customerRepository.findAll();
    }

    /**
     * Updates a customer
     * @param id the given id if the customer
     * @param firstName the given firstName if the customer
     * @param lastName the given lastName if the customer
     * @param phone the given phone if the customer
     * @param city the given city if the customer
     * @param street the given street if the customer
     * @param number the given number if the customer
     * @return the updated customer
     */
    public Optional<Customer> updateCustomerService(Long id, String firstName, String lastName, String phone, String city, String street, String number) {

        Optional<Customer> customerOptional = this.customerRepository.findOne(id);

        if(customerOptional.isEmpty()) {

            throw new IllegalArgumentException("The id does not exist");
        }

        Customer newCustomer = new Customer(id, firstName, lastName, phone, city, street, number);
        this.customerValidator.validate(newCustomer);

        Customer existingCustomer = customerOptional.get();

        if (newCustomer.equals(existingCustomer)) {

            return Optional.empty();

        } else {

            return this.customerRepository.update(newCustomer);
        }
    }


    /**
     * Gets the customers in a specific city
     *
     * @param city the given city
     * @return the customers
     */
    public List<Customer> showCustomersFromASpecificCity(String city) {

        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(customer -> customer.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());

    }

    /**
     * Deletes a customer from repository
     *
     * @param id
     * @return the deleted customer
     */
    public Optional<Customer> deleteCustomerService(Long id) {

        Optional<Customer> customerOptional = this.customerRepository.findOne(id);

        if(customerOptional.isEmpty()) {

            throw new IllegalArgumentException("The id does not exist");
        }

        return this.customerRepository.delete(id);
    }
}
