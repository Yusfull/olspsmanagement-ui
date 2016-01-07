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
import com.olsps.olspsaccesscontrolapi.Role;
import com.olsps.olspsaccesscontrolapi.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.omnifaces.util.Ajax;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
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
    Role role = new Role();
    boolean editable;
    String selectedGroupValue;

    private DualListModel<User> userModel;

    private User selectedUser;
    private Group selectedGroup;
    private Role selectedRole;
    private List<User> userList = new ArrayList<>();
    private List<User> toGroupUser = new ArrayList<>();
    private List<Group> groupsList = new ArrayList<>();
    private List<Role> rolesList = new ArrayList<>();
    private List<User> notinGroupList = new ArrayList<>();
    private List<User> inList = new ArrayList<>();
    private List<User> filteredUsers;

    @PostConstruct
    public void init() {

        findAllUsers();
        userModel = new DualListModel<>(userList, notinGroupList);
        findAllUsers();
        findRoles();
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

    public List<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Role> rolesList) {
        this.rolesList = rolesList;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }
    
    public List<User> getToGroupUser() {
        return toGroupUser;
    }

    public void setToGroupUser(List<User> toGroupUser) {
        this.toGroupUser = toGroupUser;
    }

    public String getSelectedGroupValue() {
        return this.selectedGroupValue;
    }

    public void setSelectedGroupValue(String selectedGroupValue) {
        this.selectedGroupValue = selectedGroupValue;
    }

    public List<User> getNotinGroupList() {
        return notinGroupList;
    }

    public void setNotinGroupList(List<User> notinGroupList) {
        this.notinGroupList = notinGroupList;
    }

    public List<User> getInList() {
        return inList;
    }

    public void setInList(List<User> inList) {
        this.inList = inList;
    }

    public void login(String uname, String pname) {
        FacesContext context = FacesContext.getCurrentInstance();
        log.log(Priority.DEBUG, "Constructor logging info:");
        try {
            boolean validate = accessControll.isUserCredentialsValid(uname, pname);
            if (validate) {
                user = accessControll.findUser(uname);
                String name = user.getFirstName();
                context.addMessage(null, new FacesMessage("Welcome: ", name));
            } else {
                context.addMessage(null, new FacesMessage("Opps! username and password do not match "));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String addUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addUser(user);
            context.addMessage(null, new FacesMessage("Successful"));
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
            accessControll.addUserToGroup(selectedUser.getUserName(), selectedGroup.getName());
            context.addMessage(null, new FacesMessage(selectedUser.getUserName() + " added to " + selectedGroup.getName() + " Successful!"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    public void adToGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addUserToGroup(selectedUser.getUserName(), selectedGroup.getName());
            context.addMessage(null, new FacesMessage(selectedUser.getUserName() + " added to " + selectedGroup.getName() + " Successful!"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    public List<User> editUserGroups() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean value = true;
        while (value) {
            try {
                for (User user1 : userList) {
                    value = accessControll.isUserInGroup(user1.getUserName(), selectedGroup.getName());
                    if (!value) {
                        notinGroupList.add(user1);
                        System.out.println("Users not in a group:" + " " + user1.getFirstName());
                    } else if (value) {
                        inList.add(user1);
                        System.out.println("Users in a group:" + " " + user1.getFirstName());
                    }
                }
            } catch (RecordNotFoundException_Exception ex) {
                java.util.logging.Logger.getLogger(OlspsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        return inList;
    }

    public Group deleteGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.addMessage(null, new FacesMessage("Please confirm deleted!"));
            accessControll.deleteGroup(selectedGroup.getName());
            context.addMessage(null, new FacesMessage("Group" + group.getName() + " " + "deleted!"));
            groupsList.clear();
            findAllGroups();
            Ajax.update("deletefrmgrpid:usergroupsid");
        } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception excxception) {
            excxception.printStackTrace();
        }
        return null;
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
      public void addRole() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addRole(role.getName());
            context.addMessage(null, new FacesMessage("Successful"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
      public List<Role> findRoles(){
          try{
              rolesList = accessControll.findRoles("%");
              if(!rolesList.isEmpty()){
                  return rolesList;
              }else{
                  return null;
              }
          }catch(Exception exception){
              exception.printStackTrace();
          }
          return  null;
      }
    
    public void assignRoletoGroups(){
        FacesContext context = FacesContext.getCurrentInstance();
        try{
            accessControll.assignRoleToGroup(selectedRole.getName(), selectedGroup.getName());
            context.addMessage(null, new FacesMessage("Role assigned"));
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    /*
     * Facility for picklist
     */
    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Group) item).getName()).append("<br />");
        }
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            System.out.println(event.getObject().toString());
        } catch (Exception exe) {

        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        try {
            if (event.getSource() != null) {
                System.out.println(event.getObject().toString());
            }
        } catch (Exception e) {
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    /*
     * facilitis to take care of datatable  
     */
    public void onRowEdit(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (event.getObject() != null && event.getObject() instanceof User) {
                accessControll.updateUser((User) event.getObject());
                context.addMessage("Success", new FacesMessage(event.getObject() + " Edited"));
            }
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
