package com.jagrosh.jmusicbot.playlistLoader;

import com.jagrosh.jmusicbot.BotConfig;
import com.jagrosh.jmusicbot.entities.Prompt;
import com.jagrosh.jmusicbot.playlist.PlaylistLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PlaylistLoaderTest {
    PlaylistLoader playlistLoader;
    String validToken = "NTc3Njc3OTQ5MTgxMTAwMDMz.XNoing._r5EAREfG9XuFdJ6eoayTZIvXJA";
    String validOwner = "476995339631460353";
    @Before
    public void setUp(){
        Prompt prompt = new Prompt("Title","Test",true);
        BotConfig botConfig = new BotConfig(prompt);
        System.setIn(new ByteArrayInputStream(validToken.getBytes()));
        System.setIn(new ByteArrayInputStream(validOwner.getBytes()));
        botConfig.load();
        if(!botConfig.isValid())
            return;
        playlistLoader = new PlaylistLoader(botConfig);
        try {
            playlistLoader.createPlaylist("TEST");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: createFolder Test
     * Input:  void
     * return Null
     */
    @Test
    public void createFolderTest(){
        playlistLoader.createFolder();
    }

    /**
     * Purpose: Testing folderExistsTest
     * Input:  void
     * return True
     */
    @Test
    public void folderExistsTest()
    {
        assertTrue(playlistLoader.folderExists());
    }


    /**
     * Purpose: Testing writePlaylist
     * Input:  "TEST" , "Test Write"
     * return void
     */
    @Test
    public void writePlaylistTest(){
        try {
            playlistLoader.writePlaylist("TEST", "Test Write.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: Testing getPlaylistNames
     * Input:  void
     * return {"TEST"}   (@Before)
     */
    @Test
    public void getPlaylistNamesTest(){
        List<String> Test = new ArrayList<>();
        Test.add("TEST");
        assertEquals(Test,playlistLoader.getPlaylistNames());
    }


    @After
    public void tearDown()
    {
        try {
            playlistLoader.deletePlaylist("TEST");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
