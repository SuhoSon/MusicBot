package com.jagrosh.jmusicbot.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FormatUtilTest {
	
	/**
	 * Purpose : Format the time from a positive duration which smaller than a hour.
	 * Input   : formatTime 0<=duration<1000*1*60*60 -> "(^[0-5][0-9]:[0-5][0-9]$)"
	 * Expected:
	 * 			return "(^[0-5][0-9]:[0-5][0-9]$)"
	 */
	@Test
	public void smallerThanHoursDurationTimeFormattingTest() {
		long[]         duration = {0, 1000*1, 1000*(59*60+59), 1000*(59*60+58)};
		String[] expectedResult = {"00:00", "00:01", "59:59", "59:58"};
		for(int i=0; i<duration.length; i++) {
			assertEquals(expectedResult[i], FormatUtil.formatTime(duration[i]));
		}
	}
	
	/**
	 * Purpose : Format the time from a duration which bigger than a hour.
	 * Input   : formatTime 1000*1*60*60<=duration<Long.MAX_VALUE -> "(^*:[0-5][0-9]:[0-5][0-9]$)"
	 * Expected:
	 * 			return "(^*:[0-5][0-9]:[0-5][0-9]$)"
	 */
	@Test
	public void biggerThanHoursDurationTimeFormattingTest() {
		long[]         duration = {1000*1*60*60, 1000*(1*60*60+1), Long.MAX_VALUE-1, Long.MAX_VALUE-2};
		String[] expectedResult = {"1:00:00", "1:00:01", "2562047788015:12:56", "2562047788015:12:56"};
		for(int i=0; i<duration.length; i++) {
			assertEquals(expectedResult[i], FormatUtil.formatTime(duration[i]));
		}
	}
	
	/**
	 * Purpose : Format the time from a duration which bigger than a threshold.
	 * Input   : formatTime Long.MAX_VALUE<=duration -> "LIVE"
	 * Expected:
	 * 			return "LIVE"
	 */
	@Test
	public void biggerThanThresholdDurationTimeFormattingTest() {
		long[]         duration = {Long.MAX_VALUE};
		String[] expectedResult = {"LIVE"};
		for(int i=0; i<duration.length; i++) {
			assertEquals(expectedResult[i], FormatUtil.formatTime(duration[i]));
		}
	}
	
	/**
	 * Purpose : Format the time from a negative duration.
	 * Input   : formatTime duration<0 -> "(^(-|)[0-5][0-9]:(-|[0-5])([0-5]|)[0-9]$)"
	 * Expected:
	 * 			return "(^(-|)[0-5][0-9]:(-|[0-5])([0-5]|)[0-9]$)"
	 */
	@Test
	public void negativeDurationTimeFormattingTest() {
		long[]         duration = {1000*(-1), 1000*(-10), Long.MAX_VALUE+1};
		String[] expectedResult = {"00:-1", "00:-10", "-12:-56"};
		for(int i=0; i<duration.length; i++) {
			assertEquals(expectedResult[i], FormatUtil.formatTime(duration[i]));
		}
	}
	
	
	
	/**
	 * Purpose : Format the progress bar from a percentile between 0 and 1/12.
	 * Input   : progressBar 0<=percent<1/12 -> "\uD83D\uDD18▬▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "\uD83D\uDD18▬▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void oneToTwelveProgressBarFormattingTest() {
		double[]      percent = {0, 0.01, 1/13};
		String expectedResult = "\uD83D\uDD18▬▬▬▬▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 1/12 and 2/12.
	 * Input   : progressBar 1/12<=percent<2/12 -> "▬\uD83D\uDD18▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬\uD83D\uDD18▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void twoToTwelveProgressBarFormattingTest() {
		double[]      percent = {1/12, 2/13};
		String expectedResult = "▬\uD83D\uDD18▬▬▬▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 2/12 and 3/13.
	 * Input   : progressBar 2/12<=percent<3/12 -> "▬▬\uD83D\uDD18▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬\uD83D\uDD18▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void threeToTwelveProgressBarFormattingTest() {
		double[]      percent = {2/12, 3/13};
		String expectedResult = "▬▬\uD83D\uDD18▬▬▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 3/12 and 4/13.
	 * Input   : progressBar 3/12<=percent<4/12 -> "▬▬▬\uD83D\uDD18▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬\uD83D\uDD18▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void fourToTwelveProgressBarFormattingTest() {
		double[]      percent = {3/12, 4/13};
		String expectedResult = "▬▬▬\uD83D\uDD18▬▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 4/12 and 5/13.
	 * Input   : progressBar 4/12<=percent<5/12 -> "▬▬▬▬\uD83D\uDD18▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬\uD83D\uDD18▬▬▬▬▬▬▬"
	 */
	@Test
	public void fiveToTwelveProgressBarFormattingTest() {
		double[]      percent = {4/12, 5/13};
		String expectedResult = "▬▬▬▬\uD83D\uDD18▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 5/12 and 6/13.
	 * Input   : progressBar 5/12<=percent<6/12 -> "▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬▬"
	 */
	@Test
	public void sixToTwelveProgressBarFormattingTest() {
		double[]      percent = {5/12, 6/13};
		String expectedResult = "▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 6/12 and 7/13.
	 * Input   : progressBar 6/12<=percent<7/12 -> "▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬"
	 */
	@Test
	public void sevenToTwelveProgressBarFormattingTest() {
		double[]      percent = {6/12, 7/13};
		String expectedResult = "▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 7/12 and 8/13.
	 * Input   : progressBar 7/12<=percent<8/12 -> "▬▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬"
	 */
	@Test
	public void eightToTwelveProgressBarFormattingTest() {
		double[]      percent = {7/12, 8/13};
		String expectedResult = "▬▬▬▬▬▬▬\uD83D\uDD18▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 8/12 and 9/13.
	 * Input   : progressBar 8/12<=percent<9/12 -> "▬▬▬▬▬▬▬▬\uD83D\uDD18▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬\uD83D\uDD18▬▬▬"
	 */
	@Test
	public void nineToTwelveProgressBarFormattingTest() {
		double[]      percent = {8/12, 9/13};
		String expectedResult = "▬▬▬▬▬▬▬▬\uD83D\uDD18▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 9/12 and 10/13.
	 * Input   : progressBar 9/12<=percent<10/12 -> "▬▬▬▬▬▬▬▬▬\uD83D\uDD18▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬\uD83D\uDD18▬▬"
	 */
	@Test
	public void tenToTwelveProgressBarFormattingTest() {
		double[]      percent = {9/12, 10/13};
		String expectedResult = "▬▬▬▬▬▬▬▬▬\uD83D\uDD18▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 10/12 and 11/13.
	 * Input   : progressBar 10/12<=percent<11/12 -> "▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18▬"
	 */
	@Test
	public void elevenToTwelveProgressBarFormattingTest() {
		double[]      percent = {10/12, 11/13};
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile between 11/12 and 12/13.
	 * Input   : progressBar 11/12<=percent<12/12 -> "▬▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18"
	 */
	@Test
	public void twelveToTwelveProgressBarFormattingTest() {
		double[]      percent = {11/12, 12/13};
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬\uD83D\uDD18";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a percentile which bigger than 1.
	 * Input   : progressBar 1<percent -> "▬▬▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void overOneProgressBarFormattingTest() {
		double[]      percent = {12/12, 13/12};
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}

	/**
	 * Purpose : Format the progress bar from a negative percentile.
	 * Input   : progressBar percent<0 -> "▬▬▬▬▬▬▬▬▬▬▬▬"
	 * Expected:
	 * 			return "▬▬▬▬▬▬▬▬▬▬▬▬"
	 */
	@Test
	public void negativeProgressBarFormattingTest() {
		double[]      percent = {-1/12, -2/12};
		String expectedResult = "▬▬▬▬▬▬▬▬▬▬▬▬";
		
		for(int i=0; i<percent.length; i++) {
			assertEquals(expectedResult, FormatUtil.progressBar(percent[i]));
		}
	}
	
	
	
	/**
	 * Purpose : Format the volume Icon from a volume which between 0 to 10.
	 * Input   : volumeIcon 0<=volume<10 -> "\uD83D\uDD07"
	 * Expected:
	 * 			return "\uD83D\uDD07"
	 */
	@Test
	public void muteVolumeIconFormattingTest() {
		int[]          volume = {0, 1, 8, 9};
		String expectedResult = "\uD83D\uDD07";
		
		for(int i=0; i<volume.length; i++) {
			assertEquals(expectedResult, FormatUtil.volumeIcon(volume[i]));
		}
	}
	
	/**
	 * Purpose : Format the volume Icon from a volume which between 10 to 30.
	 * Input   : volumeIcon 10<=volume<30 -> "\uD83D\uDD08"
	 * Expected:
	 * 			return "\uD83D\uDD08"
	 */
	@Test
	public void smallestVolumeIconFormattingTest() {
		int[]          volume = {10, 11, 28, 29};
		String expectedResult = "\uD83D\uDD08";
		
		for(int i=0; i<volume.length; i++) {
			assertEquals(expectedResult, FormatUtil.volumeIcon(volume[i]));
		}
	}
	
	/**
	 * Purpose : Format the volume Icon from a volume which between 30 to 70.
	 * Input   : volumeIcon 10<=volume<30 -> "\uD83D\uDD09"
	 * Expected:
	 * 			return "\uD83D\uDD09"
	 */
	@Test
	public void middleVolumeIconFormattingTest() {
		int[]          volume = {30, 31, 68, 69};
		String expectedResult = "\uD83D\uDD09";
		
		for(int i=0; i<volume.length; i++) {
			assertEquals(expectedResult, FormatUtil.volumeIcon(volume[i]));
		}
	}
	
	/**
	 * Purpose : Format the volume Icon from a volume which bigger than 70.
	 * Input   : volumeIcon 70<=volume -> "\uD83D\uDD0A"
	 * Expected:
	 * 			return "\uD83D\uDD0A"
	 */
	@Test
	public void largestVolumeIconFormattingTest() {
		int[]          volume = {70,71};
		String expectedResult = "\uD83D\uDD0A";
		
		for(int i=0; i<volume.length; i++) {
			assertEquals(expectedResult, FormatUtil.volumeIcon(volume[i]));
		}
	}
	
	/**
	 * Purpose : Format the volume Icon from a negative volume.
	 * Input   : volumeIcon 70<=volume -> "\uD83D\uDD0A"
	 * Expected:
	 * 			return "\uD83D\uDD0A"
	 */
	@Test
	public void negativeVolumeIconFormattingTest() {
		int[]          volume = {70,71};
		String expectedResult = "\uD83D\uDD0A";
		
		for(int i=0; i<volume.length; i++) {
			assertEquals(expectedResult, FormatUtil.volumeIcon(volume[i]));
		}
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
