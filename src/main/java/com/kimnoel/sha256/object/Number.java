package com.kimnoel.sha256.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Number {
    private static final Logger logger = LoggerFactory.getLogger(Number.class);

    Double number;
    String bits;
    List<Integer> listBits;
    List<Integer> listMathBits;
    int formatNbBits;



    public Number(Double number){
        this.number = number;
        this.bits = Long.toBinaryString(number.longValue());
        this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
        this.listMathBits = new ArrayList<>(this.listBits);
        this.formatNbBits = 0;
        Collections.reverse(this.listMathBits);
    }
    public Number(Double number, int formatNbBits){
        this.formatNbBits = formatNbBits;
        if (formatNbBits == 0) {
            this.number = number;
            this.bits = Long.toBinaryString(number.longValue());
            this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
        } else {
            this.number = number;
            this.bits = Long.toBinaryString(number.longValue());
            this.bits = "0".repeat(formatNbBits - this.bits.length()) + this.bits;
            this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
        }
        this.listMathBits = new ArrayList<>(this.listBits);
        Collections.reverse(this.listMathBits);

    }

    public Number(String bits){
        this.number = (double) Integer.parseInt(bits, 2);
        this.bits = bits;
        this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
        this.listMathBits = new ArrayList<>(this.listBits);
        Collections.reverse(this.listMathBits);
    }

    public Number(String bits, int formatNbBits){
        this.formatNbBits = formatNbBits;
        if (formatNbBits == 0) {
            this.number = (double) Integer.parseInt(bits, 2);
            this.bits = bits;
            this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
        } else {
            String bitsNoZerosInHeader = bits.substring(formatNbBits - bits.length() + 1);
            this.number = (double) Integer.parseInt(bitsNoZerosInHeader, 2);
            this.bits = bits;
            this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
        }
        this.listMathBits = new ArrayList<>(this.listBits);
        Collections.reverse(this.listMathBits);
    }

    public Number(List<Integer> listBits){
        this.listBits = listBits;
        this.bits = listBits.stream().map(Object::toString).collect(Collectors.joining());
        this.number = (double) Integer.parseInt(this.bits, 2);
        this.listMathBits = new ArrayList<>(this.listBits);
        Collections.reverse(this.listMathBits);
    }

    public Number(List<Integer> listBits, int formatNbBits){
        this.formatNbBits = formatNbBits;
        if (formatNbBits == 0) {
            this.listBits = listBits;
            this.bits = listBits.stream().map(Object::toString).collect(Collectors.joining());
            this.number = (double) Integer.parseInt(this.bits, 2);
        } else {
            this.listBits = listBits;
            this.bits = listBits.stream().map(Object::toString).collect(Collectors.joining());
            this.number = (double) Integer.parseInt(this.bits.substring(formatNbBits-this.bits.length()+1), 2);
        }
        this.listMathBits = new ArrayList<>(this.listBits);
        Collections.reverse(this.listMathBits);
    }

    public String toSumPowerString(){
        StringBuilder sb = new StringBuilder();
        for (int k=0 ; k < this.listMathBits.size() ; k++){
            sb.append("x").append(this.listMathBits.size() - k - 1)
                    .append("*2^")
                    .append(this.listMathBits.size() - k - 1)
                    .append(" + ");
        }
        // Remove last + sign
        sb.substring(sb.length() - " + ".length());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Number{" +
                "number=" + number +
                ", bits='" + bits + '\'' +
                ", listBits=" + listBits +
                ", listMathBits=" + listMathBits +
                '}';
    }
}
