package com.kimnoel.sha256.object;

import com.kimnoel.sha256.config.PropertiesExtractor;
import com.kimnoel.sha256.service.BitUtils;

import java.util.ArrayList;
import java.util.List;

public class CubeRootConstants {

    private List<String> first64CubeRoots;

    public CubeRootConstants(){
        this.first64CubeRoots = new ArrayList<>();
        String[] primeList = PropertiesExtractor.getProperties("primes").split(",");

        for (String prime : primeList){
            this.first64CubeRoots.add(fractionalPartOfCubeRootTo32Bits(Integer.parseInt(prime)));
        }
    }

    public static String fractionalPartOfCubeRootTo32Bits(Integer x){
        Double value = (Math.cbrt(x) % 1)*4294967296d; //4294967296L=2^32

        return BitUtils.toNBits(Long.toBinaryString(value.longValue()), 32);
    }

    public List<String> getFirst64CubeRoots() {
        return first64CubeRoots;
    }

    public void setFirst64CubeRoots(List<String> first64CubeRoots) {
        this.first64CubeRoots = first64CubeRoots;
    }
}
