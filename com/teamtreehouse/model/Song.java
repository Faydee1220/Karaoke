package com.teamtreehouse.model;

public class Song {
  // protected 代表同一個 package 內可以存取
  protected String artist;
  protected String title;
  protected String videoUrl;
  
  public Song(String artist, String title, String videoUrl) {
    this.artist = artist;
    this.title = title;
    this.videoUrl = videoUrl;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getArtist() {
    return artist;
  }
  
  public String getVideoUrl() {
    return videoUrl;
  }
  
  @Override
  public String toString() {
    return String.format("Song:  %s by %s", title, artist);
  }
  
}