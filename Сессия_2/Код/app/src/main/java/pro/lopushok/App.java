package pro.lopushok;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Lopushok");

        try {
            Image png = new Image(App.class.getResourceAsStream("/Лопушок.png"));
            stage.getIcons().add(png);
        } catch (Exception ignored) {}
        try {
            Image ico = new Image(App.class.getResourceAsStream("/Лопушок.ico"));
            stage.getIcons().add(ico);
        } catch (Exception ignored) {}
        if (stage.getIcons().isEmpty()) {
            try { stage.getIcons().add(new Image(App.class.getResourceAsStream("/picture.png"))); } catch (Exception ignored) {}
        }

        stage.setMinWidth(1000);
        stage.setMinHeight(640);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}
