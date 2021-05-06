<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  
<meta charset="UTF-8">
  
<title>一括登録｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
  
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
  
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
  
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
  
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
  
<script src="resources/js/thumbnail.js"></script>
    
<script src="resources/js/addBtn.js"></script>
</head>
<body class="wrapper">
      
    <header>
            
        <div class="left">
                  <img class="mark" src="resources/img/logo.png" />       
            <div class="logo">Seattle Library</div>
                
        </div>
            
        <div class="right">
                  
            <ul>
                        
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>         
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>       
            </ul>
                
        </div>
          
    </header>
      
    <main>
        <div>
            <form action="<%=request.getContextPath()%>/bulkBook" method="post" enctype="multipart/form-data" id="data_upload_form">
                <h1>一括登録</h1>
                <div class="bulk_form">
                    <h2>CSVファイルをアップロードすることで書籍を一括で登録できます。</h2>
                    <div class=caution>
                        <p>「書籍名,著者名,出版社,出版日,ISBN」の形式で記載してください。</p>
                        <p>※サムネイル画像は一括登録できません。編集画面で一冊単位で登録してください。</p>
                    </div>
                    <p>
                        <input type="file" accept="text/csv" name="csv" id="csv">
                        <c:if test="${!empty errorMessage}">
                            <div class="error">${errorMessage}</div>
                        </c:if>
                        <c:if test="${!empty errorImport}">
                            <div class="error">${errorImport}</div>
                        </c:if>
                    </p>
                </div>
                <div class="addBookBtn_box">
                    <button type="submit" id="add-btn" class="btn_bulkRegist">一括登録</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>