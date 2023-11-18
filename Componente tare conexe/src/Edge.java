import java.awt.*;

public class Edge {
    private Node start;
    public Node getStart() {
        return start;
    }
    private Node end;
    public Node getEnd() {
        return end;
    }
    public void drawEdge(Graphics g) {
        g.setColor(Color.BLACK);
        if(this.start.center.x==this.end.center.x && this.start.center.y==this.end.center.y)
            g.drawOval(start.center.x - Frame.getNode_diam(), start.center.y - Frame.getNode_diam()+3, Frame.getNode_diam()*2, Frame.getNode_diam());
        else
            //g.drawLine(start.center.x, start.center.y, end.center.x, end.center.y
        g.drawLine(start.center.x, start.center.y, end.center.x, end.center.y);
        double angle = Math.atan2(this.end.center.y - this.start.center.y, this.end.center.x - this.start.center.x);
        int arrowLength = start.getDiameter()/3;  // You can adjust this value

        // Adjust position of arrowhead based on node radius
        int adjustedEndX = this.end.center.x - (int)(start.getDiameter()/2 * Math.cos(angle));
        int adjustedEndY = this.end.center.y - (int)(start.getDiameter()/2 * Math.sin(angle));

        int xArrow[] = {adjustedEndX, adjustedEndX - (int)(arrowLength * Math.cos(angle + Math.PI/6)), adjustedEndX - (int)(arrowLength * Math.cos(angle - Math.PI/6))};
        int yArrow[] = {adjustedEndY, adjustedEndY - (int)(arrowLength * Math.sin(angle + Math.PI/6)), adjustedEndY - (int)(arrowLength * Math.sin(angle - Math.PI/6))};
        g.fillPolygon(xArrow, yArrow, 3);
    }
    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(Node node) {
        start = node;
    }

    public void setEnd(Node node) {
        end = node;
    }
}
