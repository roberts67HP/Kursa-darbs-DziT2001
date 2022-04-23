package lv.roberts.kursa_darbs;

public class Book {
    public int id;
    public String title;
    public String image;
    public String author;
    public String publYear;
    public int amountAvailable;
    public String description;

    Book (int id, String title, String image, String author, String publYear, int amountAvailable,
          String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.publYear = publYear;
        this.amountAvailable = amountAvailable;
        this.description = description;
    }
}