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
     * Rotate this string "123456789" into "000000123" (not using bits for readability).
     * The trick is to remove the last chunk of string with length shift = 6
     * and appending 0s at the beginning of te stripped string.
     * @param shift
     */
    public Word rightShift(Word w, int shift){
        if (shift < 0) return null;
        w.setBits("0".repeat(shift) + w.getBits().substring(0, w.getLength() - shift));
        return w;
    }

    /**
     * Rotate this string "123456789" into "456789123" (not using bits for readability).
     * The trick is to move the last chunk of string with length shift = 6
     * to the end of the result
     * @param shift
     */
    public Word rotateRight(Word w, int shift){
        if (shift < 0) return null;
        w.setBits(w.getBits().substring(w.getLength() - shift) + w.getBits().substring(0, w.getLength() - shift));
        return w;
    }

    /**
     * Apply XOR on two words with same bit length.
     * @param w2 word 2
     * @return new Word with xOR-ed bits
     */
    public Word xOr(Word w1, Word w2){
        if (w1.getBits().length() == w2.getBits().length()) {
            StringBuilder result = new StringBuilder();
            char[] b1 = w1.getBits().toCharArray();
            char[] b2 = w2.getBits().toCharArray();

            for (int i=0;i<w1.getBits().length();i++) {
                result.append(Integer.parseInt(String.valueOf(b1[i])) ^
                        Integer.parseInt(String.valueOf(b2[i])));
            }
            return new Word(result.toString());
        } else {
            return null;
        }
    }

    /**
     * Addition modulo 2^(length of bits) of a list of words.
     * For example:
     *
     * The operation x + y is defined as follows. 
     * The words x and y represent integers X and Y, where 0 ≤ X < 2w and 0 ≤ Y < 2w
     *
     * For positive integers U and V, let U modV be the remainder upon dividing U by V. 
     * Compute Z=( X + Y ) mod 2w
     * 
     * Then 0 ≤ Z < 2w
     * Convert the integer Z to a word, z, and define z=x + y.
     * @param words list of words
     * @return the addition
     */
    public Word addition(List<Word> words){
        Long sum = 0L;
        long power = Long.parseLong(words.get(0).getLength().toString());

        for (Word w : words) {
            sum += Long.parseLong(w.getBits(), 2);
        }

        double sumD = sum % Math.pow(2, power);
        return new Word(Integer.toBinaryString((int) sumD));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%830-sigma0rb
     * σ0(b) = rotateRight(b,7) ^ rotateRight(b,18) ^ rightShift(b,3)
     * @param w
     * @return
     */
    public Word sigma0(Word w){
        return xOr(
                xOr(rotateRight(w,7), rotateRight(w,18)),
                rightShift(w,3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%831-sigma1rb
     * σ1(b) = rotateRight(b,17) ^ rotateRight(b,19) ^ rightShift(b,10)
     * @param w
     * @return
     */
    public Word sigma1(Word w){
        return xOr(
                xOr(rotateRight(w,17), rotateRight(w,19)),
                rightShift(w,10));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%830-usigma0rb
     * Σ0(b) = rotateRight(b,2) ^ rotateRight(b,13) ^ rotateRight(b,22)
     * @param w
     * @return
     */
    public Word uSigma0(Word w){
        return xOr(
                xOr(rotateRight(w,2), rotateRight(w,13)),
                rotateRight(w,22));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%831-usigma1rb
     * Σ1(b) = rotateRight(b,6) ^ rotateRight(b,11) ^ rotateRight(b,25)
     * @param w
     * @return
     */
    public Word uSigma1(Word w){
        return xOr(
                xOr(rotateRight(w,6), rotateRight(w,11)),
                rotateRight(w,25));
    }

    public Word and(Word w1, Word w2) {
        StringBuilder result = new StringBuilder();
        char[] b1 = w1.getBits().toCharArray();
        char[] b2 = w2.getBits().toCharArray();

        for (int i=0;i<w1.getLength();i++) {
            result.append(('1' == b1[i] && '1' == b2[i])? "1" : "0");
        }
        return new Word(result.toString());

    }

    public Word not(Word w) {
        StringBuilder result = new StringBuilder();
        char[] b = w.getBits().toCharArray();

        for (int i=0;i<w.getLength();i++) {
            result.append(('1' == b[i])? "0" : "1");

        }
        return new Word(result.toString());
    }
    
    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#choice-chrb
     * This function uses the x bit to choose between the y and z bits.
     * It chooses the y bit if x=1, and chooses the z bit if x=0.
     * Ch(x, y, z) = (x & y) ^ (~x & z)
     * @param w1 word 1
     * @param w2 word 2
     * @param w3 word 3
     * @return result
     */
    public Word choice(Word w1, Word w2, Word w3){
        return xOr(and(w1, w2), and(not(w1), w3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#majority-majrb
     * This function returns the majority of the three bits.
     * Maj(x, y, z) = (x & y) ^ (x & z) ^ (y & z)
     * @param w1 word 1
     * @param w2 word 2
     * @param w3 word 3
     * @return result
     */
    public Word majority(Word w1, Word w2, Word w3){
        return xOr(xOr(and( w1,  w2), and( w1,  w3)), and( w2,  w3));
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
    
}
