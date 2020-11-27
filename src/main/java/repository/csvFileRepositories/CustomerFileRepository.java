package repository.csvFileRepositories;

import model.Customer;
import model.validators.Validator;
import model.validators.ValidatorException;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CustomerFileRepository extends InMemoryRepository<Long, Customer> {

    private String fileName;

    public CustomerFileRepository(Validator<Customer> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));

                String firstName = items.get((1));

                String lastName = items.get((2));

                String phone = items.get((3));

                String city = items.get((4));

                String street = items.get((5));

                String number = items.get((6));

                Customer customer = new Customer(id, firstName, lastName, phone, city, street, number);
                customer.setId(id);

                try {

                    super.save(customer);

                } catch (ValidatorException e) {

                    e.printStackTrace();
                }
            });

        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> save(Customer customer) {

        Optional<Customer> optional = super.save(customer);
        if (optional.isPresent()) {

            return optional;
        }

        saveToFile(customer);
        return Optional.empty();
    }

    private void saveToFile(Customer customer) {

        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

            bufferedWriter.write(
                    customer.getId() + "," + customer.getFirstName() + "," + customer.getLastName() + "," +
                            customer.getPhone() + "," + customer.getCity() + "," + customer.getStreet() + "," + customer.getNumber()
            );

            bufferedWriter.newLine();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> delete(Long id) {

        Optional<Customer> customerToBeDeleted = super.findOne(id);

        super.delete(id);

        Iterable<Customer> all = super.findAll();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

            for (Customer customer : all) {

                this.saveToFile(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customerToBeDeleted;
    }

    @Override
    public Optional<Customer> update(Customer customer) {

        Optional<Customer> customerToUpdate = super.update(customer);

        Iterable<Customer> all = super.findAll();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

            all.forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return customerToUpdate;

    }
}
