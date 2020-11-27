package repository.csvFileRepositories;

import model.Bike;
import model.BikeType;
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

public class BikeFileRepository extends InMemoryRepository<Long, Bike> {

    private String fileName;

    public BikeFileRepository(Validator<Bike> validator, String fileName) {
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

                String name = items.get((1));

                BikeType type = BikeType.valueOf(items.get(2));

                double price = Double.valueOf(items.get(3));

                Bike bike = new Bike(id, name, type, price);
                bike.setId(id);

                try {

                    super.save(bike);

                } catch (ValidatorException e) {

                    e.printStackTrace();
                }
            });

        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Bike> save(Bike bike) {

        Optional<Bike> optional = super.save(bike);
        if (optional.isPresent()) {

            return optional;
        }

        saveToFile(bike);
        return Optional.empty();
    }

    private void saveToFile(Bike bike) {

        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

            bufferedWriter.write(
                    bike.getId() + "," + bike.getName() + "," + bike.getType() + "," + bike.getPrice());

            bufferedWriter.newLine();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public Optional<Bike> delete(Long id) {

        Optional<Bike> bikeToBeDeleted = super.findOne(id);

        super.delete(id);

        Iterable<Bike> all = super.findAll();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

            all.forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bikeToBeDeleted;
    }

    @Override
    public Optional<Bike> update(Bike bike) {

        Optional<Bike> bikeToUpdate = super.update(bike);

        Iterable<Bike> all = super.findAll();

        try {
            FileWriter fileWriter = new FileWriter(fileName, false);

            all.forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bikeToUpdate;

    }
}
