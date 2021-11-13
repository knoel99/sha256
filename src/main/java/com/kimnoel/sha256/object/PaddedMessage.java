package com.kimnoel.sha256.object;


import com.kimnoel.sha256.service.BitUtils;

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

    public String getMessageBits() {
        return messageBits;
    }

    public void setMessageBits(String messageBits) {
        this.messageBits = messageBits;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getPaddedPart() {
        return paddedPart;
    }

    public void setPaddedPart(String paddedPart) {
        this.paddedPart = paddedPart;
    }

    public String getLengthPart() {
        return lengthPart;
    }

    public void setLengthPart(String lengthPart) {
        this.lengthPart = lengthPart;
    }
}
