package com.jagrosh.jmusicbot.entities;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NoGuiPromptTest {

    Prompt prompt;
    @Before
    public void construct(){
        prompt = new Prompt("NoGuiTest", "Testing Prompt in NoGui State",true);
    }

    /**
     * Purpose: alert Test 3 Level
     * Input:   Prompt.Level.WARNING, "NoGUI", "No Gui alert WARNING TEST"
     *          Prompt.Level.INFO, "NoGUI", "No Gui alert INFO TEST"
     *          Prompt.Level.ERROR, "NoGUI", "No Gui alert ERROR TEST"
     * return void
     */
    @Test
    public void alertTest(){
        prompt.alert(Prompt.Level.WARNING, "NoGUI", "No Gui alert WARNING TEST");
        prompt.alert(Prompt.Level.INFO, "NoGUI", "No Gui alert INFO TEST");
        prompt.alert(Prompt.Level.ERROR, "NoGUI", "No Gui alert ERROR TEST");
    }

    /**
     * Purpose: prompt input output test
     * Input:   "Test Success" in InputStream
     *          "No Gui prompt Test."
     * return "Test Success"
     */
    @Test
    public void promptTest(){
        String input = "Test Success";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("Test Success", prompt.prompt("No Gui prompt Test."));
    }

    /**
     * Purpose: prompt null input output test
     * Input:   "" in InputStream
     *          "No Gui prompt Test."
     * return Null
     */
    @Test
    public void promptNullTest(){
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertNull(prompt.prompt("No Gui prompt Test."));
    }

    /**
     * Purpose: one parameter construction Test
     * Input:   "title"
     * return Prompt
     */
    @Test
    public void oneParameterConstructorTest(){
        prompt = new Prompt("title");
    }


    /**
     * Purpose: two parameter construction Test
     * Input:   "title", "Test"
     * return Prompt
     */
    @Test
    public void twoParameterConstructorTest(){
        prompt = new Prompt("title","Test");
    }
}
