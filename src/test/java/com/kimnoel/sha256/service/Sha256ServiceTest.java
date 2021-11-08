package com.kimnoel.sha256.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Sha256ServiceTest {


	@Test
	public void messageTest() {
		Assertions.assertEquals("011000010110001001100011", WordService.convertMessageStringToBinary("abc"));
	}

	@Test
	public void paddingTest() {
		String expected = "01100001011000100110001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000011000";
		String binMessage = WordService.convertMessageStringToBinary("abc");
		String actual = Sha256Service.padding(binMessage);

		Assertions.assertEquals(expected, actual);
		Assertions.assertEquals(512, actual.length());

		// Test if the separator is placed after the message
		Assertions.assertEquals("1", actual.substring(binMessage.length(), binMessage.length()+1));
	}

	@Test
	public void messageScheduleTest() {
		String expected = "01100001011000100110001110000000";
		String binMessage = WordService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
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
		String binMessage = WordService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> actual = Sha256Service.expandMessageSchedule(initMessageSchedule);

		Assertions.assertEquals(expected, actual.get(47));

		expected = "00010010101100011110110111101011";
		Assertions.assertEquals(expected, actual.get(63));
	}


	@Test
	public void tmpWord1Test() {
		String expected = "01010100110110100101000011101000";
		String binMessage = WordService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> expandMessageSchedule = Sha256Service.expandMessageSchedule(initMessageSchedule);
		String actual = Sha256Service.tmpWord1(0, expandMessageSchedule, ConstantsService.BIN_INITIAL_HASH);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void tmpWord2Test() {
		String expected = "00001000100100001001101011100101";
		String actual = Sha256Service.tmpWord2(ConstantsService.BIN_INITIAL_HASH);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void compressionTest() {
		String binMessage = WordService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> expandMessageSchedule = Sha256Service.expandMessageSchedule(initMessageSchedule);
		List<String> actual = Sha256Service.compression(expandMessageSchedule, ConstantsService.BIN_INITIAL_HASH);

		String expected = "10111010011110000001011010111111";
		Assertions.assertEquals(expected, actual.get(0));

		expected = "10001111000000011100111111101010";
		Assertions.assertEquals(expected, actual.get(1));

		expected = "01000001010000010100000011011110";
		Assertions.assertEquals(expected, actual.get(2));

		expected = "01011101101011100010001000100011";
		Assertions.assertEquals(expected, actual.get(3));

		expected = "10110000000000110110000110100011";
		Assertions.assertEquals(expected, actual.get(4));

		expected = "10010110000101110111101010011100";
		Assertions.assertEquals(expected, actual.get(5));

		expected = "10110100000100001111111101100001";
		Assertions.assertEquals(expected, actual.get(6));

		expected = "11110010000000000001010110101101";
		Assertions.assertEquals(expected, actual.get(7));
	}

	@Test
	public void sha256Test() {
		String binMessage = WordService.convertMessageStringToBinary("abc");
		String paddedMessage = Sha256Service.padding(binMessage);
		List<String> initMessageSchedule = Sha256Service.initMessageSchedule(paddedMessage);
		List<String> expandMessageSchedule = Sha256Service.expandMessageSchedule(initMessageSchedule);
		List<String> actual = Sha256Service.compression(expandMessageSchedule, ConstantsService.BIN_INITIAL_HASH);

		Assertions.assertEquals("ba7816bf", WordService.binaryToHash(actual.get(0)));
		Assertions.assertEquals("8f01cfea", WordService.binaryToHash(actual.get(1)));
		Assertions.assertEquals("414140de", WordService.binaryToHash(actual.get(2)));
		Assertions.assertEquals("5dae2223", WordService.binaryToHash(actual.get(3)));
		Assertions.assertEquals("b00361a3", WordService.binaryToHash(actual.get(4)));
		Assertions.assertEquals("96177a9c", WordService.binaryToHash(actual.get(5)));
		Assertions.assertEquals("b410ff61", WordService.binaryToHash(actual.get(6)));
		Assertions.assertEquals("f20015ad", WordService.binaryToHash(actual.get(7)));
		Assertions.assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", Sha256Service.sha256(actual));
	}

	@Test
	public void sha256Test2() {
		String expected = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
		String actual = Sha256Service.sha256("abc");
		Assertions.assertEquals(expected, actual);

		expected = "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb";
		actual = Sha256Service.sha256("a");
		Assertions.assertEquals(expected, actual);

		expected = "c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646";
		actual = Sha256Service.sha256("1234567890");
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void sha256CompleteTest() {
		String expected = "2ece3a9ac0e61275fe34d89ea768aff7bf6df0b2bf37f0b08113f9b0fc9bbc83";
		String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
				"Mauris tincidunt diam eget turpis aliquet, volutpat volutpat enim accumsan. " +
				"Vivamus id urna pellentesque, volutpat tellus id, lobortis ex.";
		String actual = Sha256Service.sha256Complete(message);
		Assertions.assertEquals(expected, actual);


		expected = "6314bc42de9ce540e71aa532bf45ccc1c17514584013a45a7329c99c8248d6a2";
		message = "wyzgzJdaqV\n" +
				"MUhFcqojtu\n" +
				"YtSUVSQ57i\n" +
				"AkNbujoE0X\n" +
				"OCVliESTej\n" +
				"22KviAe3nQ\n" +
				"JkxkjsMXkc\n" +
				"e0jVfthJWw\n" +
				"3mCRCsQjPp\n" +
				"0uDvm6vv47\n";
		actual = Sha256Service.sha256Complete(message);
		Assertions.assertEquals(expected, actual);
	}

}
