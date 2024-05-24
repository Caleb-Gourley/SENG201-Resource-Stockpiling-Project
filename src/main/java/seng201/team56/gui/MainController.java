package seng201.team56.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng201.team56.GameEnvironment;
import seng201.team56.models.*;
import seng201.team56.models.upgrades.Upgrade;
import seng201.team56.services.RoundService;
import seng201.team56.services.ShopService;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

/**
 * Controller for the main.fxml window
 * @author Sean Reitsma
 */
public class MainController implements PropertyChangeListener {
    @FXML
    private Button resTower1Button;
    @FXML
    private Button resTower2Button;
    @FXML
    private Button resTower3Button;
    @FXML
    private Button resTower4Button;
    @FXML
    private Button resTower5Button;
    @FXML
    private Button fieldTower1Button;
    @FXML
    private Button fieldTower2Button;
    @FXML
    private Button fieldTower3Button;
    @FXML
    private Button fieldTower4Button;
    @FXML
    private Button fieldTower5Button;
    @FXML
    private Button popupButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label coinsLabel;
    @FXML
    private Label roundNumLabel;
    @FXML
    private Label difficultyLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private Polyline trackLine;
    @FXML
    private ListView<Upgrade<?>> upgradesView;
    @FXML
    private ListView<Purchasable> shopListView;
    @FXML
    private Button buyItemButton;
    @FXML
    private Label moneyLess;
    @FXML
    private Label moneyMore;
    private int selectedTower = -1;
    private final GameEnvironment gameEnvironment;
    private final RoundService roundService;
    private final ShopService shopService;
    private List<Button> fieldButtons;
    private List<Button> resButtons;
    private Button selectedButton;

    /**
     * Construct a MainController, initialises a new {@link RoundService} and {@link ShopService}.
     * @param gameEnvironment the {@link GameEnvironment} object in charge of the game state
     */
    public MainController(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
        this.shopService = new ShopService(this.gameEnvironment.getPlayer());
        this.roundService = new RoundService(this.gameEnvironment.getPlayer(), shopService);
    }

    /**
     * Called when the main screen is opened. Initialises the screen by setting the player info labels, button graphics,
     * and button actions.
     */
    @FXML
    public void initialize() {
        roundService.addRunningSubscriber(this);
        shopService.updateItems(1);
        shopListView.setCellFactory(new ShopCellFactory());
       // shopListView.setItems(FXCollections.observableArrayList(shopService.getItems()));
        shopListView.setItems(shopService.getItems());
        moneyLess.setVisible(false);
        moneyMore.setVisible(true);

        fieldButtons = List.of(fieldTower1Button,fieldTower2Button,fieldTower3Button,fieldTower4Button,fieldTower5Button);
        resButtons = List.of(resTower1Button,resTower2Button,resTower3Button,resTower4Button,resTower5Button);
        nameLabel.setText(gameEnvironment.getPlayer().getName());
        coinsLabel.setText(String.format("$%d", gameEnvironment.getPlayer().getMoney()));
        roundNumLabel.setText(String.format("%d/%d", roundService.getRoundNum(), gameEnvironment.getPlayer().getMaxRounds()));
        upgradesView.setCellFactory(new UpgradeCellFactory());
        upgradesView.setItems(gameEnvironment.getPlayer().getInventory().getUpgrades());
        upgradesView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        difficultyLabel.setText(gameEnvironment.getPlayer().getDifficulty().toString());
        Tower[] resTowers = new Tower[5];
        Tower[] fieldTowers = new Tower[5];
        Inventory inventory = gameEnvironment.getPlayer().getInventory();
        for (int i = 0; i < inventory.getTowers().size(); i++) {
            resTowers[i] = inventory.getTowers().get(i);
        }
        for (int i = 0; i < inventory.getFieldTowers().size(); i++) {
            fieldTowers[i] = inventory.getFieldTowers().get(i);
        }
        assignButtonGraphics(fieldButtons, fieldTowers);
        assignButtonGraphics(resButtons, resTowers);
        assignButtonActions(fieldButtons, fieldTowers, resTowers, resButtons, inventory);
        assignButtonActions(resButtons, resTowers, fieldTowers, fieldButtons, inventory);

        shopListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        shopListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Purchasable>) r -> {
            System.out.println("Action " + r);
            System.out.println("Current Selection: " + shopListView.getSelectionModel().getSelectedItem());
            buyItemButton.setOnAction(event -> {
                boolean success = false;
                try {
                    success = shopService.buyItem(shopListView.getSelectionModel().getSelectedIndex());
                } catch (ArrayStoreException exception) {
                    shopPopupTimer(moneyLess);
                }
                if (success) {
                    shopPopupTimer(moneyMore);
                    //shopListView.setItems(FXCollections.observableArrayList(shopService.getItems()));
                    coinsLabel.setText(String.format("$%d", gameEnvironment.getPlayer().getMoney()));
                    Tower[] towers = inventory.getTowers().toArray(new Tower[5]);
                    assignButtonGraphics(resButtons, towers);
                    assignButtonActions(resButtons, towers, fieldTowers, fieldButtons, inventory);
                    assignButtonActions(fieldButtons, fieldTowers, towers, resButtons, inventory);
                } else {
                    shopPopupTimer(moneyLess);
                }
            });
        });



    }

    /**
     * Assigns images to each of the tower buttons. The tower at index i in towers
     * should be assigned to the button at index i in buttonList.
     * @param buttonList the list of buttons to assign
     * @param towers the array of towers
     */
    private void assignButtonGraphics(List<Button> buttonList,Tower[] towers) {
        for (int i = 0; i < towers.length && towers[i] != null; i++) {
            Tower tower = towers[i];
            ImageView image = new ImageView(String.format("/images/tower_%s.png", tower.getRarity().toString().toLowerCase()));
            buttonList.get(i).setGraphic(image);
            buttonList.get(i).setTooltip(new Tooltip(tower.getDescription()));
            buttonList.get(i).setText(tower.getName());
        }
    }

    /**
     * Assigns actions to each of the tower buttons. The action of each tower button (i.e. a button with a tower assigned)
     * is to move the tower to whichever button in the opposing group the user selects next. This change is reflected by
     * moving the tower from one inventory group to another.
     *
     * @param buttonList      the list of buttons to assign actions to
     * @param towers          the list of towers to which the buttons in buttonsList are assigned
     * @param otherTowers     the opposing list of towers
     * @param otherButtonList the opposing list of buttons
     * @param inventory       the inventory object the towers belong to
     */
    private void assignButtonActions(List<Button> buttonList, Tower[] towers, Tower[] otherTowers, List<Button> otherButtonList, Inventory inventory) {
        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            int finalI = i;
            button.setOnAction(event -> {
                if (selectedTower == -1) {
                    if (towers[finalI] != null) {
                        button.setStyle("-fx-background-color: #2fd094");
                        selectedButton = (Button) event.getSource();
                        selectedTower = finalI;
                    }
                } else if ((selectedButton == null || !buttonList.contains(selectedButton)) && otherTowers[selectedTower] != null) {
                    Tower tower = otherTowers[selectedTower];
                    Button otherButton = otherButtonList.get(selectedTower);
                    button.setStyle("");
                    otherButton.setStyle("");
                    otherTowers[selectedTower] = towers[finalI];
                    towers[finalI] = tower;
                    Node otherButtonGraphic = otherButton.getGraphic();
                    otherButton.setGraphic(button.getGraphic());
                    otherButton.setText(button.getText());
                    button.setGraphic(otherButtonGraphic);
                    button.setText(tower.getName());
                    Tooltip otherButtonTooltip = otherButton.getTooltip();
                    otherButton.setTooltip(button.getTooltip());
                    button.setTooltip(otherButtonTooltip);
                    selectedTower = -1;
                    try {
                        inventory.moveTower(tower);
                    } catch (ArrayStoreException exception) {
                        messageLabel.setVisible(true);
                        messageLabel.setText(exception.getMessage());
                    }
                }
            });
        }
    }
    /**
     * Shows a popup to let the player select a difficulty for the upcoming round.
     */
    @FXML
    public void showDiffSelection() {
        try {
            FXMLLoader newStageLoader = new FXMLLoader(getClass().getResource("/fxml/TowerEatsRoundDiff.fxml"));
            newStageLoader.setControllerFactory(param -> new RoundDiffPopupController(roundService));
            AnchorPane root = newStageLoader.load();
            Scene modalScene = new Scene(root);
            Stage modalStage = new Stage();
            modalStage.setScene(modalScene);
            modalStage.setWidth(600);
            modalStage.setHeight(400);
            modalStage.setResizable(false);
            modalStage.setTitle("Set Round Difficulty");
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(popupButton.getScene().getWindow());
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler for the play round button.
     */
    @FXML
    public void playRound() {
        if(gameEnvironment.getPlayer().getInventory().getFieldTowers().isEmpty()) {
            messageLabel.setText("No towers on the field!");
            messageLabel.setTextFill(Color.web("#c22622"));
            shopPopupTimer(messageLabel);
        } else {
            roundService.createRound();
            roundService.playRound();
        }
    }

    public void shopPopupTimer(Label labelPopup) {
        Platform.runLater(() -> labelPopup.setVisible(true));

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> labelPopup.setVisible(false));
        }).start();
    }

    @FXML
    private void openRoundSummaryPopup() {
        System.out.println("??");
        Dialog<ButtonType> dialog = new Dialog<>();
        System.out.println("Hello!");
        dialog.setTitle("Round Summary");
        dialog.setHeaderText("Round Summary");

        VBox dialogContent = new VBox(10);
        Label roundStatus = new Label("Win or Loss");
        Label roundMonetaryGain = new Label("Money");
        Label roundFieldTower1Xp = new Label("Xp");
        Label roundFieldTower2Xp = new Label("Xp");
        Label roundFieldTower3Xp = new Label("Xp");
        Label roundFieldTower4Xp = new Label("Xp");
        Label roundFieldTower5Xp = new Label("Xp");
        dialogContent.getChildren().addAll(roundStatus, roundMonetaryGain, roundFieldTower1Xp, roundFieldTower2Xp, roundFieldTower3Xp, roundFieldTower4Xp, roundFieldTower5Xp);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                System.out.println("Clicked OK");
            }
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() instanceof RoundService
        && evt.getPropertyName().equals("roundRunning")
        && evt.getOldValue() instanceof Boolean oldValue
        && evt.getNewValue() instanceof Boolean newValue
        && oldValue && !newValue) {
            System.out.println("Popup!");
            Platform.runLater(this::openRoundSummaryPopup);

        }

    }
}
