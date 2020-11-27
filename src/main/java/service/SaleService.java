package service;

import model.Bike;
import model.Customer;
import model.Sale;
import model.validators.Validator;
import repository.IRepository;

import java.time.LocalDate;
import java.util.Optional;

public class SaleService {

    private IRepository<Long, Sale> saleRepository;
    private IRepository<Long, Bike> bikeRepository;
    private IRepository<Long, Customer> customerRepository;

    private Validator<Sale> saleValidator;

    public SaleService(IRepository<Long, Sale> saleRepository, IRepository<Long, Bike> bikeRepository, IRepository<Long, Customer> customerRepository, Validator<Sale> saleValidator) {

        this.saleRepository = saleRepository;
        this.bikeRepository = bikeRepository;
        this.customerRepository = customerRepository;
        this.saleValidator = saleValidator;
    }

    /**
     * Adds a sale to the repository
     * @param b_id the given id of the bike
     * @param c_id the given id of the customer
     * @param saleDate the given date of the sale
     */
    public void addSaleService(Long b_id, Long c_id, LocalDate saleDate) {

        Bike bike = null;
        Optional<Bike> bikeOptional = this.bikeRepository.findOne(b_id);
        if(bikeOptional.isPresent()) {

            bike = bikeOptional.get();
        }

        Customer customer = null;
        Optional<Customer> customerOptional = this.customerRepository.findOne(c_id);
        if(customerOptional.isPresent()) {

            customer = customerOptional.get();
        }

        Sale sale = new Sale(bike, customer, saleDate);
        this.saleValidator.validate(sale);

        this.saleRepository.save(sale);
    }

    /**
     * Gets a sale
     * @param id the given id of the sale
     * @return the sale
     */
    public Optional<Sale> findOneSale(Long id) {

        return this.saleRepository.findOne(id);
    }

    /**
     * Gets the sales
     * @return the sales
     */
    public Iterable<Sale> getAll() {

        return this.saleRepository.findAll();
    }

    /**
     * Delete a sale
     * @param s_id the given id of the sale
     * @return the deleted sale
     */
    public Optional<Sale> deleteSaleService(Long s_id) {

        return this.saleRepository.delete(s_id);
    }

    /**
     * Updates a sale
     * @param s_id the given id of the sale
     * @param b_id the given id of the bike
     * @param c_id the given id of the customer
     * @param saleDate the given date of the sale
     * @return the updated sale
     */
    public Optional<Sale> updateSaleService(Long s_id, Long b_id, Long c_id, LocalDate saleDate) {

        Bike bike = null;
        Optional<Bike> bikeOptional = this.bikeRepository.findOne(b_id);
        if(bikeOptional.isPresent()) {

            bike = bikeOptional.get();
        }

        Customer customer = null;
        Optional<Customer> customerOptional = this.customerRepository.findOne(c_id);
        if(customerOptional.isPresent()) {

            customer = customerOptional.get();
        }

        Sale newSale = new Sale(bike, customer, saleDate);
        this.saleValidator.validate(newSale);

        Sale existingSale = null;
        Optional<Sale> saleOptional = this.saleRepository.findOne(s_id);
        if(saleOptional.isPresent()) {

            existingSale = saleOptional.get();
        }

        if(newSale.equals(existingSale)) {

            return Optional.empty();

        } else {

            return this.saleRepository.update(newSale);
        }
    }
}
