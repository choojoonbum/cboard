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

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i> Reply
                <button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">New Reply</button>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <ul class="chat"></ul>
            </div>
            <!-- /.panel-body -->
            <div class="panel-footer text-center">
            </div>
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Reply Modal</h4>
            </div>
            <div class="modal-body">
                <div class="form_group">
                    <label>Reply</label>
                    <input class="form-control" name="reply" value='New Reply!!!!'>
                </div>
                <div class="form_group">
                    <label>Replyer</label>
                    <input class="form-control" name="replyer" value='replyer'>
                </div>
                <div class="form_group">
                    <label>Reply Date</label>
                    <input class="form-control" name="replyDate" value=''>
                </div>

            </div>
            <div class="modal-footer">
                <button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
                <button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
                <button id="modalRegisterBtn" type="button" class="btn btn-default">Register</button>
                <button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript" src="/resources/js/reply.js?timestamp=<%=System.currentTimeMillis()%>"></script>
<script>
    $(document).ready(function () {
        var bnoValue = '${board.bno}';
        var replyUl = $('.chat');

        showList(1);

        var modal = $(".modal");
        var modalInputReply = modal.find("input[name='reply']");
        var modalInputReplyer = modal.find("input[name='replyer']");
        var modalInputReplyDate = modal.find("input[name='replyDate']");

        var modalModBtn = $("#modalModBtn");
        var modalRemoveBtn = $("#modalRemoveBtn");
        var modalRegisterBtn = $("#modalRegisterBtn");

        $("#addReplyBtn").on("click", function (){
            modal.find("input").val("");
            modalInputReplyDate.closest("div").hide();
            modal.find("button[id != 'modalCloseBtn']").hide();

            modalRegisterBtn.show();
            modal.modal("show");
        });

        modalRegisterBtn.on("click", function (){
            var reply = {
                reply: modalInputReply.val(),
                replyer: modalInputReplyer.val(),
                bno: bnoValue
            };
            replyService.add(reply, function (result){
                console.log(result);
                modal.find("input").val("");
                modal.modal("hide");
                showList(-1);
            });
        });

        modalModBtn.on("click", function (e) {
            var reply = {
                rno: modal.data("rno"),
                reply: modalInputReply.val()
            };
            replyService.update(reply, function (result){
                console.log(result);
                modal.modal("hide");
                showList(pageNum);
            });
        });

        modalRemoveBtn.on("click", function (e) {
            var rno = modal.data("rno");
            console.log("RNO: " + rno);

            replyService.remove(rno, function (result) {
                console.log(result);
                modal.modal("hide");
                showList(pageNum);
            });
        });

        $(".chat").on("click", "li", function (){
            var rno = $(this).data("rno");
            replyService.get(rno, function (reply) {
                modalInputReply.val(reply.reply);
                modalInputReplyer.val(reply.replyer);
                modalInputReplyDate.val(replyService.displayTime(reply.regDate)).attr("readonly","readonly");
                modal.data("rno", reply.rno);

                modal.find("button[id != 'modalCloseBtn']").hide();
                modalModBtn.show();
                modalRemoveBtn.show();
                modal.modal("show");
            });
        });

        function showList(page) {
            if (page == -1) {
                replyService.getBnoCount(bnoValue , function (replyCnt) {
                    pageNum = Math.ceil(replyCnt / 10.0);
                    showList(pageNum);
                });
                return;
            }
            replyService.getList({bno:bnoValue, page:page || 1}, function (replyCnt, list) {
                console.log("replyCnt: " + replyCnt);
                console.log("list: " + list);
                console.log(list);
                console.log(page);

                var str = "";
                if (list == null || list.length == 0) {
                    replyUl.html("");
                    return;
                }
                for (var i = 0, len = list.length || 0; i < len; i++) {
                    str += "<li class='left clearfix' data-rno='"+ list[i].rno + "'>";
                    str += "<div><div class='header'>";
                    str += "<strong class='primary-font'>"+ list[i].replyer + "</strong>";
                    str += "<small class='pull-right text-muted'>"+ replyService.displayTime(list[i].regDate) + "</small></div>";
                    str += "	<p>"+ list[i].reply + "</p></div></li>";
                }
                replyUl.html(str);
                showReplyPage(replyCnt);
            });
        }

        var pageNum = 1;
        var replyPageFooter = $(".panel-footer");

        function showReplyPage(replyCnt) {
            var endNum = Math.ceil(pageNum / 10.0) * 10;
            var startNum = endNum - 9;
            var prev = startNum != 1;
            var next = false;

            if (endNum * 10 >= replyCnt) {
                endNum = Math.ceil(replyCnt / 10.0);
            }
            if (endNum * 10 < replyCnt) {
                next = true;
            }

            var str = "<ul class='pagination'>";
            if (prev) {
                str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'>Previous</a></li>";
            }
            for(var i = startNum; i <= endNum; i++) {
                var active = pageNum == i ? "active" : "";
                str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>";
            }
            if (next) {
                str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'>Next</a></li>";
            }
            str += "</ul></div>";

            replyPageFooter.html(str);
        }

        replyPageFooter.on("click", "li a", function (e) {
            e.preventDefault();
            console.log("page click");
            var targetPageNum = $(this).attr("href");
            console.log("targetPageNum: " + targetPageNum);
            pageNum = targetPageNum;
            showList(pageNum);
        });
    });
</script>
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