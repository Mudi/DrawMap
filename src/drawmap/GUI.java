package drawmap;

import java.awt.*;
import java.awt.event.*;

import java.io.File;

import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author Olli Koskinen
 */
public class GUI extends JFrame {

    final JFileChooser chooser = new JFileChooser();
    private DrawMapController controller;
    
    // napit
    private JButton loadMap;
    private JButton lenghten;
    private JButton reloadMap;
    private JButton showLineCount;
    // Paneelit
    private JPanel paaPaneeli;
    private JPanel karttaPaneeli;
    private JPanel buttonPanel;
    // reunat
    private Border reunus;


    public GUI() {
        super("DrawMap");
    }

    public void registerComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300 * DrawMap.SCALE + 10, 225 * DrawMap.SCALE + 30);

        // Buttons
        loadMap = new JButton("Load Map");
        reloadMap = new JButton("Reload Map");
        showLineCount = new JButton("LineCount");
        lenghten = new JButton("Lenghten Lines");

        // Panels
        paaPaneeli = new JPanel();
        buttonPanel = new JPanel();
        karttaPaneeli = controller.getDrawMapPanel();

        // Layouts
        paaPaneeli.setLayout(new BorderLayout());
        buttonPanel.add(loadMap, BorderLayout.WEST);
        buttonPanel.add(reloadMap, BorderLayout.EAST);
        buttonPanel.add(showLineCount);
        buttonPanel.add(lenghten);
        paaPaneeli.add(buttonPanel, BorderLayout.SOUTH);
        paaPaneeli.add(karttaPaneeli);

        // Borders
        reunus = BorderFactory.createEtchedBorder();
        paaPaneeli.setBorder(reunus);
        loadMap.setBorder(reunus);
        reloadMap.setBorder(reunus);
        showLineCount.setBorder(reunus);
        lenghten.setBorder(reunus);
        karttaPaneeli.setBorder(reunus);

        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();

        setLocation(new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.width) / 2));
        addKeyListener(new LiikeTunnistin());
        
        //Action listeners
        showLineCount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showLineCount();
                repaint();
            }
        });
        
        lenghten.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               controller.lenghtenLines();
               repaint();
            }
            
        });
        
      

        //Skaalauksen muunnos
        //TODO korjaa scaalaus
        paaPaneeli.setFocusable(true);
       this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {    // Päivitetään näkymä panamalla nuolta ylöspäin
                    controller.increaseScale();
                    repaint();
                } else if (keyCode == KeyEvent.VK_DOWN) {    // Päivitetään näkymä panamalla nuolta ylöspäin
                    controller.decreaseScale();
                    repaint();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e){
                
            }
        });

        // Load map button function
        loadMap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                File koti = new File("/home/mudi/Dropbox/Java/DrawMapEclips/src/DM/");
                chooser.setCurrentDirectory(koti);

                int state = chooser.showOpenDialog(null);
                File file = chooser.getSelectedFile();

                if ((file != null) && (state == JFileChooser.APPROVE_OPTION)) {
                    if (DrawMap.debug) {
                        JOptionPane.showMessageDialog(null, file.getPath());
                    }

                    controller.drawMapFromFile(file);

                } else if (state == JFileChooser.CANCEL_OPTION) {
                    if (DrawMap.debug) {
                        JOptionPane.showMessageDialog(null, "Canceled");
                    }
                }

                repaint();
            }
        });
        reloadMap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.redrawMapPanel();
                repaint();
            }
        });
        setContentPane(paaPaneeli);
        setVisible(true);
    }

    void registerController(DrawMapController controller) {
        this.controller = controller;
    }
    

    // Tunnistetaan eri nappien painalluksia
    private class LiikeTunnistin extends KeyAdapter {
    }
}
