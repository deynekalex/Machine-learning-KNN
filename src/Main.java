/**
 * Created by deynekalex on 26.09.15.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

public class Main {
    public static void main(String[] arg) {
        //reading dataset from file
        DataSet dataSet = new DataSet("input/chips.txt", 2);
        dataSet.shuffle();
        //visualize
        JFrame f = new JFrame("KNN");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new Visualization(dataSet.data);
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(800,800));
        f.setVisible(true);
        //get 80% to analyze and 20% to test
        DataSet build = new DataSet();
        DataSet test = new DataSet();
        dataSet.split(build, test, 0.8);
        //get 4 parts of analyze set
        ArrayList<DataSet> list = new ArrayList<>();
        list.add(new DataSet());
        list.add(new DataSet());
        list.add(new DataSet());
        list.add(new DataSet());
        DataSet ds1 = new DataSet();
        DataSet ds2 = new DataSet();
        build.split(ds1, ds2, 0.5);
        ds1.split(list.get(0), list.get(1), 0.5);
        ds2.split(list.get(2), list.get(3), 0.5);
        double max = 0;
        int maxk = 0;//k with best result
        for (int k = 1; k < 10; k++) {
            double res = 0;
            for (int i = 0; i < 4; i++) {
                DataSet initial = new DataSet();
                DataSet input = new DataSet();
                for (int j = 0; j < 4; j++) {
                    if (j == i) {
                        input = list.get(j);
                    } else {
                        initial.merge(list.get(j));
                    }
                }
                res += testing(initial, input, k);
            }
            res = res / 4;//ищем среднее
            System.out.println(k);
            System.out.println(res);
            System.out.println();
            if (res > max) {
                max = res;
                maxk = k;
            }
        }
        System.out.println("Наилучший результат = " + max + ". При k = " + maxk);
        DataSet initial = new DataSet();
        for (int i = 0; i < 4; i++)
            initial.merge(list.get(i));
        double res = testing(initial, test, maxk);
        System.out.println("В итоге = " + res);
    }

    public static double testing(DataSet initial, DataSet input, int k) {
        //1 - истина
        //0 - ложь
        int TP = 0;
        int TN = 0;
        int FP = 0;
        int FN = 0;
        int good = 0;
        for (ArrayList point1 : input.data) {
            int assumption = findassumption(initial, point1, k);
            if (assumption == (int) point1.get(0))
                good++;
            if (assumption == 0){
                if ((int)point1.get(0) == 0)
                    TP++;
                else
                    FP++;
            }else{
                if ((int)point1.get(0) == 1)
                    TN++;
                else
                    FN++;
            }
        }
        double precision = (double)TP/(TP+FP);
        double recall = (double)TP/(TP+FN);
        return 2*precision*recall/(precision+recall);
    }

    public static double accuracy(int p, int n){
        return p/n;
    }

    public static int findassumption(DataSet initial, ArrayList point, int k) {
        Collections.sort(initial.data, new Comparator<ArrayList>() {
            @Override
            public int compare(ArrayList o1, ArrayList o2) {
                if (calculateMetric(o1, point) > calculateMetric(o2, point)) {
                    return 1;
                } else if (calculateMetric(o1, point) < calculateMetric(o2, point)) {
                    return -1;
                } else return 0;
            }
        });
        int zero = 0;
        for (int i = 0; i < k; i++) {
            if ((int) initial.data.get(i).get(0) == 0) {
                zero++;
            }
        }
        if (zero >= k / 2 + 1)
            return 0;
        else
            return 1;
    }

    public static double calculateMetric(ArrayList point1, ArrayList point2) {
        double sum = 0;
        for (int i = 1; i < 3; i++) {
            sum += pow((double) point1.get(i) - (double) point2.get(i), 2);
        }
        return sqrt(sum);
    }
}
