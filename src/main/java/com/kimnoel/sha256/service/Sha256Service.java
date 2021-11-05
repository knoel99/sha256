package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    public static String tempWord1(int index, List<String> messageSchedule) {
        return BinaryService.addition(
                BinaryService.uSigma1(ConstantsService.E),
                BinaryService.choice(ConstantsService.E, ConstantsService.F, ConstantsService.G),
                ConstantsService.H,
                ConstantsService.BIN_CONSTANT_64_FIRST_CUBE_ROOT.get(index),
                messageSchedule.get(index)
        );
    }

    public static String tempWord2() {
        return BinaryService.addition(
                BinaryService.uSigma0(ConstantsService.A),
                BinaryService.majority(ConstantsService.A, ConstantsService.B, ConstantsService.C)
        );
    }

    public static List<String> compression(List<String> messageSchedule) {
        List<String> binInitialHash = ConstantsService.getBinInitialHash();

        for (int i=0; i<binInitialHash.size(); i++) {
            //System.out.println("binInitialHash.get(i) = "+ binInitialHash.get(i)+ " tempWord1(i, binInitialHash) = "+tempWord1(i, binInitialHash));
            binInitialHash.set(i, BinaryService.addition(binInitialHash.get(i), tempWord1(i, binInitialHash)));
            //System.out.println("binInitialHash.get(i) = "+ binInitialHash.get(i));
        }

        for (int i=0; i<messageSchedule.size(); i++) {
            binInitialHash.add(0, BinaryService.addition(tempWord1(i, messageSchedule), tempWord2()));
            binInitialHash.set(4, BinaryService.addition(binInitialHash.get(4), tempWord1(i, messageSchedule)));
            binInitialHash.remove(binInitialHash.size()-1);
        }
        return binInitialHash;
    }
}