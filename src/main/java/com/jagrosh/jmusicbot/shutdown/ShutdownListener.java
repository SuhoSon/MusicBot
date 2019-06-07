package com.jagrosh.jmusicbot.shutdown;

import java.util.ArrayList;

import com.jagrosh.jmusicbot.gui.GUI;

import net.dv8tion.jda.core.JDA;

public class ShutdownListener extends Subject {
	private final JDA jda;
	private GUI gui;
	public ShutdownListener(JDA jda) {
		observers = new ArrayList<Observer>();
		this.jda = jda;
	}
	
	public void setGUI(GUI gui) {
		this.gui = gui;
	}
	
	public void shutdown() {
		notify_();
        jda.shutdown();
        if(gui!=null)
        	gui.dispose();
        System.exit(0);
	}
}