package com.kimnoel.sha256.bitwise.object;

import com.kimnoel.sha256.bitwise.utils.BitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaddedMessageTest {

	@Test
	public void paddingTest() {
		String expected = "01100001011000100110001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011000";
		String bitsMessage = BitUtils.messageToBits("abc");
		PaddedMessage actual = new PaddedMessage(bitsMessage);

		Assertions.assertEquals(expected, actual.toString());
		Assertions.assertEquals(512, actual.toString().length());

		// Test if the separator is placed after the message
		Assertions.assertEquals("1", actual.toString().substring(bitsMessage.length(), bitsMessage.length()+1));
	}
}
