package ch.thomsch.seshat;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * @author Thomsch.
 */
public class DialogHelper {
    public void showInformation(String message) {
        Dialog<Void> dialog = new Dialog<>();
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(ok);
        dialog.setContentText(message);
        dialog.setTitle("Information");
        dialog.showAndWait();
    }

    public void showError(String message, String description) {
        Dialog<Void> dialog = new Dialog<>();
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(ok);
        dialog.setHeaderText(message);
        dialog.setContentText(description);
        dialog.setTitle("Error");
        dialog.showAndWait();
    }
}
