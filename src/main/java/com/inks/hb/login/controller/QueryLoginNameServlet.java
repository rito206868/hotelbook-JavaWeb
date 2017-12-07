package com.inks.hb.login.controller;

import com.google.gson.Gson;
import com.inks.hb.login.service.LoginService;
import com.inks.hb.login.service.LoginServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * 此servlet是登录界面使用的，根据用户登录名和用户密码进行登录判断。
 * 如果登录结果判断成功就在session中写入当前的登录名值
 * 通过ajax返回给判断的结果。
 */
@WebServlet(value = "/QueryLoginNameServlet", name = "QueryLoginNameServlet")
public class QueryLoginNameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // 调用service
        LoginService service = new LoginServiceImpl();

        // 获得姓名
        String loginName = request.getParameter("loginName");
        String loginPwd = request.getParameter("loginPwd");

        try {
            int check = service.queryByName(loginName, loginPwd);
            if (check == 1) { // 设置session
                HttpSession session = request.getSession();
                session.setAttribute("LoginName", loginName);
            }
            Gson gson = new Gson();
            out.print(gson.toJson(check));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
