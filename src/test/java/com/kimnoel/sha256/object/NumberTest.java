package com.kimnoel.sha256.object;

import com.kimnoel.sha256.Utils.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kimnoel.sha256.Utils.WordUtils.sigma0;

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
		Number number = new Number("00000000000000000010111111111111");
		Number actual = NumberUtils.rotateRight(number,1);

		Assertions.assertEquals("1000000000000000001011111111111", actual.getBits());
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
	public void sigma0Test() {
		String expected = "11110001111111111100011110000000";
		Number x = new Number("00000000000000000011111111111111");
		Number actual = NumberUtils.sigma0(x);
		Assertions.assertEquals(expected, actual.getBits());
	}

	@Test
	public void test() {
		Long l1 = 288001745378344961L/64;
		Long l2 = 17592045011169L/16384;
		Long l3 = 943649537L/68719476736L;

		System.out.println(l1);
		System.out.println(l2);
		System.out.println(l3);

		//Number x1 = new Number(l1);

	}

}
