package com.kimnoel.sha256.object;


import com.kimnoel.sha256.Utils.BitUtils;

import java.util.Arrays;

/**
 * A group of either 32 bits (4 bytes) or 64 bits (8 bytes).
 */
public class Word {
    
    private String bits;
    private Integer length;

    public Word(){}

    public Word(String bits){
        this.bits = BitUtils.toNBits(bits, 32);;
        this.length = this.bits.length();
    }

    public Word(String bits, int nbBits){
        this.bits = BitUtils.toNBits(bits, nbBits);
        this.length = nbBits;
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
        return sb.substring(0, sb.length() - separator.length());
    }

    @Override
    public String toString() {
        if(bits !=null) return bits; else return "";
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
