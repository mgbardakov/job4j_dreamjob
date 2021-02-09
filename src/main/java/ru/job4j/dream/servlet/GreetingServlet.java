package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class GreetingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = req.getReader().lines().collect(Collectors.joining());
        JsonObject json = new Gson().fromJson(data, JsonObject.class);
        resp.setContentType("json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.print(json.toString());
        writer.flush();
    }
}
