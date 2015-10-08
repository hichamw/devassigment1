/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static screens.ScreensFramework.mainContainer;
;

/**
 * FXML Controller class
 *
 * @author gamerszvidx
 */
public class LogInController  implements Initializable, ControlledScreen{
    private static final String PERSISTENCE_UNIT_NAME = "Assignment1fxPU";
    private static EntityManagerFactory factory;
    ScreensController myController;
    @FXML
    private Label label;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button Login;
    @FXML
    private Label loginvalidate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void login(ActionEvent event){
        String Username = username.getText();
        String Password = password.getText(); 
        boolean verified = validate(Username,Password);
        if(verified){
            mainContainer.loadScreen(ScreensFramework.screen3ID, ScreensFramework.screen3File);
            myController.setScreen(ScreensFramework.screen3ID);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("SELECT u FROM Users u WHERE u.userName = :login ");
        q.setParameter("login", Username);
        Users user = (Users) q.getSingleResult();
        Users.VerifiedUser = user;
        }else{
            loginvalidate.setText("Username/Password incorrect");
        }
       
    }
    @FXML
    private void OpenRegister(ActionEvent event){
       myController.setScreen(ScreensFramework.screen2ID);
    }

   private boolean validate(String Username, String Password) {
          String flag="failure";
              factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = factory.createEntityManager();
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.userName = :login AND u.password = :pass");
           q.setParameter("login", Username);
           q.setParameter("pass", Password);
           try{
               Users user = (Users) q.getSingleResult();
             if (Username.equalsIgnoreCase(user.getUserName())&&password.equals(user.getPassword())) {
                flag="success";
             }
           }catch(Exception e){      
               return false;
           }

          return true;
     }
    
}
