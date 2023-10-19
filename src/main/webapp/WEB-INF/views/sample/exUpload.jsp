<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/sample/exUploadPost" method="post" enctype="multipart/form-data">
    <div>
        <input type="file" name="files">
    </div>
    <div>
        <input type="file" name="files">
    </div>
    <div>
        <input type="file" name="files">
    </div>
    <div>
        <input type="file" name="files">
    </div>
    <div>
        <input type="file" name="files">
    </div>
    <div>
        <input type="file" name="files">
    </div>
    <div>
        <input type="submit">
    </div>
</form>

</body>
</html>
