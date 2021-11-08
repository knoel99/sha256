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


    /**
     *
     * @param headBinaryMessage
     * @return
     */
    public static String padding(String headBinaryMessage){
        int totalLength = 512;
        int tailLength = 64;
        int indexTail = totalLength - tailLength;
        int indexHeadAndSeparator = headBinaryMessage.length() +1;
        int nbPaddingZeros = (448 - (indexHeadAndSeparator)) % 512;
        if (nbPaddingZeros < 0) nbPaddingZeros+=512;

        String separator = "1";
        String tail = BinaryService.to64Bits(Integer.toBinaryString(headBinaryMessage.length()));

        return headBinaryMessage + separator + "0".repeat(nbPaddingZeros) + tail;
    }

    public static List<String> paddings(String headBinaryMessage){
        int totalLength = 512;
        int tailLength = 64;
        int indexTail = totalLength - tailLength;

        String head, tail;
        String separator = "1";
        List<String> paddings = new ArrayList<>();

        int maxHeadLength = 447;
        int nbPaddedMsg = headBinaryMessage.length() / maxHeadLength;
        int indexInputBegin = 0;
        int indexInputEnd = Math.min(headBinaryMessage.length(), maxHeadLength);
        int indexHeadAndSeparator;

        // Work on each message slice of length 447 characters and put them in the padding word list
        for (int i=0; i<=nbPaddedMsg; i++){
            head = headBinaryMessage.substring(indexInputBegin, indexInputEnd);

            tail = BinaryService.to64Bits(Integer.toBinaryString(head.length()));
            indexHeadAndSeparator = head.length() +1;

            paddings.add(head + separator + "0".repeat(indexTail - indexHeadAndSeparator) + tail);

            indexInputBegin = indexInputEnd;
            indexInputEnd = Math.min(indexInputEnd + maxHeadLength, headBinaryMessage.length());
        }

        return paddings;
    }


    /**
     * Initialize message schedule with formula
     * W_t = M_it
     * (for 0 ≤ t ≤ 15)
     * @param paddedMessage is a 512 bits word
     * @return List of words
     */
    public static List<String> initMessageSchedule(String paddedMessage) {
        ArrayList<String> result = new ArrayList<>();
        int index = 0;

        for (int i=0;i<16;i++) {
            // Split the padded message into slices of length 32
            result.add(paddedMessage.substring(index, index+32));
            index += 32;
        }
        return result;
    }

    /**
     * Fill message schedule with formula
     * W_t = M_it
     * (for 0 ≤ t ≤ 15)
     * @param paddedMessage list of 512-multiple bits words
     * @return List of list of words
     */
    public static List<List<String>> initMessageSchedules(String paddedMessage) {
        List<List<String>> res = new ArrayList<>();
        // Each element is a 512-multiple bit word
        List<String> paddedMsgList = Arrays.asList(paddedMessage.split("(?<=\\G.{512})"));

        for (String msg : paddedMsgList) {
            ArrayList<String> result = new ArrayList<>();
            int index = 0;

            for (int i=0;i<16;i++) {
                // Split the padded message into slices of length 32
                result.add(msg.substring(index, index+32));
                index += 32;
            }
            res.add(result);
        }

        return res;
    }

    /**
     * Fill in the message schedule from 16th to 64th element with formula
     * Wt = σ1(Wt-2) + Wt-7 + σ0(Wt-15) + Wt-16
     * (for 16 ≤ t ≤ 63)
     * @param messageSchedule
     * @return
     */
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

    /**
     * Fill in the message schedule from 16th to 64th element with formula
     * @param messageSchedules
     * @return
     */
    public static List<List<String>> expandMessageSchedules(List<List<String>> messageSchedules) {
        for (List<String> messageSchedule : messageSchedules) {
            for (int i = 16; i < 64; i++) {
                messageSchedule.add(BinaryService.addition(
                        BinaryService.sigma1(messageSchedule.get(i - 2)),
                        messageSchedule.get(i - 7),
                        BinaryService.sigma0(messageSchedule.get(i - 15)),
                        messageSchedule.get(i - 16))
                );
            }
        }
        return messageSchedules;
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

    public static List<String> compression(List<String> messageSchedule, List<String> initialBinHash) {
        List<String> binHash = new ArrayList<>(initialBinHash);
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
            binHash.set(i,BinaryService.addition(binHash.get(i), initialBinHash.get(i)));
        }

        return binHash;
    }

    public static List<String> compressions(List<List<String>> messageSchedules) {

        List<String> binHash = new ArrayList<>(ConstantsService.BIN_INITIAL_HASH);
        List<String> binHashPrev = new ArrayList<>(ConstantsService.BIN_INITIAL_HASH);

        for (List<String> messageSchedule : messageSchedules) {
            binHash = new ArrayList<>(compression(messageSchedule, binHashPrev));
            binHashPrev = new ArrayList<>(binHash);
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
        List<String> binaryToHash = compression(expandMessageSchedule, ConstantsService.BIN_INITIAL_HASH);
        return sha256(binaryToHash);
    }

    /**
     * In construction
     * @param message
     * @return
     */
    public static String sha256Complete(String message) {
        String binMessage = BinaryService.convertMessageStringToBinary(message);
        String paddedMessages = padding(binMessage);
        List<List<String>> initMessageSchedules = initMessageSchedules(paddedMessages);
        List<List<String>> expandMessageSchedules = expandMessageSchedules(initMessageSchedules);
        List<String> binHash = compressions(expandMessageSchedules);

        return sha256(binHash);
    }
}