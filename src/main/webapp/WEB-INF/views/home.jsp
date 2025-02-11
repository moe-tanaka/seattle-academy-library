<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/home.js" /></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li>現在の登録件数は${count}冊です。</li>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <div>
            <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a> 
            <a href="<%=request.getContextPath()%>/bulkRegistBook" class="btn_bulk_book">一括登録</a>
            <form method="post" action="deleteSelectBook">
                <button type="submit" id="allDeleteBook" name="allDeleteBook" class="btn_rentBook">一括削除</button>
                <input type="checkbox" id="all" value=all>一括選択
                <input type="hidden" name="bookList" id="bookList" value="a">
            </form>
            <form class="searchForm" id="form1" action="<%=request.getContextPath()%>/searchBook">
                <select name="searchSelect">
                <option value="title">書籍名</option>
                <option value="author">著者名</option>
                <option value="publisher">出版社名</option>
                </select>
                <input type="radio" name="check" value="perfect" checked>完全一致 
                <input type="radio" name="check" value="parts">部分一致 
                <input type="search" class="search1" name="searchBook" placeholder="キーワードを入力"> 
                <input type="submit" class=".search_box" id="searchButton" value="検索" />
            </form>
        </div>
        <div class="content_body">
            <c:if test="${count == 0}">
                <div class="error_msg">登録書籍は0件です。書籍を登録して下さい。</div>
            </c:if>
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <c:if test="${!empty searchError && count != 0}">
                <div class="error_msg">${searchError}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                                <form>
                                <input type="checkbox" name="checkbox" id="checkbox" value="${bookInfo.bookId}">削除
                                </form>
                            <form method="post" class="book_thumnail" action="<%=request.getContextPath()%>/details">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> 
                                    <c:if test="${bookInfo.thumbnail == 'null'}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if>
                                     <c:if test="${bookInfo.thumbnail != 'null'}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> 
                                <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">(著)${bookInfo.author}</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
