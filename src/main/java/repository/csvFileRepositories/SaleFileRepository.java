package repository.csvFileRepositories;

import model.Bike;
import model.BikeType;
import model.Customer;
import model.Sale;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SaleFileRepository extends InMemoryRepository<Long, Sale> {

    private String fileName;

    public SaleFileRepository(Validator<Sale> validator, String fileName) {

        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {

        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {

                List<String> items = Arrays.asList(line.split(","));

                Long s_id = Long.valueOf(items.get(0));

                Long b_id = Long.valueOf(items.get(1));
                String name = items.get((2));
                BikeType type = BikeType.valueOf(items.get(3));
                double price = Double.valueOf(items.get(4));

                Bike bike = new Bike(b_id, name, type, price);
                bike.setId(b_id);

                Long c_id = Long.valueOf(items.get(5));
                String firstName = items.get((6));
                String lastName = items.get((7));
                String phone = items.get((8));
                String city = items.get((9));
                String street = items.get((10));
                String number = items.get((11));

                Customer customer = new Customer(c_id, firstName, lastName, phone, city, street, number);
                customer.setId(c_id);

                LocalDate saleDate = LocalDate.parse(items.get(12));

                Sale sale = new Sale(s_id, bike, customer, saleDate);

                try {

                    super.save(sale);

                } catch (ValidatorException e) {

                    e.printStackTrace();
                }
            });

        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Sale> save(Sale sale) {

        Optional<Sale> optional = super.save(sale);
        if (optional.isPresent()) {

            return optional;
        }

        saveToFile(sale);
        return Optional.empty();
    }

    private void saveToFile(Sale sale) {

        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

            bufferedWriter.write(
                    sale.getId() + "," + sale.getBike().getId() + "," + sale.getBike().getName() + ","
                    + sale.getBike().getType() + "," + sale.getBike().getPrice() + "," + sale.getCustomer().getId() + ","
                    + sale.getCustomer().getFirstName() + "," + sale.getCustomer().getLastName() + ","
                    + sale.getCustomer().getPhone() + "," + sale.getCustomer().getCity() + "," + sale.getCustomer().getStreet()
                    + "," + sale.getCustomer().getNumber() + "," + sale.getSaleDate());

            bufferedWriter.newLine();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public Optional<Sale> delete(Long id) {

        Optional<Sale> saleToBeDeleted = super.findOne(id);

        super.delete(id);

        Iterable<Sale> all = super.findAll();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

            all.forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return saleToBeDeleted;
    }

    @Override
    public Optional<Sale> update(Sale sale) {

        Optional<Sale> bikeToUpdate = super.update(sale);

        Iterable<Sale> all = super.findAll();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

            all.forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bikeToUpdate;

    }
}
