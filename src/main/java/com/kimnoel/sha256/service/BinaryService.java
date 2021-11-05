package com.kimnoel.sha256.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryService {

    private static final Logger logger = LoggerFactory.getLogger(BinaryService.class);

    private BinaryService(){}

    /**
     * Rotate this string "123456789" into "000000123" by removing the last chunk of string with length shift = 6
     * and appending 0s at the beginning of te stripped string.
     * @param binary
     * @param shift
     * @return
     */
    public static String rightShift(String binary, int shift){
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
    public static String rotateRight(String binary, int shift){
        if (shift < 0) return binary;
        int length = binary.length();

        return binary.substring(length - shift, length) + binary.substring(0, length - shift);
    }

    /**
     * Apply XOR on strings of binary
     * @param binary1
     * @param binary2
     * @return
     */
    public static String xOr(String binary1, String binary2){
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

    public static String to32Bits(String binary){
        if (binary.length() < 32)
            return "0".repeat(32 - binary.length()) + binary;
        else
            return binary.substring(binary.length()-32);
    }

    public static String to64Bits(String binary){
        if (binary.length() < 64)
            return "0".repeat(64 - binary.length()) + binary;
        else
            return binary.substring(binary.length()-64);
    }

    public static String addition(String binary1, String binary2){
        Long dec1 = Long.parseLong(binary1, 2);
        Long dec2 = Long.parseLong(binary2, 2);

        Long sum = (dec1 + dec2) % 4294967296L; //4294967296L=2^32
        return to32Bits(Integer.toBinaryString(sum.intValue()));
    }

    public static String addition(String binary1, String binary2, String binary3, String binary4){
        Long dec1 = Long.parseLong(binary1, 2);
        Long dec2 = Long.parseLong(binary2, 2);
        Long dec3 = Long.parseLong(binary3, 2);
        Long dec4 = Long.parseLong(binary4, 2);

        Long sum = (dec1 + dec2 + dec3 + dec4) % 4294967296L; //4294967296L=2^32
        return to32Bits(Integer.toBinaryString(sum.intValue()));
    }

    public static String addition(String binary1, String binary2, String binary3, String binary4, String binary5){
        Long dec1 = Long.parseLong(binary1, 2);
        Long dec2 = Long.parseLong(binary2, 2);
        Long dec3 = Long.parseLong(binary3, 2);
        Long dec4 = Long.parseLong(binary4, 2);
        Long dec5 = Long.parseLong(binary5, 2);

        Long sum = (dec1 + dec2 + dec3 + dec4 + dec5) % 4294967296L; //4294967296L=2^32
        return to32Bits(Integer.toBinaryString(sum.intValue()));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%830-sigma0rb
     * σ0(b) = rotateRight(b,7) ^ rotateRight(b,18) ^ rightShift(b,3)
     * @param binary
     * @return
     */
    public static String sigma0(String binary){
        String b = to32Bits(binary);
        return xOr(
                xOr(rotateRight(b,7), rotateRight(b,18)),
                rightShift(b,3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%831-sigma1rb
     * σ1(b) = rotateRight(b,17) ^ rotateRight(b,19) ^ rightShift(b,10)
     * @param binary
     * @return
     */
    public static String sigma1(String binary){
        String b = to32Bits(binary);
        return xOr(
                xOr(rotateRight(b,17), rotateRight(b,19)),
                rightShift(b,10));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%830-usigma0rb
     * Σ0(b) = rotateRight(b,2) ^ rotateRight(b,13) ^ rotateRight(b,22)
     * @param binary
     * @return
     */
    public static String uSigma0(String binary){
        String b = to32Bits(binary);
        return xOr(
                xOr(rotateRight(b,2), rotateRight(b,13)),
                rotateRight(b,22));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%831-usigma1rb
     * Σ1(b) = rotateRight(b,6) ^ rotateRight(b,11) ^ rotateRight(b,25)
     * @param binary
     * @return
     */
    public static String uSigma1(String binary){
        String b = to32Bits(binary);
        return xOr(
                xOr(rotateRight(b,6), rotateRight(b,11)),
                rotateRight(b,25));
    }

    public static String and(String binary1, String binary2) {
        StringBuilder result = new StringBuilder();
        char[] b1 = binary1.toCharArray();
        char[] b2 = binary2.toCharArray();

        for (int i=0;i<binary1.length();i++) {
            result.append(('1' == b1[i] && '1' == b2[i])? "1" : "0");
        }
        return result.toString();

    }

    public static String not(String binary) {
        StringBuilder result = new StringBuilder();
        char[] b = binary.toCharArray();

        for (int i=0;i<binary.length();i++) {
            result.append(('1' == b[i])? "0" : "1");

        }
        return result.toString();

    }
    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#choice-chrb
     * This function uses the x bit to choose between the y and z bits.
     * It chooses the y bit if x=1, and chooses the z bit if x=0.
     * Ch(x, y, z) = (x & y) ^ (~x & z)
     * @param b1
     * @param b2
     * @param b3
     * @return
     */
    public static String choice(String b1, String b2, String b3){
        return xOr(and(b1, b2), and(not(b1), b3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#majority-majrb
     * This function returns the majority of the three bits.
     * Maj(x, y, z) = (x & y) ^ (x & z) ^ (y & z)
     * @param b1
     * @param b2
     * @param b3
     * @return
     */
    public static String majority(String b1, String b2, String b3){
        return xOr(xOr(and(b1, b2), and(b1, b3)), and(b2, b3));
    }

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
    public static String convertBinaryToMessageString(String input) {
        StringBuilder sb = new StringBuilder(); // Some place to store the chars

        Arrays.stream(input.split("(?<=\\G.{8})"))
                .forEach(s -> sb.append((char) Integer.parseInt(s, 2)));

        return sb.toString();
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
