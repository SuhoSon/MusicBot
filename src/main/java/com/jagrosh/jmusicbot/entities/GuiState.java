package com.jagrosh.jmusicbot.entities;

import javax.swing.*;

public class GuiState implements State {

    private final String title;
    private final String noguiMessage;

    public GuiState(String title, String noguiMessage) {
        this.title = title;
        this.noguiMessage = noguiMessage == null ? "Switching to nogui mode. You can manually start in nogui mode by including the -Dnogui=true flag." : noguiMessage;
    }

    @Override
    public void alert(Prompt.Level level, String context, String message,State state) {
        try
        {
            int option = 0;
            switch(level)
            {
                case INFO:
                    option = JOptionPane.INFORMATION_MESSAGE;
                    break;
                case WARNING:
                    option = JOptionPane.WARNING_MESSAGE;
                    break;
                case ERROR:
                    option = JOptionPane.ERROR_MESSAGE;
                    break;
                default:
                    option = JOptionPane.PLAIN_MESSAGE;
                    break;
            }
            JOptionPane.showMessageDialog(null, "<html><body><p style='width: 400px;'>"+message, title, option);
        }
        catch(Exception e)
        {
            state = new NoGuiState(title,noguiMessage);
            alert(Prompt.Level.WARNING, context, noguiMessage,state);
            alert(level, context, message,state);
        }
    }

    @Override
    public String prompt(String content,State state) {
        try
        {
            return JOptionPane.showInputDialog(null, content, title, JOptionPane.QUESTION_MESSAGE);
        }
        catch(Exception e)
        {
            state = new NoGuiState(title,noguiMessage);
            state.alert(Prompt.Level.WARNING, title, noguiMessage,state);
            return state.prompt(content,state);
        }
    }
}
