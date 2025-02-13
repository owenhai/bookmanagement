package org.example.bookmanagement;

import java.sql.Date;

public class BookData
{
    private Integer bookId;
    private String title;
    private String author;
    private String genre;
    private Double price;
    private Date date;
    private String image;

    public BookData(Integer bookId, String title, String author, String genre, Date date, Double price, String image)
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.date = date;
        this.image = image;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
