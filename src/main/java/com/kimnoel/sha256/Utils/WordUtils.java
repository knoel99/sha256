package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.object.CubeRootConstants;
import com.kimnoel.sha256.object.Hash;
import com.kimnoel.sha256.object.MessageSchedule;
import com.kimnoel.sha256.object.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WordUtils {

    private static final Logger logger = LoggerFactory.getLogger(WordUtils.class);

    private WordUtils(){}

    /**
     * Rotate this string "123456789" into "000000123" (not using bits for readability).
     * The trick is to remove the last chunk of string with length shift = 6
     * and appending 0s at the beginning of te stripped string.
     * @param shift
     */
    public static Word rightShift(Word w, int shift){
        if (shift < 0) return null;
        String bits = "0".repeat(shift) + w.getBits().substring(0, w.getLength() - shift);
        return new Word(bits, bits.length());
    }

    /**
     * Rotate this string "123456789" into "456789123" (not using bits for readability).
     * The trick is to move the last chunk of string with length shift = 6
     * to the end of the result
     * @param shift
     */
    public static Word rotateRight(Word w, int shift){
        if (shift < 0) return null;
        String bits = w.getBits().substring(w.getLength() - shift) + w.getBits().substring(0, w.getLength() - shift);
        return new Word(bits, bits.length());
    }

    /**
     * Apply XOR on two words with same bit length.
     * @param w2 word 2
     * @return new Word with xOR-ed bits
     */
    public static Word xOr(Word w1, Word w2){
        if (w1.getBits().length() == w2.getBits().length()) {
            StringBuilder result = new StringBuilder();
            char[] b1 = w1.getBits().toCharArray();
            char[] b2 = w2.getBits().toCharArray();

            for (int i=0;i<w1.getBits().length();i++) {
                result.append(Integer.parseInt(String.valueOf(b1[i])) ^
                        Integer.parseInt(String.valueOf(b2[i])));
            }
            return new Word(result.toString(), w1.getLength());
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
    public static Word addition(List<Word> words){
        Long sum = 0L;
        long power = Long.parseLong(words.get(0).getLength().toString());

        for (Word w : words) {
            sum += Long.parseLong(w.getBits(), 2);
        }

        double sumD = sum % Math.pow(2, power);
        return new Word(Long.toBinaryString((long) sumD));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%830-sigma0rb
     * σ0(b) = rotateRight(b,7) ^ rotateRight(b,18) ^ rightShift(b,3)
     * @param w word
     * @return σ0(w)
     */
    public static Word sigma0(Word w){
        return xOr(
                xOr(rotateRight(w,7), rotateRight(w,18)),
                rightShift(w,3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%831-sigma1rb
     * σ1(b) = rotateRight(b,17) ^ rotateRight(b,19) ^ rightShift(b,10)
     * @param w word
     * @return σ1(w)
     */
    public static Word sigma1(Word w){
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
    public static Word bigSigma0(Word w){
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
    public static Word bigSigma1(Word w){
        return xOr(
                xOr(rotateRight(w,6), rotateRight(w,11)),
                rotateRight(w,25));
    }

    public static Word and(Word w1, Word w2) {
        StringBuilder result = new StringBuilder();
        char[] b1 = w1.getBits().toCharArray();
        char[] b2 = w2.getBits().toCharArray();

        for (int i=0;i<w1.getLength();i++) {
            result.append(('1' == b1[i] && '1' == b2[i])? "1" : "0");
        }
        return new Word(result.toString(), w1.getLength());

    }

    public static Word not(Word w) {
        StringBuilder result = new StringBuilder();
        char[] b = w.getBits().toCharArray();

        for (int i=0;i<w.getLength();i++) {
            result.append(('1' == b[i])? "0" : "1");

        }
        return new Word(result.toString(), w.getLength());
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
    public static Word choice(Word w1, Word w2, Word w3){
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
    public static Word majority(Word w1, Word w2, Word w3){
        return xOr(xOr(and( w1,  w2), and( w1,  w3)), and( w2,  w3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#temporary-word-1-t1rb
     * This temporary word takes the next word in the message schedule along with the next constant from the list.
     * These values added to a Σ1 rotation of the fifth value in the state register, the choice of the values in the
     * last three registers, and the value of the last register on its own.
     * T1 = Σ1(e) + Ch(e, f, g) + h + Kt + Wt
     * @param index index
     * @param cubeRootConstants cubeRootConstants
     * @param messageSchedule messageSchedule
     * @param hash hash
     * @return T1
     */
    public static Word tmpWord1(int index, CubeRootConstants cubeRootConstants, MessageSchedule messageSchedule, Hash hash) {
        List<Word> tmp = new ArrayList<>();
        tmp.add(WordUtils.bigSigma1(new Word(hash.getE())));
        tmp.add(WordUtils.choice(new Word(hash.getE()), new Word(hash.getF()), new Word(hash.getG())));
        tmp.add(new Word(hash.getH()));
        tmp.add(new Word(cubeRootConstants.getFirst64CubeRoots().get(index)));
        tmp.add(messageSchedule.getWordList().get(index));

        return WordUtils.addition(tmp);
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#temporary-word-2-t2rb
     * This temporary word is calculated by adding a Σ0 rotation of the first value in the state register to
     * a majority of the values in the first three registers.
     * T2 = Σ0(a) + Maj(a, b, c)
     * @param hash hash
     * @return T2
     */
    public static Word tmpWord2(Hash hash) {
        List<Word> tmp = new ArrayList<>();
        tmp.add(WordUtils.bigSigma0(new Word(hash.getA())));
        tmp.add(WordUtils.majority(new Word(hash.getA()), new Word(hash.getB()), new Word(hash.getC())));
        return WordUtils.addition(tmp);
    }

}
