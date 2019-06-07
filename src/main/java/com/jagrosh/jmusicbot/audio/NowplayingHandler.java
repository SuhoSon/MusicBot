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

import com.jagrosh.jmusicbot.entities.Pair;
import com.jagrosh.jmusicbot.settings.Settings;
import com.jagrosh.jmusicbot.shutdown.Observer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class NowplayingHandler implements Observer
{
	private final Nowplaying nowplaying;
    private final HashMap<Long,Pair<Long,Long>> lastNP; // guild -> channel,message
    
    private boolean shuttingDown = false;
    private JDA jda;
    
    public NowplayingHandler(Nowplaying nowplaying)
    {
    	this.nowplaying = nowplaying;
        this.lastNP = new HashMap<>();
    }
    
    public void setJDA(JDA jda) {
    	this.jda = jda;
    }
    
    public JDA getJDA() {
    	return this.jda;
    }
    
    public void init()
    {
        nowplaying.getThreadpool().scheduleWithFixedDelay(() -> updateAll(), 0, 5, TimeUnit.SECONDS);
    }
    
    public Nowplaying getNowplaying() {
    	return this.nowplaying;
    }
    
    public void setLastNPMessage(Message m)
    {
        lastNP.put(m.getGuild().getIdLong(), new Pair<>(m.getTextChannel().getIdLong(), m.getIdLong()));
    }
    
    public void clearLastNPMessage(Guild guild)
    {
        lastNP.remove(guild.getIdLong());
    }
    
    public void shutdown() {
        if(shuttingDown)
            return;
        shuttingDown = true;
        
        if(jda.getStatus()!=JDA.Status.SHUTTING_DOWN)
        {
            jda.getGuilds().stream().forEach(g -> 
            {
                g.getAudioManager().closeAudioConnection();
                AudioHandler ah = (AudioHandler)g.getAudioManager().getSendingHandler();
                if(ah!=null)
                {
                    ah.stopAndClear();
                    ah.getPlayer().destroy();
                    this.updateTopic(g.getIdLong(), ah, true);
                }
            });
        }
    }
    
    private void updateAll()
    {
        Set<Long> toRemove = new HashSet<>();
        for(long guildId: lastNP.keySet())
        {
            Guild guild = jda.getGuildById(guildId);
            if(guild==null)
            {
                toRemove.add(guildId);
                continue;
            }
            Pair<Long,Long> pair = lastNP.get(guildId);
            TextChannel tc = guild.getTextChannelById(pair.getKey());
            if(tc==null)
            {
                toRemove.add(guildId);
                continue;
            }
            AudioHandler handler = (AudioHandler)guild.getAudioManager().getSendingHandler();
            Message msg = handler.getNowPlaying(jda);
            if(msg==null)
            {
                msg = handler.getNoMusicPlaying(jda);
                toRemove.add(guildId);
            }
            try 
            {
                tc.editMessageById(pair.getValue(), msg).queue(m->{}, t -> lastNP.remove(guildId));
            } 
            catch(Exception e) 
            {
                toRemove.add(guildId);
            }
        }
        toRemove.forEach(id -> lastNP.remove(id));
    }
    
    public void updateTopic(long guildId, AudioHandler handler, boolean wait)
    {
        Guild guild = jda.getGuildById(guildId);
        if(guild==null)
            return;
        Settings settings = nowplaying.getSettingsManager().getSettings(guildId);
        TextChannel tchan = settings.getTextChannel(guild);
        if(tchan!=null && guild.getSelfMember().hasPermission(tchan, Permission.MANAGE_CHANNEL))
        {
            String otherText;
            if(tchan.getTopic()==null || tchan.getTopic().isEmpty())
                otherText = "\u200B";
            else if(tchan.getTopic().contains("\u200B"))
                otherText = tchan.getTopic().substring(tchan.getTopic().lastIndexOf("\u200B"));
            else
                otherText = "\u200B\n "+tchan.getTopic();
            String text = handler.getTopicFormat(jda) + otherText;
            if(!text.equals(tchan.getTopic()))
            {
                try 
                {
                    if(wait)
                        tchan.getManager().setTopic(text).complete();
                    else
                        tchan.getManager().setTopic(text).queue();
                } 
                catch(PermissionException ignore) {}
            }
        }
    }
    
    // "event"-based methods
    public void onTrackUpdate(long guildId, AudioTrack track, AudioHandler handler)
    {
        // update bot status if applicable
        if(nowplaying.getConfig().getSongInStatus())
        {
            if(track!=null && jda.getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count()<=1)
                jda.getPresence().setGame(Game.listening(track.getInfo().title));
            else
                nowplaying.resetGame();
        }
        
        // update channel topic if applicable
        updateTopic(guildId, handler, false);
    }
    
    public void onMessageDelete(Guild guild, long messageId)
    {
        Pair<Long,Long> pair = lastNP.get(guild.getIdLong());
        if(pair==null)
            return;
        if(pair.getValue() == messageId)
            lastNP.remove(guild.getIdLong());
    }
}
