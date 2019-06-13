package com.jagrosh.jmusicbot;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.security.Permission;

import org.junit.*;

public class JMusicBotTest {
	String validToken = "NTc3Njc3OTQ5MTgxMTAwMDMz.XNoing._r5EAREfG9XuFdJ6eoayTZIvXJA";
	String validOwner = "476995339631460353";
	String tokenIdentifier = "token = ";
	String ownerIdentifier = "owner = ";
	
	File originalFile;
	File newFile;
	FileInputStream fileInputStream;
	FileOutputStream fileOutputStream;
	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;
	
	protected static class ExitException extends SecurityException 
    {
        public final int status;
        public ExitException(int status) 
        {
            super("There is no escape!");
            this.status = status;
        }
    }
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) 
        {
            // allow anything.
        }
        @Override
        public void checkPermission(Permission perm, Object context) 
        {
            // allow anything.
        }
        @Override
        public void checkExit(int status) 
        {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
	
	@BeforeClass
	public static void setUp() throws Exception {
        System.setSecurityManager(new NoExitSecurityManager());
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		System.setSecurityManager(null);
	}

	@Before
	public void setUpBeforeClass() throws Exception {
		String line;
		String replacedLine;

		originalFile = new File("C:\\Users\\Son\\git\\MusicBot\\config_temp.txt");
		newFile = new File("C:\\Users\\Son\\git\\MusicBot\\config.txt");
		fileInputStream = new FileInputStream(originalFile);
		fileOutputStream = new FileOutputStream(newFile);
		bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
		
		while((line = bufferedReader.readLine()) != null) {
			if(line.contains(tokenIdentifier)) {
				replacedLine = line.replaceAll(tokenIdentifier, tokenIdentifier + validToken);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else if(line.contains(ownerIdentifier)) {
				replacedLine = line.replaceAll(ownerIdentifier, ownerIdentifier + validOwner);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else {
				bufferedWriter.write(line, 0, line.length());
			}
			bufferedWriter.newLine();
		}
		bufferedReader.close();
		bufferedWriter.close();
	}

	@After
	public void tearDownAfterClass() throws Exception {
		System.clearProperty("nogui");
		newFile.delete();
	}
	
	/**
	 * Purpose : Valid config file test.
	 * Input   : main {} -> null
	 * Expected:
	 * 			No error and successfully started.
	 * @throws IOException 
	 */
	@Test
	public void GUITest() {
		String[] args = {};
		JMusicBot.main(args);
	}
	/**
	 * Purpose : Valid config file test.
	 * Input   : main {"-nogui"} -> null
	 * Expected:
	 * 			Prompt alert the message which "Please " and successfully started.
	 * @throws IOException 
	 */
	@Test
	public void noGUITest() {
		System.setProperty("nogui", "true");
		String[] args = {"-nogui"};
		JMusicBot.main(args);
	}
	
	/**
	 * Purpose : No config file test.
	 * Input   : main {"-Dnogui=true"} -> null
	 * Expected:
	 * 			Prompt alert the message which "Please provide a bot token" and returned and system is down.
	 * @throws IOException 
	 */
	@Test
	public void noConfigAndnullInputTokenAndZeroInputOwnerTest() {   
        System.setIn(null);
        System.setIn(new ByteArrayInputStream("0".getBytes()));     
		newFile.delete();
		System.setProperty("nogui", "true");
		
		String[] args = {"-Dnogui=true"};
		try {
			JMusicBot.main(args);
		} catch(ExitException e) {
			assertEquals("Exit status", 0, e.status);
		}
	}
	
	/**
	 * Purpose : No config file test.
	 * Input   : main {"-Dnogui=true"} -> null
	 * Expected:
	 * 			Prompt alert the message which "Please provide a bot token" and returned.
	 * @throws IOException 
	 */
	@Test
	public void noConfigAndValidInputTokenAndInvalidInputOwnerTest() throws IOException {
		String line;
		String replacedLine;
		String invalidOwner = "owner = 0";
		
        System.setIn(new ByteArrayInputStream(validToken.getBytes()));
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        
		newFile.delete();

		originalFile = new File("C:\\Users\\Son\\git\\MusicBot\\config_temp.txt");
		newFile = new File("C:\\Users\\Son\\git\\MusicBot\\config.txt");
		fileInputStream = new FileInputStream(originalFile);
		fileOutputStream = new FileOutputStream(newFile);
		bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
		
		while((line = bufferedReader.readLine()) != null) {
			if(line.contains(tokenIdentifier)) {
				replacedLine = line.replaceAll(tokenIdentifier, tokenIdentifier + validToken);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else if(line.contains(ownerIdentifier)) {
				replacedLine = line.replaceAll(ownerIdentifier, invalidOwner);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else {
				bufferedWriter.write(line, 0, line.length());
			}
			bufferedWriter.newLine();
		}
		bufferedReader.close();
		bufferedWriter.close();
		
		System.setProperty("nogui", "true");
		
		String[] args = {"-Dnogui=true"};
		
		JMusicBot.main(args);
	}
	
	/**
	 * Purpose : Set the token to invalid token which BOT_TOKEN_HERE.
	 * Input   : main {"-Dnogui=true"} -> null
	 * Expected: 
	 * 			Prompt alert the message to get the token from input and system is down.
	 * @throws IOException 
	 */
	@Test
	public void invalidTokenTest() throws IOException {
		String line;
		String replacedLine;
		String invalidToken = "token = BOT_TOKEN_HERE";
		
        System.setIn(new ByteArrayInputStream((validToken+"A").getBytes()));
        System.setIn(new ByteArrayInputStream((validOwner+5).getBytes()));
		
		newFile.delete();

		originalFile = new File("C:\\Users\\Son\\git\\MusicBot\\config_temp.txt");
		newFile = new File("C:\\Users\\Son\\git\\MusicBot\\config.txt");
		fileInputStream = new FileInputStream(originalFile);
		fileOutputStream = new FileOutputStream(newFile);
		bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
		
		while((line = bufferedReader.readLine()) != null) {
			if(line.contains(tokenIdentifier)) {
				replacedLine = line.replaceAll(tokenIdentifier, invalidToken);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else if(line.contains(ownerIdentifier)) {
				replacedLine = line.replaceAll(ownerIdentifier, ownerIdentifier + validOwner);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else {
				bufferedWriter.write(line, 0, line.length());
			}
			bufferedWriter.newLine();
		}
		bufferedReader.close();
		bufferedWriter.close();

		System.setProperty("nogui", "true");
		String[] args = {"-Dnogui=true"};
		
		try {
			JMusicBot.main(args);
		} catch(ExitException e) {
			assertEquals("Exit status", 1, e.status);
		}
	}
}
