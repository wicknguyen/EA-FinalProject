package EA.Project.RabbitMQ.domain;


import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Book() {
        super();
    }

    public Book(String title, String iSBN, String author, double price) {
        super();
        this.title = title;
        ISBN = iSBN;
        this.author = author;
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    private String title;


    private String ISBN;

    private String author;

    private double price;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}
