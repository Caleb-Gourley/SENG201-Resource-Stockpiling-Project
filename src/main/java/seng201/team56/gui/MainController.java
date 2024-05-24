package seng201.team56.gui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import seng201.team56.services.util.TowerButtonListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
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
    @FXML
    private VBox resultBox;
    @FXML
    private Button sellItemButton;
    private int selectedTower = -1;
    private final GameEnvironment gameEnvironment;
    private final RoundService roundService;
    private final ShopService shopService;
    private List<Button> fieldButtons;
    private List<Button> resButtons;
    private List<Label> statusLabels;
    private Button selectedButton;
    private Upgrade<?> selectedUpgrade;
    private Tower[] towers;
    private Tower[] resTowers;


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
        moneyLess.setVisible(false);
        moneyMore.setVisible(true);
        Inventory inventory = gameEnvironment.getPlayer().getInventory();

        fieldButtons = List.of(fieldTower1Button,fieldTower2Button,fieldTower3Button,fieldTower4Button,fieldTower5Button);
        resButtons = List.of(resTower1Button,resTower2Button,resTower3Button,resTower4Button,resTower5Button);

        updateStatusBar();
        initButtons(inventory);
        initShop(inventory);
        initUpgradeView();
    }

    private void initShop(Inventory inventory) {
        shopService.updateItems(roundService.getRoundNum());
        shopListView.setCellFactory(new ShopCellFactory());
        shopListView.setItems(shopService.getItems());
        shopListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        shopListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Purchasable>) r -> {
            buyItemButton.setOnAction(event -> {
                ListChangeListener<Tower> listener = new TowerButtonListener(resButtons);
                inventory.getTowers().addListener(listener);
                boolean success = false;
                try {
                    success = shopService.buyItem(shopListView.getSelectionModel().getSelectedIndex());
                } catch (ArrayStoreException exception) {
                    shopPopupTimer(moneyLess);
                }
                if (success) {
                    shopPopupTimer(moneyMore);
                    updateStatusBar();
                    assignButtonGraphics(resButtons);
                } else {
                    shopPopupTimer(moneyLess);
                }
                inventory.getTowers().removeListener(listener);
            });
        });
        sellItemButton.setOnAction(event -> {
            if (selectedButton != null && selectedButton.getUserData() != null && resButtons.contains(selectedButton)) {
                inventory.getTowers().remove((Tower) selectedButton.getUserData());
                shopService.sellItem((Purchasable) selectedButton.getUserData());
                selectedButton.setUserData(null);
                assignButtonGraphics(resButtons);
                updateStatusBar();
                selectedButton = null;
            }
        });
    }

    private void initButtons(Inventory inventory) {
        assignButtons(fieldButtons, inventory.getFieldTowers());
        assignButtons(resButtons, inventory.getTowers());
        assignButtonGraphics(fieldButtons);
        assignButtonGraphics(resButtons);
        assignButtonActions(fieldButtons, inventory);
        assignButtonActions(resButtons, inventory);
    }

    private void initUpgradeView() {
        upgradesView.setCellFactory(new UpgradeCellFactory());
        upgradesView.setItems(gameEnvironment.getPlayer().getInventory().getUpgrades());
        upgradesView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        upgradesView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Upgrade<?>>) s -> {
            selectedUpgrade = upgradesView.getSelectionModel().getSelectedItem();
        });
    }

    /**
     * Assigns each tower in towers to a button in buttonList using button.setUserData(tower).
     * @param buttonList the list of buttons to assign
     * @param towers the list of towers to assign
     */
    private void assignButtons(List<Button> buttonList, ObservableList<? extends Tower> towers) {

        for (int i = 0; i < towers.size(); i++) {
            buttonList.get(i).setUserData(towers.get(i));
            towers.get(i).addListener(this);
        }
    }

    /**
     * Assigns images to each of the tower buttons.
     * @param buttonList the list of buttons to assign
     */
    private void assignButtonGraphics(List<Button> buttonList) {
        for (Button button: buttonList) {
            assignButtonGraphic(button);
        }
    }

    /**
     * Assign the graphic of the button to its assigned tower.
     * @param button the button to set the graphic for
     */
    private void assignButtonGraphic(Button button) {
        Tower tower = (Tower) button.getUserData();
        button.setStyle("");
        if (tower != null) {
            ImageView image = new ImageView();
            if (tower.isBroken()) image.setImage(new Image("/images/tower_broken.png"));
            else image.setImage(new Image(String.format("/images/tower_%s.png", tower.getRarity().toString().toLowerCase())));
            button.setGraphic(image);
            button.setTooltip(new Tooltip(tower.getDescription()));
            button.setText(tower.getName());
        } else {
            button.setText("Empty");
            button.setGraphic(null);
            button.setTooltip(null);
        }
    }

    /**
     * Assigns actions to each of the tower buttons. The action of each tower button (i.e. a button with a tower assigned)
     * is to move the tower to whichever button in the opposing group the user selects next. This change is reflected by
     * moving the tower from one inventory group to another.
     *
     * @param buttonList      the list of buttons to assign actions to
     * @param inventory       the inventory object the towers belong to
     */
    private void assignButtonActions(List<Button> buttonList, Inventory inventory) {
        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            button.setOnAction(event -> {
                if (selectedUpgrade != null) {
                    Tower tower = (Tower) button.getUserData();
                    if (inventory.getFieldTowers().contains(tower)) {
                        inventory.getFieldTowers().remove(tower);
                        tower = selectedUpgrade.applyUpgrade(tower);
                        inventory.addFieldTower(tower);
                    } else if (inventory.getTowers().contains(tower)) {
                        inventory.getTowers().remove(tower);
                        tower = selectedUpgrade.applyUpgrade(tower);
                        inventory.addTower(tower);
                    }
                    inventory.getUpgrades().remove(selectedUpgrade);
                    button.setUserData(tower);
                    assignButtonGraphic(button);
                    upgradesView.getSelectionModel().clearSelection();
                    selectedUpgrade = null;
                } else if (selectedButton == null) {
                    selectedButton = button;
                    button.setStyle("-fx-background-color: #2fd094");
                } else if (!buttonList.contains(selectedButton)) {
                    Tower tower = (Tower) button.getUserData();
                    inventory.moveTower((Tower) selectedButton.getUserData());
                    button.setUserData(selectedButton.getUserData());
                    selectedButton.setUserData(tower);
                    assignButtonGraphic(button);
                    assignButtonGraphic(selectedButton);
                    selectedButton = null;
                } else {
                    assignButtonGraphic(selectedButton);
                    selectedButton = button;
                    button.setStyle("-fx-background-color: #2fd094");
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
            if (roundService.isRoundRunning()) {
                createStatus();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createStatus() {
        statusLabels = new ArrayList<>();
        for (Cart cart: roundService.getCurrentRound().getCarts()) {
            Label cartLabel = new Label(String.format("%s cart:\n\tdistance: %.2f\n\tamount/full: %d/%d", cart.getResourceType(),cart.getDistance(),cart.getResourceAmount(),
                    cart.getSize()));
            statusLabels.add(cartLabel);
        }
        resultBox.getChildren().clear();
        resultBox.getChildren().addAll(statusLabels);
    }

    private void updateStatus() {
        for (int i = 0; i < roundService.getCurrentRound().getCarts().size(); i++) {
            Cart cart = roundService.getCurrentRound().getCarts().get(i);
            statusLabels.get(i).setText(String.format("%s cart:\n\tdistance: %.2f\n\tamount/full: %d/%d", cart.getResourceType(),cart.getDistance(),cart.getResourceAmount(),
                    cart.getSize()));
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
        } else if (roundService.getRoundDifficulty() == null) {
            messageLabel.setText("Please select a round difficulty!");
            messageLabel.setTextFill(Color.web("#c22622"));
            shopPopupTimer(messageLabel);
        } else {
            roundService.createRound();
            for (Cart cart: roundService.getCurrentRound().getCarts()) {
                cart.addDistanceListener(this);
            }
            roundService.playRound();
            createStatus();
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
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Round Summary");
        dialog.setHeaderText("Round Summary");

        VBox dialogContent = new VBox(10);
        String winOrLose;
        if (roundService.isRoundWon()) {
            winOrLose = "Round won!";
        } else {
            winOrLose = "Round lost!";
        }
        Label roundStatus = new Label(winOrLose);
        Label roundMonetaryGain = new Label(String.format("$%d", roundService.getRoundDifficulty().monetaryReward()));
        Label roundFieldTower1Xp = new Label();
        Label roundFieldTower2Xp = new Label();
        Label roundFieldTower3Xp = new Label();
        Label roundFieldTower4Xp = new Label();
        Label roundFieldTower5Xp = new Label();
        String randomEvent = roundService.getRandomEvent();
        Label randomEventLabel = new Label();
        if (randomEvent != null) {
            randomEventLabel.setText(randomEvent);
        }
        List<Label> towerLabels = List.of(roundFieldTower1Xp,roundFieldTower2Xp,roundFieldTower3Xp,roundFieldTower4Xp,roundFieldTower5Xp);
        for (int i = 0; i < gameEnvironment.getPlayer().getInventory().getFieldTowers().size(); i++) {
            Tower tower = gameEnvironment.getPlayer().getInventory().getFieldTowers().get(i);
            towerLabels.get(i).setText(String.format("Xp (tower %s): %d", tower.getName(),
                    roundService.getRoundDifficulty().xpReward() * tower.getUseCount()));
        }
        if (roundService.isRoundWon()) dialogContent.getChildren().addAll(roundStatus, roundMonetaryGain, roundFieldTower1Xp, roundFieldTower2Xp, roundFieldTower3Xp, roundFieldTower4Xp, roundFieldTower5Xp,randomEventLabel);
        else dialogContent.getChildren().addAll(roundStatus, randomEventLabel);

        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                System.out.println("Clicked OK");
                assignButtonGraphics(fieldButtons);
                assignButtonGraphics(resButtons);
            }
        });

    }

    private void updateStatusBar() {
        coinsLabel.setText(String.format("$%d", gameEnvironment.getPlayer().getMoney()));
        roundNumLabel.setText(String.format("%d/%d", roundService.getRoundNum(), gameEnvironment.getPlayer().getMaxRounds()));
        difficultyLabel.setText(gameEnvironment.getPlayer().getDifficulty().toString());
        nameLabel.setText(gameEnvironment.getPlayer().getName());
    }

    private void towerLevelUp(Tower tower) {
        messageLabel.setText(String.format("Tower %s levelled up!", tower.getName()));
        shopPopupTimer(messageLabel);
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
            Platform.runLater(this::checkGameEnd);
            Platform.runLater(this::updateStatusBar);

        } else if (evt.getSource() instanceof Tower tower) {
            if (evt.getPropertyName().equals("level")) {
                Platform.runLater(() -> towerLevelUp(tower));
            }
        } else if (evt.getSource() instanceof Cart) {
                if (evt.getPropertyName().equals("distance")) {
                    Platform.runLater(this::updateStatus);
                }
            }
    }

    private void checkGameEnd() {
        boolean gameWon = gameEnvironment.getPlayer().getXp() <= 0;
        boolean gameLost = roundService.getRoundNum() > gameEnvironment.getPlayer().getMaxRounds();
        if (gameWon || gameLost) {
            Dialog<ButtonType> dialog = buttonTypeDialog(gameWon, gameLost);
            VBox dialogContent = new VBox(1);
            Label playAgainLabel = new Label("Play again?");
            dialogContent.getChildren().add(playAgainLabel);
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

            dialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    gameEnvironment.closeMainScreen();
                    gameEnvironment.launchSetupScreen();
                } else if (buttonType == ButtonType.NO) {
                    gameEnvironment.closeMainScreen();
                }
            });
        }
    }

    private Dialog<ButtonType> buttonTypeDialog(boolean gameWon, boolean gameLost) {
        String titleText = "";
        String headerText = "";
        if (gameWon) {
            titleText = "You lost the game!";
            headerText = "You lose!";
        } else if (gameLost) {
            titleText = "You won the game!";
            headerText = "You win!";
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(titleText);
        dialog.setHeaderText(headerText);
        return dialog;
    }


}
