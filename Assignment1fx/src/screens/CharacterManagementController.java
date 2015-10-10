/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static screens.ScreensFramework.mainContainer;

/**
 * FXML Controller class
 *
 * @author hicham
 */
public class CharacterManagementController implements Initializable, ControlledScreen {
    private static final String PERSISTENCE_UNIT_NAME = "Assignment1fxPU";
    private static EntityManagerFactory factory;
    ScreensController myController;
    List<Characters> characterList = new ArrayList<Characters>();
    List<Users> userList = new ArrayList<Users>();
    @FXML
    private Label label;
    @FXML
    private ChoiceBox<String> races;
    @FXML
    private ChoiceBox<String> classes;
    @FXML
    private TextField name;
    @FXML
    private Label characterName;
    @FXML
    private Label characterClass;
    @FXML
    private Label characterRace;
    @FXML
    private Label characterLevel;
    @FXML
    private ComboBox<String> chooseCharacter;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateCharacters();
    }    
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    public void CreateCharacter(ActionEvent event){
        int currentSlots = Users.VerifiedUser.getCharacterSlots();
        if(currentSlots > 0){
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
            character.setLevel((int) (Math.random() * 99 + 1));
            characterList.add(character);
            Users.VerifiedUser.setCharactersCollection(characterList);
            userList.add(Users.VerifiedUser);
            character.setUsersCollection(userList);
            System.out.println(character.getClass());
            System.out.println(character.getRace());
            System.out.println(character.getName());
            int newSlots = currentSlots - 1;
            Users.VerifiedUser.setCharacterSlots(newSlots);
            persist(character,Users.VerifiedUser);
            chooseCharacter.getItems().clear();
            populateCharacters();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Characterslot available");
            alert.setHeaderText(null);
            alert.setContentText("All your characterslots are full please add one.");
            alert.showAndWait();
        }
    }
    
    public void persist(Object object,Object object2) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Assignment1fxPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(object);
            em.merge(object2);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    public void back(ActionEvent event){
        mainContainer.loadScreen(ScreensFramework.screen3ID, ScreensFramework.screen3File);
        myController.setScreen(ScreensFramework.screen3ID);
    }

    public void populateCharacters() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("SELECT c FROM Users u JOIN u.charactersCollection c WHERE u.userName = :user_name ORDER BY c.level");
        q.setParameter("user_name", Users.VerifiedUser.getUserName());
        List<Characters> resultList = q.getResultList();
        Map<String, String> results = new HashMap<String, String>();
        //List<String> comboList = new FXCollections.ObservableList(list) {};
        //ComboBox chooseCharacter = new ComboBox(resultList.toArray());
        
        for(int x = 0;x<resultList.size();x++){
            chooseCharacter.getItems().add(resultList.get(x).getName());
        }
        
   }
}
