package com.kimnoel.sha256.service;

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

    @Value(value = "${primes}")
    private static String primes;


    public static String getConstants(String binary){
        List<String> primeList = Arrays.asList(primes.split(";"));


        return "0".repeat(shift) + binary.substring(0, length - shift);
    }

    public static byte getFractionalPart(Double x){
        return x.byteValue();
    }

}