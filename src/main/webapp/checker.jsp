<%-- 
    Document   : checker
    Created on : 03 Mar 2016, 1:32:06 PM
    Author     : Eusuph
--%>

<%@page import="com.olsps.olspsManagement.app.controller.OlspsController"%>
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html">
    <h:head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <title>Facelet Title</title>
    </h:head>
    <h:body>
        <% String username=request.getParameter("userLogin");
        String password=request.getParameter("password"); 
        OlspsController controller = new OlspsController();
        
        if(username !=null &&  password !=null) 
        { 
        //controller.logon();
        
        System.out.println("com.olsps.olspsManagement.app.controller.OlspsController.checker()");
        session.setAttribute("userLogin",username);
        response.sendRedirect("faces/index.xhtml"); 
        } 
        else
            response.sendRedirect("loginOption2.xhtml");
            System.out.println("com.olsps.olspsManagement.app.controller.OlspsController.else()");
            System.out.println(session.getAttribute(username));
        %>

    </h:body>
</html>
