package com.kimnoel.sha256.object;

import com.kimnoel.sha256.Utils.BitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageScheduleTest {

	@Test
	public void messageScheduleTest() {
		String expected = "01100001011000100110001110000000";
		String bitsMessage = BitUtils.messageToBits("abc");
		PaddedMessage paddedMessage = new PaddedMessage(bitsMessage);
		// paddedMessage here is expected to be only 512 bits length
		MessageSchedule actual = new MessageSchedule(paddedMessage);

		// Test if the message in binary is found at the head of the message schedule
		Assertions.assertEquals(expected, actual.getWordList().get(0).getBits());
		Assertions.assertEquals(32, actual.getWordList().get(0).getLength());

		// Test if the the next 32 bits are 0s in the message schedule
		expected = "00000000000000000000000000000000";
		Assertions.assertEquals(expected, actual.getWordList().get(1).getBits());
		Assertions.assertEquals(32, actual.getWordList().get(1).getLength());

		// Test if the length of the message in binary is at the tail of the message schedule
		expected = "00000000000000000000000000011000";
		Assertions.assertEquals(expected, actual.getWordList().get(15).getBits());
		Assertions.assertEquals(32, actual.getWordList().get(15).getLength());
	}

	@Test
	public void expandMessageScheduleTest() {
		String expected = "01100001011000100110001110000000";
		String bitsMessage = BitUtils.messageToBits("abc");
		PaddedMessage paddedMessage = new PaddedMessage(bitsMessage);
		// paddedMessage here is expected to be only 512 bits length
		MessageSchedule actual = new MessageSchedule(paddedMessage);
		actual.expand();

		Assertions.assertEquals("01100001011000100110001110000000", actual.getWordList().get(16).getBits());
		Assertions.assertEquals("00000000000011110000000000000000", actual.getWordList().get(17).getBits());
		Assertions.assertEquals("01111101101010000110010000000101", actual.getWordList().get(18).getBits());
		Assertions.assertEquals("01100000000000000000001111000110", actual.getWordList().get(19).getBits());
		Assertions.assertEquals("00111110100111010111101101111000", actual.getWordList().get(20).getBits());
		Assertions.assertEquals("00000001100000111111110000000000", actual.getWordList().get(21).getBits());
		Assertions.assertEquals("00010010110111001011111111011011", actual.getWordList().get(22).getBits());
		Assertions.assertEquals(expected, actual.getWordList().get(47).getBits());

		expected = "00010010101100011110110111101011";
		Assertions.assertEquals(expected, actual.getWordList().get(63).getBits());
	}
}
