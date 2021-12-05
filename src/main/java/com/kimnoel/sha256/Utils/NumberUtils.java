package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.object.Number;
import com.kimnoel.sha256.object.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberUtils {

    private static final Logger logger = LoggerFactory.getLogger(NumberUtils.class);

    private NumberUtils(){}

   public static Number not(Number number){
        List<Integer> listBits = new ArrayList<>();
        for (Integer bit: number.getListBits()) {
            listBits.add(1 - bit);
        }
        return new Number(listBits);
   }

    public static Number rightShift(Number number, int shift){
        return new Number(Math.floor(Math.pow(2, -shift)*number.getNumber()));
    }

    public static Number rotateRight(Number number, int shift){
        double sum1=0;
        double sum2=0;
        int nJava = number.getListMathBits().size();
        int nMath = nJava - 1;
        int xk;

        for (int k = shift; k < nJava ; k++){
            xk = number.getListMathBits().get(k);
            sum1 += xk*Math.pow(2, k - shift);
        }

        for (int k=0; k <= shift - 1 ; k++){
            xk = number.getListMathBits().get(k);
            sum2 += xk*Math.pow(2, nMath + k - shift + 1);
        }

        return new Number(Math.floor(sum1 + sum2));
    }

    public static Number xOR(Number x, Number y) {
        List<Integer> listBits = new ArrayList<>();
        Integer xk, yk;

        for (int k = 0 ; k < x.getListBits().size() ; k++) {
            xk = x.getListBits().get(k);
            yk = y.getListBits().get(k);
            listBits.add((int) Math.pow(xk - yk,2));
        }
        return new Number(listBits);
    }

    public static Number xOR(Number x, Number y, Number z) {
        List<Integer> listBits = new ArrayList<>();
        Integer xk, yk, zk;

        for (int k = 0 ; k < x.getListBits().size() ; k++) {
            xk = x.getListBits().get(k);
            yk = y.getListBits().get(k);
            zk = z.getListBits().get(k);

            listBits.add((int) Math.pow(xk - yk - zk,2) - 4*yk*zk + 3*xk*yk*zk);
        }
        return new Number(listBits);
    }


    public static Number add(Number x, Number y) {
        List<Integer> listMathBits = new ArrayList<>();
        List<Integer> longest, shortest;
        Integer zk;

        // Set longest and shortest bit chain to avoid loop issue
        if (x.getListMathBits().size() >= y.getListMathBits().size()) {
            longest = new ArrayList<>(x.getListMathBits());
            shortest = new ArrayList<>(y.getListMathBits());
        } else {
            longest = new ArrayList<>(y.getListMathBits());
            shortest = new ArrayList<>(x.getListMathBits());
        }
        
        // k = 0
        zk = shortest.get(0)+longest.get(0);
        listMathBits.add(zk % 2);

        int k=1;
        while(k < longest.size()) {
            if (k < shortest.size())
                zk = shortest.get(k) + longest.get(k) + zk/2;
            else if (k < longest.size())
                zk = longest.get(k) + zk/2;
            else
                zk = zk/2;

            listMathBits.add(zk % 2);
            k++;
        }

        if (zk >= 2) listMathBits.add(zk / 2);

        Collections.reverse(listMathBits);
        return new Number(listMathBits);
    }


    public static Number add(Number x, Number y, Number z, Number u, Number v) {
        return add(
                add(add(x,y) ,z ),
                add(u,v)
                );
    }
}
