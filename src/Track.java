public class Track {
    int SongId;
    public String Title;
    String Author;
    String Length;
    float Price;
    String Category;
    
    public Track(int songId, String title, String author, String length, float price, String category) {
	SongId = songId;
	Title = title;
	Author = author;
	Length = length;
	Price = price;
	Category = category;
    }
}
