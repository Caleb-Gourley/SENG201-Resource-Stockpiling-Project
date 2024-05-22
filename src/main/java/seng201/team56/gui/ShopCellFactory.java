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

public class ShopCellFactory implements Callback<ListView<Purchasable>, ListCell<Purchasable>> {
    @Override
    public ListCell<Purchasable> call(ListView<Purchasable> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(Purchasable purchasable, boolean broken) {
                super.updateItem(purchasable, broken);
                if (broken || purchasable == null) {
                    setGraphic(null);
                } else if(purchasable instanceof Tower) {
                    HBox hBox = new HBox(5);
                    ImageView imageView = new ImageView(String.format("/images/tower_%s.png", ((Tower) purchasable).getRarity().toString().toLowerCase()));
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(50);
                    imageView.setFitWidth(50);
                    VBox vBox = new VBox(5);
                    Label nameLabel = new Label(((Tower) purchasable).getName());
                    nameLabel.setFont(new Font(20));
                    vBox.getChildren().addAll(
                            nameLabel,
                            new Label(purchasable.getDescription()),
                            new Label("Price: " + purchasable.getBuyPrice())
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
