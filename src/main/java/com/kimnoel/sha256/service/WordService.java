package com.kimnoel.sha256.service;

import com.kimnoel.sha256.object.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordService {

    private static final Logger logger = LoggerFactory.getLogger(WordService.class);

    private WordService(){}



    /**
     * Source: https://mkyong.com/java/java-convert-string-to-binary/
     * @param input
     * @return
     */
    public static String convertMessageStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
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
     * Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
     * Go through each 8-char-section and turn it into an int and then to a char
     * @param input Binary input as String
     * @return Output text (t)
     */
    public static String binaryToMessageString(String input) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(input.split("(?<=\\G.{8})"))
                .forEach(s -> sb.append((char) Integer.parseInt(s, 2)));

        return sb.toString();
    }

    public static String binaryToHash(String input) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(input.split("(?<=\\G.{4})"))
                .forEach(s -> sb.append(binaryToHexa(s)));

        return sb.toString();
    }

    public static String binaryToHexa(String binaryStr) {
        return Integer.toString(Integer.parseInt(binaryStr,2),16);
    }

    /**
     * Source: https://mkyong.com/java/java-convert-string-to-binary/
     * @param binary
     * @param blockSize
     * @param separator
     * @return
     */
    public static String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }
}
