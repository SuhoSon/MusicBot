package com.jagrosh.jmusicbot.settings;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsTest {
    Settings settings;
    @Before
    public void construct(){
        settings = new Settings(new SettingsManager(),"textId","voiceId","roleId",10,"",false);
    }

    /**
     * Purpose: Testing getVolume
     * Input:  void
     * return 10
     */
    @Test
    public void getVolumeTest(){
        assertEquals(10,settings.getVolume());
    }

    /**
     * Purpose: Testing getDefaultPlaylist
     * Input:  void
     * return ""
     */
    @Test
    public void getDefaultPlaylistTest(){
        assertEquals("",settings.getDefaultPlaylist());
    }

    /**
     * Purpose: Testing getRepeatMode
     * Input:  void
     * return False
     */
    @Test
    public void getRepeatModeTest(){
        assertFalse(settings.getRepeatMode());
    }

    /**
     * Purpose: Testing setRepeatMode
     * Input:  void
     * return true
     */
    @Test
    public void setRepeatModeTest(){
        settings.setRepeatMode(true);
        assertTrue(settings.getRepeatMode());
    }

    /**
     * Purpose: Testing setDefaultPlaylist
     * Input:  void
     * return "test"
     */
    @Test
    public void setDefaultPlaylistTest(){
        settings.setDefaultPlaylist("test");
        assertEquals("test",settings.getDefaultPlaylist());
    }

    /**
     * Purpose: Testing setVolume
     * Input:  void
     * return 50
     */
    @Test
    public void setVolumeTest(){
        settings.setVolume(50);
        assertEquals(50,settings.getVolume());
    }


    /**
     * Purpose: Testing Settings(SettingsManager, long, long, long, int,String,boolean)
     * Input:  void
     * return void
     */
    @Test
    public void anotherConstructTest(){
        settings = new Settings(new SettingsManager(),1,2,3,4,"",true);
    }
}
