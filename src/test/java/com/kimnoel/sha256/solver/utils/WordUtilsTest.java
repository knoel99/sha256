package com.kimnoel.sha256.solver.utils;

import com.kimnoel.sha256.solver.object.Word;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WordUtilsTest {

	@Test
	public void notTest() {
		Word number = new Word(251d);

		Assertions.assertEquals("00000100", WordUtils.not(number).getBits());
	}

	@Test
	public void rightShiftTest() {
		Word number = new Word(251d);
		Word actual = WordUtils.rightShift(number,1);

		Assertions.assertEquals(125d, actual.getNumber());
		Assertions.assertEquals("1111101", actual.getBits());
		Assertions.assertEquals("[1, 1, 1, 1, 1, 0, 1]", actual.getListBits().toString());
	}

	@Test
	public void rightShiftTest2() {
		Word number = new Word("00000000000000000010111111111111");
		Word actual = WordUtils.rightShift(number,1);

		Assertions.assertEquals("1011111111111", actual.getBits());
	}

	@Test
	public void rotateRightTest() {
		Word number = new Word(251d);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 0, 1, 1]", number.getListBits().toString());

		Word actual = WordUtils.rotateRight(number,1);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 0, 1]", actual.getListBits().toString());

		actual = WordUtils.rotateRight(number,2);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 1, 0]", actual.getListBits().toString());

		actual = WordUtils.rotateRight(number,3);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 1]", actual.getListBits().toString());
	}

	@Test
	public void rotateRightTest2() {
		Word number = new Word("101000110");
		Assertions.assertEquals("[1, 0, 1, 0, 0, 0, 1, 1, 0]", number.getListBits().toString());

		Word actual = WordUtils.rotateRight(number,1);
		Assertions.assertEquals("[1, 0, 1, 0, 0, 0, 1, 1]", actual.getListBits().toString());

		actual = WordUtils.rotateRight(number,2);
		Assertions.assertEquals("[1, 0, 1, 0, 1, 0, 0, 0, 1]", actual.getListBits().toString());

		actual = WordUtils.rotateRight(number,3);
		Assertions.assertEquals("[1, 1, 0, 1, 0, 1, 0, 0, 0]", actual.getListBits().toString());
	}

	@Test
	public void rotateRightTest3() {
		String input 	  = "00000000000000000010111111111111";
		String expected1  = "10000000000000000001011111111111";
		String expected2  = "11000000000000000000101111111111";
		String expected16 = "00101111111111110000000000000000";

		Word number = new Word(input, 32);
		Word actual = WordUtils.rotateRight(number,1);
		Assertions.assertEquals(expected1, actual.getBits());

		actual = WordUtils.rotateRight(number,2);
		Assertions.assertEquals(expected2, actual.getBits());

		actual = WordUtils.rotateRight(number,16);
		Assertions.assertEquals(expected16, actual.getBits());
	}

	@Test
	public void rotateRightTest4() {
		Word number = new Word("00000000000000000011111111111111", 32);
		Word actual = WordUtils.rotateRight(number,7);

		Assertions.assertEquals("11111110000000000000000001111111", actual.getBits());
	}

	@Test
	public void xOrTest() {
		Word x = new Word("101010100");
		Word y = new Word("111000110");
		Word z = new Word("101010100");

		Word actual = WordUtils.xOR(x,y);
		Assertions.assertEquals("[0, 1, 0, 0, 1, 0, 0, 1, 0]", actual.getListBits().toString());

		// Then check symmetry of the XOR
		actual = WordUtils.xOR(y,x);
		Assertions.assertEquals("[0, 1, 0, 0, 1, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = WordUtils.xOR(x,y,z);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());

		// Then check symmetry of the XOR
		actual = WordUtils.xOR(y,z,x);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = WordUtils.xOR(z,x,y);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = WordUtils.xOR(x,z,y);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());
	}

	@Test
	public void xOrTest2() {
		String inputX 	= "00000000000000000011111111111111";
		String inputY 	= "00000000000000000011101110111001";
		String inputZ 	= "00000000000000000011110110111101";

		String expected1= "00000000000000000000000000000000";
		String expected2= "00000000000000000000010001000110";
		String expected3= "00000000000000000000000001000010";

		Word x = new Word(inputX);
		Word y = new Word(inputY);
		Word z = new Word(inputZ);
		Word actual = WordUtils.xOR(x,x);
		Assertions.assertEquals(expected1, actual.getBits());

		actual = WordUtils.xOR(x,y);
		Assertions.assertEquals(expected2, actual.getBits());

		actual = WordUtils.xOR(x,y,z);
		Assertions.assertEquals(expected3, actual.getBits());
	}

	@Test
	public void addTest() {
		Word x = new Word("10");
		Word y = new Word("01");

		Word actual = WordUtils.add(x,y);
		Assertions.assertEquals("[1, 1]", actual.getListBits().toString());

		x = new Word("10");
		y = new Word("10");
		actual = WordUtils.add(x,y);
		Assertions.assertEquals("[1, 0, 0]", actual.getListBits().toString());

		x = new Word("110");
		y = new Word("101");
		actual = WordUtils.add(x,y);
		Assertions.assertEquals("[1, 0, 1, 1]", actual.getListBits().toString());

		x = new Word("111");
		y = new Word("111");
		actual = WordUtils.add(x,y);
		Assertions.assertEquals("[1, 1, 1, 0]", actual.getListBits().toString());

	}

	@Test
	public void add2Test() {
		Word x = new Word("110");
		Word y = new Word("101");
		Word z = new Word("100");
		Word u = new Word("111");
		Word v = new Word("110");

		Word expected = new Word(28d);
		Word actual = WordUtils.add(x,y,z,u,v);
		Assertions.assertEquals(expected.getListBits().toString(), actual.getListBits().toString());
	}

	@Test
	public void add3Test() {
		Word x = new Word("111111");
		Word y = new Word("111111");
		Word z = new Word("111111");
		Word u = new Word("111111");
		Word v = new Word("111111");

		Word expected = new Word(315d);
		Word actual = WordUtils.add(x,y,z,u,v);
		Assertions.assertEquals(expected.getListBits().toString(), actual.getListBits().toString());
	}

	@Test
	public void sigma0Test() {
		String expected = "00000010000000000100000000000000";
		Word x = new Word("00000000000000000000000000000001",32);

		Word actual = WordUtils.sigma(x,18,7,3);
		Assertions.assertEquals(expected, actual.getBits());

		expected = "11110001111111111100011110000000";
		x = new Word("00000000000000000011111111111111",32);
		actual = WordUtils.sigma(x,18,7,3);
		Assertions.assertEquals(expected, actual.getBits());

		expected = "00000010000000000100000000000000";
		x = new Word("00000000000000000000000000000001",32);
		actual = WordUtils.sigma(x,18,7,3);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void sigma0DefinitionTest() {
		String input 			= "00000000000000000011111111111111";
		String expectedR7 		= "11111110000000000000000001111111";
		String expectedR18 		= "00001111111111111100000000000000";
		String expectedS3 		= "00000000000000000000011111111111";
		String expectedSigma0 	= "11110001111111111100011110000000";

		Word x = new Word(input,32);

		Word actualR7 = WordUtils.rotateRight(x, 7);
		Assertions.assertEquals(expectedR7, actualR7.getBits());

		Word actualR18 = WordUtils.rotateRight(x, 18);
		Assertions.assertEquals(expectedR18, actualR18.getBits());

		Word actualS3 = WordUtils.rightShift(x, 3);
		Assertions.assertEquals(expectedS3, actualS3.getBits());

		Word actualSigma0Definition = WordUtils.sigma0Definition(x);
		Assertions.assertEquals(expectedSigma0, actualSigma0Definition.getBits());
	}

	@Test
	public void sigma0DefinitionTest2() {
		String input 			= "00000000000000000000000000000001";
		String expectedR7 		= "00000010000000000000000000000000";
		String expectedR18 		= "00000000000000000100000000000000";
		String expectedS3 		= "00000000000000000000000000000000";
		String expectedSigma0 	= "00000010000000000100000000000000";

		Word x = new Word(input,32);

		Word actualR7 = WordUtils.rotateRight(x, 7);
		Assertions.assertEquals(expectedR7, actualR7.getBits());

		Word actualR18 = WordUtils.rotateRight(x, 18);
		Assertions.assertEquals(expectedR18, actualR18.getBits());

		Word actualS3 = WordUtils.rightShift(x, 3);
		Assertions.assertEquals(expectedS3, actualS3.getBits());

		Word actualSigma0Definition = WordUtils.sigma0Definition(x);
		Assertions.assertEquals(expectedSigma0, actualSigma0Definition.getBits());
	}

	@Test
	public void sigma1Test() {
		String expected = "00011000000000000110000000001111";
		Word x = new Word("00000000000000000011111111111111",32);

		Word actual = WordUtils.sigma(x,19,17,10);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void bigSigma0Test() {
		String expected = "00111111000001111111001111111110";
		Word x = new Word("00000000000000000011111111111111",32);

		Word actual = WordUtils.bigSigma(x,22,13,2);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void bigSigma1Test() {
		String expected = "00000011111111111111111101111000";
		Word x = new Word("00000000000000000011111111111111",32);

		Word actual = WordUtils.bigSigma(x,25,11,6);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void choiceTest() {
		String expected = "11111111000000000000000011111111";
		String bits1  = "00000000111111110000000011111111";
		String bits2  = "00000000000000001111111111111111";
		String bits3  = "11111111111111110000000000000000";
		Word x = new Word(bits1,32);
		Word y = new Word(bits2,32);
		Word z = new Word(bits3,32);

		Assertions.assertEquals(expected, WordUtils.choice(x,y,z).getBits());
	}

	@Test
	public void majorityTest() {
		String expected = "00000000111111110000000011111111";
		String bits1  = "00000000111111110000000011111111";
		String bits2  = "00000000000000001111111111111111";
		String bits3  = "11111111111111110000000000000000";
		Word x = new Word(bits1,32);
		Word y = new Word(bits2,32);
		Word z = new Word(bits3,32);
		Assertions.assertEquals(expected, WordUtils.majority(x,y,z).getBits());
	}
}
