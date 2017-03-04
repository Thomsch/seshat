package ch.ceruleansands.seshat.io.loader.header;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads the first information:
 * - Seshat Project
 * - Last Modified
 * - Language
 * @author thmss
 */
public class HeaderReader {
    public Header read(BufferedReader reader) throws FileNotFoundException, HeaderException {
        List<String> headerlines = reader.lines().limit(2).collect(Collectors.toList());

        String firstLine = headerlines.get(0);

        if(!firstLine.equals(Header.firstLine)) throw HeaderException.seshatProjectMissing();

        return new Header(headerlines.get(1));
    }

}
