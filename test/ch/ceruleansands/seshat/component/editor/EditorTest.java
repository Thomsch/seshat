package ch.ceruleansands.seshat.component.editor;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.component.diagram.DiagramBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * @author Thomsch
 */
public class EditorTest {

    private Editor editor;

    @Before
    public void setUp() throws Exception {
        editor = new Editor(mock(DiagramBuilder.class), mock(ActionFactory.class));
    }

    @Test(expected = NullPointerException.class)
    public void setEmptyDiagramBeforeSettingTheView() throws Exception {
        editor.setEmptyDiagram();
    }

    @Test(expected = NullPointerException.class)
    public void setMenuBeforeSettingTheView() throws Exception {
        editor.setMenu();
    }
}
