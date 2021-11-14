package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.object.Hash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashUtilsTest {

	@Test
	public void sha256Test2() {
		String expected = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
		String actual = HashUtils.hash("abc");
		Assertions.assertEquals(expected, actual);

		expected = "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb";
		actual = HashUtils.hash("a");
		Assertions.assertEquals(expected, actual);

		expected = "c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646";
		actual = HashUtils.hash("1234567890");
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void sha256LongMessageTest() {
		String expected = "2ece3a9ac0e61275fe34d89ea768aff7bf6df0b2bf37f0b08113f9b0fc9bbc83";
		String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
				"Mauris tincidunt diam eget turpis aliquet, volutpat volutpat enim accumsan. " +
				"Vivamus id urna pellentesque, volutpat tellus id, lobortis ex.";
		String actual = HashUtils.hash(message);
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
		actual = HashUtils.hash(message);
		Assertions.assertEquals(expected, actual);
	}

}
