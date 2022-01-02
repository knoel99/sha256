package com.kimnoel.sha256.solver.utils;

import com.kimnoel.sha256.solver.object.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordUtils {

    private static final Logger logger = LoggerFactory.getLogger(WordUtils.class);

    private WordUtils(){}

   public static Word not(Word number){
        List<Integer> listBits = new ArrayList<>();
        for (Integer bit: number.getListBits()) {
            listBits.add(1 - bit);
        }
        return new Word(listBits, number.getFormatNbBits());
   }

    public static Word rightShift(Word number, int shift){
        return new Word(Math.floor(Math.pow(2, -shift)*number.getNumber()), number.getFormatNbBits());
    }

    public static Word rotateRight(Word number, int shift){
        double sum1=0;
        double sum2=0;
        int nJava = number.getBits().length();
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

        return new Word(Math.floor(sum1 + sum2), number.getFormatNbBits());
    }

    public static Word xOR(Word x, Word y) {
        List<Integer> listBits = new ArrayList<>();
        Integer xk, yk;

        for (int k = 0 ; k < x.getListBits().size() ; k++) {
            xk = x.getListBits().get(k);
            yk = y.getListBits().get(k);
            listBits.add((int) Math.pow(xk - yk,2));
        }
        return new Word(listBits, x.getFormatNbBits());
    }

    public static Word xOR(Word x, Word y, Word z) {
        List<Integer> listBits = new ArrayList<>();
        Integer xk, yk, zk;

        for (int k = 0 ; k < x.getListBits().size() ; k++) {
            xk = x.getListBits().get(k);
            yk = y.getListBits().get(k);
            zk = z.getListBits().get(k);

            listBits.add((int) Math.pow(xk - yk - zk,2) - 4*yk*zk + 3*xk*yk*zk);
        }
        return new Word(listBits, x.getFormatNbBits());
    }

    public static int xOR(Word x, int index1, int index2, int index3) {
        Integer xk, yk, zk;
        xk = x.getListMathBits().get(index1);
        yk = x.getListMathBits().get(index2);
        zk = x.getListMathBits().get(index3);

        return (int) (Math.pow(xk - yk - zk, 2) - 4 * yk * zk + 3 * xk * yk * zk);
    }

    public static int xOR(Word x, int index1, int index2) {
        Integer xk, yk;
        xk = x.getListMathBits().get(index1);
        yk = x.getListMathBits().get(index2);

        return (int) Math.pow(xk - yk, 2);
    }


    public static Word add(Word x, Word y) {
        List<Integer> listMathBits = new ArrayList<>();
        List<Integer> longest, shortest;
        int zk;

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
        return new Word(listMathBits, x.getFormatNbBits());
    }


    public static Word add(Word x, Word y, Word z, Word u, Word v) {
        return add(
                add(add(x,y) ,z ),
                add(u,v)
                );
    }

    public static Word sigma0Definition(Word x) {
        Word r7 = rotateRight(x, 7);
        Word r18 = rotateRight(x, 18);
        Word s3 = rightShift(x,3);

        return xOR(r7, r18, s3);
    }

    public static Word sigma(Word x, int p, int q, int s) {
        List<Integer> listMathBits = new ArrayList<>();
        int n = x.getBits().length()-1;

        for (int k = 0; k <= n-p; k++) {
            listMathBits.add(xOR(x,k+p, k+q, k+s));
        }
        for (int k = n-p+1; k <= n-q; k++) {
            listMathBits.add(xOR(x,k-n+p-1, k+q, k+s));
        }
        for (int k = n-q+1; k <= n-s; k++) {
            listMathBits.add(xOR(x,k-n+p-1, k-n+q-1, k+s));
        }
        for (int k = n-s+1; k <= n; k++) {
            listMathBits.add(xOR(x,k-n+p-1, k-n+q-1));
        }

        Collections.reverse(listMathBits);
        return new Word(listMathBits, x.getFormatNbBits());
    }

    public static Word bigSigma(Word x, int p, int q, int s) {
        List<Integer> listMathBits = new ArrayList<>();
        int n = x.getBits().length()-1;

        for (int k = 0; k <= n-p; k++) {
            listMathBits.add(xOR(x,k+p, k+q, k+s));
        }
        for (int k = n-p+1; k <= n-q; k++) {
            listMathBits.add(xOR(x,k-n+p-1, k+q, k+s));
        }
        for (int k = n-q+1; k <= n-s; k++) {
            listMathBits.add(xOR(x,k-n+p-1, k-n+q-1, k+s));
        }
        for (int k = n-s+1; k <= n; k++) {
            listMathBits.add(xOR(x,k-n+p-1, k-n+q-1, k-n+s-1));
        }

        Collections.reverse(listMathBits);
        return new Word(listMathBits, x.getFormatNbBits());
    }

    public static Word choice(Word x, Word y, Word z) {
        List<Integer> listMathBits = new ArrayList<>();
        int n = x.getBits().length()-1;
        Integer xk, yk, zk;

        for (int k = 0; k <= n; k++) {
            xk = x.getListMathBits().get(k);
            yk = y.getListMathBits().get(k);
            zk = z.getListMathBits().get(k);

            listMathBits.add((int) Math.pow(xk*yk - (1 - xk)*zk, 2));
        }

        Collections.reverse(listMathBits);
        return new Word(listMathBits, x.getFormatNbBits());
    }

    public static Word majority(Word x, Word y, Word z) {
        List<Integer> listMathBits = new ArrayList<>();
        int n = x.getBits().length()-1;
        Integer xk, yk, zk;
        Integer xk2, yk2, zk2;

        for (int k = 0; k <= n; k++) {
            xk = x.getListMathBits().get(k);
            yk = y.getListMathBits().get(k);
            zk = z.getListMathBits().get(k);
            xk2 = (int) Math.pow(xk, 2);
            yk2 = (int) Math.pow(yk, 2);
            zk2 = (int) Math.pow(zk, 2);

            listMathBits.add((int) (Math.pow(xk*yk - xk*zk - yk*zk, 2) - 4*xk*yk*zk2 +3*xk2*yk2*zk2));
        }

        Collections.reverse(listMathBits);
        return new Word(listMathBits, x.getFormatNbBits());
    }
}
