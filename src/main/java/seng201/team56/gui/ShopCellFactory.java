package seng201.team56.gui;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import seng201.team56.models.Purchasable;
import seng201.team56.models.Tower;
import seng201.team56.models.upgrades.Upgrade;

/**
 * A factory which creates custom cells for shop items in the shop ListView
 */
public class ShopCellFactory implements Callback<ListView<Purchasable>, ListCell<Purchasable>> {

    /**
     * Returns a custom ListCell for a shop item.
     * @param listView the ListView which contains the cells
     * @return the ListCell object
     */
    @Override
    public ListCell<Purchasable> call(ListView<Purchasable> listView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Purchasable item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5);
                    ImageView imageView = new ImageView();
                    VBox vBox = new VBox(5);

                    if (item instanceof Tower tower) {
                        imageView.setImage(new ImageView(String.format("/images/tower_%s.png", tower.getRarity().toString().toLowerCase())).getImage());
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        Label nameLabel = new Label(tower.getName());
                        nameLabel.setFont(new Font(20));
                        vBox.getChildren().addAll(
                                nameLabel,
                                new Label(tower.getDescription()),
                                new Label("Price: " + tower.getBuyPrice())
                        );
                    } else if (item instanceof Upgrade upgrade) {
                        imageView.setImage(new ImageView("/images/upgrade.png").getImage());
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        vBox.getChildren().addAll(
                                new Label(upgrade.getDescription())
                        );
                    }

                    hBox.getChildren().addAll(imageView, vBox);
                    setGraphic(hBox);
                }
            }
        };
    }
}
