package com.kimnoel.sha256.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinaryService {


    /**
     * Rotate this string "123456789" into "000000123" by removing the last chunk of string with length shift = 6
     * and appending 0s at the beginning of te stripped string.
     * @param binary
     * @param shift
     * @return
     */
    public String rightShift(String binary, int shift){
        if (shift < 0) return binary;
        int length = binary.length();

        return "0".repeat(shift) + binary.substring(0, length - shift);
    }

    /**
     * Rotate this string "123456789" into "456789123" by moving the last chunk of string with length shift = 6
     * to the end of the result
     * @param binary
     * @param shift
     * @return
     */
    public String rotateRight(String binary, int shift){
        if (shift < 0) return binary;
        int length = binary.length();

        return binary.substring(length - shift, length) + binary.substring(0, length - shift);
    }

    public String xOrBinary(String binary1, String binary2){
        binary1 = binary1.replace(" ", "");
        binary2 = binary2.replace(" ", "");

        if (binary1.length() == binary2.length()) {
            StringBuilder result = new StringBuilder();
            char[] b1 = binary1.toCharArray();
            char[] b2 = binary2.toCharArray();

            for (int i=0;i<binary1.length();i++) {
                result.append(Integer.parseInt(String.valueOf(b1[i])) ^
                        Integer.parseInt(String.valueOf(b2[i])));
            }
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * Source: https://mkyong.com/java/java-convert-string-to-binary/
     * @param input
     * @return
     */
    public String convertStringToBinary(String input) {

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
     * Source: https://mkyong.com/java/java-convert-string-to-binary/
     * @param binary
     * @param blockSize
     * @param separator
     * @return
     */
    public String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }
}
