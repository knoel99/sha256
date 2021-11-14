package com.kimnoel.sha256.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordTest {

	@Test
	public void prettyBitsTest() {
		String expected = "01100001 01100010 01100011";
		String input = "011000010110001001100011";
		String actual = new Word(input, input.length()).toPrettyString(8, " ");
		Assertions.assertEquals(expected, actual);

		expected = "01001000 01100101 01101100 01101100 01101111";
		input = "0100100001100101011011000110110001101111";
		actual = new Word(input, input.length()).toPrettyString(8," ");
		Assertions.assertEquals(expected, actual);
	}

}
