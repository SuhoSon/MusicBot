package com.jagrosh.jmusicbot.audio;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jmusicbot.BotConfig;
import com.jagrosh.jmusicbot.playlist.PlaylistLoader;
import com.jagrosh.jmusicbot.settings.SettingsManager;

public interface Player {
	public SettingsManager getSettingsManager();
	public PlaylistLoader getPlaylistLoader();
	public NowplayingHandler getNowplayingHandler();
	public BotConfig getConfig();
	public void closeAudioConnection(long guildId);
	public EventWaiter getWaiter();
}
