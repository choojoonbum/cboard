<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                File Attach
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
<script>
    $(document).ready(function (e) {

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

        $(".uploadResult").on("click", "button", function (e) {
            var targetFile = $(this).data("file");
            var type = $(this).data("type");
            var targetLi = $(this).closest("li");

            $.ajax({
                url: '/deleteFile',
                data: {fileName: targetFile, type:type},
                dataType: 'text',
                type: 'post',
                success: function (result) {
                    alert(result);
                    targetLi.remove();
                }
            });
        });

        var formObj = $("form[role='form']");
        $("button[type='submit']").on("click", function (e) {
            e.preventDefault();
            console.log("submit clicked");
            var str = "";
            $(".uploadResult ul li").each(function (i, obj) {
                var jobj = $(obj);
                console.log(jobj);
                str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
            });
            console.log(str);
            formObj.append(str).submit();
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

    });
</script>