/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_test;

import com.olsps.olspsaccesscontrolapi.AccessControllerWebService;
import com.olsps.olspsaccesscontrolapi.AccessControllerWebService_Service;
import com.olsps.olspsaccesscontrolapi.Group;
import com.olsps.olspsaccesscontrolapi.InvalidKeySpecException_Exception;
import com.olsps.olspsaccesscontrolapi.NoSuchAlgorithmException_Exception;
import com.olsps.olspsaccesscontrolapi.RecordNotFoundException_Exception;
import com.olsps.olspsaccesscontrolapi.RecordNotUniqueException_Exception;
import com.olsps.olspsaccesscontrolapi.Role;
import com.olsps.olspsaccesscontrolapi.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Eusuph
 */
public class TestSample {

    private static AccessControllerWebService accessControll;
    AccessControllerWebService_Service serviceFactory;
    User user = new User();
    Group group = new Group();
    Role role = new Role();
    private List<User> userList = new ArrayList<>();

    public TestSample() {
        serviceFactory = new AccessControllerWebService_Service();
        accessControll = serviceFactory.getAccessControllerWebServicePort();

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test(priority = 0)
    public void hello() {

        System.out.println("This is a test !!");
    }

    //@Test(priority = 0)
    public void login() throws InvalidKeySpecException_Exception, NoSuchAlgorithmException_Exception, RecordNotFoundException_Exception, RecordNotUniqueException_Exception {
        String uname = "bilbo";
        String pname = "precious123";
        try {
            boolean valid = accessControll.isLdapUserCredentialsValid(uname, pname);
             Assert.assertTrue(valid);
             System.out.println("Valid user" + " " + valid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     using testNG to test addUser method
     */
   // @Test(priority = 0)
    void testAddUser() {
        user.setFirstName("Yusuf ");
        user.setLastName("Khunga");
        user.setUserName("qoloinawe");
        user.setPasswordCypher("85647");
        //user.setSoapId("56782");
        try {
            accessControll.addUser(user);
            Assert.assertNotNull(user, "User object not null!");
        } catch (Exception ex) {
            Logger.getLogger(TestSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //@Test(priority = 0)
    public void updataUsedata() {
        try {
            String name = "kaka";
            user = accessControll.findUser("yusuf");
            String username = user.getFirstName();
            username = name;
            System.err.println("***" + name);
            user.setFirstName(username);
            System.out.println("What is in there: " + username);

            accessControll.updateUser(user);
        } catch (RecordNotUniqueException_Exception | RecordNotFoundException_Exception tx) {
            tx.printStackTrace();
        }
    }

    /*
     * test add groups 
     */
    // @Test(priority = 2)
    public void testAddGroup() {
        try {
            List groups = accessControll.findGroupNames("Support test");
            if (groups.isEmpty()) {
                accessControll.addGroup("Support test");
                Assert.assertNotNull(groups, "Added new group!");
            } else {
                Assert.assertTrue(true, null);
                System.out.println("Record exists!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
    }

    //@Test(priority = 0)
    void testFindGroups() {
        try {
            List groups = accessControll.findGroups("%");
            for (Object group : groups) {
                System.out.println("dada: " + group);
            }
        } catch (AssertionError exe) {
            exe.printStackTrace();
        }
    }

    //@Test(priority = 0)
    void testAssignUserToGroup() {
        try {
            accessControll.addUserToGroup("adam15422", "Barcelona");
            Assert.assertTrue(true);
        } catch (Exception ex) {
            ex.getStackTrace();
            ex.printStackTrace();
        }
    }
    //@Test(priority = 0)
    public void deleteUser(){
        
        try {
            String name = "123451";
            accessControll.deleteUser(name);
            Assert.assertNull(accessControll.findUser(name),"User not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Test(priority = 0)
    public void deleteGroup() {
        try {
            String groupToDelete = "Test Grp";
            accessControll.deleteGroup(groupToDelete);
            Assert.assertNull(accessControll.findGroup(groupToDelete), groupToDelete);
        } catch (RecordNotFoundException_Exception | RecordNotUniqueException_Exception ex) {
        }
    }

    /*
     * Test passes and returns a list of users objects
     */
    //@Test(priority = 5)
    public void testFindUsers() {
        List<User> registeredUsers;

        try {
            registeredUsers = accessControll.findUsers("%");
            if (registeredUsers.isEmpty()) {
                System.out.println("This guy is empty..Add users");
            } else {
                for (User registeredUser : registeredUsers) {
                    System.out.println(registeredUser.getFirstName());
                }
                Assert.assertNotNull(registeredUsers, "never null");
            }
        } catch (AssertionError ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
    }

    /*
     * Test pass, searches for a user by username not first name and returns a user object
     */
    //@Test(priority = 2)
    void testFindUser() {
        try {
            user = accessControll.findUser("yusufcassim");
            if (user.getFirstName().equals("Cassim")) {
                Assert.assertTrue(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //@Test
    public void testAddGroups() {
        try {
            accessControll.addGroup("Data Science");
            List groups = accessControll.findGroups("%");
            if (!groups.isEmpty()) {
                Assert.assertTrue(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //@Test(priority = 0)
    void testAddRole() {
        try {
            accessControll.addRole("Administrator");
            System.out.println(role.getName());
        } catch (Exception e) {
        }
    }

    //@Test(priority = 0)
    public void testfindRoles() {
        try {

            List<Role> emRoles = accessControll.findRoles("%");
            for (Role emRole : emRoles) {
                System.out.println(emRole.getId());
            }
            System.out.println("app_test.TestSample.testfindRoles()");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    //@Test(priority = 0)
    public void getRolesForGroup(){
        try {
            List<Role> tryRoles = accessControll.getRolesAssignedToGroup("Dev Team");
            for (Role tryRole : tryRoles) {
                System.out.println("**" + tryRole.getName());    
            }
        } catch (Exception e) {
        }
    }

    //@Test
    void testUpdateGroup() {
        try{
            String grouName = "ChelseaX";
            group = accessControll.findGroup("Chelsea");
            String real = group.getName();
            real = grouName;
            group.setName(grouName);   
        }catch(Exception exception){
            exception.printStackTrace();
        }
                
         
    }

    @BeforeClass
    public static void setUpClass() throws AssertionError {
    }

    @AfterClass
    public static void tearDownClass() throws AssertionError {
    }

    @BeforeMethod
    public void setUpMethod() throws AssertionError {
    }

    @AfterMethod
    public void tearDownMethod() throws AssertionError {
    }
}
