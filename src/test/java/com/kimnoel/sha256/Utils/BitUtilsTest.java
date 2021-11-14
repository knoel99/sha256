package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.Utils.BitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitUtilsTest {

	@Test
	public void messageTest() {
		Assertions.assertEquals("011000010110001001100011", BitUtils.messageToBits("abc"));
		Assertions.assertEquals("0100100001100101011011000110110001101111", BitUtils.messageToBits("Hello"));
	}


	@Test
	public void bitsTest() {
		Assertions.assertEquals("abc", BitUtils.bitsToMessage("011000010110001001100011"));
	}

	@Test
	public void binaryToHexaTest() {
		Assertions.assertEquals("a", BitUtils.bitsToHexa("1010"));
		Assertions.assertEquals("b", BitUtils.bitsToHexa("1011"));
		Assertions.assertEquals("c", BitUtils.bitsToHexa("1100"));
		Assertions.assertEquals("0", BitUtils.bitsToHexa("0000"));
		Assertions.assertEquals("1", BitUtils.bitsToHexa("0001"));
	}

	@Test
	public void toNBitsTest() {
		Assertions.assertEquals("00000000000000000000000000000000", BitUtils.toNBits("0000",32));
		Assertions.assertEquals("00000000000000000000000000000001", BitUtils.toNBits("1",32));
		Assertions.assertEquals("00000000000000000000001001001001", BitUtils.toNBits("1001001001",32));
		Assertions.assertEquals("21098765432109876543210987654321", BitUtils.toNBits("0987654321".repeat(4),32));
	}

}
