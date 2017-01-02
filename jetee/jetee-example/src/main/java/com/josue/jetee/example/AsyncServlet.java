/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.jetee.example;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Josue
 */
@WebServlet(urlPatterns = {"/AsyncServlet"})
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println("STARTING ASYNC...<br />");
        writer.flush();

        AsyncContext aCtx = request.startAsync(request, response);

        aCtx.start(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Done !!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
