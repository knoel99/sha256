package com.kimnoel.sha256.object;


import java.util.List;

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


    /**
     * Rotate this string "123456789" into "000000123" (not using bits for readability).
     * The trick is to remove the last chunk of string with length shift = 6
     * and appending 0s at the beginning of te stripped string.
     * @param shift
     */
    public void rightShift(int shift){
        if (shift < 0) return;
        this.bits = "0".repeat(shift) + bits.substring(0, bits.length() - shift);
    }

    /**
     * Rotate this string "123456789" into "456789123" (not using bits for readability).
     * The trick is to move the last chunk of string with length shift = 6
     * to the end of the result
     * @param shift
     */
    public void rotateRight(int shift){
        if (shift < 0) return;
        bits = bits.substring(bits.length() - shift) + bits.substring(0, bits.length() - shift);
    }

    /**
     * Apply XOR on two words with same bit length.
     * @param w2 word 2
     * @return new Word with xOR-ed bits
     */
    public Word xOr(Word w2){
        if (this.bits.length() == w2.getBits().length()) {
            StringBuilder result = new StringBuilder();
            char[] b1 = this.bits.toCharArray();
            char[] b2 = w2.getBits().toCharArray();

            for (int i=0;i<this.bits.length();i++) {
                result.append(Integer.parseInt(String.valueOf(b1[i])) ^
                        Integer.parseInt(String.valueOf(b2[i])));
            }
            return new Word(result.toString());
        } else {
            return null;
        }
    }

    /**
     * Addition modulo 2^(length of bits) of a list of words
     * @param words list of words
     * @return
     */
    public Word addition(List<Word> words){
        Long dec1 = Long.parseLong(this.bits, 2);
        Long dec = 0L;
        long power = Long.parseLong(this.getLength().toString());

        for (Word w : words) {
            dec += Long.parseLong(w.getBits(), 2);
        }

        double sum = (dec1 + dec) % Math.pow(2, power);
        return new Word(Integer.toBinaryString((int) sum), this.bits.length());
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%830-sigma0rb
     * σ0(b) = rotateRight(b,7) ^ rotateRight(b,18) ^ rightShift(b,3)
     * @param binary
     * @return
     */
    public String sigma0(){
        return xOr(
                rotateRight(7).xOr(this.rotateRight(18)),
                rightShift(3));
    }

    /**
     * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#%CF%831-sigma1rb
     * σ1(b) = rotateRight(b,17) ^ rotateRight(b,19) ^ rightShift(b,10)
     * @param binary
     * @return
     */
    public String sigma1(String binary){
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
    public String uSigma0(String binary){
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
    public String uSigma1(String binary){
        String b = to32Bits(binary);
        return xOr(
                xOr(rotateRight(b,6), rotateRight(b,11)),
                rotateRight(b,25));
    }

    public String and(String binary1, String binary2) {
        StringBuilder result = new StringBuilder();
        char[] b1 = binary1.toCharArray();
        char[] b2 = binary2.toCharArray();

        for (int i=0;i<binary1.length();i++) {
            result.append(('1' == b1[i] && '1' == b2[i])? "1" : "0");
        }
        return result.toString();

    }

    public String not(String binary) {
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
    public String choice(String b1, String b2, String b3){
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
    public String majority(String b1, String b2, String b3){
        return xOr(xOr(and(b1, b2), and(b1, b3)), and(b2, b3));
    }


    public String toNBits(String bits, int nbBits){
        if (bits.length() < nbBits)
            return "0".repeat(nbBits - bits.length()) + bits;
        else
            return bits.substring(bits.length()-nbBits);
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
