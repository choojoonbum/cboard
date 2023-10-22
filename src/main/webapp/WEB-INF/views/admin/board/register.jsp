<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">게시판</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                게시글 등록
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-12">
                        <form:form modelAttribute="formData" role="form">
                            <div class="form-group">
                                <label>제목</label>
                                <form:input path="title" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                <form:errors path="title" cssClass="help-block" element="p"/>
                            </div>
                            <div class="form-group">
                                <label>내용</label>
                                <form:textarea path="content" cssClass="form-control" cssErrorClass="form-control is-invalid" rows="3"/>
                                <form:errors path="content" cssClass="help-block" element="p"/>
                            </div>
                            <div class="form-group">
                                <label>작성자</label>
                                <form:input path="writer" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                <form:errors path="writer" cssClass="help-block" element="p"/>
                            </div>
                            <button type="submit" class="btn btn-defalut">등록</button>
                            <button type="reset" class="btn btn-defalut">Reset</button>
                        </form:form>
                    </div>
                </div>
                <!-- /.row (nested) -->
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<script>
    $(document).ready(function (e) {





    });
</script>