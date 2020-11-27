package repository.xmlFileRepositories;

import model.Customer;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CustomerXmlRepository extends XmlRepository<Long, Customer> {

    private String fileName;

    public CustomerXmlRepository(Validator<Customer> validator, String fileName) {

        super(validator, fileName);
        this.fileName = fileName;

    }

    @Override
    protected Customer buildEntityFromElement(Element currentElement) {

        String city = currentElement.getAttribute("bikeType");
        Customer customer = new Customer();
        customer.setCity(city);

        String customerId = getTextFromTagName(currentElement, "id");
        customer.setId(Long.parseLong(customerId));

        String firstName = getTextFromTagName(currentElement, "firstName");
        customer.setFirstName(firstName);

        String lastName = getTextFromTagName(currentElement, "lastName");
        customer.setLastName(lastName);

        String phone = getTextFromTagName(currentElement, "phone");
        customer.setPhone(phone);

        String street = getTextFromTagName(currentElement, "street");
        customer.setStreet(street);

        String number = getTextFromTagName(currentElement, "number");
        customer.setNumber(number);

        return customer;
    }

    @Override
    protected Node createNodeFromEntity(Customer customer, Document document) {

        Element customerElement = document.createElement("customer");
        customerElement.setAttribute("city", customer.getCity());

        addChildWithTextContent(document, customerElement, "id", String.valueOf(customer.getId()));
        addChildWithTextContent(document, customerElement, "firstName", customer.getFirstName());
        addChildWithTextContent(document, customerElement, "lastName", customer.getLastName());
        addChildWithTextContent(document, customerElement, "phone", customer.getPhone());
        addChildWithTextContent(document, customerElement, "street", customer.getStreet());
        addChildWithTextContent(document, customerElement, "number", customer.getNumber());

        return customerElement;
    }
}
