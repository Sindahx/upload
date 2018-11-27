$(function(){
    //返回顶部代码
    var toTopHtml='';
    toTopHtml+='<div class="toTopBtn"><i class="jlfont">&#xe633;</i>回顶部</div>';
    $('body').append(toTopHtml);
    $(document).scroll(function(){

        if($(document).scrollTop()>1){
            $('.toTopBtn').show();
        }else{
            $('.toTopBtn').hide();
        }
    });
    $('.toTopBtn').on('click',function(){
        $(document).scrollTop(0);
    });
});

var common={};
//公用的头像方法
common.headPic=function(age,sex,size) {
    if (age.indexOf('岁') == -1) {
        age = 1;
    } else {
        age = age.substring(0, age.indexOf('岁'));
    }
    var classname = '';
    if (size == '30') {
        classname = 'br_spic brp_pic';
    } else if (size == '60') {
        classname = 'br_pic brp_pic';
    } else if (size == '90') {
        classname = 'br_lpic brp_pic';
    } else if (size == '120') {
        classname = 'br_bpic brp_pic';
    }

    if (age <= 12) {
        if (sex == 1) {
            return classname + '10';
        } else if (sex == 2) {
            return classname + '10N';
        } else {
            return classname + '120';
        }
    } else if (age > 12 && age <= 55) {
        if (sex == 1) {
            return classname + '40';
        } else if (sex == 2) {
            return classname + '40N';
        } else {
            return classname + '120';
        }
    } else if (age > 55) {
        if (sex == 1) {
            return classname + '60';
        } else if (sex == 2) {
            return classname + '60N';
        } else {
            return classname + '120';
        }
    } else {
        return classname + '120';
    }
};

//根据出生日期计算年龄
common.age=function(birthday,indate){
    if (birthday == null || birthday == "" || indate == null || indate == "") {
        return "";
    }
    var startTime = new Date(Date.parse(birthday.replace(/-/g, "/"))).getTime();
    var endTime = new Date(Date.parse(indate.replace(/-/g, "/"))).getTime();
    var l = endTime-startTime;
    var nd = 1000*24*60*60;//一天的毫秒数

    var year = parseInt(l/(nd*365));
    var month = parseInt(l%(nd*365)/(nd*30));

    if (year > 0) {
        return (year+1)+"岁";
    } else if (month > 2) {
        return month+"个月";
    } else {
        var day = parseInt(l/(nd));
        return day+"天";
    }
};

/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
        // millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
        - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}



//医生等级
common.docLevel=function(tempInt){
    var tempStr = "";
    if(tempInt ==0){
        tempStr = "主任医师";
    }else if(tempInt ==1){
        tempStr = "副主任医师";
    }else if(tempInt ==2){
        tempStr = "主治医师";
    }else if(tempInt ==3){
        tempStr = "医师";
    }else if(tempInt ==4){
        tempStr = "医生";
    }
    return tempStr;
};

//显示医生名片
common.showDocLayer=function (obj,data){
    $('.DoctorLayer').remove(); //清除其它的医生框
    //console.log(obj);
    var oleft=obj.offset().left;
    var otop=obj.offset().top;
    var owidth=obj.outerWidth(true);
    var oheight=obj.outerHeight(true);
    var winwidth=$(window).width();
    var winheight=$(window).height();
    var xlocation='';
    var ylocation='';
    var cssLeft,cssTop;

    if(location=='left'){
        cssLeft=oleft-410;
        cssTop=otop-110+oheight/2;
        xlocation='leftj';
    }else if(location=='right'){
        cssLeft=oleft+owidth+410;
        cssTop=otop-110+oheight/2;
        xlocation='rightj';
    }else{
        cssLeft=oleft;
        cssTop=otop-220;
        if(winwidth-oleft<400){
            xlocation='inright';
            cssLeft=winwidth-400;
        }
        if(otop<210){
            ylocation='inbottom';
            cssTop=otop+oheight;
        }
    }
    
 var html='';
    html+='<div class="DoctorLayer '+ylocation+' '+xlocation+'" >';
    html+='<span class="bottomj"><span class="jlfont">&#xe637;</span></span>';
    html+='<div class="odoctor_allImp">';
    html+='<div class="odoctorPic">';
    if(data[0].HEADPATH == 0){
        html+='<img id="detailHeadpath" src="'+data[0].ROOTURL+'/images/doctor_Dpic.png" />';
    }else{
        html+='<img id="detailHeadpath" src="'+data[0].HEADURL+data[0].HEADPATH+'" class="doctorImg" />';
    }

    html+='</div>';
    html+='<div class="odoctorInfoRight">';
    html+='<div class="odoctorName">';
    html+='<p>￥'+data[0].FEE+'/次</p>';
    html+='<strong>'+data[0].NAME+' </strong>';
    html+='<span>'+numToChanieseDocLevel(Number(data[0].DOCLEVEL))+'</span>';
    html+='</div>';
    html+='<p class="odoctor_hos">'+data[0].HOSPITAL+'</p>';
    html+='<p class="odoctor_dept">'+data[0].DEPT_NAME+'</p>';
    html+='</div>';
    html+='</div>';
    html+='<div class="odoctor_fen">';
    html+='<div class="odoctor_hzshu pull-right">';
    if(data[0].HZ_JOIN_COUNT!=undefined)
    {
        html+='远程会诊量：'+data[0].HZ_JOIN_COUNT+'次';
    } else {
        html+='远程会诊量：0次';
    }
    html+='</div>';
    var tempInt = 0;
    if(!!data[0].INT1){
        tempInt = tempInt+Number(data[0].INT1);
    }
    if(!!data[0].INT2){
        tempInt = tempInt+Number(data[0].INT2);
    }
    if(!!data[0].INT3){
        tempInt = tempInt+Number(data[0].INT3);
    }
    if(!!data[0].INT4){
        tempInt = tempInt+Number(data[0].INT4);
    }
    tempInt = tempInt*5;
    //var tempInt = (data[0].INT1+data[0].INT2+data[0].INT3+data[0].INT4)*5;

    html+='<div class="odoctor_xing">';
    html+='<strong>评分:</strong>';
    html+='<span class="doctor_star" name="KnowEval"><i style="width: '+tempInt+'%;" data-title="80%;"></i></span>';
    html+='</div>';
    html+='</div>';
    if(data[0].MAIN_SKILL!=null){
        html+='<div class="odoctor_nor"><p>擅长：'+data[0].MAIN_SKILL+'</p></div>';
    } else {
        html+='<div class="odoctor_nor"><p>擅长：</p></div>';
    }

    html+='</div>';
    $('body').append(html);
    $('.DoctorLayer').css({
        'left':cssLeft,
        'top':cssTop
    });
    $(".doctorImg").error(function(){
        $(this).attr('src',data[0].ROOTURL+'/images/doctor_Dpic.png');
    });
};



common.getIpAuth = function (id) {
    $.post( sysCtx+"/home_checkFile.action",function(data){
        console.log('data='+data.info);
        if(data.info !=1){
            $('.'+id+'').show();
        }else{
            $('.'+id+'').hide();
            var newdiv = document.getElementById("ipAuthDiv");
            newdiv.innerHTML = "<div style='color: red'>（内网权限控制，不能下载。）</div>";
        }
    });
}

common.getUserFtpInfo=function() {
    var data='';
    $.ajax({
        url : sysCtx+'/system/user_getUserFtpInfo.action?userId='+userId,
        async : false,
        type : "POST",
        dataType : "text",
        success : function(result) {
            data = JSON.parse(result);
        },
        error : function(xhr,status,statusText) {
            data = '';
            //console.log(xhr.status);
        }
    });
    return data;
};

//中间提示框展现
common.mMessege=function(type,text){  //type:1为成功，0为失败，2为警告 3位置在顶部   4位置在中间不消失   text:为内容
    $('.middle_messege').remove();
    var html='';
    html+='<div class="middle_messege" style="z-index: 99999999">';
    html+='<div class="closeBtn" onclick="common.removemMessege()"><i class="jlfont">&#xe62a;</i></div>';
    if(type=='1'){
        html+='<div class="success jlcont">';
        html+='<i class="jlfont">&#xe621;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='0'){
        html+='<div class="fail jlcont">';
        html+='<i class="jlfont">&#xe620;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='2'){
        html+='<div class="warning jlcont">';
        html+='<i class="jlfont">&#xe61f;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='3'){
        html+='<div class="warning jlcont">';
        html+='<i class="jlfont">&#xe61f;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='4'){
        html+='<div class="warning jlcont">';
        html+='<i class="jlfont">&#xe61f;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }
    html+='</div>';
    $('body').append(html);
    if (type != '3') {
        $('.middle_messege').animate({'top':'45%'},'normal',function(){
            if (type != '4'){
                setTimeout(function(){
                    $('.middle_messege').animate({'opacity':'0.1'},1000,function(){
                        $('.middle_messege').remove();
                    });
                },2000);
            }
        });
    }else{
        $('.middle_messege').css('top',0);
    }
};
common.removemMessege=function(){
    $('.middle_messege').remove();
};

//底部提示框展现
common.bMessege=function(tJson){
    var thei=0;
    thei+=$('.bottom_message').length;
    this.bMessegeId='bottom_message'+thei;
    var html='';
    html+='<div class="bottom_message" id="'+tJson.id+'">';
    html+='<div class="jltitle">';
    html+='<a href="javascript:;" onclick="common.removebMessege(\''+tJson.id+'\')"><i class="jlfont">&#xe62a;</i></a>';
    html+='<p>'+tJson.btitle+'</p>';
    html+='</div>';
    html+='<div class="jlcont">';
    html+='<h4>'+tJson.stitle+'</h4>';
    html+='<p>'+tJson.cont+'</p>';
    html+='</div>';
    if(tJson.btn && tJson.btn.length){
        html+='<div class="jlbtn clearfix">';
        for(var i=0;i<tJson.btn.length;i++){
            html+='<a href="javascript:;" class="thebtn" data-id="'+i+'">'+tJson.btn[i].name+'</a>';
        };
        html+='</div>';
    }
    html+='</div>';
    $('body').append(html);

    //按钮上添加事件
    $('#'+tJson.id+' .thebtn').off('click');
    $('#'+tJson.id+' .thebtn').on('click',function(){
       var theid=$(this).attr('data-id');
        tJson.btn[theid].fn();
    });
    if(tJson.time!=undefined){
        if(tJson.time!='0'){
            setTimeout(function(){
                if($('#'+tJson.id).length){
                    $('#'+tJson.id).remove();
                }
            },tJson.time);
        }

    }


};
//圆形加载进度条
common.DrowProcess = function (x,y,radius,process,backColor,proColor,fontColor) {
        var canvas = document.getElementById('myCanvas');

        if (canvas.getContext) {
            var cts = canvas.getContext('2d');
        }else{
            return;
        }

        cts.beginPath();
        // 坐标移动到圆心
        cts.moveTo(x, y);
        // 画圆,圆心是24,24,半径24,从角度0开始,画到2PI结束,最后一个参数是方向顺时针还是逆时针
        cts.arc(x, y, radius, 0, Math.PI * 2, false);
        cts.closePath();
        // 填充颜色
        cts.fillStyle = backColor;
        cts.fill();

        cts.beginPath();
        // 画扇形的时候这步很重要,画笔不在圆心画出来的不是扇形
        cts.moveTo(x, y);
        // 跟上面的圆唯一的区别在这里,不画满圆,画个扇形
        cts.arc(x, y, radius, Math.PI * 1.5, Math.PI * 1.5 -  Math.PI * 2 * process / 100, true);
        cts.closePath();
        cts.fillStyle = proColor;
        cts.fill();

        //填充背景白色
        cts.beginPath();
        cts.moveTo(x, y);
        cts.arc(x, y, radius - (radius * 0.1), 0, Math.PI * 2, true);
        cts.closePath();
        cts.fillStyle = 'rgba(255,255,255,1)';
        cts.fill();

        // 画一条线
        cts.beginPath();
        cts.arc(x, y, radius-(radius*0.10), 0, Math.PI * 2, true);
        cts.closePath();
        // 与画实心圆的区别,fill是填充,stroke是画线
        cts.strokeStyle = backColor;
        cts.stroke();

        //在中间写字
        cts.font = "bold 7pt Arial";
        cts.fillStyle = fontColor;
        cts.textAlign = 'center';
        cts.textBaseline = 'middle';
        cts.moveTo(x, y);
        cts.fillText(process+"%", x, y);
}
common.removebMessege=function(id){
    $('#'+id).remove();
};
//判断提示框
common.decide=function(title,fn){
    $('#Decide').remove();
    var thml='';
    thml+='<div class="modal fade" id="Decide" style="z-index:1000001">';
    thml+='<div class="modal-dialog small-dialog">';
    thml+='<div class="modal-content">';
    thml+='<div class="modal-header">';
    thml+='<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    thml+='<h4 class="modal-title">提示</h4>';
    thml+='</div>';
    thml+='<div class="modal-body">';
    thml+='<div class="theQue">';
    thml+='<i class="jlfont">&#xe622;</i>'+title;
    thml+='</div>';
    thml+='</div>';
    thml+='<div class="modal-footer">';
    thml+='<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
    thml+='<button type="button" class="btn btn-primary" id="Mtrue">确认</button>';
    thml+='</div>';
    thml+='</div><!-- /.modal-content -->';
    thml+='</div><!-- /.modal-dialog -->';
    thml+='</div>';
    $('body').append(thml);
    $('#Decide').modal('show');
    $("#Mtrue").click(function(){
        fn();
        $('#Decide').modal('hide');
    });
};

//滚动加载更多
common.scrollload=function(obj,fn){
    var isstop=1;
    //页面加载完时执行
    if(!$(".no_data").html()){
        if(obj=$(document)){
            if($('.loadMore').offset().top+30<$(window).height()){
                fn();
            }
        }else{
            if($('.loadMore').offset().top-obj.offset().top<obj.height()){
                fn();
            }
        }

        //滚动时执行
        obj.scroll(function(){
            if(isstop && $('.loadMore').length){
                if(obj=$(document)){
                    if($('.loadMore').offset().top+30<$(window).height()+obj.scrollTop()){
                        isstop=0;
                        fn();
                        setTimeout(function(){
                            isstop=1;
                        },500);
                    }
                }else{
                    if($('.loadMore').offset().top-obj.offset().top<obj.height()+obj.scrollTop()){
                        isstop=0;
                        fn();
                        setTimeout(function(){
                            isstop=1;
                        },500);
                    }
                }

            }

        });

    } else {
        $(".loadMore").remove();
    }


};

common.successBtn=function(title){
    var msgid = Math.ceil(Math.random()*10000);
    common.bMessege({
        'id':'bMessege'+msgid,
        'btitle':'提醒',
        'stitle': title,
        'cont':title,
        'time':'5000',    //默认不关闭，0也为不关闭，数字表示毫秒数 例：1000表示1秒后关闭
        'btn':[{
            'name':'查看',
            'fn':function () {
                $(".ychz").click();
            }
        }]
    });
};


common.goApplyList=function(title,status,id){

    var msgid = Math.ceil(Math.random()*10000);
    common.bMessege({
        'id':'bMessege'+msgid,
        'btitle':'远程会诊',
        'stitle': title,
        'cont':title,
        'time':'5000',    //默认不关闭，0也为不关闭，数字表示毫秒数 例：1000表示1秒后关闭
        'btn':[{
            'name':'查看',
            'fn':function () {
                window.location.href = sysCtx + '/remote/center_ychzCenterBegin.action?applyId='+id;
            }
        }]
    });
};

common.goHospitalApply=function(title,id){


    var msgid = Math.ceil(Math.random()*10000);
    common.bMessege({
        'id':'bMessege'+msgid,
        'btitle':'院内会诊',
        'stitle': title,
        'cont':title,
        'time':'5000',    //默认不关闭，0也为不关闭，数字表示毫秒数 例：1000表示1秒后关闭
        'btn':[{
            'name':'查看',
            'fn':function () {
                window.location.href = sysCtx + '/remote/hospitalApply_ynhzCenterBegin.action?applyId='+id;
            }
        }]
    });
};


//设置居中
common.setFixed = function (obj) {
    var style = obj.style,
        width = $(obj).width(),
        height = $(obj).height();
    style.top = '50%';
    style.left = '50%';
    style.marginLeft = -parseInt(width / 2) + 'px';
    style.marginTop = -parseInt(height / 2) + 'px';
};

//图片预览弹层----依赖jquery.ui.js
common.ylImgLayer = function (src) {
    var mask='<div id="imgMask" style="margin:0;padding:0;border:none;width:100%;height:100%;background:#000;opacity:0.9;filter:alpha(opacity=90);z-index:1000005;position:fixed;top:0;left:0;"></div>';
    var loadHtml='';
    loadHtml='<div id="ylImgDiv" style="position:fixed;z-index:1000006;overflow:hidden;left:0;top:0;"><img style="position:absolute;" src="'+src+'" /></div>';
    $("body").append(mask);
    $("body").append(loadHtml);
    $( "#ylImgDiv").css({
        'width':$(window).width(),
        'height':$(window).height()
    });
    var yWidth,yHeight;
    $("#ylImgDiv img").load(function(){
        yWidth=$("#ylImgDiv img").width();
        yHeight=$("#ylImgDiv img").height();
        if(yWidth>$(window).width()){
            $("#ylImgDiv img").width($(window).width());
            $("#ylImgDiv img").css('height','auto');
        }
        if($("#ylImgDiv img").height()>$( "#ylImgDiv").height()){
            $("#ylImgDiv img").height($( "#ylImgDiv").height());
            $("#ylImgDiv img").css('width','auto');
        }
        common.setFixed($("#ylImgDiv img")[0]);// 加载完成
    });
    var fshtml='<div id="fsDiv" style="position:fixed;z-index:1000006;bottom:3%;left:50%;margin:0 0 0 -75px;width:150px;height:60px;padding:10px;background:rgba(0,0,0,0.8);border-radius:30px;">';
    fshtml+='<span class="jlfont fsDivAdd">&#xe639;</span>';
    fshtml+='<span class="jlfont fsDivMinus">&#xe638;</span>';
    fshtml+='</div>';
    $("body").append(fshtml);


    var pencentHtml='<div id="ImgPencent" style="position:absolute;text-align:center;color:#fff;font-size:18px;box-shadow: 0 0 3px #666;border-radius: 3px;background:rgba(0,0,0,0.5);top:50%;left:50%;width:100px;margin-left:-50px;height:40px;line-height:40px;z-index:1000006;margin-top:20px;display:none"></div>'
    $("body").append(pencentHtml);

    $('#fsDiv span').click(function(){
        var pecent;
        if($("#ylImgDiv img").css('width')< yWidth && $("#ylImgDiv img").css('width')!='auto'){
            if($(this).hasClass('fsDivAdd')){
                pecent=parseInt($("#ylImgDiv img").width()*100/yWidth)+10;
                if(pecent>100){
                    pecent=100;
                }
            }else{
                pecent=parseInt($("#ylImgDiv img").width()*100/yWidth)-10;
                if(pecent<5){
                    pecent<5;
                }
            }
            $('#ImgPencent').html(pecent+'%');
            $('#ImgPencent').show();
            $("#ylImgDiv img").width(yWidth*pecent/100);
            $("#ylImgDiv img").height(yHeight*pecent/100);
            setFixed($("#ylImgDiv img")[0]);
        }else{
            if($(this).hasClass('fsDivAdd')){
                pecent=parseInt($("#ylImgDiv img").height()*100/yHeight)+10;
                if(pecent>150){
                    pecent=150;
                }
            }else{
                pecent=parseInt($("#ylImgDiv img").height()*100/yHeight)-10;
                if(pecent<10){
                    pecent=10;
                }
            }
            $('#ImgPencent').html(pecent+'%');
            $('#ImgPencent').show();
            $("#ylImgDiv img").width(yWidth*pecent/100);
            $("#ylImgDiv img").height(yHeight*pecent/100);
            common.setFixed($("#ylImgDiv img")[0]);

        }
        setTimeout(function(){
            $('#ImgPencent').hide();
        },500);
    });

    var Chtml='<a href="javascript:;" class="jlfont closeImg" style="z-index: 1000006">&#xe62a;</a>  ';10000000
    $("body").append(Chtml);
    $( "#ylImgDiv img").draggable({
        helper: 'original',
        scroll: false
    });
    $(".closeImg").click(function(){
        $(".closeImg").remove();
        $("#imgMask").remove();
        $("#ylImgDiv").remove();
        $('#ImgPencent').remove();
        $('#fsDiv').remove();
    })

};



Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
}

Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
}

//更新PACS上传情况
function updatePacsUploadInfo(info) {
    var param = {};
    param["pacsUploadInfo"] = info;
    $.post(sysCtx+'/apply/simple_updatePacsUploadInfo.action', param);
}

//更新病历PACS上传状态
function updatePacsUploadStatus(simpleId, status) {
    var param = {};
    param["simpleId"] = simpleId;
    param["status"] = status;
    $.post(sysCtx+'/apply/simple_updatePacsUploadStatus.action', param);
}
//中间提示框展现
common.mPMessege=function(type,text){  //type:1为成功，0为失败，2为警告 3位置在顶部   4位置在中间不消失   text:为内容
    $('.middle_messege', window.parent.document).remove();
    var html='';
    html+='<div class="middle_messege" style="z-index: 99999999">';
    html+='<div class="closeBtn" onclick="common.removemPMessege()"><i class="jlfont">&#xe62a;</i></div>';
    if(type=='1'){
        html+='<div class="success jlcont">';
        html+='<i class="jlfont">&#xe621;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='0'){
        html+='<div class="fail jlcont">';
        html+='<i class="jlfont">&#xe620;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='2'){
        html+='<div class="warning jlcont">';
        html+='<i class="jlfont">&#xe61f;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='3'){
        html+='<div class="warning jlcont">';
        html+='<i class="jlfont">&#xe61f;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }else if(type=='4'){
        html+='<div class="warning jlcont">';
        html+='<i class="jlfont">&#xe61f;</i>';
        html+='<span>'+text+'</span>';
        html+='</div>';
    }
    html+='</div>';
    $('body', window.parent.document).append(html);
    if (type != '3') {
        $('.middle_messege', window.parent.document).animate({'top':'45%'},'normal',function(){
            if (type != '4'){
                setTimeout(function(){
                    $('.middle_messege', window.parent.document).animate({'opacity':'0.1'},1000,function(){
                        $('.middle_messege', window.parent.document).remove();
                    });
                },2000);
            }
        });
    }else{
        $('.middle_messege', window.parent.document).css('top',0);
    }
};
common.removemMessege=function(){
    $('.middle_messege', window.parent.document).remove();
};
//判断提示框
common.pDecide=function(title,fn){
   $('#Decide', window.parent.document).remove();
    var thml='';
    thml+='<div class="modal fade" id="Decide" style="z-index:1000001">';
    thml+='<div class="modal-dialog small-dialog">';
    thml+='<div class="modal-content">';
    thml+='<div class="modal-header">';
    thml+='<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    thml+='<h4 class="modal-title">提示</h4>';
    thml+='</div>';
    thml+='<div class="modal-body">';
    thml+='<div class="theQue">';
    thml+='<i class="jlfont">&#xe622;</i>'+title;
    thml+='</div>';
    thml+='</div>';
    thml+='<div class="modal-footer">';
    thml+='<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
    thml+='<button type="button" class="btn btn-primary" id="Mtrue">确认</button>';
    thml+='</div>';
    thml+='</div><!-- /.modal-content -->';
    thml+='</div><!-- /.modal-dialog -->';
    thml+='</div>';
    $('body', window.parent.document).append(thml);
    $('#Decide', window.parent.document).modal('show');
   $('#Mtrue', window.parent.document).click(function(){
        fn();
       $('#Decide', window.parent.document).modal('hide');
    });
};

//显示文件夹插件
(function ($) {
    $.fn.showFile = function (options) {
        var defaults = {
            data: {},  //文件夹数据
            selId:'',  //选中文件夹ID
            addPoint:'',  //新增标签
            addPoint_fn:null,  //新增标签点击方法
            width:'250px',    //弹出层宽度
            height:'450px',    //弹出层高度
            whetherParent:false  //是否显示父级菜单
        };
        var settings = $.extend({},defaults,options);
        var that=$(this);
        var inputId=$(this).attr('id');

        //console.log(settings.data);
        that.off('click');
        that.on('click',function(){
            if(that.attr('data-id')!=''){
                settings.selId=that.attr('data-id');
            }

            var oleft=that.offset().left;
            var otop=that.offset().top;
            var owidth=that.outerWidth(true);
            var oheight=that.outerHeight(true);
            var winwidth=$(window).width();
            var winheight=$(window).height();
            var cssLeft,cssTop;
            if(oleft>winwidth-parseInt(settings.width)){
                cssLeft=oleft+owidth-parseInt(settings.width);
            }else{
                cssLeft=oleft;
            }
            cssTop=otop+oheight;

            if( $('#fileLayer[layerid="'+inputId+'"]').length){
                $('#fileLayer[layerid="'+inputId+'"]').css("left",cssLeft);
                $('#fileLayer[layerid="'+inputId+'"]').css("top",cssTop);
                $('#fileLayer').show();
            }else{
                $('#fileLayer').remove();
                var thtml='';
                thtml+='<div class="fileLayer" id="fileLayer" layerid="'+inputId+'" style="width:'+settings.width+';left:'+cssLeft+'px;top:'+cssTop+'px">';
                thtml+='<div class="fileListNei" style="max-height:'+settings.height+'">';
                if(settings.whetherParent){
                    thtml+='<li  data-id="0" data-type="1" data-level="0">';
                    thtml+='<div class="bigclass_list layerclass_list">';
                    thtml+='';
                    thtml+='<em class="jlfont">&#xe63e;</em>';
                    thtml+="<strong>我的病历库</strong>"
                    thtml+='</div>';
                    thtml+='</li>';
                }
                thtml+='<ul class="cur">';
                thtml+='</ul>';
                thtml+='</div>';
                thtml+='</div>';
                $('body').append(thtml);
                var html='';
                for(var i=0;i<settings.data.length;i++){
                    if(settings.data[i].pid=='0'){
                        html='';
                        html+='<li data-id="'+settings.data[i].id+'"  data-level="'+settings.data[i].level+'"  data-type="'+settings.data[i].type+'">';
                        html+='<div class="bigclass_list">';
                        html+='<span class="arrowBtn ishide" title="收起"></span>';
                        html+='<em class="jlfont">&#xe61c;</em>';
                        html+='<strong>'+settings.data[i].name+'</strong>';
                        html+='</div>';
                        html+='</li>';
                        $('#fileLayer ul').append(html);
                    }else{
                        var _parents=$('#fileLayer li[data-id="'+settings.data[i].pid+'"]');
                        if(_parents.find('.arrowBtn:first i').length){
                            html='';
                            html+='<li data-id="'+settings.data[i].id+'" data-level="'+settings.data[i].level+'"  data-type="'+settings.data[i].type+'">';
                            html+='<div class="bigclass_list">';
                            html+='<span class="arrowBtn ishide" title="展开"></span>';
                            html+='<em class="jlfont">&#xe61c;</em>';
                            html+='<strong>'+settings.data[i].name+'</strong>';
                            html+='</div>';
                            html+='</li>';
                            _parents.find('ul:first').append(html);
                        }else{
                            html='';
                            html+='<ul>';
                            html+='<li data-id="'+settings.data[i].id+'" data-level="'+settings.data[i].level+'"  data-type="'+settings.data[i].type+'">';
                            html+='<div class="bigclass_list">';
                            html+='<span class="arrowBtn ishide" title="展开"></span>';
                            html+='<em class="jlfont">&#xe61c;</em>';
                            html+='<strong>'+settings.data[i].name+'</strong>';
                            html+='</div>';
                            html+='</li>';
                            html+='</ul>';
                            _parents.append(html);
                            _parents.find('.arrowBtn:first').append('<i class="jlfont">&#xe637;</i>');
                        }
                    }
                    if(settings.selId==settings.data[i].id){
                        //console.log(settings.selId);
                        var _that=$('#fileLayer li[data-id="'+settings.data[i].id+'"]');
                        _that.addClass('cur');
                        _that.parents('ul').addClass('cur');
                        //console.log(_that.parents('li .arrowBtn:first').html());
                        _that.parents('li').find('.arrowBtn:first').removeClass('ishide');
                        _that.parents('li').find('.arrowBtn:first').attr('title','收起');
                    }
                }
                if(settings.addPoint!=''){
                    var theHtml='<div class="toaddFile">'+settings.addPoint+'</div>';
                    $('#fileLayer .fileListNei').prepend(theHtml);
                    $('#fileLayer .toaddFile').off('click');
                    $('#fileLayer .toaddFile').on('click',function(){
                        settings.addPoint_fn();
                    })
                }
                //左侧多层菜单点击事件
                $('#fileLayer').off('click','.bigclass_list');
                $('#fileLayer').on('click','.bigclass_list',function(){
                    $('#fileLayer li').removeClass('cur');
                    $(this).parents('li:first').addClass('cur');
                    that.val($(this).find('strong').text());
                    that.attr('data-id',$(this).parents('li:first').attr('data-id'));
                    that.attr('data-level',$(this).parents('li:first').attr('data-level'));
                    that.attr('data-type',$(this).parents('li:first').attr('data-type'));
                    $('#fileLayer').hide();
                });
                $('#fileLayer').off('click','.arrowBtn');
                $('#fileLayer').on('click','.arrowBtn',function(event){
                    if($(this).hasClass('ishide')){
                        $(this).removeClass('ishide');
                        $(this).attr('title','收起');
                        $(this).parents('.bigclass_list:first').siblings('ul').addClass('cur');
                    }else{
                        $(this).addClass('ishide');
                        $(this).attr('title','展开');
                        $(this).parents('.bigclass_list:first').siblings('ul').removeClass('cur');
                    }
                    event.stopPropagation();
                });
            }
            return false;
        });
        $('body').on('click',function(){
            $('#fileLayer').hide();
        });
    };
}(jQuery));