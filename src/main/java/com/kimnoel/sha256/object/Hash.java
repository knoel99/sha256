package com.kimnoel.sha256.object;

import com.kimnoel.sha256.Utils.BitUtils;
import com.kimnoel.sha256.Utils.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hash {
    private static final Logger logger = LoggerFactory.getLogger(Hash.class);

    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;

    /**
     * No argument constructor generate the initial hash with bit values:
     * a= 01101010000010011110011001100111 = 6a09e667
     * b= 10111011011001111010111010000101 = bb67ae85
     * c= 00111100011011101111001101110010 = 3c6ef372
     * d= 10100101010011111111010100111010 = a54ff53a
     * e= 01010001000011100101001001111111 = 510e527f
     * f= 10011011000001010110100010001100 = 9b05688c
     * g= 00011111100000111101100110101011 = 1f83d9ab
     * h= 01011011111000001100110100011001 = 5be0cd19
     * 6a09e667bb67ae853c6ef372a54ff53a510e527f9b05688c1f83d9ab5be0cd19
     */
    public Hash() {
        this.A = fractionalPartOfSquareRootTo32Bits(2);
        this.B = fractionalPartOfSquareRootTo32Bits(3);
        this.C = fractionalPartOfSquareRootTo32Bits(5);
        this.D = fractionalPartOfSquareRootTo32Bits(7);
        this.E = fractionalPartOfSquareRootTo32Bits(11);
        this.F = fractionalPartOfSquareRootTo32Bits(13);
        this.G = fractionalPartOfSquareRootTo32Bits(17);
        this.H = fractionalPartOfSquareRootTo32Bits(19);
    }

    /**
     * Constructor used as a copy method.
     * @param hash hash to copy
     */
    public Hash(Hash hash) {
        this.A = hash.getA();
        this.B = hash.getB();
        this.C = hash.getC();
        this.D = hash.getD();
        this.E = hash.getE();
        this.F = hash.getF();
        this.G = hash.getG();
        this.H = hash.getH();
    }

    /**
     * Constructor that computes the hash of the input message.
     * For example for "abc" this gives:
     * a= 10111010011110000001011010111111 = ba7816bf
     * b= 10001111000000011100111111101010 = 8f01cfea
     * c= 01000001010000010100000011011110 = 414140de
     * d= 01011101101011100010001000100011 = 5dae2223
     * e= 10110000000000110110000110100011 = b00361a3
     * f= 10010110000101110111101010011100 = 96177a9c
     * g= 10110100000100001111111101100001 = b410ff61
     * h= 11110010000000000001010110101101 = f20015ad
     * ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad
     * @param message input message to hash
     */
    public Hash(String message) {
        try {
            String bitsMessage = BitUtils.messageToBits(message);
            PaddedMessage paddedMessage = new PaddedMessage(bitsMessage);
            List<MessageSchedule> messageSchedules = new ArrayList<>();

            Arrays.stream(paddedMessage.toString().split("(?<=\\G.{512})"))
                    .forEach(s -> {
                        MessageSchedule messageSchedule = new MessageSchedule(new PaddedMessage(s));
                        messageSchedule.expand();
                        messageSchedules.add(messageSchedule);
                    });

            Hash hash = new Hash();
            hash.compression(messageSchedules);

            this.A = hash.getA();
            this.B = hash.getB();
            this.C = hash.getC();
            this.D = hash.getD();
            this.E = hash.getE();
            this.F = hash.getF();
            this.G = hash.getG();
            this.H = hash.getH();

        } catch (Exception e) {
            logger.error("An error occured in Hash(String message)",e);
        }
    }

    public static String fractionalPartOfSquareRootTo32Bits(Integer x) {
        Double value = (Math.sqrt(x) % 1) * 4294967296d; //4294967296L=2^32

        return BitUtils.toNBits(Long.toBinaryString(value.longValue()), 32);
    }

    public void shiftDown() {
        this.H = this.getG();
        this.G = this.getF();
        this.F = this.getE();
        this.E = this.getD();
        this.D = this.getC();
        this.C = this.getB();
        this.B = this.getA();
        this.A = null;
    }

    public void compression(List<MessageSchedule> messageSchedules) throws IllegalAccessException {
        CubeRootConstants constants = new CubeRootConstants();
        Word tmpWord1, tmpWord2;
        List<Word> tmp = new ArrayList<>();

        for (MessageSchedule messageSchedule : messageSchedules) {
            Hash initialHash = new Hash(this);
            for (int i = 0; i < messageSchedule.getWordList().size(); i++) {
                tmp.clear();
                tmpWord1 = WordUtils.tmpWord1(i, constants, messageSchedule, this);
                tmpWord2 = WordUtils.tmpWord2(this);
                tmp.add(tmpWord1);
                tmp.add(tmpWord2);

                this.shiftDown();
                this.setA(WordUtils.addition(tmp).toString());

                tmp.remove(1);
                tmp.add(new Word(this.getE()));
                this.setE(WordUtils.addition(tmp).toString());
            }

            /*
            After we have compressed the entire message schedule,
            we add the resulting hash value to the initial hash value we started with.
            This gives us the final hash value for this message block.
             */
            for (Field field : this.getClass().getDeclaredFields()) {
                if (!isLoggerAttribute(field)) {
                    tmp.clear();
                    tmp.add(new Word(field.get(this).toString()));
                    tmp.add(new Word(field.get(initialHash).toString()));
                    field.set(this, WordUtils.addition(tmp).toString());
                }
            }
        }

    }

    public boolean isLoggerAttribute(Field field){
        return field.toString().contains("org.slf4j.Logger");
    }

    public String bitsToHash() {
        StringBuilder sb = new StringBuilder();
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (!isLoggerAttribute(field)) {
                    Arrays.stream(field.get(this).toString().split("(?<=\\G.{4})"))
                            .forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                }
            }
        } catch (Exception e) {
            logger.error("An error occured in Hash(String message)",e);
        }
        return sb.toString();
    }

    public String bitsToHash(String attribute) {
        StringBuilder sb = new StringBuilder();
        switch (attribute) {
            case "A":
                Arrays.stream(this.getA().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "B":
                Arrays.stream(this.getB().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "C":
                Arrays.stream(this.getC().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "D":
                Arrays.stream(this.getD().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "E":
                Arrays.stream(this.getE().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "F":
                Arrays.stream(this.getF().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "G":
                Arrays.stream(this.getG().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
            case "H":
                Arrays.stream(this.getH().split("(?<=\\G.{4})")).forEach(s -> sb.append(BitUtils.bitsToHexa(s)));
                break;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return
                "a= " + A + " = " + bitsToHash("A") + "\n" +
                "b= " + B + " = " + bitsToHash("B") + "\n" +
                "c= " + C + " = " + bitsToHash("C") + "\n" +
                "d= " + D + " = " + bitsToHash("D") + "\n" +
                "e= " + E + " = " + bitsToHash("E") + "\n" +
                "f= " + F + " = " + bitsToHash("F") + "\n" +
                "g= " + G + " = " + bitsToHash("G") + "\n" +
                "h= " + H + " = " + bitsToHash("H") + "\n" +
                bitsToHash();
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getG() {
        return G;
    }

    public void setG(String g) {
        G = g;
    }

    public String getH() {
        return H;
    }

    public void setH(String h) {
        H = h;
    }
}
