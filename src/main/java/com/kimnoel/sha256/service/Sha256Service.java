package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Sha256Service {

    private static final Logger logger = LoggerFactory.getLogger(Sha256Service.class);

    private Sha256Service(){}



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
        String[] paddedMsgList = paddedMessage.split("(?<=\\G.{512})");

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
            messageSchedule.add(WordUtils.addition(
                    WordUtils.sigma1(messageSchedule.get(i-2)),
                    messageSchedule.get(i-7),
                    WordUtils.sigma0(messageSchedule.get(i-15)),
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
                messageSchedule.add(WordUtils.addition(
                        WordUtils.sigma1(messageSchedule.get(i - 2)),
                        messageSchedule.get(i - 7),
                        WordUtils.sigma0(messageSchedule.get(i - 15)),
                        messageSchedule.get(i - 16))
                );
            }
        }
        return messageSchedules;
    }



    public static List<String> compression(List<String> messageSchedule, List<String> initialBinHash) {
        List<String> binHash = new ArrayList<>(initialBinHash);
        String tmpWord1, tmpWord2;

        for (int i=0; i<messageSchedule.size(); i++) {
            tmpWord1 = tmpWord1(i, messageSchedule, binHash);
            tmpWord2 = tmpWord2(binHash);

            binHash.add(0, WordUtils.addition(tmpWord1, tmpWord2));
            binHash.set(4, WordUtils.addition(binHash.get(4), tmpWord1));
            binHash.remove(binHash.size()-1);
        }
        
        /*
        After we have compressed the entire message schedule,
        we add the resulting hash value to the initial hash value we started with.
        This gives us the final hash value for this message block.
         */
        for (int i=0; i<binHash.size(); i++) {
            binHash.set(i, WordUtils.addition(binHash.get(i), initialBinHash.get(i)));
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
        binHash.forEach(s -> sb.append(WordUtils.binaryToHash(s)));
        return sb.toString();
    }

    public static String sha256(String message) {
        String binMessage = WordUtils.convertMessageStringToBinary(message);
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
        String binMessage = WordUtils.convertMessageStringToBinary(message);
        String paddedMessages = padding(binMessage);
        List<List<String>> initMessageSchedules = initMessageSchedules(paddedMessages);
        List<List<String>> expandMessageSchedules = expandMessageSchedules(initMessageSchedules);
        List<String> binHash = compressions(expandMessageSchedules);

        return sha256(binHash);
    }
}