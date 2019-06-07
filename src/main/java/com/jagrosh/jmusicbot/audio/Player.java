package com.jagrosh.jmusicbot.audio;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jmusicbot.settings.SettingsManager;

public interface Player {
	public SettingsManager getSettingsManager();
	public void closeAudioConnection(long guildId);
	public EventWaiter getWaiter();
}
