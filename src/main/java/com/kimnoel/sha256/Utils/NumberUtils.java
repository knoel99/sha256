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
        double sum=0;

        for (int k=0; k < shift; k++){
            sum += number.getListBits().get(k)*Math.pow(2, k) *
                    (Math.pow(2, number.getListBits().size() - 1)*Math.pow(2, -2*(k-shift)) -1);
        }
        double num = Math.pow(2, -shift)*number.getNumber() + sum;

        return new Number(Math.floor(num));
    }
}
