/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ricardo van der spek
 */
public class AccountManagementController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private Label Funds;
    @FXML
    private Label iban;
    @FXML
    private Button AddFunds;
    @FXML
    private ChoiceBox<?> SubsciptionAmount;
    @FXML
    private Button addsubscription;
    @FXML
    private Label CharacterSlots;
    @FXML
    private Button BuyCharacterSlot;
    @FXML
    private TextField FundsAmount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
