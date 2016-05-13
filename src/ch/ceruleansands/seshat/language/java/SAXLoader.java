package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.GraphicData;
import ch.ceruleansands.seshat.loader.LanguageDiagramLoader;
import com.google.inject.Inject;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Load diagrams in the SAX format for the Java language.
 * @author Thomsch
 */
class SAXLoader implements LanguageDiagramLoader {

    private final DiagramBuilder diagramBuilder;

    @Inject
    public SAXLoader(DiagramBuilder diagramBuilder) {
        this.diagramBuilder = diagramBuilder;
    }

    @Override
    public Diagram loadFromBuffer(BufferedReader bufferedReader) throws IOException, SaveFormatException {
        JavaDiagram diagram = diagramBuilder.createEmpty();
        load(diagram, bufferedReader);
        bufferedReader.close();
        return diagram;
    }

    /**
     * Parses & loads the content of <code>bufferedReader</code> in <code>diagram</code>.
     * We are using an exact sequential approach. We are not skipping any tags to get the one we want.
     * If it's not there, the document is invalid and a {@link SaveFormatException} is raised.
     * @param diagram The diagram in which we load the content of the reader
     * @param bufferedReader The diagram description in text format
     * @throws SaveFormatException when the content of <code>bufferedReader</code> is not in the correct format
     */
    public void load(JavaDiagram diagram, BufferedReader bufferedReader) throws SaveFormatException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        XMLEventReader eventReader = null;
        try {
            eventReader = inputFactory.createXMLEventReader(bufferedReader);

            XMLEvent event = eventReader.nextTag();
            if(!(event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase("javadiagram"))) {
                throw SaveFormatException.startDiagram(event.toString());
            }

            String version = readVersion(eventReader);
            System.out.println("Version loaded is " + version);

            Set<JavaTileModel> tiles = new HashSet<>();
            Set<JavaRelationModel> relations = new HashSet<>();

            loadDiagramElements(eventReader, tiles, relations);
            closeTags(eventReader);

            System.out.println("Loaded " + tiles.size() + " tile" + (tiles.size() > 1 ? "s" : ""));
            System.out.println("Loaded " + relations.size() + " relation" + (relations.size() > 1 ? "s" : ""));
            tiles.forEach(diagram::addTile);
            relations.forEach(diagram::addRelation);

            eventReader.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            if (eventReader != null) {
                try {
                    eventReader.close();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads the tiles and relations from the <code>eventReader</code> and adds them respectively to <code>tiles</code> and <code>relations</code>.
     * @param eventReader The XML description of the tiles and relations
     * @param tiles The list of tiles to add tiles to
     * @param relations The list of relation to add relations to
     * @throws XMLStreamException when the format is incorrect
     * @throws NullPointerException when any of the parameters is null
     */
    private void loadDiagramElements(XMLEventReader eventReader, Set<JavaTileModel> tiles, Set<JavaRelationModel> relations) throws XMLStreamException, SaveFormatException {
        Objects.requireNonNull(tiles);
        Objects.requireNonNull(relations);
        Objects.requireNonNull(eventReader);

        closeTags(eventReader);

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextTag();

            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                switch (element.getName().getLocalPart().toLowerCase()) {
                    case "tile":
                        JavaTileModel tile = loadTile(element, eventReader);
                        tiles.add(tile);
                        break;

                    case "relation":
                        JavaRelationModel relation = loadRelation(element, eventReader);
                        relations.add(relation);
                        break;
                }

            }
            closeTags(eventReader);
        }
    }

    /**
     * Loads a tile tag from the parameters.
     * @param element The starting element for the tile
     * @param eventReader The remaining of the document
     * @return The tile in a parsed format
     */
    private JavaTileModel loadTile(StartElement element, XMLEventReader eventReader) throws SaveFormatException {
        try {
            String id = element.getAttributeByName(new QName("id")).getValue();
            String name = loadName(eventReader);
            GraphicData graphicData = loadGraphic(eventReader);
            return new JavaTileModel(id, name, graphicData);
        } catch (XMLStreamException e) {
            throw SaveFormatException.tileMalformed();
        }
    }

    /**
     * Reads the event reader to parse the next graphic node.
     * @param eventReader contains the description of the graphic node
     * @return a new instance of {@link GraphicData}
     */
    private GraphicData loadGraphic(XMLEventReader eventReader) throws XMLStreamException, SaveFormatException {
        if (eventReader.peek().isStartElement() && eventReader.peek().asStartElement().getName().getLocalPart().equalsIgnoreCase("graphic")) {
            eventReader.nextTag(); // Consume the <graphic> tag
            Double x = readCoordinate("x", eventReader);
            Double y = readCoordinate("y", eventReader);
            return new GraphicData(x, y);
        } else {
            throw SaveFormatException.startElement(eventReader.peek().toString());
        }

    }

    /**
     * Reads the <code>name</code> coordinate immediately available in the <code>eventReader</code>.
     * @param name the name of the coordinate to read
     * @param eventReader the container of the coordinate description
     * @return the coordinate
     * @throws XMLStreamException when the <code>eventReader</code> is empty or cannot be read
     * @throws SaveFormatException when something else than the coordinate was found
     */
    private Double readCoordinate(String name, XMLEventReader eventReader) throws XMLStreamException, SaveFormatException {
        closeTags(eventReader);
        if (eventReader.peek().isStartElement() && eventReader.peek().asStartElement().getName().getLocalPart().equalsIgnoreCase(name)) {
            XMLEvent event = eventReader.nextTag(); // Place the cursor on the tag
            event = eventReader.nextEvent();
            Double result = new Double(event.toString());
            closeTags(eventReader);
            return result;
        } else {
            throw SaveFormatException.expectedMessage(name, eventReader.peek().toString());
        }
    }

    /**
     * Load the name of a tile.
     * @param eventReader The XML content to parse
     * @return The name of the tile
     */
    private String loadName(XMLEventReader eventReader) throws XMLStreamException {
        XMLEvent event = eventReader.nextTag();
        String elementText = eventReader.getElementText();
        closeTags(eventReader);
        return elementText;
    }

    /**
     * Loads a relation tag.
     * @param element the start of the tag
     * @param eventReader the reader containing the remaining of the document
     * @return The relation in a parsed format
     */
    private JavaRelationModel loadRelation(StartElement element, XMLEventReader eventReader) {
        return new JavaRelationModel(null, null);
    }

    /**
     * Place the reader on the next start of element. Discarding everything before.
     * @param eventReader the reader we want to forward
     */
    private void closeTags(XMLEventReader eventReader) throws XMLStreamException {
        while (eventReader.peek() != null && !eventReader.peek().isStartElement()) {
            eventReader.nextEvent();
        }
    }

    private String readVersion(XMLEventReader eventReader) throws XMLStreamException, SaveFormatException {
        XMLEvent event = eventReader.nextTag();
        if(event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase("version")) {
            event = eventReader.nextEvent();
            if (event.isCharacters()) {
                return event.asCharacters().getData();
            } else {
                throw SaveFormatException.versionNotFound(event.toString());
            }
        } else {
            throw SaveFormatException.versionNotFound(event.toString());
        }
    }

    /**
     * Prints the remaining of the events in <code>eventReader</code>.
     */
    private void printRemaining(XMLEventReader eventReader) throws XMLStreamException {
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            System.out.println(event);
        }
    }
}
