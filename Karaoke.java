import com.teamtreehouse.model.Song;
import com.teamtreehouse.model.SongBook;
import com.teamtreehouse.KaraokeMachine;

public class Karaoke {
  
  public static void main(String[] args) {
//    Song song = new Song(
//      "Michael Jackson", 
//      "Beat It",
//      "https://www.youtube.com/watch?v=Ym0hZG-zNOk");
//    
//    SongBook songBook = new SongBook();
//    System.out.printf("Adding %s %n", song);
//    songBook.addSong(song);
//    System.out.printf("There are %d songs. %n", songBook.getSongsCount());
    
    SongBook songBook = new SongBook();
    songBook.importFrom("songs.txt");
    KaraokeMachine karaokeMachine = new KaraokeMachine(songBook);
    karaokeMachine.run();
    System.out.println("Saving songbook...");
    songBook.exportTo("songs.txt");
  }
  
  
}
