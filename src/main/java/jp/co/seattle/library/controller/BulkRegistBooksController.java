package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller //APIの入り口
public class BulkRegistBooksController {
    final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

    @Autowired
    private BooksService booksService;

    @RequestMapping(value = "/bulkRegistBook", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String bulkRegistBook(Model model) {
        return "bulkRegistBook";
    }

    /**
     * 書籍情報をCSVファイルから一括登録する
     * @param locale ロケール情報
     * @param file csvファイル
     * @return 一括登録画面
     */
    @Transactional
    @RequestMapping(value = "/bulkBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String bulkBook(Locale locale,
            @RequestParam("csv") MultipartFile file,
            Model model) {
        logger.info("Welcome insertBooksbulk.java! The client locale is {}.", locale);


        try {
            List<BookDetailsInfo> bookcsv = new ArrayList<BookDetailsInfo>();
            String line = null;

            InputStream csv = file.getInputStream();
            Reader reader = new InputStreamReader(csv);
            BufferedReader br = new BufferedReader(reader);
            int rowCount = 1;
            boolean flag = false;
            String errorMessage = "";

            line = br.readLine();

            //while文は値を保持する
            while((line = br.readLine()) != null) {
                String[] detail = line.split(",", 6);
                //必須項目が入力されているかチェックする。
                if (detail[0].isEmpty() || detail[1].isEmpty() || detail[2].isEmpty() || detail[3].isEmpty()) {
                    errorMessage = +rowCount + "行目で必要な情報がありません";
                    flag = true;
                }
                if (detail[3].isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        sdf.setLenient(false);
                        sdf.parse(detail[3]);
                    } catch (ParseException ex) {
                        errorMessage = +rowCount + "行目の出版日はYYYYMMDDの形式で入力してください";
                        flag = true;
                    }
                }
                if (detail[4].isEmpty() || detail[4] != null || !(detail[4].matches("([0-9]{10}|[0-9]{13})?"))) {
                    errorMessage = +rowCount + "行目のISBNは10桁もしくは13桁の数字で入力してください";
                    flag = true;

                }

                BookDetailsInfo bookInfo = new BookDetailsInfo();
                bookInfo.setTitle(detail[0]);
                bookInfo.setAuthor(detail[1]);
                bookInfo.setPublisher(detail[2]);
                bookInfo.setPublishDate(detail[3]);
                bookInfo.setIsbn(detail[4]);
                bookInfo.setDescription(detail[5]);

                bookcsv.add(bookInfo);
            }


            if (flag) {
                model.addAttribute("errorMessage", errorMessage);
                return "bulkRegistBook";
            }

            for (BookDetailsInfo book : bookcsv) {
                booksService.registBook(book);
            }

            model.addAttribute("complete", "登録完了");
            return "bulkRegistBook";


        } catch (IOException e) {
            model.addAttribute("errorImport", "CSVファイルを読み込むことが出来ませんでした。");

            return "bulkregistBook";

        }catch (Exception e) {
            model.addAttribute("errorImport", "CSVファイルを読み込むことが出来ませんでした。");

            return "bulkregistBook";

    }
}
}