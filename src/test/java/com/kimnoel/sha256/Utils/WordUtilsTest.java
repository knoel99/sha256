package com.kimnoel.sha256.Utils;

import com.kimnoel.sha256.object.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.kimnoel.sha256.Utils.WordUtils.*;

public class WordUtilsTest {

	@Test
	public void rightShiftTest() {
		String input = "011000010110001001100011";
		Assertions.assertEquals("011000010110001001100011", Objects.requireNonNull(rightShift(new Word(input, 24), 0)).toString());
		Assertions.assertEquals("001100001011000100110001", Objects.requireNonNull(rightShift(new Word(input, 24), 1)).toString());
		Assertions.assertEquals("000110000101100010011000", Objects.requireNonNull(rightShift(new Word(input, 24), 2)).toString());
		Assertions.assertEquals("000000000110000101100010", Objects.requireNonNull(rightShift(new Word(input, 24), 8)).toString());
		Assertions.assertEquals("000000000000000000000001", Objects.requireNonNull(rightShift(new Word(input, 24), new Word(input, 24).getLength() - 2)).toString());
		Assertions.assertEquals("000000000000000000000000", Objects.requireNonNull(rightShift(new Word(input, 24), new Word(input, 24).getLength())).toString());
	}

	@Test
	public void rotateRightTest() {
		Assertions.assertNull(rotateRight(new Word("123456789"), -1));
		Assertions.assertEquals("123456789", Objects.requireNonNull(rotateRight(new Word("123456789",9), 0)).toString());
		Assertions.assertEquals("912345678", Objects.requireNonNull(rotateRight(new Word("123456789",9), 1)).toString());
		Assertions.assertEquals("456789123", Objects.requireNonNull(rotateRight(new Word("123456789",9), 6)).toString());
		Assertions.assertEquals("123456789", Objects.requireNonNull(rotateRight(new Word("123456789",9), "123456789".length())).toString());
	}

	@Test
	public void xOrTest() {
		Assertions.assertEquals("0000", Objects.requireNonNull(xOr(new Word("0000",4), new Word("0000",4))).toString());
		Assertions.assertEquals("1111", Objects.requireNonNull(xOr(new Word("1111",4), new Word("0000",4))).toString());
		Assertions.assertEquals("1111", Objects.requireNonNull(xOr(new Word("0000",4), new Word("1111",4))).toString());
		Assertions.assertEquals("0000", Objects.requireNonNull(xOr(new Word("1111",4), new Word("1111",4))).toString());
	}
	

	@Test
	public void additionTest() {
		String expected = "00000000000000000000000000000000";
		String bit1  = "0";
		String bit2  = "00000";
		List<Word> words = new ArrayList<>();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "00000000000000000000000000001111";
		bit1  = "1111";
		bit2  = "00000000000";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "00000000000000000000010010010010";
		bit1  = "00000000000000000000001001001001";
		bit2  = "00000000000000000000001001001001";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "00000000000000000000000000000000";
		bit1  = "11111111111111111111111111111111";
		bit2  = "1";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		Assertions.assertEquals(expected, addition(words).toString());
	}

	@Test
	public void additionTest2() {
		String expected = "00000000000000000000000000000000";
		String bit1  = "0";
		String bit2  = "00";
		String bit3  = "0000";
		String bit4  = "0000000000";
		List<Word> words = new ArrayList<>();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		words.add(new Word(bit3));
		words.add(new Word(bit4));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "00000000000000000000000000000100";
		bit1  	 = "00000000000000000000000000000001";
		bit2  	 = "00000000000000000000000000000001";
		bit3  	 = "00000000000000000000000000000001";
		bit4  	 = "00000000000000000000000000000001";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		words.add(new Word(bit3));
		words.add(new Word(bit4));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "00000000000000000000100100100100";
		bit1  	 = "00000000000000000000001001001001";
		bit2	 = "00000000000000000000001001001001";
		bit3  	 = "00000000000000000000001001001001";
		bit4  	 = "00000000000000000000001001001001";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		words.add(new Word(bit3));
		words.add(new Word(bit4));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "00000000000000000111111111111111";
		bit1  	 = "11111111111111111111111111111111";
		bit2  	 = "1";
		bit3  	 = "00000000000000000100000000000000";
		bit4  	 = "00000000000000000011111111111111";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		words.add(new Word(bit3));
		words.add(new Word(bit4));
		Assertions.assertEquals(expected, addition(words).toString());
	}

	@Test
	public void additionTest3() {
		String expected = "00000000000000000000000000000001";
		String bit1 	= "11111111111111111111111111111111";
		String bit2 	= "00000000000000000000000000000010";
		List<Word> words = new ArrayList<>();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		Assertions.assertEquals(expected, addition(words).toString());

		expected = "11100010111000101100001110001110";
		bit1  	 = "01100001011000100110001110000000";
		bit2  	 = "10000001100000000110000000001110";
		words.clear();
		words.add(new Word(bit1));
		words.add(new Word(bit2));
		Assertions.assertEquals(expected, addition(words).toString());
	}
		@Test
	public void additionTest4() {
		String expected = "11100010111000101100001110001110";
		String w7 		= "00000000000000000000000000000000";
		String sigma0W8 = "00000000000000000000000000000000";
		String w16 		= "01100001011000100110001110000000";
		String sigma1W21= "10000001100000000110000000001110";

		List<Word> words = new ArrayList<>();
		words.add(new Word(w7));
		words.add(new Word(sigma0W8));
		words.add(new Word(w16));
		words.add(new Word(sigma1W21));
		Assertions.assertEquals(expected, addition(words).toString());
	}

		@Test
	public void sigma0Test() {
		String expected = "11110001111111111100011110000000";
		String bits 	= "00000000000000000011111111111111";
		Word w = new Word(bits);
		Assertions.assertEquals(expected, sigma0(w).toString());

		// Intermediate value for tmpWord2
		Hash hash = new Hash();
		expected = "11001110001000001011010001111110";
		w = new Word(hash.getA());
		Assertions.assertEquals(expected, bigSigma0(w).toString());
	}

	@Test
	public void sigma1Test() {
		String expected = "00011000000000000110000000001111";
		String bits   	= "00000000000000000011111111111111";
		Assertions.assertEquals(expected, sigma1(new Word(bits)).toString());
	}

	@Test
	public void bigSigma0Test() {
		String expected = "00111111000001111111001111111110";
		String bits   	= "00000000000000000011111111111111";
		Assertions.assertEquals(expected, bigSigma0(new Word(bits)).toString());
	}

	@Test
	public void bigSigma1Test() {
		String expected = "00000011111111111111111101111000";
		String bits   	= "00000000000000000011111111111111";
		Assertions.assertEquals(expected, bigSigma1(new Word(bits)).toString());
	}

	@Test
	public void andTest() {
		String expected = "100010";
		String bits1  	= "101010";
		String bits2  	= "100110";
		Assertions.assertEquals(expected, and(new Word(bits1,6), new Word(bits2,6)).toString());
	}

	@Test
	public void notTest() {
		String expected = "100010";
		String bits   = "011101";
		Assertions.assertEquals(expected, not(new Word(bits,6)).toString());
	}

	@Test
	public void choiceTest() {
		String expected = "11111111000000000000000011111111";
		String bits1  = "00000000111111110000000011111111";
		String bits2  = "00000000000000001111111111111111";
		String bits3  = "11111111111111110000000000000000";
		Assertions.assertEquals(expected, choice(new Word(bits1), new Word(bits2), new Word(bits3)).toString());
	}

	@Test
	public void majorityTest() {
		String expected = "00000000111111110000000011111111";
		String bits1  = "00000000111111110000000011111111";
		String bits2  = "00000000000000001111111111111111";
		String bits3  = "11111111111111110000000000000000";
		Assertions.assertEquals(expected, majority(new Word(bits1), new Word(bits2), new Word(bits3)).toString());

		// Intermediate value for tmpWord2
		Hash hash = new Hash();
		expected = "00111010011011111110011001100111";
		Assertions.assertEquals(expected, majority(new Word(hash.getA()), new Word(hash.getB()), new Word(hash.getC())).toString());
	}

	@Test
	public void tmpWord1Test() {
		String expected = "01010100110110100101000011101000";

		CubeRootConstants constants = new CubeRootConstants();
		String bitsMessage = BitUtils.messageToBits("abc");
		PaddedMessage paddedMessage = new PaddedMessage(bitsMessage);
		// paddedMessage here is expected to be only 512 bits length
		MessageSchedule messageSchedule = new MessageSchedule(paddedMessage);
		Hash hash = new Hash();
		List<MessageSchedule> messageSchedules = new ArrayList<>();
		messageSchedules.add(messageSchedule);

		Word actual = tmpWord1(0, constants, messageSchedules.get(0), hash);
		Assertions.assertEquals(expected, actual.toString());
	}

	@Test
	public void tmpWord2Test() {
		String expected = "00001000100100001001101011100101";
		Hash hash = new Hash();
		Word actual = tmpWord2(hash);
		Assertions.assertEquals(expected, actual.toString());
	}
}
