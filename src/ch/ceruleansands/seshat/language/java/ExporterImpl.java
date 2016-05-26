package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Exporter;
import ch.ceruleansands.seshat.gui.DialogHelper;
import com.google.inject.Inject;
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

    private DialogHelper dialogHelper;

    @Inject
    public ExporterImpl(DialogHelper dialogHelper) {
        this.dialogHelper = dialogHelper;
    }

    @Override
    public Optional<File> export(Optional<File> saveLocation, Window window, List<JavaTile> tiles) {

        File file = saveLocation.orElseGet(() -> {
            FileChooser fileChooser = new FileChooser();
//            saveLocation.ifPresent(file -> fileChooser.setInitialDirectory(file.getParentFile()));
            return fileChooser.showSaveDialog(window);
        });

        return saveAtLocation(tiles, file);
    }

    private Optional<File> saveAtLocation(List<JavaTile> tiles, File file) {
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

                    tile.getAttributes().forEach(attribute -> {
                        writer.println("    " + attribute);
                    });

                    tile.getMethods().forEach(method -> {
                        writer.println("    " + method);
                    });
                });
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(file);
    }
}
