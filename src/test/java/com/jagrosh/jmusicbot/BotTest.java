package com.jagrosh.jmusicbot;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import com.jagrosh.jdautilities.examples.command.PingCommand;
import com.jagrosh.jmusicbot.commands.general.SettingsCmd;
import com.jagrosh.jmusicbot.entities.Prompt;
import com.jagrosh.jmusicbot.settings.SettingsManager;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jmusicbot.audio.Nowplaying;
import com.jagrosh.jmusicbot.audio.NowplayingConfig;
import com.jagrosh.jmusicbot.audio.NowplayingHandler;
import com.jagrosh.jmusicbot.audio.Player;
import com.jagrosh.jmusicbot.audio.PlayerConfig;
import com.jagrosh.jmusicbot.audio.PlayerManager;
import com.jagrosh.jmusicbot.commands.admin.*;
import com.jagrosh.jmusicbot.commands.dj.*;
import com.jagrosh.jmusicbot.commands.music.*;
import com.jagrosh.jmusicbot.commands.owner.*;
import com.jagrosh.jmusicbot.playlist.PlaylistLoader;
import com.jagrosh.jmusicbot.playlist.PlaylistConfig;
import com.jagrosh.jmusicbot.shutdown.ShutdownListener;

public class BotTest {
	static String validToken = "NTc3Njc3OTQ5MTgxMTAwMDMz.XNoing._r5EAREfG9XuFdJ6eoayTZIvXJA";
	static String validOwner = "476995339631460353";
	static String tokenIdentifier = "token = ";
	static String ownerIdentifier = "owner = ";
	
	static File originalFile;
	static File newFile;
	static FileInputStream fileInputStream;
	static FileOutputStream fileOutputStream;
	static BufferedReader bufferedReader;
	static BufferedWriter bufferedWriter;
	
	static Prompt prompt;
	static BotConfig config;
	static EventWaiter waiter;
	static SettingsManager settings;
	static Bot bot;
	static CommandClient client;
	static CommandClientBuilder cb;
	static PlayerManager playermanager;
	static JDA jda;

	final static Permission[] RECOMMENDED_PERMS = new Permission[]{Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY, Permission.MESSAGE_ADD_REACTION,
            Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_MANAGE, Permission.MESSAGE_EXT_EMOJI,
            Permission.MANAGE_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.NICKNAME_CHANGE};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		String line;
		String replacedLine;

		originalFile = new File("C:\\Users\\Son\\git\\MusicBot\\config_temp.txt");
		newFile = new File("C:\\Users\\Son\\git\\MusicBot\\config.txt");
		fileInputStream = new FileInputStream(originalFile);
		fileOutputStream = new FileOutputStream(newFile);
		bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
		
		while((line = bufferedReader.readLine()) != null) {
			if(line.contains(tokenIdentifier)) {
				replacedLine = line.replaceAll(tokenIdentifier, tokenIdentifier + validToken);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else if(line.contains(ownerIdentifier)) {
				replacedLine = line.replaceAll(ownerIdentifier, ownerIdentifier + validOwner);
				bufferedWriter.write(replacedLine, 0, replacedLine.length());
			} else {
				bufferedWriter.write(line, 0, line.length());
			}
			bufferedWriter.newLine();
		}
		bufferedReader.close();
		bufferedWriter.close();
		
		Boolean nogui = "true".equalsIgnoreCase(System.getProperty("nogui", "false"));
		prompt = new Prompt("JMusicBot", "Switching to nogui mode. You can manually start in nogui mode by including the -Dnogui=true flag.", 
                nogui);
		
		config = new BotConfig(prompt);
        config.load();
        waiter = new EventWaiter();
        settings = new SettingsManager();
        bot = new Bot.Builder().setEventWaiter(waiter).setBotConfig(config).setSettingsManager(settings).build();
        
        NowplayingHandler nowplaying = new NowplayingHandler((Nowplaying)bot, (NowplayingConfig)config);
        nowplaying.init();
        
        PlaylistLoader playlists = new PlaylistLoader((PlaylistConfig)config);

        playermanager = new PlayerManager((Player)bot, nowplaying, (PlayerConfig)config, playlists);
        playermanager.init();

        AboutCommand aboutCommand = new AboutCommand(Color.BLUE.brighter(),
                                "a music bot that is [easy to host yourself!](https://github.com/jagrosh/MusicBot) (v)",
                                new String[]{"High-quality music playback", "FairQueue™ Technology", "Easy to host yourself"},
                                RECOMMENDED_PERMS);
        aboutCommand.setIsAuthor(false);
        aboutCommand.setReplacementCharacter("\uD83C\uDFB6"); // 🎶
        
        // set up the command client
        cb = new CommandClientBuilder()
                .setPrefix(config.getPrefix())
                .setAlternativePrefix(config.getAltPrefix())
                .setOwnerId(Long.toString(config.getOwnerId()))
                .setEmojis(config.getSuccess(), config.getWarning(), config.getError())
                .setHelpWord(config.getHelp())
                .setLinkedCacheSize(200)
                .setGuildSettingsManager(settings)
                .addCommands(aboutCommand,
                        new PingCommand(),
                        new SettingsCmd(),
                        
                        new LyricsCmd(),
                        new NowplayingCmd(playermanager),
                        new PlayCmd(playermanager, config.getLoading()),
                        new PlaylistsCmd(playermanager),
                        new QueueCmd(playermanager),
                        new RemoveCmd(),
                        new SearchCmd(playermanager, config.getSearching()),
                        new SCSearchCmd(playermanager, config.getSearching()),
                        new ShuffleCmd(),
                        new SkipCmd(),
                        
                        new ForceskipCmd(),
                        new MoveTrackCmd(),
                        new PauseCmd(),
                        new PlaynextCmd(playermanager, config.getLoading()),
                        new RepeatCmd(),
                        new SkiptoCmd(),
                        new StopCmd(),
                        new VolumeCmd(),
                        
                        new SetdjCmd(),
                        new SettcCmd(),
                        new SetvcCmd(),
                        
                        new AutoplaylistCmd(playlists),
                        new PlaylistCmd(playlists),
                        new SetavatarCmd(),
                        new SetgameCmd(),
                        new SetnameCmd(),
                        new SetstatusCmd()
                );
        
        cb.setStatus(config.getStatus());
        cb.useDefaultGame();
        client = cb.build();
        
        jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())
                .setAudioEnabled(true)
                .setGame(null)
                .setStatus(OnlineStatus.INVISIBLE)
                .addEventListener(client, waiter, new Listener(playermanager))
                .setBulkDeleteSplittingEnabled(true)
                .build();
        bot.setJDA(jda);
        nowplaying.setJDA(jda);
        
        ShutdownListener shutdownlistener = new ShutdownListener(jda);
        cb.addCommand(new ShutdownCmd(shutdownlistener));
        
        shutdownlistener.attach(bot);
        shutdownlistener.attach(nowplaying);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		newFile.delete();
	}

	/**
	 * Purpose : Invalid guild id test for closeAudioConnection().
	 * Input   : closeAudioConnection 1 -> null
	 * Expected:
	 * 			Return null.
	 */
	@Test
	public void closeAudioConnectionWithInvalidGuildIDTest() {
		bot.closeAudioConnection(1);
	}

	/**
	 * Purpose : Test resetGameTest().
	 * Input   : resetGame null -> null
	 * Expected:
	 * 			Return null.
	 * @throws Exception 
	 */
	@Test
	public void resetGameTest() throws Exception {
		bot.resetGame();
		jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())
                .setAudioEnabled(true)
                .setGame(Game.playing("loading..."))
                .setStatus(OnlineStatus.INVISIBLE)
                .addEventListener(client, waiter, new Listener(playermanager))
                .setBulkDeleteSplittingEnabled(true)
                .build();
        bot.setJDA(jda);
		bot.resetGame();
	}

	/**
	 * Purpose : When shutdown() is called twice, shuttingDown variable control the state.
	 * Input   : shutdown null -> null
	 * Expected:
	 * 			Return null.
	 */
	@Test
	public void shutDownTwiceTest() {
		bot.shutdown();
		bot.shutdown();
	}

	/**
	 * Purpose : Getter test.
	 * Input   : getSettingsManager,getConfig,getJDA null -> initial instances
	 * Expected:
	 * 			Return correct instances.
	 */
	@Test
	public void getSettingsManagerTest() {
        assertEquals(settings, bot.getSettingsManager());
        assertEquals(config, bot.getConfig());
        assertEquals(jda, bot.getJDA());
	}
}
