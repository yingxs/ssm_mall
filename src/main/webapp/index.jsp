<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>file upload</title>
</head>
<body>
<h2>Hello World!</h2>

SpringMVC上传文件
<form action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="上传文件">

</form>

richtext_img_upload富文本上传
<form action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="富文本上传">

</form>

</body>
</html>
