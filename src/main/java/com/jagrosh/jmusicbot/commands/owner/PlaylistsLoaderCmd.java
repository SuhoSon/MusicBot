package com.jagrosh.jmusicbot.commands.owner;

import java.io.IOException;
import java.util.List;

import com.jagrosh.jmusicbot.playlist.PlaylistLoader.Playlist;

public interface PlaylistsLoaderCmd {
	public Playlist getPlaylist(String name);
	public void createPlaylist(String name) throws IOException;
	public boolean folderExists();
	public void createFolder();
	public List<String> getPlaylistNames();
	public void writePlaylist(String name, String text) throws IOException;
	public void deletePlaylist(String name) throws IOException;
}
