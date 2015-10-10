/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.lang.String;
import static java.lang.String.valueOf;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javax.persistence.Convert;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import static screens.ScreensFramework.mainContainer;

/**
 * FXML Controller class
 *
 * @author ricardo van der spek
 */
public class AccountManagementController implements Initializable, ControlledScreen {
    String euro = "\u20ac";
    private static final String PERSISTENCE_UNIT_NAME = "Assignment1fxPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    ScreensController myController;
    @FXML
    private Label label;
    @FXML
    private Label Funds;
    @FXML
    private Label iban;
    @FXML
    private Button AddFunds;
    @FXML
    private ChoiceBox<String> SubsciptionAmount;
    @FXML
    private Button addsubscription;
    @FXML
    private Button GoToCharacterManagement;
    @FXML
    private Label CharacterSlots;
    @FXML
    private Button BuyCharacterSlot;
    @FXML
    private TextField FundsAmount;
    @FXML
    private Label monthsLeft;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getFunds();
        getIban();
        getCharacterSlot();
        getMonthsLeft();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    public void getMonthsLeft(){
        monthsLeft.setText(valueOf(Users.VerifiedUser.getMonthsPayed()));
    }
    public void getCharacterSlot() {
        CharacterSlots.setText(valueOf(Users.VerifiedUser.getCharacterSlots()));
    }

    public void getIban() {
        iban.setText(valueOf(Users.VerifiedUser.getIban()));
    }

    public void getFunds() {
        Funds.setText(valueOf(Users.VerifiedUser.getBalance()));
    }

    public void addSlots(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Add characterslot");
        alert.setHeaderText("Confirmation characterslot");
        alert.setContentText("Are you sure you want to buy a characterslot? $1.00 will be deducted from your funds.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            EntityManager em = factory.createEntityManager();
            EntityTransaction updateTransaction = em.getTransaction();
            updateTransaction.begin();
            Query q = em.createQuery("UPDATE Users u SET u.balance = :newBalance,u.characterSlots = :newSlotsAmount WHERE u.userName = :login ");
            q.setParameter("login", Users.VerifiedUser.getUserName());
            int newBalance = Users.VerifiedUser.getBalance() - 1;
            int newSlots = Users.VerifiedUser.getCharacterSlots() + 1;
            Users.VerifiedUser.setBalance(newBalance);
            Users.VerifiedUser.setCharacterSlots(newSlots);
            q.setParameter("newBalance", Users.VerifiedUser.getBalance());
            q.setParameter("newSlotsAmount", Users.VerifiedUser.getCharacterSlots());
            q.executeUpdate();
            updateTransaction.commit();

            getFunds();
            getCharacterSlot();
            System.out.println("hallo");

        } else {
            System.out.println("The user chose cancel or close the alertbox");
        }
    }

    public void addSub(ActionEvent event) {
        switch (SubsciptionAmount.getSelectionModel().getSelectedIndex()) {
            case 0:
                SetLastPaymentDate();
                UpdateMonthsPayed(1);
                break;
            case 1:
                SetLastPaymentDate();
                UpdateMonthsPayed(2);
                break;
            case 2:
                SetLastPaymentDate();
                UpdateMonthsPayed(3);
                break;
            case 3:
                SetLastPaymentDate();
                UpdateMonthsPayed(12);
                break;
        }
    }

    public void SetLastPaymentDate() {
        Date date = new Date();
        Users.VerifiedUser.setLastPayment(date);
        EntityManager em = factory.createEntityManager();
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query q = em.createQuery("UPDATE Users u SET u.lastPayment = :newDate WHERE u.userName = :login ");
        q.setParameter("login", Users.VerifiedUser.getUserName());
        q.setParameter("newDate", Users.VerifiedUser.getLastPayment());
        q.executeUpdate();
        updateTransaction.commit();
    }

    public void UpdateMonthsPayed(int amount) {
        int currentMonths = Users.VerifiedUser.getMonthsPayed();
        int NewAmountMonths = currentMonths + amount;
        int currentBalance = Users.VerifiedUser.getBalance();
        int SubscriptionCost = amount * 5;

        if (currentBalance >= SubscriptionCost) {
            int NewBalance = currentBalance - amount * 5;
            Users.VerifiedUser.setMonthsPayed(NewAmountMonths);
            Users.VerifiedUser.setBalance(NewBalance);
            EntityManager em = factory.createEntityManager();
            EntityTransaction updateTransaction = em.getTransaction();
            updateTransaction.begin();
            Query q = em.createQuery("UPDATE Users u SET u.monthsPayed = :newAmount, u.balance = :newBalance WHERE u.userName = :login ");
            q.setParameter("login", Users.VerifiedUser.getUserName());
            q.setParameter("newAmount", Users.VerifiedUser.getMonthsPayed());
            q.setParameter("newBalance", Users.VerifiedUser.getBalance());
            q.executeUpdate();
            updateTransaction.commit();
            getFunds();
            getMonthsLeft();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Balance too low");
            alert.setHeaderText(null);
            alert.setContentText("Your balance is too low, please upgrade your balance!");

            alert.showAndWait();
        }
    }
    public void addFunds(ActionEvent event){
        int currentBalance = Users.VerifiedUser.getBalance();
        int fundsToAdd = Integer.parseInt(FundsAmount.getText());
        int newBalance = currentBalance + fundsToAdd;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Add funds");
        alert.setHeaderText("Confirmation funds");
        alert.setContentText("Are you sure you want to add " + euro + "" + fundsToAdd + " to your account?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Users.VerifiedUser.setBalance(newBalance);
            EntityManager em = factory.createEntityManager();
            EntityTransaction updateTransaction = em.getTransaction();
            updateTransaction.begin();
            Query q = em.createQuery("UPDATE Users u SET u.balance = :newBalance WHERE u.userName = :login ");
            q.setParameter("login", Users.VerifiedUser.getUserName());
            q.setParameter("newBalance", Users.VerifiedUser.getBalance());
            q.executeUpdate();
            updateTransaction.commit();
            getFunds();
        } else {
            System.out.println("The user chose cancel or close the alertbox");
        }
    }
    public void GoToCharacterManagement(){
        mainContainer.loadScreen(ScreensFramework.screen4ID, ScreensFramework.screen4File);
        myController.setScreen(ScreensFramework.screen4ID);
    }
}
