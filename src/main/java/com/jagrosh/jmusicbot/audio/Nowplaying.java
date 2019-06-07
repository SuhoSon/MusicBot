package com.jagrosh.jmusicbot.audio;

import java.util.concurrent.ScheduledExecutorService;

import com.jagrosh.jmusicbot.BotConfig;
import com.jagrosh.jmusicbot.settings.SettingsManager;

public interface Nowplaying {
	public ScheduledExecutorService getThreadpool();
	public SettingsManager getSettingsManager();
    public BotConfig getConfig();
    public void resetGame();
}
