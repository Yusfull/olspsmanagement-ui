/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import com.olsps.olspsaccesscontrolapi.AccessControllerWebService;
import com.olsps.olspsaccesscontrolapi.AccessControllerWebService_Service;
import com.olsps.olspsaccesscontrolapi.Group;
import com.olsps.olspsaccesscontrolapi.RecordExistsException_Exception;
import com.olsps.olspsaccesscontrolapi.RecordNotFoundException_Exception;
import com.olsps.olspsaccesscontrolapi.RecordNotUniqueException_Exception;
import com.olsps.olspsaccesscontrolapi.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Eusuph
 *
 * OlspsController is the actual controller which delegates
 */
@ManagedBean
@SessionScoped
public class OlspsController implements Serializable {

    private static AccessControllerWebService accessControll;
    AccessControllerWebService_Service serviceFactory;

    final static Logger log = Logger.getLogger(OlspsController.class.getName());

    User user = new User();
    Group group = new Group();
    boolean editable;

    private DualListModel<User> userModel;

    User selectedUser;
    //String groupName;
    List<User> userList;
    List<User> toGroupUser = new ArrayList();
    List<Group> groupsList = new ArrayList();
    List<User> filteredUsers;

    @PostConstruct
    public void init() {
        
        //userList = new ArrayList();
        //userModel.setSource(getUserList());
        findAllUsers();
        userModel = new DualListModel<>(userList, toGroupUser);
        findAllUsers();
        findAllGroups();
    }

    //default constructor
    public OlspsController() {
        serviceFactory = new AccessControllerWebService_Service();
        accessControll = serviceFactory.getAccessControllerWebServicePort();
        log.log(Priority.DEBUG, "Constructor logging info:");
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public DualListModel<User> getUserModel() {
        return this.userModel;
    }

    public void setUserModel(DualListModel<User> userModel) {
        this.userModel = userModel;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Group> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(List<Group> groupsList) {
        this.groupsList = groupsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getToGroupUser() {
        return toGroupUser;
    }

    public void setToGroupUser(List<User> toGroupUser) {
        this.toGroupUser = toGroupUser;
    }

    public String addUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addUser(user);
            context.addMessage(null, new FacesMessage("Successful", "Your message: "));
        } catch (RecordExistsException_Exception rec) {
            rec.getStackTrace();
        }
        return "/faces/index.xhtml";
    }

    public void updateUserData() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.updateUser(user);
        } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception ex) {
            ex.printStackTrace();
        } finally {
            context.getAttributes();
        }
    }

    public void addGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addGroup(group.getName());
            context.addMessage(null, new FacesMessage("New Group added Successful !"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            context.getMessages();
        }
    }

    public List<Group> findAllGroups() {

        try {
            groupsList = accessControll.findGroups("%");
            return groupsList;
        } catch (Exception exception) {
            exception.getStackTrace();
        }
        return null;
    }

    public void addUserToGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addUserToGroup(user.getUserName(), group.getName());
            context.addMessage(null, new FacesMessage(user.getUserName() + " added to " + group.getName() + " Successful!"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            context.getMessages();
        }
    }

    public List<User> findAllUsers() {
        userList = new ArrayList();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            userList = accessControll.findUsers("%");
            return userList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            context.getMessages();
        }
        return null;
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            accessControll.updateUser((User) event.getObject());
            FacesMessage msg = new FacesMessage("Success", ((User) event.getObject()).getId() + " Edited");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception ex) {
            java.util.logging.Logger.getLogger(OlspsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((User) event.getObject()).getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            try {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception rx) {
                rx.printStackTrace();

            } finally {

            }

        }
    }
}
