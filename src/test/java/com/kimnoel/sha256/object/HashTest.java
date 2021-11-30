package com.kimnoel.sha256.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashTest {

	@Test
	public void fractionalPartOfSquareRootTo32BitsTest() {
		Assertions.assertEquals("01101010000010011110011001100111", Hash.fractionalPartOfSquareRootTo32Bits(2));
		Assertions.assertEquals("10111011011001111010111010000101", Hash.fractionalPartOfSquareRootTo32Bits(3));
		Assertions.assertEquals("00111100011011101111001101110010", Hash.fractionalPartOfSquareRootTo32Bits(5));
		Assertions.assertEquals("10100101010011111111010100111010", Hash.fractionalPartOfSquareRootTo32Bits(7));
		Assertions.assertEquals("01010001000011100101001001111111", Hash.fractionalPartOfSquareRootTo32Bits(11));
		Assertions.assertEquals("10011011000001010110100010001100", Hash.fractionalPartOfSquareRootTo32Bits(13));
		Assertions.assertEquals("00011111100000111101100110101011", Hash.fractionalPartOfSquareRootTo32Bits(17));
		Assertions.assertEquals("01011011111000001100110100011001", Hash.fractionalPartOfSquareRootTo32Bits(19));
	}

	@Test
	public void compressionTest() {
		Hash hash = new Hash("abc");

		String expected = "10111010011110000001011010111111";
		Assertions.assertEquals(expected, hash.getA());

		expected = "10001111000000011100111111101010";
		Assertions.assertEquals(expected, hash.getB());

		expected = "01000001010000010100000011011110";
		Assertions.assertEquals(expected, hash.getC());

		expected = "01011101101011100010001000100011";
		Assertions.assertEquals(expected, hash.getD());

		expected = "10110000000000110110000110100011";
		Assertions.assertEquals(expected, hash.getE());

		expected = "10010110000101110111101010011100";
		Assertions.assertEquals(expected, hash.getF());

		expected = "10110100000100001111111101100001";
		Assertions.assertEquals(expected, hash.getG());

		expected = "11110010000000000001010110101101";
		Assertions.assertEquals(expected, hash.getH());
	}


	@Test
	public void sha256Test() {
		Hash hash = new Hash("abc");

		Assertions.assertEquals("ba7816bf", hash.getHexDigest("A"));
		Assertions.assertEquals("8f01cfea", hash.getHexDigest("B"));
		Assertions.assertEquals("414140de", hash.getHexDigest("C"));
		Assertions.assertEquals("5dae2223", hash.getHexDigest("D"));
		Assertions.assertEquals("b00361a3", hash.getHexDigest("E"));
		Assertions.assertEquals("96177a9c", hash.getHexDigest("F"));
		Assertions.assertEquals("b410ff61", hash.getHexDigest("G"));
		Assertions.assertEquals("f20015ad", hash.getHexDigest("H"));
		Assertions.assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", hash.getHexDigest());
	}
}
