package com.zxyl.controller;

import com.zxyl.redis.service.BaseService;
import com.zxyl.utils.SpringPropertyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by Administrator on 2018/3/21.
 */
@Controller
public class Image {

    @Resource
    private BaseService baseService;

    @RequestMapping("/image")
    public String index(String imageuuid, Model model) {

//        String isOk = (String) baseService.get("imageTransform",imageuuid);
        String tomcatPath = (String) SpringPropertyUtil.getContextProperty("tomcatPath");
        String path = tomcatPath+imageuuid+".dzi";

        model.addAttribute("imageuuid",imageuuid);

//        if ("".equals(isOk) || isOk == null){
//            return "noImage";
//        }
        if (!(new File(path).exists())){
            return "noImage";
        }

        return "image";
    }
}
