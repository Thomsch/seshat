package ch.thomsch.seshat.component.editor;

import ch.thomsch.seshat.action.ActionFactory;
import ch.thomsch.seshat.component.diagram.Diagram;
import ch.thomsch.seshat.component.diagram.DiagramBuilder;
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
    private Pane diagramView;

    @Before
    public void setUp() throws Exception {
        diagramBuilder = mock(DiagramBuilder.class);
        editor = new Editor(diagramBuilder, mock(ActionFactory.class));

        final Diagram emptyDiagram = mock(Diagram.class);
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

    @Test
    public void setEmptyDiagramAfterCreatingTheView() throws Exception {
        final Parent view = editor.createView();

        editor.setEmptyDiagram();

        verify(diagramBuilder).createEmpty();
        assertEquals(1, view.getChildrenUnmodifiable().size());
        assertEquals(diagramView, view.getChildrenUnmodifiable().get(0));
    }
}
