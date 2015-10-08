/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author hicham
 */
public class CharacterManagementController implements Initializable, ControlledScreen {
ScreensController myController;
    @FXML
    private Label label;
    @FXML
    private ChoiceBox<String> races;
    @FXML
    private ChoiceBox<String> classes;
    @FXML
    private TextField name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    public void CreateCharacter(ActionEvent event){
        Characters character = new Characters();
        character.setName(name.getText());
        switch (races.getSelectionModel().getSelectedIndex()) {
            case 0:
                character.setRace("Ork");
                break;
            case 1:
                character.setRace("Imperial");
                break;
            case 2:
                character.setRace("Khajit");
                break;
            case 3:
                character.setRace("Nord");
                break;
        }
        switch (classes.getSelectionModel().getSelectedIndex()) {
            case 0:
                character.setClass1("Warrior");
                break;
            case 1:
                character.setClass1("Ranger");
                break;
            case 2:
                character.setClass1("Magic");
                break;
        }
        System.out.println(character.getClass());
        System.out.println(character.getRace());
        System.out.println(character.getName());
        persist(character);
      
        
    }
    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Assignment1fxPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
}
