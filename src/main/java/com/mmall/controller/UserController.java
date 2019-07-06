package com.mmall.controller;

import com.mmall.beans.Mail;
import com.mmall.dao.SysUserMapper;
import com.mmall.model.SysUser;
import com.mmall.service.SysLogService;
import com.mmall.service.SysUserService;
import com.mmall.util.Base64Coded;
import com.mmall.util.MD5Util;
import com.mmall.util.MailUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

@Controller
public class UserController {
    private final int DEFAULT_DEPT_ID = 1;

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserMapper sysUserMapper;

    @RequestMapping("/threadState.page")
    public ModelAndView threadState(){
        return new ModelAndView("threadState");
    }

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        String path = "signin.jsp";
        response.sendRedirect(path);
    }

    @RequestMapping("/register.page")
    public void register(HttpServletResponse response) throws IOException {
        String path = "register.jsp";
        response.sendRedirect(path);
    }

    @RequestMapping("/register")
    public void registerAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String telephone = request.getParameter("telephone");
        String mail = request.getParameter("mail");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        StringBuilder errorMsg = new StringBuilder();
        if (StringUtils.isBlank(username)) {
            errorMsg.append("昵称为空");errorMsg.append(";");
        } else{
            request.setAttribute("username",username);
            if(username.length()>20) errorMsg.append("昵称过长").append(";");
        }
        if(StringUtils.isBlank(telephone)){
            errorMsg.append("电话为空");errorMsg.append(";");
        } else {
            request.setAttribute("telephone",telephone);
            if(telephone.length()!=11) errorMsg.append("电话不合法").append(";");
            else if(sysUserService.checkTelephoneExist(telephone,null)) errorMsg.append("该电话已注册").append(";");
        }
        if (StringUtils.isBlank(mail)){
            errorMsg.append("邮箱为空").append(";");
        }else {
            request.setAttribute("mail",mail);
            if(mail.length()<5||mail.length()>30) errorMsg.append("邮箱不合法").append(";");
            else if(sysUserService.checkEmailExist(mail,null)) errorMsg.append("该邮箱已注册").append(";");
        }
        if (StringUtils.isBlank(password1) || StringUtils.isBlank(password2)){
            errorMsg.append("密码为空").append(";");
        } else if(!password1.equals(password2)) {
            errorMsg.append("两次输入密码不一样").append(";");
        }

        if(StringUtils.isBlank(errorMsg)){
            //对邮箱信息进行编码
            String encodeMail = Base64Coded.encode(mail.getBytes());

            //校验信息成功
            String mailUrl = "http://pengyechang.xyz"+request.getRequestURI()+"/"+encodeMail;
            String htmlMsg = "<html lang='zh-CN'><head ><meta charset='utf-8'>"
                    + "</head><body>欢迎"+username+"注册，请点击以下连接验证邮箱："
                    + "<a href='"+mailUrl+"'>【点击验证】</a></body></html>";
            Mail sendMail = Mail.builder()
                    .subject("欢迎注册")
                    .message(htmlMsg)
                    .receivers(new HashSet<String>(){{add(mail);}})
                    .build();
            if(MailUtil.send(sendMail)){
                String encryptedPassword = MD5Util.encrypt(password1);
                SysUser user = SysUser.builder().username(username).telephone(telephone).mail(mail)
                        .password(encryptedPassword).deptId(DEFAULT_DEPT_ID).status(0).build();
                user.setOperateTime(new Date());

                sysUserMapper.insertSelective(user);

                String path = "mailValid.jsp";
                request.getRequestDispatcher(path).forward(request, response);
                return;
            }
            request.setAttribute("mail","");
        }
        errorMsg.deleteCharAt(errorMsg.length()-1);
        errorMsg.append("。");
        request.setAttribute("error", errorMsg.toString());
        String path = "register.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }

    @RequestMapping("/register/{encodeMail}")
    public void validMail(HttpServletRequest request, HttpServletResponse response, @PathVariable("encodeMail") String encodeMail) throws IOException {
        //对邮件信息进行解码
        String mail = Base64Coded.decode(encodeMail.getBytes());
        SysUser sysUser = sysUserMapper.findByKeyword(mail);
        if(sysUser!=null){
            sysUser.setStatus(1);
            request.getSession().setAttribute("user", sysUser);
            sysUserMapper.updateByPrimaryKeySelective(sysUser);
            response.sendRedirect("/admin/index.page"); //TODO
        }else {
            response.sendRedirect("/register.page");
        }
    }

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定的用户";
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "用户已被冻结，请联系管理员";
        } else {
            // login success
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page"); //TODO
            }
        }

        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }
}
