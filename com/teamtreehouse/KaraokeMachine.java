package com.teamtreehouse;

import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class KaraokeMachine {
  private SongBook songBook;
  private BufferedReader reader;
  private Map<String, String> menu;
  private Queue<Song> songQueue;

  public KaraokeMachine(SongBook songBook) {
    this.songBook = songBook;
    reader = new BufferedReader(new InputStreamReader(System.in));
    menu = new HashMap<String, String>();
    songQueue = new ArrayDeque<Song>();
    menu.put("add", "Add a new song to the songbook");
    menu.put("play", "Play next song in the queue");
    menu.put("choose", "Choose a song to sing!");
    menu.put("quit", "Give up. Exit the program");
  }

  private String promptAction() throws IOException {
    System.out.printf("There are %d songs available and %d in the queue. Your options are: %n", 
                      songBook.getSongsCount(), 
                      songQueue.size());
    
    for (Map.Entry<String, String> option : menu.entrySet()) {
      System.out.printf("%s - %s %n", 
                        option.getKey(), 
                        option.getValue());
    }
    
    System.out.print("What do you want to do:   ");
    String choice = reader.readLine(); // 此處可能有 IOException
    return choice.trim().toLowerCase();
  }
  
  public void run() {
    String choice = "";
    do {
      try {
        choice = promptAction();
        switch (choice) {
          case "add":
            // add a new song
            Song song = promptNewSong();
            songBook.addSong(song);
            System.out.printf("%s added! %n%n", 
                              song);
            break;
          case "play":
            playNext();
            break;
          case "choose":
            String artist = promptArtist();
            Song artistSong = promptSongForArtist(artist);
            // Add to a song queue
            songQueue.add(artistSong);
            System.out.printf("You chose: %s %n", artistSong);
            break;
          case "quit":
            System.out.println("Thanks for playing!");
            break;
          default:
            System.out.printf("Unknow choice: '%s'. Try again. %n%n%n", 
                              choice);
            break;
        }
      } catch (IOException e) {
        System.out.println("Problem with input");
        e.printStackTrace();
      }
      
    } while(!choice.equals("quit"));
  }
  
  private Song promptNewSong() throws IOException {
    System.out.print("Enter the artist's name:  ");
    String artist = reader.readLine();
    
    System.out.print("Enter the title:  ");
    String title = reader.readLine();
    
    System.out.print("Enter the videro URL:  ");
    String videoUrl = reader.readLine();
    
    return new Song(artist, title, videoUrl);
  }
  
  private int promptForIndex(List<String> options) throws IOException {
    int counter = 1; // 顯示選項編號用
    for (String option : options) {
      System.out.printf("%d.)  %s %n", counter, option);
      counter += 1;
    }
    System.out.print("Your choice:   ");
    String optionAsString = reader.readLine();
    int choice = Integer.parseInt(optionAsString.trim());
    return choice - 1; // 因為 List 的 index 從 0 開始
  }
  
  private String promptArtist() throws IOException {
    System.out.println("Available artists:");
    // ArrayList 的初始化可以放 Set
    List<String> artists = new ArrayList<>(songBook.getArtists());
    int index = promptForIndex(artists);
    return artists.get(index);
  }
  
  private Song promptSongForArtist(String artist) throws IOException {
    List<Song> songs = songBook.getSongsForArtist(artist);
    List<String> songTitles = new ArrayList<>();
    for (Song song : songs) {
      songTitles.add(song.getTitle());
    }
    System.out.printf("Available songs for %s %n", artist);
    int index = promptForIndex(songTitles);
    return songs.get(index);
  }
  
  public void playNext() {
    Song song = songQueue.poll();
    if (song == null) {
      System.out.println("Sorry there are no songs in the queue. " + 
                         "Use choose form menu to add some");
    } else {
      System.out.printf("%n%n%n Open %s to hear %s by %s %n%n%n", 
                        song.getVideoUrl(), 
                        song.getTitle(), 
                        song.getArtist());
    }
  
  }
  
  
  
}