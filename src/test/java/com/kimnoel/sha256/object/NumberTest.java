package com.kimnoel.sha256.object;

import com.kimnoel.sha256.Utils.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kimnoel.sha256.Utils.WordUtils.*;
import static com.kimnoel.sha256.Utils.WordUtils.majority;

@SpringBootTest
public class NumberTest {

	@Test
	public void constructorTest() {
		Number number = new Number(251d);

		Assertions.assertEquals(Integer.toBinaryString(251), number.getBits());
		Assertions.assertEquals("[1, 1, 1, 1, 1, 0, 1, 1]", number.getListBits().toString());
	}

	@Test
	public void notTest() {
		Number number = new Number(251d);

		Assertions.assertEquals("00000100", NumberUtils.not(number).getBits());
	}

	@Test
	public void rightShiftTest() {
		Number number = new Number(251d);
		Number actual = NumberUtils.rightShift(number,1);

		Assertions.assertEquals(125d, actual.getNumber());
		Assertions.assertEquals("1111101", actual.getBits());
		Assertions.assertEquals("[1, 1, 1, 1, 1, 0, 1]", actual.getListBits().toString());
	}

	@Test
	public void rightShiftTest2() {
		Number number = new Number("00000000000000000010111111111111");
		Number actual = NumberUtils.rightShift(number,1);

		Assertions.assertEquals("1011111111111", actual.getBits());
	}

	@Test
	public void rotateRightTest() {
		Number number = new Number(251d);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 0, 1, 1]", number.getListBits().toString());

		Number actual = NumberUtils.rotateRight(number,1);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 0, 1]", actual.getListBits().toString());

		actual = NumberUtils.rotateRight(number,2);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 1, 0]", actual.getListBits().toString());

		actual = NumberUtils.rotateRight(number,3);
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 1]", actual.getListBits().toString());
	}

	@Test
	public void rotateRightTest2() {
		Number number = new Number("101000110");
		Assertions.assertEquals("[1, 0, 1, 0, 0, 0, 1, 1, 0]", number.getListBits().toString());

		Number actual = NumberUtils.rotateRight(number,1);
		Assertions.assertEquals("[1, 0, 1, 0, 0, 0, 1, 1]", actual.getListBits().toString());

		actual = NumberUtils.rotateRight(number,2);
		Assertions.assertEquals("[1, 0, 1, 0, 1, 0, 0, 0, 1]", actual.getListBits().toString());

		actual = NumberUtils.rotateRight(number,3);
		Assertions.assertEquals("[1, 1, 0, 1, 0, 1, 0, 0, 0]", actual.getListBits().toString());
	}

	@Test
	public void rotateRightTest3() {
		String input 	  = "00000000000000000010111111111111";
		String expected1  = "10000000000000000001011111111111";
		String expected2  = "11000000000000000000101111111111";
		String expected16 = "00101111111111110000000000000000";

		Number number = new Number(input, 32);
		Number actual = NumberUtils.rotateRight(number,1);
		Assertions.assertEquals(expected1, actual.getBits());

		actual = NumberUtils.rotateRight(number,2);
		Assertions.assertEquals(expected2, actual.getBits());

		actual = NumberUtils.rotateRight(number,16);
		Assertions.assertEquals(expected16, actual.getBits());
	}

	@Test
	public void rotateRightTest4() {
		Number number = new Number("00000000000000000011111111111111", 32);
		Number actual = NumberUtils.rotateRight(number,7);

		Assertions.assertEquals("11111110000000000000000001111111", actual.getBits());
	}

	@Test
	public void xOrTest() {
		Number x = new Number("101010100");
		Number y = new Number("111000110");
		Number z = new Number("101010100");

		Number actual = NumberUtils.xOR(x,y);
		Assertions.assertEquals("[0, 1, 0, 0, 1, 0, 0, 1, 0]", actual.getListBits().toString());

		// Then check symmetry of the XOR
		actual = NumberUtils.xOR(y,x);
		Assertions.assertEquals("[0, 1, 0, 0, 1, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = NumberUtils.xOR(x,y,z);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());

		// Then check symmetry of the XOR
		actual = NumberUtils.xOR(y,z,x);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = NumberUtils.xOR(z,x,y);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = NumberUtils.xOR(x,z,y);
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

		Number x = new Number(inputX);
		Number y = new Number(inputY);
		Number z = new Number(inputZ);
		Number actual = NumberUtils.xOR(x,x);
		Assertions.assertEquals(expected1, actual.getBits());

		actual = NumberUtils.xOR(x,y);
		Assertions.assertEquals(expected2, actual.getBits());

		actual = NumberUtils.xOR(x,y,z);
		Assertions.assertEquals(expected3, actual.getBits());
	}

	@Test
	public void addTest() {
		Number x = new Number("10");
		Number y = new Number("01");

		Number actual = NumberUtils.add(x,y);
		Assertions.assertEquals("[1, 1]", actual.getListBits().toString());

		x = new Number("10");
		y = new Number("10");
		actual = NumberUtils.add(x,y);
		Assertions.assertEquals("[1, 0, 0]", actual.getListBits().toString());

		x = new Number("110");
		y = new Number("101");
		actual = NumberUtils.add(x,y);
		Assertions.assertEquals("[1, 0, 1, 1]", actual.getListBits().toString());

		x = new Number("111");
		y = new Number("111");
		actual = NumberUtils.add(x,y);
		Assertions.assertEquals("[1, 1, 1, 0]", actual.getListBits().toString());

	}

	@Test
	public void add2Test() {
		Number x = new Number("110");
		Number y = new Number("101");
		Number z = new Number("100");
		Number u = new Number("111");
		Number v = new Number("110");

		Number expected = new Number(28d);
		Number actual = NumberUtils.add(x,y,z,u,v);
		Assertions.assertEquals(expected.getListBits().toString(), actual.getListBits().toString());
	}

	@Test
	public void add3Test() {
		Number x = new Number("111111");
		Number y = new Number("111111");
		Number z = new Number("111111");
		Number u = new Number("111111");
		Number v = new Number("111111");

		Number expected = new Number(315d);
		Number actual = NumberUtils.add(x,y,z,u,v);
		Assertions.assertEquals(expected.getListBits().toString(), actual.getListBits().toString());
	}

	@Test
	public void sigma0Test() {
		String expected = "00000010000000000100000000000000";
		Number x = new Number("00000000000000000000000000000001",32);

		Number actual = NumberUtils.sigma(x,18,7,3);
		Assertions.assertEquals(expected, actual.getBits());

		expected = "11110001111111111100011110000000";
		x = new Number("00000000000000000011111111111111",32);
		actual = NumberUtils.sigma(x,18,7,3);
		Assertions.assertEquals(expected, actual.getBits());

		expected = "00000010000000000100000000000000";
		x = new Number("00000000000000000000000000000001",32);
		actual = NumberUtils.sigma(x,18,7,3);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void sigma0DefinitionTest() {
		String input 			= "00000000000000000011111111111111";
		String expectedR7 		= "11111110000000000000000001111111";
		String expectedR18 		= "00001111111111111100000000000000";
		String expectedS3 		= "00000000000000000000011111111111";
		String expectedSigma0 	= "11110001111111111100011110000000";

		Number x = new Number(input,32);

		Number actualR7 = NumberUtils.rotateRight(x, 7);
		Assertions.assertEquals(expectedR7, actualR7.getBits());

		Number actualR18 = NumberUtils.rotateRight(x, 18);
		Assertions.assertEquals(expectedR18, actualR18.getBits());

		Number actualS3 = NumberUtils.rightShift(x, 3);
		Assertions.assertEquals(expectedS3, actualS3.getBits());

		Number actualSigma0Definition = NumberUtils.sigma0Definition(x);
		Assertions.assertEquals(expectedSigma0, actualSigma0Definition.getBits());
	}

	@Test
	public void sigma0DefinitionTest2() {
		String input 			= "00000000000000000000000000000001";
		String expectedR7 		= "00000010000000000000000000000000";
		String expectedR18 		= "00000000000000000100000000000000";
		String expectedS3 		= "00000000000000000000000000000000";
		String expectedSigma0 	= "00000010000000000100000000000000";

		Number x = new Number(input,32);

		Number actualR7 = NumberUtils.rotateRight(x, 7);
		Assertions.assertEquals(expectedR7, actualR7.getBits());

		Number actualR18 = NumberUtils.rotateRight(x, 18);
		Assertions.assertEquals(expectedR18, actualR18.getBits());

		Number actualS3 = NumberUtils.rightShift(x, 3);
		Assertions.assertEquals(expectedS3, actualS3.getBits());

		Number actualSigma0Definition = NumberUtils.sigma0Definition(x);
		Assertions.assertEquals(expectedSigma0, actualSigma0Definition.getBits());
	}

	@Test
	public void sigma1Test() {
		String expected = "00011000000000000110000000001111";
		Number x = new Number("00000000000000000011111111111111",32);

		Number actual = NumberUtils.sigma(x,19,17,10);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void bigSigma0Test() {
		String expected = "00111111000001111111001111111110";
		Number x = new Number("00000000000000000011111111111111",32);

		Number actual = NumberUtils.bigSigma(x,22,13,2);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void bigSigma1Test() {
		String expected = "00000011111111111111111101111000";
		Number x = new Number("00000000000000000011111111111111",32);

		Number actual = NumberUtils.bigSigma(x,25,11,6);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void choiceTest() {
		String expected = "11111111000000000000000011111111";
		String bits1  = "00000000111111110000000011111111";
		String bits2  = "00000000000000001111111111111111";
		String bits3  = "11111111111111110000000000000000";
		Number x = new Number(bits1,32);
		Number y = new Number(bits2,32);
		Number z = new Number(bits3,32);

		Assertions.assertEquals(expected, NumberUtils.choice(x,y,z).getBits());
	}

	@Test
	public void majorityTest() {
		String expected = "00000000111111110000000011111111";
		String bits1  = "00000000111111110000000011111111";
		String bits2  = "00000000000000001111111111111111";
		String bits3  = "11111111111111110000000000000000";
		Number x = new Number(bits1,32);
		Number y = new Number(bits2,32);
		Number z = new Number(bits3,32);
		Assertions.assertEquals(expected, NumberUtils.majority(x,y,z).getBits());
	}
}
