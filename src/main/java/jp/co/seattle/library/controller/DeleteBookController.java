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

/**
 * 削除コントローラー
 */
@Controller //APIの入り口
public class DeleteBookController {
    final static Logger logger = LoggerFactory.getLogger(DeleteBookController.class);
    @Autowired
    private BooksService booksService;

    /**
     * 対象書籍を削除する
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public String deleteBook(
            Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        logger.info("Welcome delete! The client locale is {}.", locale);

        //貸し出し可の場合のみ書籍情報を削除
        if (booksService.isLending(bookId)) {
            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
            model.addAttribute("count", booksService.getBookList().size());
            return "details";
        }

        booksService.deleteBook(bookId);
        model.addAttribute("bookList", booksService.getBookList());
        model.addAttribute("count", booksService.getBookList().size());
        return "home";
    }

    /**
     * 選択した書籍を一括削除
     * @param locale
     * @param 削除したい書籍のリスト
     * @param model
     * @return ホーム画面に遷移
     */
    @Transactional
    @RequestMapping(value = "/deleteSelectBook", method = RequestMethod.POST)
    public String deleteSelectBooksBulk(
            Locale locale,
            @RequestParam("bookList") Integer[] bookList,
            Model model) {
        logger.info("Welcome deleteBulk! The client locale is {}.", locale);
        
        
        for (int bookId : bookList) {
            if (!booksService.isLending(bookId)) {
                booksService.deleteBook(bookId);
        }
        }
        model.addAttribute("bookList", booksService.getBookList());
        model.addAttribute("count", booksService.getBookList().size());
        return "home";
        
        
}
}
