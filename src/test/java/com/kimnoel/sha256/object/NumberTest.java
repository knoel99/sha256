package com.kimnoel.sha256.object;

import com.kimnoel.sha256.Utils.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
	public void rotateRightTest() {
		Number number = new Number(251d);
		Number actual = NumberUtils.rightShift(number,1);

		System.out.println(number.getBits());
		//Assertions.assertEquals(125d, actual.getNumber());
		Assertions.assertEquals("11111101", actual.getBits());
		Assertions.assertEquals("[1, 1, 1, 1, 1, 1, 0, 1]", actual.getListBits().toString());
	}
}
