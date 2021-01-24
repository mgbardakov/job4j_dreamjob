package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    Store store = PsqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        var name = req.getParameter("name");
        if(store.findUserByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            store.saveUser(user);
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("error", "Пользователь с таким email уже зарегистрирован.");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
