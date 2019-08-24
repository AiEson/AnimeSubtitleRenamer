package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutAPPController {
    @FXML
    private ImageView imageView;
    @FXML
    public void initialize()
    {
            imageView.setImage(new Image("draw/WvTLogo.png"));
    }
}
