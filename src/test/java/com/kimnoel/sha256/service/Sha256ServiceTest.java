package com.kimnoel.sha256.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Sha256ServiceTest {


	@Test
	public void messageTest() {
		Assertions.assertEquals("011000010110001001100011", BinaryService.convertMessageStringToBinary("abc"));
	}

	@Test
	public void paddingTest() {
		String expected = "01100001011000100110001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011000";
		String binMessage = BinaryService.convertMessageStringToBinary("abc");
		String actual = Sha256Service.padding(binMessage);

		Assertions.assertEquals(expected, actual);
		Assertions.assertEquals(512, actual.length());

		// Test if the separator is placed after the message
		Assertions.assertEquals("1", actual.substring(binMessage.length(), binMessage.length()+1));
	}

	@Test
	public void messageScheduleTest() {
		String expected = "01100001011000100110001110000000";
		String paddedMessage = Sha256Service.padding("abc");
		List<String> actual = Sha256Service.initMessageSchedule(paddedMessage);

		// Test if the message in binary is found at the head of the message schedule
		Assertions.assertEquals(expected, actual.get(0));
		Assertions.assertEquals(32, actual.get(0).length());

		// Test if the the next 32 bits are 0s in the message schedule
		expected = "00000000000000000000000000000000";
		Assertions.assertEquals(expected, actual.get(1));
		Assertions.assertEquals(32, actual.get(1).length());

		// Test if the length of the message in binary is at the tail of the message schedule
		expected = "00000000000000000000000000011000";
		Assertions.assertEquals(expected, actual.get(15));
		Assertions.assertEquals(32, actual.get(15).length());
	}

	@Test
	public void expandMessageScheduleTest() {
		String expected = "00000110010111000100001111011010";
		String binMessage = BinaryService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> actual = Sha256Service.expandMessageSchedule(initMessageSchedule);

		Assertions.assertEquals(expected, actual.get(47));

		expected = "00010010101100011110110111101011";
		Assertions.assertEquals(expected, actual.get(63));
	}


	@Test
	public void tempWord1Test() {
		String expected = "01010100110110100101000011101000";
		String binMessage = BinaryService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> expandMessageSchedule = Sha256Service.expandMessageSchedule(initMessageSchedule);
		String actual = Sha256Service.tempWord1(0, expandMessageSchedule);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void tempWord2Test() {
		String expected = "00001000100100001001101011100101";
		String actual = Sha256Service.tempWord2();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void compressionTest() {
		String expected = "0";
		String binMessage = BinaryService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> expandMessageSchedule = Sha256Service.expandMessageSchedule(initMessageSchedule);
		List<String> actual = Sha256Service.compression(expandMessageSchedule);

		System.out.println("Sha256Service.tempWord1(0, expandMessageSchedule); = "+ Sha256Service.tempWord1(63, expandMessageSchedule));
		System.out.println("Sha256Service.tempWord1(0, expandMessageSchedule); = "+ Sha256Service.tempWord2());
		System.out.println("a = "+ BinaryService.addition(Sha256Service.tempWord1(63, expandMessageSchedule), Sha256Service.tempWord2()));

		Assertions.assertEquals(expected, actual.get(0));
	}

}
