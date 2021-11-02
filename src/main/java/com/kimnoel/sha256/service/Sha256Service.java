package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Sha256Service {

    private static final Logger logger = LoggerFactory.getLogger(Sha256Service.class);

    private Sha256Service(){}




    public static String padding(String message){
        int totalLength = 512;
        int tailLength = 64;

        String head = BinaryService.convertMessageStringToBinary(message);
        String separator = "1";
        String tail = BinaryService.to64Bits(Integer.toBinaryString(head.length()));

        int indexPadding = head.length() +1;
        int indexTail = totalLength - tailLength;

        return head + separator + "0".repeat(indexTail - indexPadding)+ tail;
    }

    public static ArrayList<String> initMessageSchedule(String paddedMessage) {
        ArrayList<String> result = new ArrayList<>();
        int index = 0;

        for (int i=0;i<16;i++) {
            result.add(paddedMessage.substring(index, index+32));
            index += 32;
        }
        return result;
    }

    public static ArrayList<String> expandMessageSchedule(ArrayList<String> messageSchedule) {



        return messageSchedule;
    }

}