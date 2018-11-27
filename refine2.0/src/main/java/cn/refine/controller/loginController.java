package cn.refine.controller;

import cn.refine.common.util.IpUtils;
import cn.refine.common.util.MD5Util;
import cn.refine.mapper.SystemMapper;
import cn.refine.model.LogVO;
import cn.refine.model.User;
import cn.refine.model.UserRole;
import cn.refine.util.date.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 2018/1/30.
 */
@Controller
@RequestMapping("/login")
public class loginController {

    @Autowired
    private SystemMapper systemMapper;

    private User loginInfo = new User();

    private User user ;

    private String message;

    @RequestMapping("/userLogin")
    @ResponseBody
    public Map userLogin(HttpSession session, HttpServletRequest request, @Param("username") String username, @Param("password") String password) {
        try{
        Map result = new HashMap<>();
//        System.out.println("登录中~~ 账号：" + username + " 密码：" + password + "");
        if("".equals(username) || "".equals(password)){
            message = "请输入用户名或密码!";
            result.put("message", message);
            result.put("status", "login");
            return result;
        }else{
            loginInfo.setLoginName(username);
            loginInfo.setLoginPwd(password);
        }
        boolean isUser = loginInfo.getLoginName() != null;
        if (!isUser) {
            message = "用户名或密码不正确，请重新输入!";
            result.put("message", message);
            result.put("status", "login");
            return result;
        } else {
            //转换为MD5
//            if (StringUtils.isEmpty(loginInfo.getLoginType())) {
                loginInfo.setLoginPwd(MD5Util.getMD5ofStr(loginInfo.getLoginPwd()));
//            } else {
//                loginInfo.setLoginPwd(loginInfo.getLoginPwd());
//            }
            //验证账户密码是否正确
            User userInfoDB = systemMapper.getUserInfo(loginInfo);
            if (userInfoDB == null) {
                message = "用户名或者密码不正确，请重新输入!";
                result.put("message", message);
                result.put("status", "login");
                return result;
            }else{
                if(userInfoDB.getStatus()==0){
                    message = "该账户无效状态，请联系管理员!";
                    result.put("message",message);
                    result.put("status", "login");
                    return result;
                }
                String loginName = userInfoDB.getLoginName();
                Integer id = userInfoDB.getId();
                String page = "";
                // 登录成功查询用户所有权限
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                List<Map<String,Object>> userRoles = systemMapper.getUserRoleByUserId1(userRole);
                List<String> rolePaths =  new ArrayList<>();
//                for (UserRole userRole1:userRoles){
//                    String path = userRole1.getPath();
//                    rolePaths.add(path);
//                }
                String rolePath="";
                String type="";
                boolean flag=false;
                if(userInfoDB.getType()==999){
                    rolePath = "system";
                    page = "999";
                }
                else{
                    if(userRoles!=null){
                        for (int i = 0; i < userRoles.size(); i++) {
                            String path = userRoles.get(i).get("path")==null?"":userRoles.get(i).get("path").toString();
                            rolePaths.add(path);
                            if(!flag){
                                if(userRoles.get(i).get("roleName")!=null && userRoles.get(i).get("roleName").toString().contains("管理员")){
                                    flag=true;
                                }
                            }

                        }
                        if(rolePaths!=null){
                            for (int i = 0; i < rolePaths.size(); i++) {
                                rolePath = rolePath + rolePaths.get(i)+";";
                                String path = rolePaths.get(i);
                                if("/adminPage.html".equals(path)){
                                    page = path;
                                }else if("/index2.html".equals(path)){
                                    page = path;
                                }
                            }
                        }
                    }

                }
                LogVO log = new LogVO();
                log.setUserName(userInfoDB.getName());
                log.setLoginName(userInfoDB.getLoginName());
                log.setOperFunc("用户登录");
                log.setOrgName(userInfoDB.getOrgName());
                log.setOperId(userInfoDB.getId());
                log.setOperTime( DateUtils.getCurrentDate(DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss));
                log.setOperIp(IpUtils.getIpAddr(request));
                systemMapper.insertLog(log);
                String orgName = userInfoDB.getOrgName();
                // 将用户信息放入到session中
                session.setAttribute("userInfo", userInfoDB);
                message = "登录成功！点击进入首页";
                result.put("message",message);
                result.put("username",loginName);
                result.put("id",id.toString());
                result.put("page",page);
                result.put("orgName",orgName);
                result.put("rolePath",rolePath);
                result.put("flag",flag);

            }
        }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("name","hahahahaha");
        return "index2";
    }
}
