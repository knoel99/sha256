package com.kimnoel.sha256.object;


import com.kimnoel.sha256.Utils.BitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;


/**
 * This class is the representation of the fields of the blockchain header used in the
 * computation of the proof of work.
 * Each field is a string and is a number written either in decimal, binary or hexadecimal
 * format. The field format indicates which format.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockchainHeader {
    private static final Logger logger = LoggerFactory.getLogger(Hash.class);

    private String version;
    private String hashPrevBlock;
    private String hashMerkleRoot;
    private String timeStamp;
    private String target;
    private String nonce;
    private String format;


    public void toDecimal() {
        try {
            int radix=0;
            if (format.equalsIgnoreCase("BINARY")) radix=2;
            else if (format.equalsIgnoreCase("HEXADECIMAL")) radix=16;
            else return;

            for (Field field : this.getClass().getDeclaredFields()) {
                // Ignore field=format for the number conversion
                if (!field.toString().contains("format") && !field.toString().contains("org.slf4j.Logger") ) {
                    field.set(this, String.valueOf(Long.parseLong(field.get(this).toString(), radix)));

                } else if (field.toString().contains("format")) {
                    field.set(this, "DECIMAL");
                }
            }
        } catch (Exception e){
            logger.error("An error occurred in toDecimal:", e);
        }

    }

    public void toBinaryString(){
        try {
            int radix=0;
            if (format.equalsIgnoreCase("DECIMAL")) radix=10;
            else if (format.equalsIgnoreCase("HEXADECIMAL")) radix=16;
            else return;

            for (Field field : this.getClass().getDeclaredFields()) {
                // Ignore field=format for the number conversion
                if (!field.toString().contains("format") && !field.toString().contains("org.slf4j.Logger") ) {
                    field.set(this, Long.toBinaryString(Long.parseLong(field.get(this).toString(), radix)));

                } else if (field.toString().contains("format")) {
                    field.set(this, "BINARY");
                }
            }
        } catch (Exception e){
            logger.error("An error occurred in toBinaryString:", e);
        }
    }

    public void toHexadecimal(){
        try {
            StringBuilder sb = new StringBuilder();
            int radix;
            if (format.equalsIgnoreCase("DECIMAL")) radix=10;
            else if (format.equalsIgnoreCase("BINARY")) radix=2;
            else return;

            for (Field field : this.getClass().getDeclaredFields()) {
                if (!field.toString().contains("format") && !field.toString().contains("org.slf4j.Logger") ) {
                    Arrays.stream(field.get(this).toString().split("(?<=\\G.{4})"))
                            .forEach(s -> sb.append(Long.toString(Long.parseLong(s, radix), 16)));
                    field.set(this, sb.toString());

                    //Clear sb
                    sb.setLength(0);

                } else if (field.toString().contains("format")) {
                    field.set(this, "HEXADECIMAL");
                }
            }
        } catch (Exception e){
            logger.error("An error occurred in toDecimal:", e);
        }
    }

    @Override
    public String toString() {
        return version + hashPrevBlock + hashMerkleRoot + timeStamp + target + nonce;
    }

    //TODO
    /**
     * Example: https://yogh.io/block/00000000000000000009fb658b571325c2e59cca752ed452fa7f9e3d6e7dfa71/
     * @return
     */
    public String toHexString() {
        return version + hashPrevBlock + hashMerkleRoot + timeStamp + target + nonce;
    }
}
