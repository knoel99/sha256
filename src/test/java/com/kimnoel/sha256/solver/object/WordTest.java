package com.kimnoel.sha256.solver.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WordTest {

	@Test
	public void constructorTest() {
		Word number = new Word(251d);

		Assertions.assertEquals(Integer.toBinaryString(251), number.getBits());
		Assertions.assertEquals("[1, 1, 1, 1, 1, 0, 1, 1]", number.getListBits().toString());
	}

}
