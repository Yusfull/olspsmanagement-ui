/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import com.olsps.olspsaccesscontrolapi.AccessControllerWebService;
import com.olsps.olspsaccesscontrolapi.AccessControllerWebService_Service;
import com.olsps.olspsaccesscontrolapi.GeneralSecurityException_Exception;
import com.olsps.olspsaccesscontrolapi.Group;
import com.olsps.olspsaccesscontrolapi.InvalidKeySpecException_Exception;
import com.olsps.olspsaccesscontrolapi.LDAPException_Exception;
import com.olsps.olspsaccesscontrolapi.NoSuchAlgorithmException_Exception;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;
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
@Named(value = "olspsController")
@SessionScoped
public class OlspsController implements Serializable {

    @Inject
    UserController userController;
    @Inject
    RolesController rolesController;
    @Inject
    EntitlementController entitlementController;
    @Inject
    GroupController groupController;
    private HttpSession session;

    private static AccessControllerWebService accessControll;
    AccessControllerWebService_Service serviceFactory;

    final static Logger log = Logger.getLogger(OlspsController.class.getName());

    /**
     *
     * @return
     */
    User user;
    Group group;
    Role role;
    private String userLogin;
    private String password;

    boolean editable;
    boolean isAuthorized;
    String selectedGroupValue;

    private DualListModel<User> userModel;

    private User selectedUser;
    private User userToDelete;
    private User newUser;
    //Logger rootLogger = Logger.*getLogger*("");

    Group groupToDelete = new Group();
    private Group selectedGroup;
    private Role selectedRole;
    private List<User> userList = new ArrayList<>();
    private List<User> toGroupUser = new ArrayList<>();
    private List<Group> groupsList = new ArrayList<>();
    private List<Role> rolesList = new ArrayList<>();
    private List<User> notinGroupList = new ArrayList<>();
    private List<User> inList = new ArrayList<>();
    private List<User> filteredUsers;
    private List<Group> filterGroups = new ArrayList<>();

    @PostConstruct
    public void init() {

        user = new User();
        findAllUsers();
        userModel = new DualListModel<>(userList, notinGroupList);
//        findAllUsers();
        findRoles();
        findAllGroups();
    }

    //default constructor
    public OlspsController() {
        serviceFactory = new AccessControllerWebService_Service();
        accessControll = serviceFactory.getAccessControllerWebServicePort();
        this.editable = true;
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

    public User getNewUser() {
        //newUser = new User();
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
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

    public List<Group> getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(List<Group> filterGroups) {
        this.filterGroups = filterGroups;
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

    public Group getGroupToDelete() {
        return groupToDelete;
    }

    public void setGroupToDelete(Group groupToDelete) {
        this.groupToDelete = groupToDelete;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getUserToDelete() {
        return userToDelete;
    }

    public void setUserToDelete(User userToDelete) {
        this.userToDelete = userToDelete;
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

    public boolean isIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public void addSlectedUser() {
        selectedUser = new User();
    }

    public void logon(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext requestContextcontext = RequestContext.getCurrentInstance();
        FacesMessage message = null;

        boolean val = false;
        try {
            if (userLogin != null && !userLogin.isEmpty() && password != null && !password.isEmpty()) {
                val = accessControll.isUserCredentialsValid(userLogin, password);
                if (val) {      
                    isAuthorized = true;
                    editable = false;
                    session = (HttpSession) context.getExternalContext().getSession(false);
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", user.getFirstName());
                    //context.addMessage(null, new FacesMessage("Welcome:" + " " + user.getFirstName()));
                } else {
                    isAuthorized = false;
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
                }
                FacesContext.getCurrentInstance().addMessage(null, message);
                requestContextcontext.addCallbackParam("isAuthorized", isAuthorized);
            }
        } catch (GeneralSecurityException_Exception | LDAPException_Exception e) {
            context.addMessage(null, new FacesMessage("Error Please login"));

        } catch (RecordNotUniqueException_Exception | InvalidKeySpecException_Exception | NoSuchAlgorithmException_Exception ex) {
            java.util.logging.Logger.getLogger(OlspsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String logout() {
        if (session != null) {
            session.invalidate();
            return "faces/index.xhtml";
        }
        return "";
    }

    public void addNewUser() {
        newUser = new User();
        Ajax.update("form1:addWidget");
    }

    public void addUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (isAuthorized && !session.isNew()) {
                String plainText = newUser.getPasswordCypher();
                String cypher = accessControll.getPasswordCypher(plainText);
                newUser.setPasswordCypher(cypher);
                accessControll.addUser(newUser);
                log.debug(newUser);
                context.addMessage(null, new FacesMessage("User Added"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are not authorised to add "));
            }

        } catch (RecordExistsException_Exception rec) {
            log.error("Error", rec);
            // rec.getStackTrace();
        } catch (InvalidKeySpecException_Exception | NoSuchAlgorithmException_Exception ex) {
            java.util.logging.Logger.getLogger(OlspsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateUserData() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.updateUser(user);
        } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception ex) {
            log.error("Error:", ex);
        } finally {

        }
    }

    public void addNewGroup() {
        group = new Group();
    }

    public void addGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addGroup(group.getName());
            log.debug(session);
            log.debug(group.getName());
            context.addMessage(null, new FacesMessage("New Group added Successful !"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    public List<Group> findAllGroups() {
        try {
            groupsList = accessControll.findGroups("%");
            log.debug(groupsList);
            return groupsList;
        } catch (Exception exception) {
            log.error("Error", exception);
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
            context.addMessage(null, new FacesMessage(selectedUser.getUserName() + " added to " + selectedGroup.getName() + " Successful11!"));
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
                /*
                 * get roles assigned to a selected group
                 */

                if (selectedGroup instanceof Group && selectedGroup != null) {
                    rolesList = accessControll.getRolesAssignedToGroup(selectedGroup.getName());
                }

            } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception ex) {
                java.util.logging.Logger.getLogger(OlspsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        return inList;
    }

    public void onValueChanged(AjaxBehaviorEvent event) {
        try {
            System.out.println("com.olsps.olspsManagement.app.controller.OlspsController.onValueChanged()");
            editUserGroups();
            System.out.println("****");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void deleteGroup() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.addMessage(null, new FacesMessage("Please confirm deleted!"));
            accessControll.deleteGroup(selectedGroup.getName());
            findAllGroups();
            Ajax.update("deletefrmgrpid:usergroupsid");
        } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception excxception) {
            excxception.printStackTrace();
        }

    }

    public List<User> findAllUsers() {
        userList = new ArrayList();
        log.info("Searching for users");
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            userList = accessControll.findUsers("%");
            log.info(session);
            return userList;
        } catch (Exception ex) {
            log.error("error", ex);
        } finally {
            context.getMessages();
        }
        return null;
    }

    public void addNewRole() {
        role = new Role();
    }

    public void addRole() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.addRole(role.getName());
            context.addMessage(null, new FacesMessage("haybo"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<Role> findRoles() {
        try {
            rolesList = accessControll.findRoles("%");
            if (!rolesList.isEmpty()) {
                return rolesList;
            } else {
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void assignRoletoGroups() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            accessControll.assignRoleToGroup(selectedRole.getName(), selectedGroup.getName());
            context.addMessage(null, new FacesMessage("Role assigned"));
        } catch (Exception exception) {
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

    public void deleteUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (isAuthorized) {
                if (userToDelete != null && selectedUser == null) {
                    this.selectedUser = this.userToDelete;
                    accessControll.deleteUser(selectedUser.getUserName());
                    findAllUsers();
                    context.addMessage(null, new FacesMessage("Successful"));
                }
            }
        } catch (Exception e) {
            log.error("Error", e);
            e.printStackTrace();
        }
    }

    public void setSelectedUserForDelete(User u) {
        this.userToDelete = u;
        Ajax.update("formDeleteUserDialog:userDetail");
        RequestContext.getCurrentInstance().execute("PF('wvDeleteUserDialog').show()");
    }

}
