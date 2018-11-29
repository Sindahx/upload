<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/20
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>用户登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9"/>
    <link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>/css/bootstrap-theme.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>/css/jquery.fileupload.css">
    <link rel="stylesheet" href="<%=basePath%>/css/jquery.fileupload-ui.css">

</head>
<body>
<div class="fileupload-buttonbar">
    <div class="thumbnail">
        <input id="upload_file_name" type="text" name="" hidden>
        <div class="progress progress-striped active" role="progressbar" aria-valuemin="10" aria-valuemax="100" aria-valuenow="0">
            <div id="weixin_progress" class="progress-bar progress-bar-success" style="width:0%;"></div>
        </div>
        <div class="caption" align="center">
            <span id="weixin_upload" class="btn btn-primary fileinput-button">
            <span>上传</span>
            <input type="file" id="myFile" name="myFile" multiple>
            </span>
        </div>
    </div>
</div>

<script src="<%=basePath%>/js/jquery/jquery.main.js"></script>
<script src="<%=basePath%>/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/jquery/jquery.ui.widget.js"></script>
<script src="<%=basePath%>/js/jquery/jquery.fileupload.js"></script>
<script src="<%=basePath%>/js/jquery/jquery.fileupload-ui.js"></script>
<script src="<%=basePath%>/js/jquery/jquery.iframe-transport.js"></script>
<script>
    var fileName;
    var jqXHR;
    var uuid = "${uuid}"
    var userName = "${userName}";
    $(function () {

        $("#myFile").fileupload({
            url: '<%=path%>/web/uploadFile?userName=' + userName,
            limitConcurrentUploads: 1,
            sequentialUploads: true,
            progressInterval: 100,
            maxChunkSize: ${maxChunkSize}, //设置上传片段大小，不设置则为整个文件上传
            dataType: "json",
            add: function (e, data) {
            	var fileType = ["svs", "jpg", "jpeg", "bmp", "png","tiff","tif","vms","vmu","ndpi","scn","mrxs","svslide","bif"];
                var file = (data.files[0].name).split('.');
				var itype = file[file.length - 1];
                itype = itype.toLowerCase();
               /* if (fileType.indexOf(itype) == -1) {
                    alert("仅支持 svs,jpg,jpeg, bmp, png,tiff,tif,vms,vmu,ndpi,scn,mrxs,svslide,bif 图片上传");
                    return false;
                }*/
                jqXHR = data.submit();
                $('#myFile').css("display","none");
                $("#weixin_upload").addClass("disabled");
                $('#weixin_upload span').html("正在上传。。。。");
            }
        }).bind('fileuploadprogress', function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10) - 1;
            $("#weixin_progress").css('width', progress + '%');
            $("#weixin_progress").html(progress + '%');
        }).bind('fileuploaddone', function (e, data) {
            var filename = data.result.name;
            uploaddone(filename, getPostfix(filename), uuid);
            $("#weixin_progress").css('width', 100 + '%');
            $("#weixin_progress").html(100 + '%');
            $('#weixin_upload span').html("上传成功");
            setTimeout(function(){
                alert("上传成功");
            }, 1000);
        }).bind('fileuploadpaste', function (e, data) {
            alert("aaa");
        });
    });

    //取消上传
    function cancelUpload() {
        jqXHR.abort();
    }

    function getPostfix(filename) {
        var fix = filename.split('.');
        return fix[fix.length - 1];
    }

    function uploaddone(fileName, postfix, uuid) {
        var url = "<%=path%>/web/removeFile"
        var param = {};
        param['fileName'] = userName + fileName;
        param['postfix'] = postfix;
        param['uuid'] = uuid;
        $.post(url, param, function (data) {
            fileName = data.name;
            // parent.setFileName(fileName);
            $('#upload_file_name').val(fileName)

        });

    }

    function getFileName() {
        return fileName
    }
</script>
</body>


</html>