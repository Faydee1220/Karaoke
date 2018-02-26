package com.teamtreehouse.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.io.*;
import java.util.Comparator;

public class SongBook {
  private List<Song> songs; // 因為 Song.class 在同一個 package 內，不需要另外 import

  public SongBook() {
    songs = new ArrayList<Song>();
  }
  
  public void exportTo(String fileName) {
    try (
      FileOutputStream fos = new FileOutputStream(fileName);
      PrintWriter writer = new PrintWriter(fos);
    ) {
      for (Song song : songs) {
        writer.printf("%s|%s|%s%n", 
                      song.getArtist(), 
                      song.getTitle(), 
                      song.getVideoUrl());
      }
    
    } catch (IOException e) {
      System.out.printf("Problem saving %s %n", fileName);
      e.printStackTrace();
    }
  }
  
  public void importFrom(String fileName) {
    try (
      FileInputStream fis = new FileInputStream(fileName);
      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] args = line.split("\\|");
        addSong(new Song(args[0], args[1], args[2]));
      }
    } catch (IOException e) {
      System.out.printf("Problem loading %s %n", fileName);
      e.printStackTrace();
    }
  }
  
  public void addSong(Song song) {
    songs.add(song);
  }
  
  public int getSongsCount() {
    return songs.size();
  }
  
  // TODO: This should be cached
  private Map<String, List<Song>> byArtist() {
    Map<String, List<Song>> byArtist = new TreeMap<>();
    for (Song song : songs) {
      List<Song> artistSongs = byArtist.get(song.getArtist());
      if (artistSongs == null) {
        artistSongs = new ArrayList<>();
        byArtist.put(song.getArtist(), artistSongs);
      }
      artistSongs.add(song);
    }
    return byArtist;
  }
  
  public Set<String> getArtists() {
    return byArtist().keySet();
  }
  
  public List<Song> getSongsForArtist(String artist) {
    List<Song> songs =  byArtist().get(artist);
    // Anonymous Classes
    songs.sort(new Comparator<Song>() {
      @Override
      public int compare(Song song1, Song song2) {
        if (song1.equals(song2)) {
          return 0;
        }
        return song1.title.compareTo(song2.title);
      }
    });
    return songs;
  }
  
  
  
}