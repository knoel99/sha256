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
	public void xOrTest() {
		Number x = new Number("101010100");
		Number y = new Number("111000110");
		Number z = new Number("101010100");

		Number actual = NumberUtils.xOR(x,y);
		Assertions.assertEquals("[0, 1, 0, 0, 1, 0, 0, 1, 0]", actual.getListBits().toString());

		actual = NumberUtils.xOR(x,y,z);
		Assertions.assertEquals("[0, 1, 0, 0, 0, 0, 0, 1, 0]", actual.getListBits().toString());
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

}
