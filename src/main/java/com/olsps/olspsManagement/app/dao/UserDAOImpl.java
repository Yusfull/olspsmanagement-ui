///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.olsps.olspsManagement.app.dao;
//
//
//import com.olsps.olspsManagement.app.service.UserService;
//import com.olsps.olspsManagement.app.serviceImpl.Janoid;
//import com.olsps.olspsManagement.app.model.User;
//import com.unboundid.ldap.sdk.LDAPConnection;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import javax.faces.bean.ManagedBean;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author Eusuph
// */
//@ManagedBean
//public class UserDAOImpl implements UserService {
//
//    final static Logger log = Logger.getLogger(UserDAOImpl.class.getName());
//    LDAPConnection conn;
//    private Hashtable<String, String> env = new Hashtable<String, String>();
//
//    Janoid facility;
//
//    @Override
//    public void newUser(User user) {
//
////        try {
////            log.log(Level.DEBUG, "Adding..");
////            facility = new Janoid("");
////            facility.setAttribute("firstname", user.getFirstname());
////            facility.setAttribute("lastname", user.getLastname());
////            facility.setAttribute("username", user.getUsername());
////            facility.setAttribute("password", user.getPassword());
////            facility.setAttribute("lastname", user.getGroupname());
////            log.log(Level.DEBUG, "user data");
////            System.out.println("val: " + facility);
////            if (conn != null) {
////                log.log(Level.INFO, "Live connection");
////                conn.add(facility);
////                log.log(Level.WARN, "user data corrupt");
////                System.out.println("val: " + facility);
////            } else {
////            }
////        } catch (Exception ex) {
////            log.log(Level.ERROR, "something is wrong!");
////        }
//
//    }
//
//    @Override
//    public Boolean deleteUser(String userToDelete) {
//
////        try {
////
////            List<String> values = new ArrayList<>();
////            values.add("Yusuf");
////            values.add("Cassim");
////            values.add("Zama");
////            log.log(Level.INFO, "Deleting..");
////            facility = new Janoid("");
////            values.remove(userToDelete);
////            for (String value : values) {
////                System.out.println(value);
////            }
////            facility.hasAttribute(userToDelete);
////            facility.removeAttribute(userToDelete);
////
////        } catch (Exception ex) {
////
////        }
//        return true;
//    }
//
//    @Override
//    public Boolean updateUser(User user) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<String> searchUser(String value) {
//        System.out.println("Search for users: " + value);
//        return null;
//    }
//
//    @Override
//    public void newGroup(String value) {
//        System.out.println("Search for groups: " + value);
//    }
//
//    @Override
//    public Boolean deleteGroup(String value) {
//        System.out.println("nyani");
//
//        return true;
//    }
//
//    @Override
//    public void addUserToGroup(String username, String group) {
//
//    }
//
//    @Override
//    public Boolean deletUserFromGroup(String username, String group) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String searchGroup(String group) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Boolean hasGroup(String username, String droup) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void addEntitlement(String entitlement, String role) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Boolean deleteEntitlements(String entitlement, String role) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Boolean isValidUser(String username, String entitlement) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void newRole(String role) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Boolean deleteRole(String role) {
//        System.out.println("delete successful: " + role);
//        return null;
//    }
//
//    @Override
//    public void assignRoleToGroup(String role, String group) {
//        //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Boolean removeRole(String role) {
//        System.out.println("delete successful: " + role);
//        return null;
//    }
//
//    @Override
//    public Boolean authenticateUser(String username, String password) {
////
////        Connection con = null;
////        PreparedStatement ps = null;
////
////        try {
////            con = DataConnection.getConnection();
////            System.out.println(" connection!" + DataConnection.getConnection().getMetaData());
////            ps = con.prepareStatement("Select uname, password from Users where uname = ? and password = ?");
////            System.out.println("*****");
////            ps.setString(1, username);
////            ps.setString(2, password);
////            System.out.println(username + " " + password);
////
////            ResultSet rs = ps.executeQuery();
////
////            if (rs.next()) {
////                return true;
////            }
////        } catch (SQLException ex) {
////            System.out.println("Login error -->" + ex.getMessage());
////            return false;
//////        } finally {
//////            DataConnection.close(con);
////        }
//        return false;
//    }
//
//}
