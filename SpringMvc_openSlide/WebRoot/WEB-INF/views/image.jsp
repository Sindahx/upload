<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/21
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=640, initial-scale=0.5, user-scalable=no"/>
    <title>openseadragon </title>
    <meta name="keywords" content=""/>
    <meta name="description" content="openseadragon " />
    <link rel="stylesheet" href="<%=basePath%>/js/openseadragon/openseadragon.css" />
   <%-- <link rel="stylesheet" href="<%=basePath%>/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/bootstrap-theme.min.css" />
    <script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>--%>
    <style>
        #zoom-in img:hover {
            background-image: url('/img/zoomin_hover.png');
        }
        #zoom-out img:hover {
            background-image: url('/img/zoomin_hover.png');
        }
        #home img:hover {
            background-image: url('/img/zoomin_hover.png');
        }
        #full-page img:hover {
            background-image: url('/img/zoomin_hover.png');
        }

    </style>
</head>
<body>
<div style=" position: absolute;z-index: 99999">
    <div id="toolbarDiv" style="width:140px; height:30px;">
        <%--<div >
            <a href="" id="zoom-in"><img src="<%=basePath%>/img/zoomin_grouphover.png" alt="放大"></a>
            <a href="" id="zoom-out"><img src="<%=basePath%>/img/zoomout_grouphover.png" alt="缩小"></a>
            <a href="" id="home"><img src="<%=basePath%>/img/home_grouphover.png" alt="返回默认"></a>
            <a href="" id="full-page"><img src="<%=basePath%>/img/fullpage_grouphover.png" alt="全屏"></a>
        </div>--%>
    </div>
</div>
<!-- openseadragon 容器 -->
<div id="container" style="width: 100%; height: 700px;"></div>
</body>
<script src="<%=basePath%>/js/openseadragon/openseadragon.js" type="text/javascript"></script>
<script>
    (function(){
        var viewer = OpenSeadragon({
            // debugMode: true,
            id: "container",  //容器id
            prefixUrl: "<%=basePath%>/js/openseadragon/images/", //openseadragon插件资源路径
            tileSources: "<%=basePath%>/image/${imageuuid}.dzi",  //openseadragon 图片资源xml
//            tileSources: "http://localhost/image/file.dzi",  //openseadragon 图片资源xml
            showNavigator:true,  //是否显示控制按钮
//            navigatorPosition:   "BOTTOM_LEFT",
            toolbar:"toolbarDiv",
//            zoomInButton:   "zoom-in",         //放大
//            zoomOutButton:  "zoom-out",        //缩小
//            homeButton:     "home",            //恢复默认
//            fullPageButton: "full-page",       //全屏
        });
        console.log(viewer);
    })(window);
</script>

</html>
