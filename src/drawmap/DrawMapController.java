package drawmap;

import java.io.File;

import javax.swing.JPanel;

/**
 *
 * @author Olli Koskinen
 */
public class DrawMapController {

    private GUI view;
    private DrawMap dm;

    public static void main(String[] args) {
        GUI view = new GUI();
        DrawMap dm = new DrawMap();

        DrawMapController controller = new DrawMapController(view, dm);
        view.registerController(controller);
        view.registerComponents();
    }

    private DrawMapController(GUI view, DrawMap dm) {
        this.view = view;
        this.dm = dm;
    }

    void drawMapFromFile(File file) {
        dm.clearLineVector();
        dm.loadMapFromFile(file);
    }

    void redrawMapPanel() {
        dm.drawMap();
    }
    
    void increaseScale(){
    	dm.increaseScale();
    }
    void decreaseScale(){
    	dm.decreaseScale();
    }

    void showLineCount() {
        dm.showLineCount();
    }

	public JPanel getDrawMapPanel() {
		return dm;
	}
}
