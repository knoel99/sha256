package com.kimnoel.sha256.service;

import com.kimnoel.sha256.config.PropertiesExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstantsService {

    private static final Logger logger = LoggerFactory.getLogger(ConstantsService.class);

    private ConstantsService(){}




    public static List<String> getConstants(){
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

}