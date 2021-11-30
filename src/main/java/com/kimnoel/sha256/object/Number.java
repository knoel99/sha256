package com.kimnoel.sha256.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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



    public Number(Double number){
        this.number = number;
        this.bits = Long.toBinaryString(number.longValue());
        this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
    }

    public Number(String bits){
        this.number = (double) Integer.parseInt(bits, 2);
        this.bits = bits;
        this.listBits = bits.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
    }

    public Number(List<Integer> listBits){
        this.listBits = listBits;
        this.bits = listBits.stream().map(Object::toString).collect(Collectors.joining());
        this.number = (double) Integer.parseInt(this.bits, 2);
    }

    @Override
    public String toString() {
        return "Number{" +
                "number=" + number +
                ", bits='" + bits + '\'' +
                ", listBits=" + listBits +
                '}';
    }
}
