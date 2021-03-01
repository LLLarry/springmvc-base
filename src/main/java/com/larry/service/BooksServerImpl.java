package com.larry.service;

import com.larry.dao.BooksMapper;
import com.larry.pojo.Books;

import java.util.List;

/**
 * @author larry
 * @create 2021-03-01 9:25
 */
public class BooksServerImpl implements BooksServer {
    private BooksMapper booksMapper;

    public void setBooksMapper(BooksMapper booksMapper) {
        this.booksMapper = booksMapper;
    }

    public int insertBooks(Books books) {
        return booksMapper.insertBooks(books);
    }

    public int deleteById(int id) {
        return booksMapper.deleteById(id);
    }

    public int updateBooks(Books books) {
        return booksMapper.updateBooks(books);
    }

    public Books queryBooksById(int id) {
        return booksMapper.queryBooksById(id);
    }

    public List<Books> queryBooks() {
        return booksMapper.queryBooks();
    }
}
