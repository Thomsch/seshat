package ch.ceruleansands.seshat.language.java;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedReader;

/**
 * Builds a java diagram from a text description.
 * @author Thomsch
 */
public class ParserLoader {
    /**
     * Parses & loads the content of <code>bufferedReader</code> in <code>diagram</code>.
     * @param diagram The diagram in which we load the content of the reader
     * @param bufferedReader The diagram description in text format
     */
    public void load(JavaDiagram diagram, BufferedReader bufferedReader) throws SaveFormatException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // Setup a new eventReader
//        InputStream in = new FileInputStream(configFile);

        XMLEventReader eventReader = null;
        eventReader.
        try {
            eventReader = inputFactory.createXMLEventReader(bufferedReader);

            XMLEvent event = eventReader.nextEvent();

            if(!event.isStartDocument()) {
                throw SaveFormatException.startDocument();
            }

            processTag(eventReader.nextTag(), eventReader);

//            while (eventReader.hasNext()) {
//
//
//
//                if (event.isStartElement()) {
//                    StartElement startElement = event.asStartElement();
//
//                    if (startElement.getName().getLocalPart().equalsIgnoreCase("version")) {
//                        event = eventReader.nextEvent();
//                        System.out.println("Version is " + event.asCharacters().getData());
//                    }
//                }
//
//                if(event.isCharacters()) {
//                    String value = event.asCharacters().getData().trim();
//                    if (value.length() != 0) {
//                        System.out.println(value);
//                    }
//                }
//                // If we reach the end of an item element, we add it to the list
//                if (event.isEndElement()) {
//                }
//            }

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

    private void processTag(XMLEvent event, XMLEventReader eventReader) throws SaveFormatException {
        StartElement element = getStartElement(event);
        String tagName = element.getName().getLocalPart();
        System.out.println(tagName);


    }

    private StartElement getStartElement(XMLEvent event) throws SaveFormatException {
        if (event.isStartElement()) {
            return event.asStartElement();
        } else {
            throw SaveFormatException.startElement(event.toString());
        }
    }
}
