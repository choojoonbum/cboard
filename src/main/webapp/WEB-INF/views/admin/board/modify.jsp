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

<div class="bigPictureWrapper">
    <div class="bigPicture"></div>
</div>
<style>
    .uploadResult {
        width: 100%;
        background-color: gray;
    }
    .uploadResult ul {
        display: flex;
        flex-flow: row;
        justify-content: center;
        align-items: center;
    }
    .uploadResult ul li {
        list-style: none;
        padding: 10px;
    }
    .uploadResult ul li img {
        width: 100px;
    }
    .uploadResult ul li span {
        color: white;
    }
    .bigPictureWrapper {
        position: absolute;
        display: none;
        justify-content: center;
        align-items: center;
        top: 0%;
        width: 100%;
        height: 100%;
        background-color: gray;
        z-index: 100;
        background: rgba(255,255,255,0.5);
    }
    .bigPicture {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .bigPicture img {
        width: 600px;
    }
</style>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                File
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="form-group uploadDiv">
                    <input type="file" name="uploadFile" multiple>
                </div>
                <div class="uploadResult">
                    <ul>

                    </ul>
                </div>
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

        $.getJSON("/admin/board/getAttachList", {bno: bnoValue}, function (arr) {
            console.log(arr);
            var str = "";
            $(arr).each(function (i, attach) {
                var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
                if (attach.fileType) {
                    str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'><div>";
                    str += "<span> " + attach.fileName + "</span>";
                    str += "<button data-file='" + fileCallPath +"' data-type='image' type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/display?fileName=" + fileCallPath +"'>";
                    str += "</div></li>";
                } else {
                    str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'><div>";
                    str += "<span> " + attach.fileName + "</span>";
                    str += "<button data-file='" + fileCallPath +"' data-type='file' type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/resources/img/attach.png'>";
                    str += "</div></li>";
                }
            });
            $(".uploadResult ul").html(str);
        });

        $(".uploadResult").on("click", "button", function (e) {
            var targetLi = $(this).closest("li");
            if (confirm("Remove this file? ")) {
                targetLi.remove();
            }
        });

        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        var maxSize = 5242880;

        function checkExtension(fileName, fileSize) {
            if (fileSize >= maxSize) {
                alert("파일 사이즈 초과");
                return false;
            }
            if (regex.test(fileName)) {
                alert("해당 종류의 파일은 업로드할 수 없습니다.");
                return false;
            }
            return true;
        }

        $("input[type='file']").change(function (e) {
            var formData = new FormData();
            var inputFile = $("input[name='uploadFile']");
            var files = inputFile[0].files;
            for (var i = 0; i < files.length; i++) {
                if (!checkExtension(files[i].name, files[i].size)) {
                    return false;
                }
                formData.append("uploadFile", files[i]);
            }
            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    showUploadResult(result);
                }
            });
        });

        function showUploadResult(uploadResultArr) {
            if (!uploadResultArr || uploadResultArr.length == 0) return;
            var uploadUL = $(".uploadResult ul");
            var str = "";
            $(uploadResultArr).each(function (i, obj) {
                if (obj.image) {
                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
                    str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'><div>";
                    str += "<span> " + obj.fileName + "</span>";
                    str += "<button data-file='" + fileCallPath +"' data-type='image' type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/display?fileName=" + fileCallPath +"'>";
                    str += "</div></li>";
                } else {
                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
                    str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'><div>";
                    str += "<span> " + obj.fileName + "</span>";
                    str += "<button data-file='" + fileCallPath +"' data-type='file' type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/resources/img/attach.png'>";
                    str += "</div></li>";
                }
            });
            uploadUL.append(str);
        }

        var formObj = $('form');
        $('button').on("click", function(e) {
            e.preventDefault();
            var operation = $(this).data('oper');
            console.log(operation);
            if (operation === 'remove') {
                formObj.attr("action", "/board/remove")
            } else if (operation === 'list') {
                formObj.attr("action", "/board/list").attr("method", "get");
                var pageNumTag = $('input[name="pageNum"]').clone();
                var amountTag = $('input[name="amount"]').clone();
                var keywordTag = $('input[name="keyword"]').clone();
                var typeTag = $('input[name="type"]').clone();
                formObj.empty();
                formObj.append(pageNumTag);
                formObj.append(amountTag);
                formObj.append(keywordTag);
                formObj.append(typeTag);
            } else if (operation == 'modify') {
                console.log("submit clicked");
                var str = "";
                $(".uploadResult ul li").each(function (i, obj) {
                    var jobj = $(obj);
                    console.dir(jobj);
                    str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
                });
                formObj.append(str).submit();
            }
            formObj.submit();
        });
    });

</script>