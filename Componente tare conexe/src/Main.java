import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    JFrame frame = new JFrame("Componente tare Conexe");

    Frame Graph=new Frame();



    public Main() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu
        JMenu menu = new JMenu("Meniu");
        menuBar.add(menu);

        // Create a menu item
        JMenuItem menuItem = new JMenuItem("Menu Item");
        menu.add(menuItem);

        // Create a toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // make it non-floatable

        //add button to frame. When click call TopologicSort
        JButton button = new JButton("Componente tare conexe");


        //add action listener for menu item
        button.addActionListener(e -> {
           GrafRedusFrame.main(null);
        });

        //add undo with ctrl+z
        Graph.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Z"), "undo");
        Graph.getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Graph.undo();
            }
        });

        // Add the button to the toolbar
        toolBar.add(button);

        // Add the toolbar to the menu bar
        menuBar.add(toolBar);

        // Add the menu bar to the frame
        frame.setJMenuBar(menuBar);

        frame.add(Graph);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
