package com.kimnoel.sha256.object;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Hash {

    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    
    public Hash(){
        this.A = "01101010000010011110011001100111";
        this.B = "10111011011001111010111010000101";
        this.C = "00111100011011101111001101110010";
        this.D = "10100101010011111111010100111010";
        this.E = "01010001000011100101001001111111";
        this.F = "10011011000001010110100010001100";
        this.G = "00011111100000111101100110101011";
        this.H = "01011011111000001100110100011001";
    }

    public Hash(Hash hash){
        this.A = hash.getA();
        this.B = hash.getB();
        this.C = hash.getC();
        this.D = hash.getD();
        this.E = hash.getE();
        this.F = hash.getF();
        this.G = hash.getG();
        this.H = hash.getH();
    }


    public String bitsToHash() throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        for (Field field: this.getClass().getDeclaredFields()){
            Arrays.stream(field.get(this).toString().split("(?<=\\G.{4})"))
                    .forEach(s -> sb.append(bitsToHexa(s)));
        }

        return sb.toString();
    }

    public String bitsToHash(String attribute) {
        StringBuilder sb = new StringBuilder();
        switch (attribute) {
            case "A": Arrays.stream(this.getA().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "B": Arrays.stream(this.getB().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "C": Arrays.stream(this.getC().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "D": Arrays.stream(this.getD().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "E": Arrays.stream(this.getE().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "F": Arrays.stream(this.getF().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "G": Arrays.stream(this.getG().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
            case "H": Arrays.stream(this.getH().split("(?<=\\G.{4})")).forEach(s -> sb.append(bitsToHexa(s))); break;
        }

        return sb.toString();
    }

    public String bitsToHexa(String bits) {
        return Integer.toString(Integer.parseInt(bits,2),16);
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
