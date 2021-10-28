package com.kimnoel.sha256;

import com.kimnoel.sha256.service.BinaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sha256ApplicationTests {

	@Autowired
	BinaryService binaryService;

	@Test
	void convertStringToBinaryTest() {
		String message = "Hello";
		System.out.println(binaryService.convertStringToBinary(message));
		System.out.println(binaryService.prettyBinary(
				binaryService.convertStringToBinary(message), 8, " "));
		Assertions.assertEquals("011000010110001001100011", binaryService.convertStringToBinary("abc"));
		Assertions.assertEquals("0100100001100101011011000110110001101111", binaryService.convertStringToBinary("Hello"));
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
		Assertions.assertEquals(
				"011000010110001001100011", binaryService.rightShift("011000010110001001100011", -1));
		Assertions.assertEquals(
				"011000010110001001100011", binaryService.rightShift("011000010110001001100011", 0));
		Assertions.assertEquals(
				"001100001011000100110001", binaryService.rightShift("011000010110001001100011", 1));
		Assertions.assertEquals(
				"000000000110000101100010", binaryService.rightShift("011000010110001001100011",8));
		Assertions.assertEquals(
				"000000000000000000000001",
				binaryService.rightShift("011000010110001001100011","011000010110001001100011".length()-2));
		Assertions.assertEquals(
				"000000000000000000000000",
				binaryService.rightShift("011000010110001001100011","011000010110001001100011".length()));
	}

	@Test
	void rotateRightTest() {
		Assertions.assertEquals(
				"123456789", binaryService.rotateRight("123456789", -1));
		Assertions.assertEquals(
				"123456789", binaryService.rotateRight("123456789", 0));
		Assertions.assertEquals(
				"912345678", binaryService.rotateRight("123456789", 1));
		Assertions.assertEquals(
				"456789123", binaryService.rotateRight("123456789",6));
		Assertions.assertEquals(
				"123456789", binaryService.rotateRight("123456789","123456789".length()));
	}

	@Test
	void xOrTest() {
		Assertions.assertEquals("0000", binaryService.xOrBinary("0000","0000"));
		Assertions.assertEquals("1111", binaryService.xOrBinary("1111","0000"));
		Assertions.assertEquals("1111", binaryService.xOrBinary("0000","1111"));
		Assertions.assertEquals("0000", binaryService.xOrBinary("1111","1111"));
	}
}
