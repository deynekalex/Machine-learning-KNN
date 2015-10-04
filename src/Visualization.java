import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Visualization extends JApplet {
    final static Color bg = Color.white;
    final static Color fg = Color.black;
    ArrayList<ArrayList> dots;

    public Visualization(ArrayList<ArrayList> data) {
        super();
        this.dots = data;
    }

    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(ArrayList t : dots) {
            Double x = ((Double) t.get(1)+1) * 300;
            Double y = ((Double) t.get(2)+1) * 300;
            if (t.get(0).equals(0))
                g2.setPaint(Color.BLUE);
            else
                g2.setPaint(Color.RED);
            Ellipse2D.Double g2Point = new Ellipse2D.Double(x.intValue(), y.intValue(), 4, 4);
            g2.fill(g2Point);
            g2.draw(g2Point);
        }
    }
}
