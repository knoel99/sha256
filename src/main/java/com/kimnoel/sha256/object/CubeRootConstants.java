package com.kimnoel.sha256.object;

import com.kimnoel.sha256.config.PropertiesExtractor;

import java.util.ArrayList;
import java.util.List;

public class CubeRootConstants {

    private List<String> first64CubeRoots;

    public CubeRootConstants(){
        String[] primeList = PropertiesExtractor.getProperties("primes").split(",");
        List<String> result = new ArrayList<>();

        for (String prime : primeList){
            result.add(to32BitsFractionalPartOfCubeRoot(Integer.parseInt(prime)));
        }

    }
}
