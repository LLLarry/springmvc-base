package com.larry.controller;

import com.larry.pojo.Books;
import com.larry.service.BooksServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author larry
 * @create 2021-03-01 11:23
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("booksServerImpl")
    private BooksServerImpl booksServer;
    @RequestMapping("/allbooks")
    public String allBooks(Model model) {
        List<Books> books = booksServer.queryBooks();
        model.addAttribute("list", books);
        return "allBook";
    }
}
