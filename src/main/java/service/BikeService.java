package service;

import model.Bike;
import model.BikeType;
import model.validators.CategoryValidator;
import model.validators.Validator;
import repository.IRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BikeService {

    private IRepository<Long, Bike> bikeRepository;
    private Validator<Bike> bikeValidator;

    public BikeService(IRepository<Long, Bike> bikeRepository, Validator<Bike> bikeValidator) {
        this.bikeRepository = bikeRepository;
        this.bikeValidator = bikeValidator;
    }

    /**
     * Adds a bike to the repository
     *
     * @param name     the given name of the bike
     * @param bikeType the given bikeType of the bike
     * @param price    the given price of the bike
     */
    public void addBikeService(String name, BikeType bikeType, double price) {

        Bike bike = new Bike(name, bikeType, price);

        this.bikeValidator.validate(bike);

        //bike.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);

        this.bikeRepository.save(bike);
    }

    /**
     * Gets a bike from the repository
     * @param id the given id of the bike
     * @return the bike
     */
    public Optional<Bike> findOneBike(Long id) {

        Optional<Bike> bikeOptional = this.bikeRepository.findOne(id);

        if(bikeOptional.isEmpty()) {

            throw new IllegalArgumentException("The id does not exist");
        }

        return this.bikeRepository.findOne(id);
    }

    /**
     * Updates a bike
     * @param id the given id of the bike
     * @param name the given name of the bike
     * @param bikeType the given bikeType of the bike
     * @param price the given price of the bike
     * @return the updated bike
     */
    public Optional<Bike> updateBikeService(Long id, String name, BikeType bikeType, double price) {

        Optional<Bike> bikeOptional = this.bikeRepository.findOne(id);

        if(bikeOptional.isEmpty()) {

            throw new IllegalArgumentException("The id does not exist");
        }

        Bike newBike = new Bike(id, name, bikeType, price);
        this.bikeValidator.validate(newBike);

        Bike existingBike = bikeOptional.get();

        if (newBike.equals(existingBike)) {

            return Optional.empty();

        } else {

            return this.bikeRepository.update(newBike);

        }
    }

    /**
     * Gets all the bikes from the repository
     *
     * @return the bikes
     */
    public Iterable<Bike> getAll() {

        return this.bikeRepository.findAll();
    }

    /**
     * Deletes a bike from repository
     *
     * @param id
     * @return the deleted bike
     */
    public Optional<Bike> deleteBikeService(Long id) {

        Optional<Bike> bikeOptional = this.bikeRepository.findOne(id);

        if(bikeOptional.isEmpty()) {

            throw new IllegalArgumentException("The id does not exist");
        }

        return this.bikeRepository.delete(id);
    }

    /**
     * Gets the bikes with max price
     *
     * @return the bikes
     */
    public List<Bike> showBikeWithMaxPrice() {

        double max = StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .mapToDouble(Bike::getPrice)
                .max()
                .orElse(-1);

        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .filter(bike -> bike.getPrice() == max)
                .collect(Collectors.toList());

    }

    /**
     * Gets the bikes grouped by category
     *
     * @return all the categories with the corresponding bikes
     */
    public Map<BikeType, List<Bike>> showBikesGroupedByCategory() {

        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .collect(Collectors.groupingBy(Bike::getType));
    }

    /**
     * Gets the bikes in a certain category
     *
     * @param category the given category
     * @return the bikes
     */
    public List<Bike> searchBikeByCategory(String category) {

        CategoryValidator.validate(category);
        return StreamSupport.stream(bikeRepository.findAll().spliterator(), false)
                .filter(bike -> bike.getType().equals(BikeType.valueOf(category.toUpperCase())))
                .collect(Collectors.toList());

    }
}
