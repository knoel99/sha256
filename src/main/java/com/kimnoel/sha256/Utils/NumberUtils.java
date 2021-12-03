package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.object.Number;
import com.kimnoel.sha256.object.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
}
