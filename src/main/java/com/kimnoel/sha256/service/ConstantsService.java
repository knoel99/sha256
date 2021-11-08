package com.kimnoel.sha256.service;

import com.kimnoel.sha256.config.PropertiesExtractor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ConstantsService {

    private static final Logger logger = LoggerFactory.getLogger(ConstantsService.class);

    private ConstantsService(){}

    public static final String A = "01101010000010011110011001100111";
    public static final String B = "10111011011001111010111010000101";
    public static final String C = "00111100011011101111001101110010";
    public static final String D = "10100101010011111111010100111010";
    public static final String E = "01010001000011100101001001111111";
    public static final String F = "10011011000001010110100010001100";
    public static final String G = "00011111100000111101100110101011";
    public static final String H = "01011011111000001100110100011001";

    public static final List<String> BIN_CONSTANT_64_FIRST_CUBE_ROOT = getBinConstant64FirstCubeRoot();
    public static final List<String> BIN_INITIAL_HASH = getBinInitialHash();


    public static List<String> getBinInitialHash(){
        List<String> result = new ArrayList<>();
        result.add(A);
        result.add(B);
        result.add(C);
        result.add(D);
        result.add(E);
        result.add(F);
        result.add(G);
        result.add(H);
        return result;
    }

    public static List<String> getBinConstant64FirstCubeRoot(){
        String[] primeList = PropertiesExtractor.getProperties("primes").split(",");
        List<String> result = new ArrayList<>();

        for (String prime : primeList){
            result.add(to32BitsFractionalPartOfCubeRoot(Integer.parseInt(prime)));
        }

        return result;
    }

    public static String to32BitsFractionalPartOfCubeRoot(Integer x){
        Double value = (Math.cbrt(x) % 1)*4294967296d; //4294967296L=2^32

        return BinaryService.to32Bits(Long.toBinaryString(value.longValue()));
    }


    public static String to32BitsFractionalPartOfSquareRoot(Integer x){
        Double value = (Math.sqrt(x) % 1)*4294967296d; //4294967296L=2^32

        return BinaryService.to32Bits(Long.toBinaryString(value.longValue()));
    }

}