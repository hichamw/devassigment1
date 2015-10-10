/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import java.util.Date;
import javax.persistence.Column;

/**
 *
 * @author hicham
 */
public class RegisterController implements ControlledScreen ,Initializable {
    ScreensController myController;
    @FXML
    private Label label;
    @FXML
    private TextField regUserName;
    @FXML
    private TextField regFirstName;
    @FXML
    private TextField regLastName;
    @FXML
    private TextField regPassword;
    @FXML
    private TextField regPasswordRepeat;
    @FXML
    private Button regSubmit;
    @FXML
    private Label regSuccessLabel;
    @FXML
    private TextField regIBAN;
    
    @Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    
    public void backtologin(ActionEvent event) {
        myController.setScreen(ScreensFramework.screen1ID);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    @FXML
    public void onSubmitClick(ActionEvent event) {
        try {
            if(!regUserName.getText().equals("") && !regFirstName.getText().equals("") && !regLastName.getText().equals("") && regPassword.getText().equals(regPasswordRepeat.getText())&& !regPassword.getText().equals("")&& !regPasswordRepeat.getText().equals("")){
            Users newUser = new Users();
            newUser.setFirstName(regFirstName.getText());
            newUser.setLastName(regLastName.getText());
            newUser.setPassword(regPassword.getText());
            newUser.setUserName(regUserName.getText());
            newUser.setIban(regIBAN.getText());
            newUser.setCharacterSlots(5);
            newUser.setBanned(false);
            newUser.setMonthsPayed(0);
            Date lastPayment = new Date();
            newUser.setLastPayment(lastPayment);    
            newUser.setBalance(0);
            persist(newUser);
            regSuccessLabel.setText("Your account is successfully created!");
            }else{regSuccessLabel.setText("That username is already in use");}
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Assignment1fxPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
}
