package com.jagrosh.jmusicbot.shutdown;

import java.util.ArrayList;

import net.dv8tion.jda.core.JDA;

public class ShutdownListener extends Subject {
	private final JDA jda;
	public ShutdownListener(JDA jda) {
		observers = new ArrayList<Observer>();
		this.jda = jda;
	}
	
	public void shutdown() {
		notify_();
        jda.shutdown();
	}
}