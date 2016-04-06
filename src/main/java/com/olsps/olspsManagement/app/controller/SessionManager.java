/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eusuph
 */
@WebServlet("faces/index.xhtml")
public class SessionManager extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
//        OlspsController controller = new OlspsController();
//        try {
//            if(request.getParameter("userLogin") !=null && request.getPart("password") != null){
//                controller.logon();
//                request.getRequestDispatcher("faces/index.xhtml").forward(request, response);
//                
//            }
//
//            HttpSession session = request.getSession(false);
//            String value = (String) session.getAttribute("username");
//           
//            
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }

}
