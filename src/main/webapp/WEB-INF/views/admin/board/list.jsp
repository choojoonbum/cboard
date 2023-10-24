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
                게시글 리스트
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <form id="actionForm" action="list" method="get">
                    <input type="hidden" name="pageNum" value="${pageMaker.criteria.pageNum}">
                    <input type="hidden" name="amount" value="${pageMaker.criteria.amount}">
                    <input type="hidden" name="type" value="${pageMaker.criteria.type}">
                    <input type="hidden" name="keyword" value="${pageMaker.criteria.keyword}">
                </form>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="dataTables_length" id="dataTables-example_length">
                            <label>Show
                                <select name="dataTables-example_length" aria-controls="dataTables-example" class="form-control input-sm">
                                <option value="10">10</option>
                                <option value="25">25</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select> entries</label>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div id="dataTables-example_filter" class="dataTables_filter">
                            <label>Search:<input type="search" class="form-control input-sm" placeholder="" aria-controls="dataTables-example" style="width: 75%"></label>
                        </div>
                    </div>
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>수정일</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="board">
                        <tr>
                            <td>${board.bno}</td>
                            <td><a class="move" href="${board.bno}">${board.title}</a></td>
                            <td>${board.writer}</td>
                            <td><fdt:formatDateTime value="${board.regDate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                            <td><fdt:formatDateTime value="${board.modDate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="text-center">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${pageMaker.prev}">
                                <li>
                                    <a href="${pageMaker.startPage - 1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>

                            <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                                <li class="${pageMaker.criteria.pageNum == num ? "active":""}"><a href="${num}">${num}</a></li>
                            </c:forEach>

                            <c:if test="${pageMaker.next}">
                                <li>
                                    <a href="${pageMaker.endPage + 1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </div>
                <button id='regBtn' type="button" class="btn btn-default pull-right">Register</button>
                <!-- /.table-responsive -->
            </div>
            <!-- /.panel-body -->
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
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript">
    $(document).ready(function() {
        var result = '${result}';
        checkModal(result);

        history.replaceState({}, null, null);

        function checkModal() {
            if (result === '' || history.state ) {
                return;
            }

            if (parseInt(result) > 0) {
                $(".modal-body").html("게시글 " + parseInt(result) + " 번이 등록되었습니다.");
            } else if (result == "success") {
                $(".modal-body").html("처리가 완료되었습니다.");
            }
            $("#myModal").modal("show");
        }

        $("#regBtn").on("click", function() {
            self.location = "register";
        })

        var actionForm = $("#actionForm");
        $(".pagination a").on("click", function(e) {
            e.preventDefault();
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });

        $(".move").on("click", function(e) {
            e.preventDefault();
            actionForm.append("<input type='hidden' name='bno' value='" + $(this).attr("href") + "'>");
            actionForm.attr("action", "get");
            actionForm.submit();
        });

        var searchForm = $("#searchForm");
        $("#searchForm button").on("click", function(e) {
            if (!searchForm.find("option:selected").val()) {
                alert("검색종류를 선택하세요");
                return false;
            }
            if (!searchForm.find("input[name=keyword]").val()) {
                alert("키워드를 입력하세요");
                return false;
            }

            searchForm.find("input[name='pageNum']").val("1");
            e.preventDefault();
            searchForm.submit();
        });

    });
</script>