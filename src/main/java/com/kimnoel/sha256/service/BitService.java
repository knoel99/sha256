package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class BitService {

    private static final Logger logger = LoggerFactory.getLogger(BitService.class);

    private BitService(){}

    /**
     * Source: https://mkyong.com/java/java-convert-string-to-binary/
     * @param message input message
     * @return bits
     */
    public static String messageToBits(String message) {

        StringBuilder result = new StringBuilder();
        char[] chars = message.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }

    /**
     * Source: https://stackoverflow.com/a/37907714/10944474
     * Splits the bits string into 8-char-sections (Since a char has 8 bits = 1 byte)
     * Go through each 8-char-section and turn it into an int and then to a char
     * @param bits as String
     * @return Output text (t)
     */
    public static String bitsToMessage(String bits) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(bits.split("(?<=\\G.{8})"))
                .forEach(s -> sb.append((char) Integer.parseInt(s, 2)));

        return sb.toString();
    }

    public static String bitsToHash(String bits) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(bits.split("(?<=\\G.{4})"))
                .forEach(s -> sb.append(bitsToHexa(s)));

        return sb.toString();
    }

    public static String bitsToHexa(String bits) {
        return Integer.toString(Integer.parseInt(bits,2),16);
    }
    
}
