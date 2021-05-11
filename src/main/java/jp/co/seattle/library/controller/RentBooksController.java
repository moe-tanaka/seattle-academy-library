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

public class RentBooksController {

    final static Logger logger = LoggerFactory.getLogger(RentBooksController.class);

    @Autowired
    private BooksService booksService;

    /**
     * 対象書籍を借りる
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String rentBook(
            Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        logger.info("Welcome rent! The client locale is {}.", locale);

        //貸し出し中か確認する
        if (booksService.isLending(bookId)) {
            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
            model.addAttribute("lendingMessage", "この本は貸出し中です");
            return "details";
        }

        //lendingテーブルに追加する
        booksService.rentBook(bookId);

        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        return "details";

    }

    /**
     * 対象書籍を返却する
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/returnBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String returnBook(
            Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        logger.info("Welcome return! The client locale is {}.", locale);

        //貸し出し中か確認する
        if (!(booksService.isLending(bookId))) {
            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
            model.addAttribute("lendingMessage", "この本は返却済みです");
            return "details";
        }

        ////lendingテーブルから書籍情報を削除
        booksService.returnBook(bookId);

        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
        return "details";

    }

}