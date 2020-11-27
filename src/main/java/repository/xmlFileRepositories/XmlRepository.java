package repository.xmlFileRepositories;

import model.Entity;
import model.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository.InMemoryRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class XmlRepository<ID, T extends Entity<ID>> extends InMemoryRepository<ID, T> {

    private String fileName;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    public XmlRepository(Validator<T> validator, String fileName) {

        super(validator);
        this.fileName = fileName;

        documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            document = documentBuilder.parse(fileName);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadEntitiesFromXML();
    }

    protected List<T> loadEntitiesFromXML() {

        List<T> entitiesList = new ArrayList<>();

        // <bikeShop> is the root tag
        Element root = document.getDocumentElement();

        // iterate <bike>/<customer> elements
        NodeList entityNodes = root.getChildNodes();
        for (int i = 0; i < entityNodes.getLength(); i++) {

            Node entityNode = entityNodes.item(i);
            if (!(entityNode instanceof Element)) {
                continue;
            }

            Element currentElement = (Element) entityNode;
            T entity = buildEntityFromElement(currentElement);

            super.save(entity);

            entitiesList.add(entity);
        }

        return entitiesList;
    }

    protected abstract T buildEntityFromElement(Element currentElement);

    protected String getTextFromTagName(Element element, String tagName) {

        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    @Override
    public Optional<T> save(T entity) {

        Optional<T> optional = super.save(entity);
        if (optional.isPresent()) {

            return optional;
        }

        saveToXml(entity);

        return Optional.empty();
    }

    private void saveToXml(T entity) {

        addEntityToDom(entity, document);

        writeToXml();

    }

    protected void addEntityToDom(T entity, Document document) {

        Element root = document.getDocumentElement();
        Node bikeNode = createNodeFromEntity(entity, document);

        root.appendChild(bikeNode);
    }

    protected abstract Node createNodeFromEntity(T entity, Document document);

    protected void addChildWithTextContent(Document document, Element parent, String tagName, String textContent) {

        Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }

    @Override
    public Optional<T> delete(ID id) {

        Optional<T> entityToBeDeleted = super.findOne(id);

        Element root = document.getDocumentElement();

        // iterate <entity> elements
        NodeList entityNodes = root.getChildNodes();
        for (int i = 0; i < entityNodes.getLength(); i++) {

            Node entityNode = entityNodes.item(i);
            if (!(entityNode instanceof Element)) {
                continue;
            }

            Element currentElement = (Element) entityNode;
            T entity = buildEntityFromElement(currentElement);
            if (entity.getId() == id) {

                super.delete(id);
                currentElement.getParentNode().removeChild(currentElement);

            }
        }
        writeToXml();

        return entityToBeDeleted;
    }

    @Override
    public Optional<T> update(T entity) {

        Optional<T> newEntity = super.update(entity);

        delete(entity.getId());
        newEntity.ifPresent(this::save);

        writeToXml();

        return newEntity;
    }

    private void writeToXml() {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        try {
            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(fileName))
            );
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
