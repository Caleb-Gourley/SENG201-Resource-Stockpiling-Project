package seng201.team56.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.Upgrade;

/**
 * A factory which creates custom cells for upgrades in the players inventory.
 *
 * @author Sean Reitsma
 */
public class UpgradeCellFactory implements Callback<ListView<Upgrade<?>>, ListCell<Upgrade<?>>> {

    /**
     * Returns a custom ListCell for an upgrade.
     * @param upgradeListView the ListView for the upgrades in the player's inventory
     * @return the ListCell object
     */
    @Override
    public ListCell<Upgrade<?>> call(ListView<Upgrade<?>> upgradeListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Upgrade upgrade, boolean b) {
                super.updateItem(upgrade, b);
                if (b || upgrade == null) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5);
                    ImageView imageView = new ImageView("/images/upgrade.png");
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    VBox vBox = new VBox(5);
                    vBox.getChildren().addAll(
                            new Label(upgrade.getDescription())
                    );
                    hBox.getChildren().addAll(
                            imageView,
                            vBox
                    );
                    setGraphic(hBox);
                }
            }
        };
    }
}
