package ch.ceruleansands.seshat.disabled.io.exporter;

import ch.ceruleansands.seshat.component.tile.Tile;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * Exporter implementation for the Java language.
 * @author Thomsch.
 */
public class ExporterImpl implements Exporter {

    @Override
    public Optional<File> export(Optional<File> saveLocation, Window window, List<Tile> tiles) {

        File file = saveLocation.orElseGet(() -> {
            FileChooser fileChooser = new FileChooser();
            return fileChooser.showSaveDialog(window);
        });

        return saveAtLocation(tiles, file);
    }

    private Optional<File> saveAtLocation(List<Tile> tiles, File file) {
        if (file != null) {
            try {
                PrintWriter writer = new PrintWriter(file);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("Failed to create new file.");
                }

                tiles.forEach(tile -> {
                    writer.println(tile.getName());
                    writer.println(tile.getNode().getTranslateX());
                    writer.println(tile.getNode().getTranslateY());

                    tile.getAttributes().forEach(attribute -> writer.println("    " + attribute));

                    tile.getMethods().forEach(method -> writer.println("    " + method));
                });
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(file);
    }
}
