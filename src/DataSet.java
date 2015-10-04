/**
 * Created by deynekalex on 26.09.15.
 */

import java.util.*;
import java.io.*;

public class DataSet {

    ArrayList<ArrayList> data;
    int dimension;
    final static int DEFAULT_DIMENSION = 2;

    public void info() {
        int a = 0;
        int b = 0;
        for (ArrayList ar : data) {
            if (ar.get(0).equals(0))
                a++;
            else
                b++;
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println();
    }

    DataSet(){
        this.dimension = DEFAULT_DIMENSION;
        this.data = new ArrayList();
    }
    
    DataSet(ArrayList data, int dimension){
        this.dimension = dimension;
        this.data = data;
    }

    DataSet(String file, int dimension) {
        this.dimension = dimension;
        this.data = new ArrayList();
        try(Scanner in = new Scanner(new File(file))) {
            in.next();
            in.next();
            while (in.hasNext()) {
                ArrayList vec = new ArrayList();
                double x = Double.parseDouble(in.next().trim().replace(',', '.'));
                double y = Double.parseDouble(in.next().trim().replace(',', '.'));
                int type = Integer.parseInt(in.next());
                vec.add(type);
                vec.add(x);
                vec.add(y);
                data.add(vec);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void separate(DataSet ds1, DataSet ds2, double portion) {
        int ds1_size = (int) Math.round(this.getSize() * portion);
        //int ds2_size = this.getSize() - ds1_size;
        for (int i = 0; i < this.getSize(); i++){
            if (i < ds1_size)
                ds1.data.add(this.get(i));
            else
                ds2.data.add(this.get(i));
        }
        if (this == ds1){
            for (int i = 0; i < this.getSize(); i++)
                ds1.data.remove(i);
        }

        if (this == ds2){
            for (int i = 0; i < this.getSize(); i++)
                ds2.data.remove(i);
        }

    }

    public void clear(){
        this.data.clear();
    }

    public void split(DataSet ds1, DataSet ds2, double portion){
        int ds1_size = (int) (this.getSize() * portion);
        ArrayList<DataSet> listofDatasets = new ArrayList();
        for (int i = 0; i < dimension; i++){
            listofDatasets.add(new DataSet(new ArrayList(), dimension));
        }
        for (int i = 0; i < this.getSize(); i++){
            listofDatasets.get((Integer) this.data.get(i).get(0)).data.add(this.get(i));
        }

        ArrayList<DataSet> list2 = new ArrayList();
        for (DataSet ds: listofDatasets){
            for (int i = 0; i < dimension; i++){
                list2.add(new DataSet());
                list2.add(new DataSet());
                ds.separate(list2.get(2*i), list2.get(2*i+1), portion);
            }
        }

        for(int i = 0; i < dimension; i++){
            ds1.merge(list2.get(i));
            i++;
            ds2.merge(list2.get(i));
        }
    }

    public void merge(DataSet ds1){
        for (ArrayList ar : ds1.data){
            this.data.add(ar);
        }
        this.shuffle();
    }

    public int getSize() {
        return data.size();
    }

    public ArrayList get(int element) {
        return data.get(element);
    }

    public void shuffle() {
        Collections.shuffle(this.data);
    }

    public String toString() {
        String res = "DataSet size = " + this.getSize() + '\n';
        for (ArrayList elem : this.data){
            for (Object o : elem){
                res += o.toString() + " ";
            }
            res += '\n';
        }
        return res;
    }
}

