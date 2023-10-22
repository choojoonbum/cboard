<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fdt" tagdir="/WEB-INF/tags" %>

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
                        <div class="form-group">
                            <label>번호</label>
                            <input class="form-control" readonly="readonly" value='${board.bno}'>
                        </div>
                        <div class="form-group">
                            <label>제목</label>
                            <input class="form-control" readonly="readonly" value='${board.title}'>
                        </div>
                        <div class="form-group">
                            <label>내용</label>
                            <textarea class="form-control" rows="3" readonly="readonly">${board.content}</textarea>
                        </div>
                        <div class="form-group">
                            <label>작성자</label>
                            <input class="form-control" readonly="readonly" value="${board.writer}">
                        </div>
                        <div class="form-group">
                            <label>등록일</label>
                            <input class="form-control" readonly="readonly" value="<fdt:formatDateTime value="${board.regDate}" pattern="yyyy-MM-dd hh:mm:ss"/>">
                        </div>
                        <div class="form-group">
                            <label>수정일</label>
                            <input class="form-control" readonly="readonly" value="<fdt:formatDateTime value="${board.modDate}" pattern="yyyy-MM-dd hh:mm:ss"/>">
                        </div>
                        <button data-oper="modify" class="btn btn-defalut">modify</button>
                        <button data-oper="list" class="btn btn-info">List</button>
                    </div>
                </div>
                <!-- /.row (nested) -->
                <form id='operForm' action="modify" method="get">
                    <input type="hidden" id="bno" name='bno' value='${board.bno}'/>
                    <input type="hidden" name='pageNum' value='${criteria.pageNum}'/>
                    <input type="hidden" name='amount' value='${criteria.amount}'/>
                    <input type="hidden" name='keyword' value='${criteria.keyword}'/>
                    <input type="hidden" name='type' value='${criteria.type}'/>
                </form>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<script>
    $(document).ready(function() {
        var operForm = $("#operForm");

        $('button[data-oper="modify"]').on("click", function() {
            operForm.attr("action", "modify").submit();
        });
        $('button[data-oper="list"]').on("click", function() {
            operForm.find("#bno").remove();
            operForm.attr("action", "list")
            operForm.submit();
        });
    })
</script>