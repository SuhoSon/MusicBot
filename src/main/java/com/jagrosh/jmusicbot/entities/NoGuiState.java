package com.jagrosh.jmusicbot.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class NoGuiState implements State {

    private final String title;
    private final String noguiMessage;

    private Scanner scanner;

    public NoGuiState(String title, String noguiMessage) {
        this.title = title;
        this.noguiMessage = noguiMessage == null ? "Switching to nogui mode. You can manually start in nogui mode by including the -Dnogui=true flag." : noguiMessage;
    }

    @Override
    public void alert(Prompt.Level level, String context, String message,State state) {
        Logger log = LoggerFactory.getLogger(context);
        switch(level)
        {
            case INFO:
                log.info(message);
                break;
            case WARNING:
                log.warn(message);
                break;
            case ERROR:
                log.error(message);
                break;
            default:
                log.info(message);
                break;
        }
    }

    @Override
    public String prompt(String content,State state) {
        if(scanner==null)
            scanner = new Scanner(System.in);
        try
        {
            System.out.println(content);
            if(scanner.hasNextLine())
                return scanner.nextLine();
            return null;
        }
        catch(Exception e)
        {
            alert(Prompt.Level.ERROR, title, "Unable to read input from command line.",state);
            e.printStackTrace();
            return null;
        }
    }
}
