package com.kimnoel.sha256.object;

import com.kimnoel.sha256.Utils.BitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HashTest {

	@Test
	public void fractionalPartOfSquareRootTo32BitsTest() {
		Assertions.assertEquals("01101010000010011110011001100111", Hash.fractionalPartOfSquareRootTo32Bits(2));
		Assertions.assertEquals("10111011011001111010111010000101", Hash.fractionalPartOfSquareRootTo32Bits(3));
		Assertions.assertEquals("00111100011011101111001101110010", Hash.fractionalPartOfSquareRootTo32Bits(5));
		Assertions.assertEquals("10100101010011111111010100111010", Hash.fractionalPartOfSquareRootTo32Bits(7));
		Assertions.assertEquals("01010001000011100101001001111111", Hash.fractionalPartOfSquareRootTo32Bits(11));
		Assertions.assertEquals("10011011000001010110100010001100", Hash.fractionalPartOfSquareRootTo32Bits(13));
		Assertions.assertEquals("00011111100000111101100110101011", Hash.fractionalPartOfSquareRootTo32Bits(17));
		Assertions.assertEquals("01011011111000001100110100011001", Hash.fractionalPartOfSquareRootTo32Bits(19));
	}

	@Test
	public void compressionTest() throws IllegalAccessException {
		String bitsMessage = BitUtils.messageToBits("abc");
		PaddedMessage paddedMessage = new PaddedMessage(bitsMessage);
		// paddedMessage here is expected to be only 512 bits length
		MessageSchedule messageSchedule = new MessageSchedule(paddedMessage);

		Hash hash = new Hash();
		List<MessageSchedule> messageSchedules = new ArrayList<>();
		messageSchedules.add(messageSchedule);

		hash.compression(messageSchedules);

		String expected = "10111010011110000001011010111111";
		Assertions.assertEquals(expected, hash.getA());

		expected = "10001111000000011100111111101010";
		Assertions.assertEquals(expected, hash.getB());

		expected = "01000001010000010100000011011110";
		Assertions.assertEquals(expected, hash.getC());

		expected = "01011101101011100010001000100011";
		Assertions.assertEquals(expected, hash.getD());

		expected = "10110000000000110110000110100011";
		Assertions.assertEquals(expected, hash.getE());

		expected = "10010110000101110111101010011100";
		Assertions.assertEquals(expected, hash.getF());

		expected = "10110100000100001111111101100001";
		Assertions.assertEquals(expected, hash.getG());

		expected = "11110010000000000001010110101101";
		Assertions.assertEquals(expected, hash.getH());
	}





	@Test
	public void sha256Test() throws IllegalAccessException {
		String bitsMessage = BitUtils.messageToBits("abc");
		PaddedMessage paddedMessage = new PaddedMessage(bitsMessage);
		// paddedMessage here is expected to be only 512 bits length
		MessageSchedule messageSchedule = new MessageSchedule(paddedMessage);

		Hash hash = new Hash();
		List<MessageSchedule> messageSchedules = new ArrayList<>();
		messageSchedules.add(messageSchedule);

		hash.compression(messageSchedules);

		Assertions.assertEquals("ba7816bf", hash.bitsToHash("A"));
		Assertions.assertEquals("8f01cfea", hash.bitsToHash("B"));
		Assertions.assertEquals("414140de", hash.bitsToHash("C"));
		Assertions.assertEquals("5dae2223", hash.bitsToHash("D"));
		Assertions.assertEquals("b00361a3", hash.bitsToHash("E"));
		Assertions.assertEquals("96177a9c", hash.bitsToHash("F"));
		Assertions.assertEquals("b410ff61", hash.bitsToHash("G"));
		Assertions.assertEquals("f20015ad", hash.bitsToHash("H"));
		Assertions.assertEquals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad", hash.bitsToHash());
	}

	@Test
	public void sha256Test2() {
		String expected = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
		Hash actual = new Hash("abc");
		Assertions.assertEquals(expected, actual.bitsToHash());

		expected = "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb";
		actual = new Hash("a");
		Assertions.assertEquals(expected, actual.bitsToHash());

		expected = "c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646";
		actual = new Hash("1234567890");
		Assertions.assertEquals(expected, actual.bitsToHash());
	}

	@Test
	public void sha256CompleteTest() {
		String expected = "2ece3a9ac0e61275fe34d89ea768aff7bf6df0b2bf37f0b08113f9b0fc9bbc83";
		String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
				"Mauris tincidunt diam eget turpis aliquet, volutpat volutpat enim accumsan. " +
				"Vivamus id urna pellentesque, volutpat tellus id, lobortis ex.";
		Hash actual = new Hash("abc");
		Assertions.assertEquals(expected, actual.bitsToHash());


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
		actual = new Hash(message);
		Assertions.assertEquals(expected, actual.bitsToHash());
	}
}
