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
            Double a = (Double) t.get(1);
            Double b = (Double) t.get(2);
            a++;b++;
            a=a*300;
            b=b*300;
            if (t.get(0).equals(0))
                g2.setPaint(Color.BLUE);
            else
                g2.setPaint(Color.RED);
            Ellipse2D.Double aDouble = new Ellipse2D.Double(a.intValue(), b.intValue(), 4, 4);
            g2.fill(aDouble);
            g2.draw(aDouble);
        }
    }
}
