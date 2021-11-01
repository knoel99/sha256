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



}
