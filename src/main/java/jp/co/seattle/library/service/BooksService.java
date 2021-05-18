package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        //BookInfoの内容をlistで返す。jdbcTemplateを用いてタイトルの昇順に並べ、BookInfoRowMapperでマッピングする。
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books order by title asc",
                new BookInfoRowMapper());

        return getedBookList;
    }

    /**
     * 書籍IDを取得する
     *
     * @return 書籍ID
     */
    public int getBookid() {
        String sql = "SELECT MAX(ID) FROM books";
        int bookId = jdbcTemplate.queryForObject(sql, Integer.class);
        return bookId;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT * FROM books where id ="
                + bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());
        bookDetailsInfo.setLending(isLending(bookId));
        return bookDetailsInfo;
    }



    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title, author,publisher,publish_date,isbn,description,thumbnail_name,thumbnail_url,reg_date,upd_date) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getPublishDate() + "','"
                + bookInfo.getIsbn() + "','"
                + bookInfo.getDescription() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "',"
                + "sysdate(),"
                + "sysdate())";

        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を編集する
     *
     * @param bookInfo 書籍情報
     */
    public void editBook(BookDetailsInfo bookInfo) {

        String sql = "update books set title = '"
                + bookInfo.getTitle() + "', author = '"
                + bookInfo.getAuthor() + "', publisher = '"
                + bookInfo.getPublisher() + "', publish_date = '"
                + bookInfo.getPublishDate() + "', isbn = '"
                + bookInfo.getIsbn() + "', description = '"
                + bookInfo.getDescription() + "', thumbnail_url = '"
                + bookInfo.getThumbnailUrl() + "', thumbnail_name = '"
                + bookInfo.getThumbnailName() + "', upd_date = sysdate() where id = " + bookInfo.getBookId();

        jdbcTemplate.update(sql);
    }

    /**
     * 書籍情報を削除する
     *
     *@param bookId 書籍ID
     */
    public void deleteBook(int bookId) {
        String sql = "delete from books where id  = '" + bookId + "'";
        jdbcTemplate.update(sql);
    }

    /**
     * 書籍が貸し出し中の場合true
     * @param bookId
     * @return true or false
     */
    public boolean isLending(int bookId) {
        String sql = "select book_id from lending where book_id = " + bookId;
        try {
            jdbcTemplate.queryForObject(sql, Integer.class);
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    /**
     * 書籍を借りる場合
     * @param bookId
     * 
     */
    public void rentBook(int bookId) {
        String sql = "insert into lending (book_id) values (" + bookId + ")";
        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を返す場合
     * @param bookId
     * 
     */
    public void returnBook(int bookId) {
        String sql = "delete from lending where book_id =" + bookId;
        jdbcTemplate.update(sql);
    }

    /**
     * 書籍を検索する(完全一致)
     * @param searchBook タイトル検索ワード
     * @return 書籍リスト
     */
    public List<BookInfo> perfectBookList(String searchBook) {
        List<BookInfo> searchedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books where title like '" + searchBook + "' ORDER BY title asc ",
                new BookInfoRowMapper());

        return searchedBookList;
    }

    /**
     * 書籍を検索する(部分一致)
     * @param searchBook タイトル検索ワード
     * @return 書籍リスト
     */
    public List<BookInfo> partBookList(String searchBook) {
        List<BookInfo> searchedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books where title like '%"+ searchBook +"%' ORDER BY title asc ",
                new BookInfoRowMapper());

        return searchedBookList;
    }


}
