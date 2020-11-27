import model.Bike;
import model.Customer;
import model.Sale;
import model.validators.BikeValidator;
import model.validators.CustomerValidator;
import model.validators.SaleValidator;
import model.validators.Validator;
import repository.*;
import repository.csvFileRepositories.BikeFileRepository;
import repository.csvFileRepositories.CustomerFileRepository;
import repository.csvFileRepositories.SaleFileRepository;
import repository.dbFileRepositories.BikeDbRepository;
import repository.dbFileRepositories.CustomerDbRepository;
import repository.dbFileRepositories.SaleDbRepository;
import repository.xmlFileRepositories.BikeXmlRepository;
import repository.xmlFileRepositories.CustomerXmlRepository;
import repository.xmlFileRepositories.SaleXmlRepository;
import repository.xmlFileRepositories.XmlRepository;
import service.BikeService;
import service.CustomerService;
import service.SaleService;
import ui.Console;

public class Main {

    public static void main(String[] args) {

        Validator<Bike> bikeValidator = new BikeValidator();
        Validator<Customer> customerValidator = new CustomerValidator();
        Validator<Sale> saleValidator = new SaleValidator();

        IRepository<Long, Bike> bikeRepository = new InMemoryRepository<>(bikeValidator);
        IRepository<Long, Customer> customerRepository = new InMemoryRepository<>(customerValidator);
        IRepository<Long, Sale> saleRepository = new InMemoryRepository<>(saleValidator);

        BikeFileRepository bikeFileRepository = new BikeFileRepository(bikeValidator, "data/bikes.csv");
        CustomerFileRepository customerFileRepository = new CustomerFileRepository(customerValidator, "data/customers.csv");
        SaleFileRepository saleFileRepository = new SaleFileRepository(saleValidator, "data/sales.csv");

        XmlRepository<Long, Bike> bikeXmlRepository = new BikeXmlRepository(bikeValidator, "data/bikeShop_bike.xml");
        XmlRepository<Long, Customer> customerXmlRepository = new CustomerXmlRepository(customerValidator, "data/bikeShop_customer.xml");
        XmlRepository<Long, Sale> saleXmlRepository = new SaleXmlRepository(saleValidator, "data/bikeShop_sale.xml");

        IRepository<Long, Bike> bikeDbRepository = new BikeDbRepository(bikeValidator);
        IRepository<Long, Customer> customerDbRepository = new CustomerDbRepository(customerValidator);
        IRepository<Long, Sale> saleDbRepository = new SaleDbRepository(saleValidator);

        BikeService bikeService = new BikeService(bikeDbRepository, bikeValidator);
        CustomerService customerService = new CustomerService(customerDbRepository, customerValidator);
        SaleService saleService = new SaleService(saleDbRepository, bikeDbRepository, customerDbRepository, saleValidator);

//        bikeService.addBikeService(1l, "bike1", BikeType.CITYBIKE, 2500.00);
//        bikeService.addBikeService(2l, "bike2", BikeType.MOUNTAINBIKE, 3600.00);
//        bikeService.addBikeService("bike3", BikeType.ELECTRICBIKE, 5320.00);
//        bikeService.addBikeService("bike4", BikeType.MOUNTAINBIKE, 2500.00);
//        bikeService.addBikeService("bike5", BikeType.MOUNTAINBIKE, 3600.00);
//        bikeService.addBikeService("bike6", BikeType.ELECTRICBIKE, 5320.00);
//
//        customerService.addCustomerService(1l, "firstName1", "lastName1", "0722334455", "Cluj-Napoca", "Florilor", "1A");
//        customerService.addCustomerService(2l, "firstName2", "lastName2", "0744659821", "Cluj-Napoca", "Florilor", "1A");
//        customerService.addCustomerService(3l,"firstName3", "lastName3", "0730638469", "Cluj-Napoca", "Florilor", "1A");
//        customerService.addCustomerService(4l, "firstName4", "lastName4", "0730623469", "Sibiu", "Florilor", "1A");
//        customerService.addCustomerService(5l, "firstName5", "lastName5", "0730638009", "Sibiu", "Florilor", "1A");

        Console console = new Console(bikeService, customerService, saleService);
        console.runUserInterface();
    }
}
