package repository.xmlFileRepositories;

import model.Bike;
import model.BikeType;
import model.Customer;
import model.Sale;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.time.LocalDate;

public class SaleXmlRepository extends XmlRepository<Long, Sale> {

    private String filename;

    public SaleXmlRepository(Validator<Sale> saleValidator, String fileName) {

        super(saleValidator, fileName);
        this.filename = filename;

    }

    @Override
    protected Sale buildEntityFromElement(Element currentElement) {

        String s_id = getTextFromTagName(currentElement, "s_id");

        Bike bike = new Bike();

        String b_id = getTextFromTagName(currentElement, "b_id");
        bike.setId(Long.parseLong(b_id));

        String name = getTextFromTagName(currentElement, "name");
        bike.setName(name);

        String bikeType = getTextFromTagName(currentElement, "bikeType");
        bike.setType(BikeType.valueOf(bikeType.toUpperCase()));

        String price = getTextFromTagName(currentElement, "price");
        bike.setPrice(Double.parseDouble(price));

        Customer customer = new Customer();

        String c_id = getTextFromTagName(currentElement, "c_id");
        customer.setId(Long.parseLong(c_id));

        String firstName = getTextFromTagName(currentElement, "firstName");
        customer.setFirstName(firstName);

        String lastName = getTextFromTagName(currentElement, "lastName");
        customer.setLastName(lastName);

        String phone = getTextFromTagName(currentElement, "phone");
        customer.setPhone(phone);

        String city = getTextFromTagName(currentElement, "city");
        customer.setCity(city);

        String street = getTextFromTagName(currentElement, "street");
        customer.setStreet(street);

        String number = getTextFromTagName(currentElement, "number");
        customer.setNumber(number);

        String saleDate = getTextFromTagName(currentElement, "saleDate");

        Sale sale = new Sale();
        sale.setId(Long.parseLong(s_id));
        sale.setBike(bike);
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.parse(saleDate));

        return sale;
    }

    @Override
    protected Node createNodeFromEntity(Sale sale, Document document) {

        Element saleElement = document.createElement("sale");
        addChildWithTextContent(document, saleElement, "s_id", String.valueOf(sale.getId()));

        Element bikeElement = document.createElement("bike");
        addChildWithTextContent(document, bikeElement, "b_id", String.valueOf(sale.getBike().getId()));
        addChildWithTextContent(document, bikeElement, "name", sale.getBike().getName());
        addChildWithTextContent(document, bikeElement, "bikeType", sale.getBike().getType().getBikeType());
        addChildWithTextContent(document, bikeElement, "price", String.valueOf(sale.getBike().getPrice()));

        saleElement.appendChild(bikeElement);

        Element customerElement = document.createElement("customer");
        addChildWithTextContent(document, customerElement, "c_id", String.valueOf(sale.getCustomer().getId()));
        addChildWithTextContent(document, customerElement, "firstName", sale.getCustomer().getFirstName());
        addChildWithTextContent(document, customerElement, "lastName", sale.getCustomer().getLastName());
        addChildWithTextContent(document, customerElement, "phone", sale.getCustomer().getPhone());
        addChildWithTextContent(document, customerElement, "city", sale.getCustomer().getCity());
        addChildWithTextContent(document, customerElement, "street", sale.getCustomer().getStreet());
        addChildWithTextContent(document, customerElement, "number", sale.getCustomer().getNumber());

        saleElement.appendChild(customerElement);

        addChildWithTextContent(document, saleElement, "saleDate", sale.getSaleDate().toString());

        return saleElement;
    }
}
