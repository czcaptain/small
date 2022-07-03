package service;/**
 * @author 17694
 * @date 2022/01/20
 **/


import com.yin.annotation.Transaction;
import com.yin.helper.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName : BookService 
 * @Description :   
 */
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);


    public List<Book> getBookList() {
        //List<Book> books = null;
        List<Book> books = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DataBaseHelper.getConnection();
            String sql = "select * from book";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBook(resultSet.getString("book"));
                book.setAuthor(resultSet.getString("author"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseHelper.closeConnection();
        }
        return books;
    }

    public List<Book> getBookListByf() {
            String sql = "select * from book";
            return  DataBaseHelper.queryList(Book.class,sql);

    }

    /**
     * 获取数据和书店相关消息
     * @return
     */
    public List<Map<String,Object>> getBookWithStore() {
        String sql = "select a.id as bookId,book,author,B.* from book a,store b" ;
        return DataBaseHelper.queryList(sql);
    }

    @Transaction
    public boolean insertBook(Book book,Map<String,Object> fieldMap) {

       return DataBaseHelper.insertBook(Book.class,fieldMap);
       
    }




}
