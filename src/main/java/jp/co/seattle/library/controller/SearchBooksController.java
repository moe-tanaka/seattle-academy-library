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
     * @param searchBook 検索名
     * @param model モデル情報
     * @param check ラジオボタンの入力情報
     * @return 遷移先画面名
     */

    @Transactional
    @RequestMapping(value = "/searchBook", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String searchBook(
            Locale locale,
            @RequestParam("searchBook") String searchBook,
            @RequestParam("check") String check,
            Model model) {
        logger.info("Welcome search! The client locale is {}.", locale);

        if (searchBook.isEmpty()) {
            model.addAttribute("searchError", "検索結果がありません。条件を変えてもう一度検索して下さい。");
            model.addAttribute("count", booksService.getBookList().size());
            return "home";
        }
        if (check.equals("perfect")) {
            if (booksService.perfectBookList(searchBook).isEmpty()) {
                model.addAttribute("searchError", "検索結果がありません。条件を変えてもう一度検索して下さい。");
                model.addAttribute("count", booksService.getBookList().size());
                return "home";
            } 
            model.addAttribute("bookList", booksService.perfectBookList(searchBook));
            model.addAttribute("count", booksService.getBookList().size());
            return "home";
        }

        if (check.equals("parts")) {
            if (booksService.partBookList(searchBook).isEmpty()) {
                model.addAttribute("searchError", "検索結果がありません。条件を変えてもう一度検索して下さい。");
                model.addAttribute("count", booksService.getBookList().size());
                return "home";
            }
            model.addAttribute("bookList", booksService.partBookList(searchBook));
            model.addAttribute("count", booksService.getBookList().size());
            return "home";
        }

        return "home";
    }

}

