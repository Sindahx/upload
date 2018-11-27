<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>HTML5大文件分片上传示例</title>
    <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="md5.js"></script>
    <script src="browser-md5-file.js"></script>
    <script>
        //vari = -1;
        var shardSize = 5 * 1024 * 1024;    //以5MB为一个分片
        var succeed = 0;
        var databgein;  //开始时间
        var dataend;    //结束时间
        var page = {
            init: function () {
                $("#upload").click(function () {
                    databgein = new Date();
                    var file = $("#file")[0].files[0];  //文件对象
                    isUpload(file);
                });
            }
        };
        $(function () {
            page.init();
        });
        function isUpload(file) {
            //构造一个表单，FormData是HTML5新增的
            var form = new FormData();
            browserMD5File(file, function (err, md5) {
                form.append("md5", md5);
                //Ajax提交
                $.ajax({
                    url: "http://localhost:8080/bookQr/test/isUpload",
                    type: "POST",
                    data: form,
                    async: true,        //异步
                    processData: false,  //很重要，告诉jquery不要对form进行处理
                    contentType: false,  //很重要，指定为false才能形成正确的Content-Type
                    success: function (data) {
                        var dataObj = eval("(" + data + ")");
                        var uuid = dataObj.fileId;
                        var date = dataObj.date;
                        if (dataObj.flag == "1") {
                            //没有上传过文件,开始分片上传文件
                            repeatupload(file, uuid, md5, date);
                        } else if (dataObj.flag == "2") {
                            //已经上传部分
                            upload(file, uuid, md5, date);
                        } else if (dataObj.flag == "3") {
                            //文件已经上传过
                            alert("文件已经上传过,秒传了！！");
                            $("#uuid").append(uuid);
                        }
                    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert("服务器出错!");
                    }

                });
            });

        }
        function repeatupload(file, uuid, filemd5, date) {
            name = file.name;        //文件名
            size = file.size;        //总大小
            shardCount = Math.ceil(size / shardSize);  //总片数

            for (var i = 0; i < shardCount; i++) {
                upload2(file, uuid, filemd5, date, i,1);
            }
        }
        /*
         *上传每一分片
         * file 文件对象
         * uuid 后端生成的uuid
         * filemd5 整个文件的md5
         * date  文件第一个分片上传的日期(如:20170122)
         * i  文件第i个分片
         * type  1为检测；2为上传
         */
        function upload2(file, uuid, filemd5, date, i,type) {
            //计算每一片的起始与结束位置
            var start = i * shardSize,
                end = Math.min(size, start + shardSize);
            //构造一个表单，FormData是HTML5新增的
            var form = new FormData();
            if (type === 1) {
                form.append("action", "check");  //检测分片是否上传
                $("#param").append("action==check ");
            } else {
                form.append("action", "upload");  //直接上传分片
                form.append("data", file.slice(start, end));  //slice方法用于切出文件的一部分
                $("#param").append("action==upload ");
            }

            form.append("uuid", uuid);
            form.append("filemd5", filemd5);
            form.append("date", date);
            form.append("name", name);
            form.append("size", size);
            form.append("total", shardCount);  //总片数
            form.append("index", i+1);        //当前是第几片

            var ssindex = i+1;
            $("#param").append("index==" + ssindex + "<br/>");

            //按大小切割文件段　　
            var data = file.slice(start, end);
            var r = new FileReader();
            r.readAsBinaryString(data);

            $(r).load(function (e) {
                var bolb = e.target.result;
                var md5 = hex_md5(bolb);
                form.append("md5", md5);
                //Ajax提交
                $.ajax({
                    url: "http://localhost:8080/bookQr/test/upload",
                    type: "POST",
                    data: form,
                    async: true,        //异步
                    processData: false,  //很重要，告诉jquery不要对form进行处理
                    contentType: false,  //很重要，指定为false才能形成正确的Content-Type
                    success: function (data) {
                        var dataObj = eval("(" + data + ")");
                        var fileuuid = dataObj.fileId;
                        var flag = dataObj.flag;
                        //服务器返回该分片是否上传过
                        if (type === 1) {
                            if (flag == "1") {
                                //未上传,继续上传　
                                upload2(file, uuid, filemd5, date,i,2);
                            } else if (dataObj.flag == "2") {
                                //已上传
                                action = false;
                                ++succeed;
                            }

                        } else {
                            //服务器返回分片是否上传成功
                            ++succeed;//改变界面
                            $("#output").text(succeed + " / " + shardCount);

                            if (i+1  == shardCount) {
                                dataend = new Date();
                                $("#uuid").append(fileuuid);
                                $("#usetime").append(dataend.getTime() - databgein.getTime());
                            }

                        }
                    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert("服务器出错!");
                    }

                });

            })

        }
    </script>
</head>
<body>

<input type="file" id="file"/>
<button id="upload">上传</button>
<span id="output" style="font-size:12px">等待</span>

<span id="usetime" style="font-size:12px;margin-left:20px;">用时</span>

<span id="uuid" style="font-size:12px;margin-left:20px;">uuid==</span>
<br/>

<br/>
<br/>
<br/>
<span id="param" style="font-size:12px;margin-left:20px;">param==</span>


</body>

</html>
