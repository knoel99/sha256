package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sha256Service {

    private static final Logger logger = LoggerFactory.getLogger(Sha256Service.class);

    private Sha256Service(){}




    public static String padding(String headBinaryMessage){
        int totalLength = 512;
        int tailLength = 64;

        String separator = "1";
        String tail = BinaryService.to64Bits(Integer.toBinaryString(headBinaryMessage.length()));

        int indexPadding = headBinaryMessage.length() +1;
        int indexTail = totalLength - tailLength;

        return headBinaryMessage + separator + "0".repeat(indexTail - indexPadding)+ tail;
    }

    public static List<String> initMessageSchedule(String paddedMessage) {
        ArrayList<String> result = new ArrayList<>();
        int index = 0;

        for (int i=0;i<16;i++) {
            result.add(paddedMessage.substring(index, index+32));
            index += 32;
        }
        return result;
    }

    public static List<String> expandMessageSchedule(List<String> messageSchedule) {
        for (int i=16; i<64; i++) {
            messageSchedule.add(BinaryService.addition(
                    BinaryService.sigma1(messageSchedule.get(i-2)),
                    messageSchedule.get(i-7),
                    BinaryService.sigma0(messageSchedule.get(i-15)),
                    messageSchedule.get(i-16))
            );
        }
        return messageSchedule;
    }

    public static String tmpWord1(int index, List<String> messageSchedule, List<String> binHash) {
        return BinaryService.addition(
                BinaryService.uSigma1(binHash.get(4)),
                BinaryService.choice(binHash.get(4), binHash.get(5), binHash.get(6)),
                binHash.get(7),
                ConstantsService.BIN_CONSTANT_64_FIRST_CUBE_ROOT.get(index),
                messageSchedule.get(index)
        );
    }

    public static String tmpWord2(List<String> binHash) {
        return BinaryService.addition(
                BinaryService.uSigma0(binHash.get(0)),
                BinaryService.majority(binHash.get(0), binHash.get(1), binHash.get(2))
        );
    }

    public static List<String> compression(List<String> messageSchedule) {
        List<String> binHash = new ArrayList<>(ConstantsService.BIN_INITIAL_HASH);
        String tmpWord1, tmpWord2;

        for (int i=0; i<messageSchedule.size(); i++) {
            tmpWord1 = tmpWord1(i, messageSchedule, binHash);
            tmpWord2 = tmpWord2(binHash);

            binHash.add(0, BinaryService.addition(tmpWord1, tmpWord2));
            binHash.set(4, BinaryService.addition(binHash.get(4), tmpWord1));
            binHash.remove(binHash.size()-1);
        }
        
        /*
        After we have compressed the entire message schedule,
        we add the resulting hash value to the initial hash value we started with.
        This gives us the final hash value for this message block.
         */
        for (int i=0; i<binHash.size(); i++) {
            binHash.set(i,BinaryService.addition(binHash.get(i), ConstantsService.BIN_INITIAL_HASH.get(i)));
        }

        return binHash;
    }

    public static String sha256(List<String> binHash) {
        StringBuilder sb = new StringBuilder();
        binHash.forEach(s -> sb.append(BinaryService.binaryToHash(s)));
        return sb.toString();
    }

    public static String sha256(String message) {
        String binMessage = BinaryService.convertMessageStringToBinary(message);
        String paddedMessage = padding(binMessage);
        List<String> initMessageSchedule = initMessageSchedule(paddedMessage);
        List<String> expandMessageSchedule = expandMessageSchedule(initMessageSchedule);
        List<String> binaryToHash = compression(expandMessageSchedule);
        return sha256(binaryToHash);
    }
}