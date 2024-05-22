package seng201.team56.gui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;
import seng201.team56.GameEnvironment;
import seng201.team56.models.Inventory;
import seng201.team56.models.Purchasable;
import seng201.team56.models.Tower;
import seng201.team56.models.Upgrade;
import seng201.team56.services.RoundService;
import seng201.team56.services.ShopService;

import java.util.List;

/**
 * Controller for the main.fxml window
 * @author Sean Reitsma
 */
public class MainController {
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
    private ListView<Upgrade> upgradesView;
    @FXML
    private ListView<Purchasable> shopListView;
    private int selectedTower = -1;
    private final GameEnvironment gameEnvironment;
    private final RoundService roundService;
    private final ShopService shopService;

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
        shopService.updateItems(1);
        shopListView.setCellFactory(new ShopCellFactory());
        shopListView.setItems(FXCollections.observableArrayList(shopService.getItems()));

        shopListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        shopListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Purchasable>) r -> {
            System.out.println("Action " + r);
            System.out.println("Current Selection: " + shopListView.getSelectionModel().getSelectedItem());
        });

        nameLabel.setText(gameEnvironment.getPlayer().getName());
        coinsLabel.setText(String.format("$%d", gameEnvironment.getPlayer().getMoney()));
        roundNumLabel.setText(String.format("%d/%d", roundService.getRoundNum(), gameEnvironment.getPlayer().getMaxRounds()));
        upgradesView.setCellFactory(new UpgradeCellFactory());
        upgradesView.setItems(FXCollections.observableArrayList(gameEnvironment.getPlayer().getInventory().getUpgrades()));
        difficultyLabel.setText(gameEnvironment.getPlayer().getDifficulty().toString());
        List<Button> fieldButtons = List.of(fieldTower1Button,fieldTower2Button,fieldTower3Button,fieldTower4Button,fieldTower5Button);
        List<Button> resButtons = List.of(resTower1Button,resTower2Button,resTower3Button,resTower4Button,resTower5Button);
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
            buttonList.get(i).setContentDisplay(ContentDisplay.TOP);
            buttonList.get(i).setTooltip(new Tooltip(tower.getDescription()));
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
                    if (towers[finalI] != null) selectedTower = finalI;
                } else if (otherTowers[selectedTower] != null) {
                    Tower tower = otherTowers[selectedTower];
                    Button otherButton = otherButtonList.get(selectedTower);
                    otherTowers[selectedTower] = towers[finalI];
                    towers[finalI] = tower;
                    Node otherButtonGraphic = otherButton.getGraphic();
                    otherButton.setGraphic(button.getGraphic());
                    button.setGraphic(otherButtonGraphic);
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
}
