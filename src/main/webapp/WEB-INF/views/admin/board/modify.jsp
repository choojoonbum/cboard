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
                        <form:form modelAttribute="board" role="form">
                            <input type="hidden" name='pageNum' value='${criteria.pageNum}'/>
                            <input type="hidden" name='amount' value='${criteria.amount}'/>
                            <input type="hidden" name='keyword' value='${criteria.keyword}'/>
                            <input type="hidden" name='type' value='${criteria.type}'/>
                            <div class="form-group">
                                <label>번호</label>
                                <input class="form-control" readonly="readonly" value='${board.bno}'>
                                <form:hidden path="bno" />
                            </div>
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
                            <div class="form-group">
                                <label>등록일</label>
                                <input class="form-control" readonly="readonly" value="<fdt:formatDateTime value="${board.regDate}" pattern="yyyy-MM-dd hh:mm:ss"/>">
                            </div>
                            <div class="form-group">
                                <label>수정일</label>
                                <input class="form-control" readonly="readonly" value="<fdt:formatDateTime value="${board.modDate}" pattern="yyyy-MM-dd hh:mm:ss"/>">
                            </div>
                            <button data-oper="modify" class="btn btn-defalut">modify</button>
                            <button data-oper="remove" class="btn btn-danger">remove</button>
                            <button data-oper="list" class="btn btn-info">List</button>
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

<script type="text/javascript">
    $(document).ready(function() {
        var bnoValue = '<c:out value="${board.bno}"/>';

        var formObj = $('form');
        $('button').on("click", function(e) {
            e.preventDefault();
            var operation = $(this).data('oper');
            console.log(operation);
            if (operation === 'remove') {
                formObj.attr("action", "remove")
            } else if (operation === 'list') {
                formObj.attr("action", "list").attr("method", "get");
                var pageNumTag = $('input[name="pageNum"]').clone();
                var amountTag = $('input[name="amount"]').clone();
                formObj.empty();
                formObj.append(pageNumTag);
                formObj.append(amountTag);
            }
            formObj.submit();
        });
    });

</script>