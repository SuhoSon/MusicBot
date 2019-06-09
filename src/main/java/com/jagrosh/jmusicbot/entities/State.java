package com.jagrosh.jmusicbot.entities;

public interface State {
    public void alert(Prompt.Level level, String context, String message,State state);
    public String prompt(String content,State state);
}
