package com.kimnoel.sha256.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BinaryServiceTest {

	@Autowired
	BinaryService binaryService;

	@Test
	void convertStringToBinaryTest() {
		Assertions.assertEquals("011000010110001001100011", binaryService.convertMessageStringToBinary("abc"));
		Assertions.assertEquals("0100100001100101011011000110110001101111", binaryService.convertMessageStringToBinary("Hello"));
	}

	@Test
	void prettyBinaryTest() {
		Assertions.assertEquals(
				"01100001 01100010 01100011",
				binaryService.prettyBinary("011000010110001001100011", 8, " "));
		Assertions.assertEquals(
				"01001000 01100101 01101100 01101100 01101111",
				binaryService.prettyBinary("0100100001100101011011000110110001101111",8," "));
	}

	@Test
	void rightShiftTest() {
		String input = "011000010110001001100011";
		Assertions.assertEquals("011000010110001001100011", binaryService.rightShift(input, -1));
		Assertions.assertEquals("011000010110001001100011", binaryService.rightShift(input, 0));
		Assertions.assertEquals("001100001011000100110001", binaryService.rightShift(input, 1));
		Assertions.assertEquals("000000000110000101100010", binaryService.rightShift(input,8));
		Assertions.assertEquals("000000000000000000000001", binaryService.rightShift(input,input.length()-2));
		Assertions.assertEquals("000000000000000000000000", binaryService.rightShift(input,input.length()));
	}

	@Test
	void rotateRightTest() {
		Assertions.assertEquals("123456789", binaryService.rotateRight("123456789", -1));
		Assertions.assertEquals("123456789", binaryService.rotateRight("123456789", 0));
		Assertions.assertEquals("912345678", binaryService.rotateRight("123456789", 1));
		Assertions.assertEquals("456789123", binaryService.rotateRight("123456789",6));
		Assertions.assertEquals("123456789", binaryService.rotateRight("123456789","123456789".length()));
	}

	@Test
	void xOrTest() {
		Assertions.assertEquals("0000", binaryService.xOr("0000","0000"));
		Assertions.assertEquals("1111", binaryService.xOr("1111","0000"));
		Assertions.assertEquals("1111", binaryService.xOr("0000","1111"));
		Assertions.assertEquals("0000", binaryService.xOr("1111","1111"));
	}

	@Test
	void to32BitsTest() {
		Assertions.assertEquals("00000000000000000000000000000000", binaryService.to32Bits("0000"));
		Assertions.assertEquals("00000000000000000000000000000001", binaryService.to32Bits("1"));
		Assertions.assertEquals("00000000000000000000001001001001", binaryService.to32Bits("1001001001"));
		Assertions.assertEquals("21098765432109876543210987654321", binaryService.to32Bits("0987654321".repeat(4)));
	}

	@Test
	void additionTest() {
		String expected = "00000000000000000000000000000000";
		String binary1  = "0";
		String binary2  = "00000";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2));

		expected = "00000000000000000000000000001111";
		binary1  = "1111";
		binary2  = "00000000000";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2));

		expected = "00000000000000000000010010010010";
		binary1  = "00000000000000000000001001001001";
		binary2  = "00000000000000000000001001001001";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2));

		expected = "00000000000000000000000000000000";
		binary1  = "11111111111111111111111111111111";
		binary2  = "1";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2));
	}

	@Test
	void additionTest2() {
		String expected = "00000000000000000000000000000000";
		String binary1  = "0";
		String binary2  = "00";
		String binary3  = "0000";
		String binary4  = "0000000000";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2, binary3, binary4));

		expected = "00000000000000000000000000000100";
		binary1  = "1";
		binary2  = "01";
		binary3  = "001";
		binary4  = "0001";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2, binary3, binary4));

		expected = "00000000000000000000100100100100";
		binary1  = "00000000000000000000001001001001";
		binary2  = "00000000000000000000001001001001";
		binary3  = "00000000000000000000001001001001";
		binary4  = "00000000000000000000001001001001";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2, binary3, binary4));

		expected = "00000000000000000111111111111111";
		binary1  = "11111111111111111111111111111111";
		binary2  = "1";
		binary3  = "00000000000000000100000000000000";
		binary4  = "00000000000000000011111111111111";
		Assertions.assertEquals(expected, binaryService.addition(binary1, binary2, binary3, binary4));
	}

	@Test
	void sigma0Test() {
		String expected = "11110001111111111100011110000000";
		String binary   = "00000000000000000011111111111111";
		Assertions.assertEquals(expected, binaryService.sigma0(binary));
	}

	@Test
	void sigma1Test() {
		String expected = "00011000000000000110000000001111";
		String binary   = "00000000000000000011111111111111";
		Assertions.assertEquals(expected, binaryService.sigma1(binary));
	}

	@Test
	void uSigma0Test() {
		String expected = "00111111000001111111001111111110";
		String binary   = "00000000000000000011111111111111";
		Assertions.assertEquals(expected, binaryService.uSigma0(binary));
	}

	@Test
	void uSigma1Test() {
		String expected = "00000011111111111111111101111000";
		String binary   = "00000000000000000011111111111111";
		Assertions.assertEquals(expected, binaryService.uSigma1(binary));
	}

	@Test
	void andTest() {
		String expected = "100010";
		String binary1  = "101010";
		String binary2  = "100110";
		Assertions.assertEquals(expected, binaryService.and(binary1, binary2));
	}

	@Test
	void notTest() {
		String expected = "100010";
		String binary   = "011101";
		Assertions.assertEquals(expected, binaryService.not(binary));
	}

	@Test
	void choiceTest() {
		String expected = "11111111000000000000000011111111";
		String binary1  = "00000000111111110000000011111111";
		String binary2  = "00000000000000001111111111111111";
		String binary3  = "11111111111111110000000000000000";
		Assertions.assertEquals(expected, binaryService.choice(binary1,binary2,binary3));
	}

	@Test
	void majorityTest() {
		String expected = "00000000111111110000000011111111";
		String binary1  = "00000000111111110000000011111111";
		String binary2  = "00000000000000001111111111111111";
		String binary3  = "11111111111111110000000000000000";

		Assertions.assertEquals(expected, binaryService.majority(binary1,binary2,binary3));
	}

}
