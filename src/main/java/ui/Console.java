package ui;

import model.Bike;
import model.BikeType;
import model.Customer;
import model.Sale;
import model.validators.BikeShopException;
import service.BikeService;
import service.CustomerService;
import service.SaleService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

public class Console {

    private BikeService bikeService;
    private CustomerService customerService;
    private SaleService saleService;

    private Scanner in = new Scanner(System.in);

    public Console(BikeService bikeService, CustomerService customerService, SaleService saleService) {

        this.bikeService = bikeService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    private void showMainMenu() {

        System.out.println("1. Bikes");
        System.out.println("2. Customers");
        System.out.println("3. Sales");
        System.out.println("x. Exit");
        System.out.println("Option: ");
    }

    private void showBikesMenu() {

        System.out.println("1. Add bike");
        System.out.println("2. Print all bikes");
        System.out.println("3. Search bike by id");
        System.out.println("4. Delete bike");
        System.out.println("5. Update bike");
        System.out.println("6. Show the most expensive bike");
        System.out.println("7. Show bikes based on categories");
        System.out.println("8. Search bike by category");
        System.out.println("9. Return to the main menu");
        //System.out.println("x. Exit");
        System.out.println("Option: ");

    }

    private void showCustomersMenu() {

        System.out.println("1. Add customer");
        System.out.println("2. Print all customers");
        System.out.println("3. Search customer by id");
        System.out.println("4. Delete customer");
        System.out.println("5. Update customer");
        System.out.println("6. Show customers from a specific city");
        System.out.println("7. Return to the main menu");
        //System.out.println("x. Exit");
        System.out.println("Option: ");

    }

    private void showSalesMenu() {

        System.out.println("1. Add sale");
        System.out.println("2. Print all sales");
        System.out.println("3. Search sale by id");
        System.out.println("4. Delete sale");
        System.out.println("5. Update sale");
        System.out.println("6. Return to the main menu");
        //System.out.println("x. Exit");
        System.out.println("Option: ");

    }

    private void bikes_menu() {

        while (true) {

            this.showBikesMenu();

            String option = in.nextLine();
            switch (option) {
                case "1":
                    this.handleAddBike();
                    break;
                case "2":
                    this.handleShowAllBikes();
                    break;
                case "3":
                    this.handleSearchBikeById();
                    break;
                case "4":
                    this.handleDeleteBike();
                    break;
                case "5":
                    this.handleUpdateBike();
                    break;
                case "6":
                    this.handleShowBikeWithMaxPrice();
                    break;
                case "7":
                    this.handleShowBikesGroupedByCategory();
                    break;
                case "8":
                    this.handleSearchBikeByCategory();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid order");
                    break;
            }
        }
    }

    private void customers_menu() {

        while (true) {

            this.showCustomersMenu();

            String option = in.nextLine();
            switch (option) {

                case "1":
                    this.handleAddCustomer();
                    break;
                case "2":
                    this.handleShowAllCustomers();
                    break;
                case "3":
                    this.handleSearchCustomerById();
                    break;
                case "4":
                    this.handleDeleteCustomer();
                    break;
                case "5":
                    this.handleUpdateCustomer();
                    break;
                case "6":
                    this.handleShowCustomersFromASpecificCity();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid order");
                    break;
            }
        }
    }

    private void sales_menu() {

        while (true) {

            this.showSalesMenu();

            String option = in.nextLine();
            switch (option) {

                case "1":
                    this.handleAddSale();
                    break;
                case "2":
                    this.handleShowAllSales();
                    break;
                case "3":
                    this.handleSearchSaleById();
                    break;
                case "4":
                    this.handleDeleteSale();
                    break;
                case "5":
                    this.handleUpdateSale();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid order");
                    break;
            }
        }
    }

    public void runUserInterface() {

        while (true) {

            showMainMenu();

            String option = in.nextLine();
            switch (option) {
                case "1":
                    this.bikes_menu();
                    break;
                case "2":
                    this.customers_menu();
                    break;
                case "3":
                    this.sales_menu();
                    break;
                case "x":
                    return;
                default:
                    System.out.println("Invalid order");
                    break;
            }
        }
    }

    private void handleUpdateSale() {

        try {

            System.out.println("Id: ");
            Long s_id = in.nextLong();

            System.out.println("Bike id: ");
            Long b_id = in.nextLong();
            in.nextLine();

            System.out.println("Customer id: ");
            Long c_id = in.nextLong();
            in.nextLine();

            System.out.println("Date of the sale: ");
            LocalDate saleDate = LocalDate.parse(in.nextLine());

            Optional<Sale> sale = this.saleService.updateSaleService(s_id, b_id, c_id, saleDate);

            String result = sale.isPresent() ? "Sale with id = " + s_id + " updated successfully!" : "Nothing to update";

            System.out.println(result);

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleDeleteSale() {

        try {

            System.out.println("Id of the sale you want to delete: ");
            Long s_id = in.nextLong();
            in.nextLine();

            this.saleService.deleteSaleService(s_id);

            System.out.println("Sale with id = " + s_id + " deleted successfully!");

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleAddSale() {

//        System.out.println("Id: ");
//        Long s_id = in.nextLong();
//        in.nextLine();
        try {

            System.out.println("Bike id: ");
            Long b_id = in.nextLong();
            in.nextLine();

            System.out.println("Customer id: ");
            Long c_id = in.nextLong();
            in.nextLine();

            System.out.println("Date of the sale: ");
            LocalDate saleDate = LocalDate.parse(in.nextLine());

            this.saleService.addSaleService(b_id, c_id, saleDate);

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleShowAllSales() {

        StreamSupport.stream(this.saleService.getAll().spliterator(), false)
                .forEach(System.out::println);
    }

    private void handleSearchSaleById() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            this.saleService.findOneSale(id).ifPresent(sale -> System.out.println(sale.toString()));

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleSearchCustomerById() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            this.customerService.findOneCustomer(id).ifPresent(customer -> System.out.println(customer.toString()));

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }

    }

    private void handleSearchBikeById() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            this.bikeService.findOneBike(id).ifPresent(bike -> System.out.println(bike.toString()));

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }

    }

    private void handleUpdateCustomer() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("First name: ");
            String firstName = in.nextLine();

            System.out.println("Last name: ");
            String lastName = in.nextLine();

            System.out.println("Phone: ");
            String phone = in.nextLine();

            System.out.println("City: ");
            String city = in.nextLine();

            System.out.println("Street: ");
            String street = in.nextLine();

            System.out.println("Number: ");
            String number = in.nextLine();

            Optional<Customer> customer = this.customerService.updateCustomerService(
                    id,
                    firstName,
                    lastName,
                    phone,
                    city,
                    street,
                    number);

            String result = customer.isPresent() ? "Customer with id " + id + " updated successfully!" : "Nothing to update";
            System.out.println(result);

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }

    }

    private void handleUpdateBike() {

        try {

            System.out.println("Id :");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("Name: ");
            String name = in.nextLine();

            System.out.println("Bike type: ");
            BikeType type = BikeType.valueOf(in.nextLine().toUpperCase());

            System.out.println("Price: ");
            double price = in.nextDouble();
            in.nextLine();

            Optional<Bike> bike = this.bikeService.updateBikeService(id, name, type, price);

            String result = bike.isPresent() ? "Bike with id " + id + " updated successfully!" : "Nothing to update";
            System.out.println(result);

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleDeleteCustomer() {

        try {

            System.out.println("Enter the id of the customer you want to delete: ");

            Long id = in.nextLong();
            in.nextLine();

            this.customerService.deleteCustomerService(id);

            System.out.println("Customer with id = " + id + " deleted successfully!");

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleDeleteBike() {

        try {

            System.out.println("Enter the id of the bike you want to delete: ");

            Long id = in.nextLong();
            in.nextLine();

            this.bikeService.deleteBikeService(id);

            System.out.println("Bike with id " + id + " deleted successfully!");

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }

    private void handleSearchBikeByCategory() {

        try {

            System.out.println("Enter the category: ");
            String category = in.nextLine();

            List<Bike> bikes = this.bikeService.searchBikeByCategory(category);

            bikes.stream()
                    .forEach(System.out::println);

        } catch (BikeShopException e) {

            System.out.println(e.getMessage());

        }
    }

    private void handleShowCustomersFromASpecificCity() {

        System.out.println("Please enter city: ");
        String city = in.nextLine();

        List<Customer> customers = this.customerService.showCustomersFromASpecificCity(city);

        customers.stream()
                .forEach(System.out::println);
    }

    private void handleShowBikesGroupedByCategory() {

        Map<BikeType, List<Bike>> bikeTypeListMap = this.bikeService.showBikesGroupedByCategory();

        bikeTypeListMap.forEach((type, bikes) -> {
            System.out.println(type);
            bikes.forEach(System.out::println);
            System.out.println();
        });
    }

    private void handleShowBikeWithMaxPrice() {

        List<Bike> bikes = this.bikeService.showBikeWithMaxPrice();
        bikes.stream()
                .forEach(System.out::println);
    }

    private void handleShowAllBikes() {

        try {

            StreamSupport.stream(this.bikeService.getAll().spliterator(), false)
                    .forEach(System.out::println);

        } catch (BikeShopException bex) {

            System.out.println(bex.getMessage());

        }
    }

    private void handleAddBike() {

        try {

//            System.out.println("Id: ");
//            Long id = in.nextLong();
//            in.nextLine();

            System.out.println("Name: ");
            String name = in.nextLine();

            System.out.println("Bike type: ");

            String type = in.nextLine();
            boolean anyMatch = Arrays.stream(BikeType.values())
                    .anyMatch(bikeType -> bikeType.getBikeType().equalsIgnoreCase(type.toUpperCase()));

            if(!anyMatch) {

                System.out.println("The category does not match");
                return;
            }

            BikeType bikeType = BikeType.valueOf(type.toUpperCase());

            System.out.println("Price: ");
            double price = in.nextDouble();
            in.nextLine();

            this.bikeService.addBikeService(
                    name,
                    bikeType,
                    price
            );

        } catch (BikeShopException bikeShopException) {
            System.out.println(bikeShopException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unknown error");
        }
    }

    private void handleShowAllCustomers() {

//        for (Customer customer : this.customerService.getAll()) {
//            System.out.println(customer.toString());
//        }
        StreamSupport.stream(this.customerService.getAll().spliterator(), false)
                .forEach(customer -> System.out.println(customer));  // can be replaced with method reference

    }

    private void handleAddCustomer() {

        try {

//            System.out.println("Id: ");
//            Long id = in.nextLong();
//            in.nextLine();

            System.out.println("First name: ");
            String firstName = in.nextLine();

            System.out.println("Last name: ");
            String lastName = in.nextLine();

            System.out.println("Phone: ");
            String phone = in.nextLine();

            System.out.println("City: ");
            String city = in.nextLine();

            System.out.println("Street: ");
            String street = in.nextLine();

            System.out.println("Number: ");
            String number = in.nextLine();

            this.customerService.addCustomerService(
                    firstName,
                    lastName,
                    phone,
                    city,
                    street,
                    number
            );
        } catch (BikeShopException bikeShopException) {
            System.out.println(bikeShopException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unknown error");
        }
    }
}
