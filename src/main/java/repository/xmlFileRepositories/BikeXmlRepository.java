package repository.xmlFileRepositories;

import model.Bike;
import model.BikeType;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BikeXmlRepository extends XmlRepository<Long, Bike> {

    private String fileName;

    public BikeXmlRepository(Validator<Bike> validator, String fileName) {

        super(validator, fileName);
        this.fileName = fileName;

    }

    @Override
    protected Bike buildEntityFromElement(Element currentElement) {

        String bikeType = currentElement.getAttribute("bikeType");
        Bike bike = new Bike();
        bike.setType(BikeType.valueOf(bikeType.toUpperCase()));

        String bikeId = getTextFromTagName(currentElement, "id");
        bike.setId(Long.parseLong(bikeId));

        String name = getTextFromTagName(currentElement, "name");
        bike.setName(name);

        String price = getTextFromTagName(currentElement, "price");
        bike.setPrice(Double.parseDouble(price));

        return bike;
    }

    @Override
    protected Node createNodeFromEntity(Bike bike, Document document) {

        Element bikeElement = document.createElement("bike");
        bikeElement.setAttribute("bikeType", bike.getType().getBikeType());

        addChildWithTextContent(document, bikeElement, "id", String.valueOf(bike.getId()));
        addChildWithTextContent(document, bikeElement, "name", bike.getName());
        addChildWithTextContent(document, bikeElement, "price", Double.toString(bike.getPrice()));

        return bikeElement;
    }
}

