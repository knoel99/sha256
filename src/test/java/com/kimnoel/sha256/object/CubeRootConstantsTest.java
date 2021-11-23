package com.kimnoel.sha256.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kimnoel.sha256.object.CubeRootConstants.fractionalPartOfCubeRootTo32Bits;

@SpringBootTest
public class CubeRootConstantsTest {


	@Test
	public void getFractionalPartOfCubeRootTest() {
		Assertions.assertEquals("01000010100010100010111110011000", fractionalPartOfCubeRootTo32Bits(2));
		Assertions.assertEquals("01110001001101110100010010010001", fractionalPartOfCubeRootTo32Bits(3));
		Assertions.assertEquals("10111110111110011010001111110111", fractionalPartOfCubeRootTo32Bits(307));
		Assertions.assertEquals("11000110011100010111100011110010", fractionalPartOfCubeRootTo32Bits(311));
	}

}
