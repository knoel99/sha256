package com.kimnoel.sha256.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConstantServiceTest {


	@Test
	public void getFractionalPartOfCubeRootTest() {
		Assertions.assertEquals("01000010100010100010111110011000", ConstantsService.to32BitsFractionalPartOfCubeRoot(2));
		Assertions.assertEquals("01110001001101110100010010010001", ConstantsService.to32BitsFractionalPartOfCubeRoot(3));
		Assertions.assertEquals("10111110111110011010001111110111", ConstantsService.to32BitsFractionalPartOfCubeRoot(307));
		Assertions.assertEquals("11000110011100010111100011110010", ConstantsService.to32BitsFractionalPartOfCubeRoot(311));
	}


	@Test
	public void to32BitsFractionalPartOfSquareRootTest() {
		Assertions.assertEquals("01101010000010011110011001100111", ConstantsService.to32BitsFractionalPartOfSquareRoot(2));
		Assertions.assertEquals("10111011011001111010111010000101", ConstantsService.to32BitsFractionalPartOfSquareRoot(3));
		Assertions.assertEquals("00111100011011101111001101110010", ConstantsService.to32BitsFractionalPartOfSquareRoot(5));
		Assertions.assertEquals("10100101010011111111010100111010", ConstantsService.to32BitsFractionalPartOfSquareRoot(7));
		Assertions.assertEquals("01010001000011100101001001111111", ConstantsService.to32BitsFractionalPartOfSquareRoot(11));
		Assertions.assertEquals("10011011000001010110100010001100", ConstantsService.to32BitsFractionalPartOfSquareRoot(13));
		Assertions.assertEquals("00011111100000111101100110101011", ConstantsService.to32BitsFractionalPartOfSquareRoot(17));
		Assertions.assertEquals("01011011111000001100110100011001", ConstantsService.to32BitsFractionalPartOfSquareRoot(19));
	}

}
