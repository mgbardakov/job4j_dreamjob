package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Candidate rslCan = new Candidate(0, "");
        if(req.getParameter("id") != null) {
            var id = Integer.parseInt(req.getParameter("id"));
            rslCan = PsqlStore.instOf().findCandidateByID(id);
        }
        req.setAttribute("candidate", rslCan);
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }
}
