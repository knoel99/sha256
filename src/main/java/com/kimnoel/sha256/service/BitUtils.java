package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class BitUtils {

    private static final Logger logger = LoggerFactory.getLogger(BitUtils.class);

    private BitUtils(){}

    /**
     * Normalize bits with a given length
     * @param bits initial value
     * @param nbBits length
     * @return final values
     */
    public static String toNBits(String bits, int nbBits){
        if (bits.length() < nbBits)
            return "0".repeat(nbBits - bits.length()) + bits;
        else
            return bits.substring(bits.length()-nbBits);
    }

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

}
