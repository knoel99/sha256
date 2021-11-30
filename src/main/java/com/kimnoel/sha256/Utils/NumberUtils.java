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
        List<Integer> listBits = new ArrayList<>();
        for (int index = 0; index < number.getListBits().size() - shift; index++) {
            listBits.add(number.getListBits().get(index));
        }
        return new Number(listBits);
    }

}
