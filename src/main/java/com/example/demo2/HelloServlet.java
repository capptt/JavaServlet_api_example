package com.example.demo2;

import Dao.UserDao;
import Domain.User;
import com.google.gson.Gson;

import javax.script.ScriptContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "HelloServlet", value = "/HelloServlet/*")
public class HelloServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao ud = new UserDao();
        String user = null;

        if (request.getPathInfo() != null && request.getPathInfo().length() > 1) {
            List<String> paths = Arrays.asList(request.getPathInfo().substring(1).split("/"));
            int id = Integer.parseInt(paths.get(0));
            if (paths.size() > 1) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                outputResponse(response, "invalid data", 500);
            } else {
                user = ud.findUserById(id);
            }

        } else {
            user = ud.getAllUser();
        }

        if (user == null) {
            System.out.println("data empty");
        } else {
            this.outputResponse(response, user, 200);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        int rc = HttpServletResponse.SC_OK;
        UserDao ud = new UserDao();

        boolean res = ud.Createuser(resBody);
        if (!res) {
            rc = HttpServletResponse.SC_BAD_REQUEST;
        }
        this.outputResponse(response, resBody, rc);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rc = HttpServletResponse.SC_OK;
        String result = null;
        String user = null;
        int id;

        if (request.getPathInfo() != null && request.getPathInfo().length() > 1) {
            List<String> paths = Arrays.asList(request.getPathInfo().substring(1).split("/"));

            if (paths.size() > 1) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                outputResponse(response, "invalid paths", 500);
            } else {
                id = Integer.parseInt(paths.get(0));
                UserDao ud = new UserDao();

                if (ud.findUserById(id) == null) {
                    result = "id not found";
                } else {
                    String resBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    result = ud.UpdateUser(resBody, id);
                }

            }

        }
        this.outputResponse(response, result, rc);
    }

    private void outputResponse(HttpServletResponse response, String payload, int status) {
        response.setHeader("Content-type", "application/json");
        try {
            response.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
