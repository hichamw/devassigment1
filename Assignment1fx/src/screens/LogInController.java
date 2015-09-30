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
;

/**
 * FXML Controller class
 *
 * @author gamerszvidx
 */
public class LogInController  implements Initializable, ControlledScreen{
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
    private Label RegisterLink;

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
       myController.setScreen(ScreensFramework.screen2ID);
    }
    @FXML
    private void OpenRegister(ActionEvent event){
       myController.setScreen(ScreensFramework.screen2ID);
    }

   
    
}
