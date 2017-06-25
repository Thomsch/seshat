package ch.ceruleansands.seshat.component.editor;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.component.diagram.DiagramBuilder;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Thomsch
 */
public class EditorTest {

    private Editor editor;
    private DiagramBuilder diagramBuilder;
    private Diagram emptyDiagram;
    private Pane diagramView;

    @Before
    public void setUp() throws Exception {
        diagramBuilder = mock(DiagramBuilder.class);
        editor = new Editor(diagramBuilder, mock(ActionFactory.class));

        emptyDiagram = mock(Diagram.class);
        diagramView = new Pane();
        when(emptyDiagram.getView()).thenReturn(diagramView);
        when(diagramBuilder.createEmpty()).thenReturn(emptyDiagram);
    }

    @Test
    public void testCreateView() throws Exception {
        final Parent view = editor.createView();
        Assert.assertNotNull(view);
    }

    @Test(expected = NullPointerException.class)
    public void setEmptyDiagramBeforeSettingTheView() throws Exception {
        editor.setEmptyDiagram();
    }

    @Test(expected = NullPointerException.class)
    public void setMenuBeforeSettingTheView() throws Exception {
        editor.setMenu();
    }

    @Test
    public void setEmptyDiagramAfterCreatingTheView() throws Exception {
        final Parent view = editor.createView();

        editor.setEmptyDiagram();

        verify(diagramBuilder).createEmpty();
        assertEquals(1, view.getChildrenUnmodifiable().size());
        assertEquals(diagramView, view.getChildrenUnmodifiable().get(0));
    }
}
