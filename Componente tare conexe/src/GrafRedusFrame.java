import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class GrafRedusFrame extends JPanel {
    private static Vector<Node> nodes;
    private static Vector<Edge> edges;
    private int nodeNr = 1;
    private int radacina = -1;
    private static int node_diam = Frame.getNode_diam() * 2;

    private Node draggedNode = null;

    void GrafRedus() {
        nodes = new Vector<>();
        edges = new Vector<>();

        ComponenteTareConexe.main(null);
        ArrayList<ArrayList<Integer>> CTC = ComponenteTareConexe.getComponenteConexe();
        ComponenteTareConexe.Graf graf = ComponenteTareConexe.getGrafRedus();

        for (int i = 0; i < CTC.size(); i++) {
            String name = "";
            for (int j = 0; j < CTC.get(i).size(); j++) {
                name += CTC.get(i).get(j) + ",";
            }
            name = name.substring(0, name.length() - 1);
            Node node = new Node(new Point(), node_diam, name);
            nodes.add(node);
        }
        for (int i = 0; i < ComponenteTareConexe.getListaAdiacenta(graf).size(); i++) {
            if (ComponenteTareConexe.getListaAdiacenta(graf).get(i).isEmpty())
                continue;
            for (int j = 0; j < ComponenteTareConexe.getListaAdiacenta(graf).get(i).size(); j++) {
                Edge edge = new Edge(nodes.get(ComponenteTareConexe.getListaAdiacenta(graf).get(i).get(j)),nodes.get(i));
                edges.add(edge);
            }
        }
    }

    GrafRedusFrame() {
        JFrame frame = new JFrame("Graf Redus");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);

        GrafRedus();
        for (Node n : nodes) {
            n.setCenter(new Point((int) (Math.random() * (frame.getWidth() - node_diam)), (int) (Math.random() * (frame.getHeight() - node_diam))));
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                draggedNode = getNodeAt(e.getPoint());

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedNode = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedNode != null) {
                    for(Node n : nodes) {
                        if(draggedNode != n && n.getBounds().contains(e.getPoint())) {
                            return;
                        }
                    }
                    draggedNode.setCenter(e.getPoint());
                    repaint();
                }
            }
        });
    }

    private Node getNodeAt(Point point) {
        for (Node node : nodes) {
            if (node.getBounds().contains(point)) {
                return node;
            }
        }
        return null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Edge e : edges) {
            e.drawEdge(g);
        }
        for (Node n : nodes) {
            n.drawNode(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GrafRedusFrame();
        });
    }
}
