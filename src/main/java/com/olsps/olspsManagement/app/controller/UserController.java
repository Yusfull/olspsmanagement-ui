/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import com.olsps.olspsaccesscontrolapi.AccessControllerWebService;
import com.olsps.olspsaccesscontrolapi.AccessControllerWebService_Service;
import com.olsps.olspsaccesscontrolapi.GeneralSecurityException_Exception;
import com.olsps.olspsaccesscontrolapi.InvalidKeySpecException_Exception;
import com.olsps.olspsaccesscontrolapi.LDAPException_Exception;
import com.olsps.olspsaccesscontrolapi.NoSuchAlgorithmException_Exception;
import com.olsps.olspsaccesscontrolapi.RecordNotFoundException_Exception;
import com.olsps.olspsaccesscontrolapi.RecordNotUniqueException_Exception;
import com.olsps.olspsaccesscontrolapi.User;
import java.io.Serializable;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eusuph
 */
@SessionScoped
public class UserController implements Serializable {

    private String userLogin;
    private String password;
    private User user;
    private boolean isAuthorized;
    private static AccessControllerWebService accessControll;
    AccessControllerWebService_Service serviceFactory;

    public UserController(){
        serviceFactory = new AccessControllerWebService_Service();
        accessControll = serviceFactory.getAccessControllerWebServicePort();
    }
    
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean Authorized) {
        this.isAuthorized = Authorized;
    }

    public void logon() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean val = false;
        try {

            val = accessControll.isUserCredentialsValid(userLogin, password);
            if (val) {
                user = accessControll.findUser(userLogin);
                setIsAuthorized(true);
                HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
                context.addMessage(null, new FacesMessage("Welcome:" + " " + user.getUserName()));
                String sessionId = session.getId();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please login "));
                isAuthorized = false;
            }
        } catch (GeneralSecurityException_Exception | LDAPException_Exception e) {
            context.addMessage(null, new FacesMessage("Error Please login"));
        } catch (RecordNotFoundException_Exception ex) {

        } catch (RecordNotUniqueException_Exception | InvalidKeySpecException_Exception | NoSuchAlgorithmException_Exception ex) {
            java.util.logging.Logger.getLogger(OlspsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
