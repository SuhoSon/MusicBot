package com.jagrosh.jmusicbot.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FormatUtilTest {


	/**
	 * Purpose : Format the time from a zero duration.
	 * Input   : formatTime 0 -> "00:00"
	 * Expected:
	 * 			return "00:00"
	 */
	@Test
	public void zeroDurationTimeFormattingTest() {
		String expectedResult = "00:00";
		assertEquals(expectedResult, FormatUtil.formatTime(0));
	}
	
	/**
	 * Purpose : Format the time from a valid duration which smaller than a hour.
	 * Input   : formatTime 1000*(34*60+2) -> "34:02"
	 * Expected:
	 * 			return "34:02"
	 */
	@Test
	public void smallerThanHoursDurationTimeFormattingTest() {
		String expectedResult = "34:02";
		assertEquals(expectedResult, FormatUtil.formatTime(1000*(34*60+02)));
	}
	
	/**
	 * Purpose : Format the time from a valid duration which bigger than a hour.
	 * Input   : formatTime 1000*(1*60*60+2*60+11) -> "1:02:11"
	 * Expected:
	 * 			return "1:02:11"
	 */
	@Test
	public void biggerThanHoursDurationTimeFormattingTest() {
		String expectedResult = "1:02:11";
		assertEquals(expectedResult, FormatUtil.formatTime(1000*(1*60*60+2*60+11)));
	}
	
	/**
	 * Purpose : Format the time from a valid duration which smaller than a hour.
	 * Input   : formatTime -1 -> "LIVE"
	 * Expected:
	 * 			return "LIVE"
	 */
	@Test
	public void negativeSmallerThanHoursDurationTimeFormattingTest() {
		String expectedResult = "LIVE";
		assertEquals(expectedResult, FormatUtil.formatTime(-1));
	}
	
	/**
	 * Purpose : Format the time from a duration which Long.MAX_VALUE.
	 * Input   : formatTime Long.MAX_VALUE -> "LIVE"
	 * Expected:
	 * 			return "LIVE"
	 */
	@Test
	public void thresholdDurationTimeFormattingTest() {
		String expectedResult = "LIVE";
		assertEquals(expectedResult, FormatUtil.formatTime(Long.MAX_VALUE));
	}
	
	/**
	 * Purpose : Format the time from a duration which bigger than Long.MAX_VALUE.
	 * Input   : formatTime Long.MAX_VALUE+1 -> "LIVE"
	 * Expected:
	 * 			return "LIVE"
	 */
	@Test
	public void biggerDurationTimeFormattingTest() {
		String expectedResult = "LIVE";
		assertEquals(expectedResult, FormatUtil.formatTime(Long.MAX_VALUE+1));
	}
	
	/**
	 * Purpose : Format the progress bar from a valid percentile which represent the music progress.
	 * Input   : progressBar 0.5 -> "▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬"
	 */
	@Test
	public void validProgressBarFormattingTest() {
		String expectedResult = "▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬";
		assertEquals(expectedResult, FormatUtil.progressBar(0.5));
	}
	
	/**
	 * Purpose : Format the progress bar from a negative percentile which represent the music progress.
	 * Input   : progressBar -0.1 -> "▬▬▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void negativePercentileProgressBarFormattingTest() {
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬▬";
		assertEquals(expectedResult, FormatUtil.progressBar(-0.1));
	}
	
	/**
	 * Purpose : Format the progress bar from a 99th percentile which represent the music progress.
	 * Input   : progressBar 0.9 -> "▬▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18"
	 */
	@Test
	public void ninetyNinePercentileProgressBarFormattingTest() {
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18";
		assertEquals(expectedResult, FormatUtil.progressBar(0.99));
	}
	
	/**
	 * Purpose : Format the progress bar from a 100th percentile which represent the music progress.
	 * Input   : progressBar 1.0 -> "▬▬▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void onePercentileProgressBarFormattingTest() {
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬▬";
		assertEquals(expectedResult, FormatUtil.progressBar(1.0));
	}
	
	/**
	 * Purpose : Format the progress bar from a 101th percentile which represent the music progress.
	 * Input   : progressBar 1.1 -> "▬▬▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void biggerThanOnePercentileProgressBarFormattingTest() {
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬▬";
		assertEquals(expectedResult, FormatUtil.progressBar(1.01));
	}
	
	/**
	 * Purpose : Format the volume Icon from a valid percentile which represent the music volume.
	 * Input   : volumeIcon 50 -> "\uD83D\uDD09"
	 * Expected:
	 * 			return "\uD83D\uDD09"
	 */
	@Test
	public void validVolumeIconFormattingTest() {
		String expectedResult = "\uD83D\uDD09";
		assertEquals(expectedResult, FormatUtil.volumeIcon(50));
	}
	
	/**
	 * Purpose : Format the volume Icon from a valid percentile which represent the music volume.
	 * Input   : volumeIcon -1 -> "\uD83D\uDD07"
	 * Expected:
	 * 			return "\uD83D\uDD07"
	 */
	@Test
	public void negativePercentileVolumeIconFormattingTest() {
		String expectedResult = "\uD83D\uDD07";
		assertEquals(expectedResult, FormatUtil.volumeIcon(-1));
	}
	
	/**
	 * Purpose : Format the volume Icon from a valid percentile which represent the music volume.
	 * Input   : volumeIcon 0 -> "\uD83D\uDD07"
	 * Expected:
	 * 			return "\uD83D\uDD07"
	 */
	@Test
	public void zeroPercentileVolumeIconFormattingTest() {
		String expectedResult = "\uD83D\uDD07";
		assertEquals(expectedResult, FormatUtil.volumeIcon(0));
	}

	/**
	 * Purpose : Format the volume Icon from a valid percentile which represent the music volume.
	 * Input   : volumeIcon 20 -> "\uD83D\uDD07"
	 * Expected:
	 * 			return "\uD83D\uDD07"
	 */
	@Test
	public void twentyPercentileVolumeIconFormattingTest() {
		String expectedResult = "\uD83D\uDD08";
		assertEquals(expectedResult, FormatUtil.volumeIcon(20));
	}
	
	/**
	 * Purpose : Format the volume Icon from a valid percentile which represent the music volume.
	 * Input   : volumeIcon 100 -> "\uD83D\uDD0A"
	 * Expected:
	 * 			return "\uD83D\uDD0A"
	 */
	@Test
	public void oneHundredPercentileVolumeIconFormattingTest() {
		String expectedResult = "\uD83D\uDD0A";
		assertEquals(expectedResult, FormatUtil.volumeIcon(100));
	}
	
	/**
	 * Purpose : Format the volume Icon from a valid percentile which represent the music volume.
	 * Input   : volumeIcon 101 -> "\uD83D\uDD0A"
	 * Expected:
	 * 			return "\uD83D\uDD0A"
	 */
	@Test
	public void biggerThanOneHundredPercentileVolumeIconFormattingTest() {
		String expectedResult = "\uD83D\uDD0A";
		assertEquals(expectedResult, FormatUtil.volumeIcon(101));
	}
	
	/**
	 * Purpose : Format the string from both "@everyone" to "@\u0435veryone" and "@here" to "@h\u0435re".
	 * Input   : filter "@everyone hello? I'm @here!!" -> "@\u0435veryone hello? I'm @h\u0435re!!"
	 * Expected:
	 * 			return "@\u0435veryone hello? I'm @h\u0435re!!"
	 */
	@Test
	public void stringFilterTest() {
		String expectedResult = "@\u0435veryone hello? I'm @h\u0435re!!";
		assertEquals(expectedResult, FormatUtil.filter("@everyone hello? I'm @here!!"));
	}
	
	/**
	 * Purpose : Format the list of channel.
	 * Input   : listOfChannels list, query, entries -> "QUERYabc"
	 * Expected:
	 * 			return "QUERYabc"
	 */
	@Test
	public void listOfChannelUnderSixElementTest() {
		String expectedResult = "QUERYabc";
		List<String> list = new ArrayList<>();
		String query = "QUERY";
		String[] entries = {"a","b","c"};
		
		list.add("A");
		list.add("B");
		list.add("C");
		
		assertEquals(expectedResult, FormatUtil.listOfChannels(list, query, entries));
	}
	
	/**
	 * Purpose : Format the list of channel.
	 * Input   : listOfChannels list, query, entries -> "QUERYabcdef"
	 * Expected:
	 * 			return "QUERYabcdef"
	 */
	@Test
	public void listOfChannelWithSixElementTest() {
		String expectedResult = "QUERYabcdef";
		List<String> list = new ArrayList<>();
		String query = "QUERY";
		String[] entries = {"a","b","c","d","e","f"};
		
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		
		assertEquals(expectedResult, FormatUtil.listOfChannels(list, query, entries));
	}
	
	/**
	 * Purpose : Format the list of channel.
	 * Input   : listOfChannels list, query, entries -> "QUERYabcdefg\n**And 6 more...**"
	 * Expected:
	 * 			return "QUERYabcdef\n**And 1 more...**"
	 */
	@Test
	public void listOfChannelOverSixElementTest() {
		String expectedResult = "QUERYabcdef\n**And 1 more...**";
		List<String> list = new ArrayList<>();
		String query = "QUERY";
		String[] entries = {"a","b","c","d","e","f","g"};
		
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("G");

		assertEquals(expectedResult, FormatUtil.listOfChannels(list, query, entries));
	}
}
