package service;



import org.junit.Assert;
import org.junit.Test;
import pojo.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * BookService 单元测试
 * @author 17694
 * @date 2022/01/20
 **/


public class BookServiceTest {

        private final BookService bookService;

    public BookServiceTest() {
        bookService = new BookService();
    }

    public void init(){
        //初始化操作
    }
    @Test
    public void getBookListTest(){
        List<Book> bookList = bookService.getBookListByf();
        bookList.stream().forEach(System.out::println);
        //Assert.assertNotNull(bookList);
    }

    @Test
    public void getBookListMap() {
        List<Map<String, Object>> bookWithStore = bookService.getBookWithStore();
        bookWithStore.stream().forEach(i -> i.entrySet().forEach(System.out::println));

    }


}