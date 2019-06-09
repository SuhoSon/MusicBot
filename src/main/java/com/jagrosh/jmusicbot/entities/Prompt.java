/*
 * Copyright 2018 John Grosh (jagrosh)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.entities;

import java.util.Scanner;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class Prompt
{

    State state;



    public Prompt(String title)
    {
        this(title, null);
    }
    
    public Prompt(String title, String noguiMessage)
    {
        this(title, noguiMessage, "true".equalsIgnoreCase(System.getProperty("nogui")));
    }

    public Prompt(String title, String noguiMessage, boolean nogui)
    {
       if(nogui)
            state = new NoGuiState(title,noguiMessage);
        else
            state = new GuiState(title,noguiMessage);
    }


    public void alert(Level level, String context, String message)
    {
        state.alert(level,context,message,state);
    }

    public String prompt(String content)
    {
        return state.prompt(content,state);
    }
    
    public static enum Level
    {
        INFO, WARNING, ERROR;
    }
}
