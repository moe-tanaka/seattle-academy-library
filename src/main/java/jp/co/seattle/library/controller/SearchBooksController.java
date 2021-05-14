package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

@Controller //APIの入り口
public class SearchBooksController {

    final static Logger logger = LoggerFactory.getLogger(SearchBooksController.class);

    @Autowired
    private BooksService booksService;

    /**
     * 書籍タイトルを検索する
     *
     * @param locale ロケール情報
     * @param title 書籍名
     * @param model モデル情報
     * @return 遷移先画面名
     */

    @Transactional
    @RequestMapping(value = "/searchBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String searchBook(
            Locale locale,
            @RequestParam("searchBook") String searchBook,
            Model model) {
        logger.info("Welcome rent! The client locale is {}.", locale);

        model.addAttribute("bookList", booksService.searchBookList(searchBook));
        return "home";
    }

}
