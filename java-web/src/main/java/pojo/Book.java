package pojo;/**
 * @author 17694
 * @date 2022/01/20
 **/

/**
 * @ClassName : Book 
 * @Description :   
 */
public class Book {
    private Integer id;
    private String book;
    private  String author;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getAuthor() {
        return author;
    }
}
