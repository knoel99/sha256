package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.object.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashUtils {

    private static final Logger logger = LoggerFactory.getLogger(HashUtils.class);

    private HashUtils(){}

    public static String hash(String message){
        return new Hash(message).getHexDigest();
    }



}
