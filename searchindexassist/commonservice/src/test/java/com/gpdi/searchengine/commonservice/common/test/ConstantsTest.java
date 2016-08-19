package com.gpdi.searchengine.commonservice.common.test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

import com.gpdi.searchengine.commonservice.common.Constants;

public class ConstantsTest {

	@Test
	public void test() {
		String input="12 ,, 34";
		Pattern pattern=Constants.COMMA_SPLIT_PATTERN;
		assertEquals(2, pattern.split(input).length);
	}

}
