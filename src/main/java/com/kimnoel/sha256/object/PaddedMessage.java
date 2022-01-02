package com.kimnoel.sha256.object;


import com.kimnoel.sha256.Utils.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Source: https://github.com/in3rsha/sha256-animation/blob/master/README.md#6-padding-paddingrb
 * The SHA-256 hash function works on data in 512-bit chunks, so all messages need to be padded
 * with zeros up to the nearest multiple of 512 bits.
 *
 * Furthermore, to prevent similar inputs from hashing to the same result, we separate the message
 * from the zeros with a 1 bit, and also include the size of the message in the last 64 bits of
 * the padding.
 *
 * NOTE: This method of separating the message with a 1 and including the message size in the
 * padding is known as Merkle–Damgård strengthening (MD strengthening).
 */
@Getter
@Setter
public class PaddedMessage {

    private String messageBits;
    private String separator;
    private String paddedPart;
    private String lengthPart;


    public PaddedMessage(String messageBits){
        this.messageBits = messageBits;
        this.separator = "1";

        int nbPaddingZeros = (448 - (this.messageBits.length() + this.separator.length())) % 512;
        if (nbPaddingZeros < 0) nbPaddingZeros += 512;
        this.paddedPart = "0".repeat(nbPaddingZeros);

        this.lengthPart = BitUtils.toNBits(Integer.toBinaryString(messageBits.length()), 64);
    }


    @Override
    public String toString() {
        return messageBits + separator + paddedPart + lengthPart;
    }

}
