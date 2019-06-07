/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
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
package com.jagrosh.jmusicbot.audio;

import com.jagrosh.jmusicbot.playlist.PlaylistLoader;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.core.entities.Guild;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class PlayerManager extends DefaultAudioPlayerManager
{
    private final Player player;
    private final PlayerConfig playerConfig;
    private final NowplayingHandler nowplayingHandler;
    private final PlaylistLoader playlists;
    
    public PlayerManager(Player player, NowplayingHandler nowplayingHandler, PlayerConfig playerConfig, PlaylistLoader playlists)
    {
        this.player = player;
        this.nowplayingHandler = nowplayingHandler;
        this.playerConfig = playerConfig;
        this.playlists = playlists;
    }
    
    public PlaylistLoader getPlaylistLoader() {
    	return this.playlists;
    }
    
    public void init()
    {
        AudioSourceManagers.registerRemoteSources(this);
        AudioSourceManagers.registerLocalSource(this);
        source(YoutubeAudioSourceManager.class).setPlaylistPageCount(10);
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    public PlayerConfig getPlayerConfig() {
    	return playerConfig;
    }
    
    public NowplayingHandler getNowplayingHandler() {
    	return nowplayingHandler;
    }
    
    public boolean hasHandler(Guild guild)
    {
        return guild.getAudioManager().getSendingHandler()!=null;
    }
    
    public AudioHandler setUpHandler(Guild guild)
    {
        AudioHandler handler;
        if(guild.getAudioManager().getSendingHandler()==null)
        {
            AudioPlayer audioPlayer = createPlayer();
            audioPlayer.setVolume(player.getSettingsManager().getSettings(guild).getVolume());
            handler = new AudioHandler(this, guild, audioPlayer);
            audioPlayer.addListener(handler);
            guild.getAudioManager().setSendingHandler(handler);
        }
        else
            handler = (AudioHandler) guild.getAudioManager().getSendingHandler();
        return handler;
    }
}
