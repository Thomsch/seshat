package ch.ceruleansands.seshat.language.java;

/**
 * The model for a relation in a java diagram.
 * @author Thomsch
 */
public class JavaRelationModel {
    private final JavaTile origin;
    private final JavaTile target;

    public JavaRelationModel(JavaTile origin, JavaTile target) {
        this.origin = origin;
        this.target = target;
    }
}
