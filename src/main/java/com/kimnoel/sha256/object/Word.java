package com.kimnoel.sha256.object;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A group of either 32 bits (4 bytes) or 64 bits (8 bytes).
 */
public class Word {
    
    private String bits;
    private Integer length;

    public Word(){}

    public Word(String bits){
        this.bits = bits;
        this.length = bits.length();
    }

    public Word(String bits, int nbBits){
        this.bits = toNBits(bits, nbBits);
        this.length = nbBits;
    }

    public String toNBits(String bits, int nbBits){
        if (bits.length() < nbBits)
            return "0".repeat(nbBits - bits.length()) + bits;
        else
            return bits.substring(bits.length()-nbBits);
    }

    /**
     * Source: https://mkyong.com/java/java-convert-string-to-binary/
     * @param blockSize number of bits per block
     * @param separator between two blocks of bits
     * @return the word
     */
    public String toPrettyString(int blockSize, String separator) {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(bits.split("(?<=\\G.{"+blockSize+"})"))
                .forEach(s -> sb.append(s).append(separator));
        // Delete last separator
        return sb.substring(0,separator.length());
    }

    @Override
    public String toString() {
        return bits;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
