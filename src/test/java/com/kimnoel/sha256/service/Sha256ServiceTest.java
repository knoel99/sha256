package com.kimnoel.sha256.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Sha256ServiceTest {


	@Test
	public void messageTest() {
		Assertions.assertEquals("011000010110001001100011", BinaryService.convertMessageStringToBinary("abc"));
	}

	@Test
	public void paddingTest() {
		String expected = "01100001011000100110001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011000";
		String actual = Sha256Service.padding("abc");
		Assertions.assertEquals(expected, actual);
		Assertions.assertEquals(512, actual.length());
	}

	@Test
	public void messageScheduleTest() {
		String expected = "01100001011000100110001110000000";
		String paddedMessage = Sha256Service.padding("abc");
		ArrayList<String> actual = Sha256Service.initMessageSchedule(paddedMessage);

		Assertions.assertEquals(expected, actual.get(0));
		Assertions.assertEquals(32, actual.get(0).length());

		expected = "00000000000000000000000000000000";
		Assertions.assertEquals(expected, actual.get(1));
		Assertions.assertEquals(32, actual.get(1).length());

		expected = "00000000000000000000000000011000";
		Assertions.assertEquals(expected, actual.get(15));
		Assertions.assertEquals(32, actual.get(15).length());
	}

}
