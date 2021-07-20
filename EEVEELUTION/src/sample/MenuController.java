package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;


public class MenuController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    private Stage prevStage;
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
    public void setMenu(){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                toLevel1();
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                toHowToPlay();
            }
        });
    }

    public void toLevel1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
            Parent root = loader.load();
            Scene game = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(game);
            stage.setTitle("GAME");
            GameController controller=loader.getController();
            controller.start();
            controller.drawMap();
            prevStage.close();
            controller.setPrevStage(stage);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void toHowToPlay() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HowToPlay.fxml"));
            Parent root = loader.load();
            Scene howtoplay = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(howtoplay);
            stage.setTitle("HOW TO PLAY?");
            HowToPlay controller=loader.getController();
            controller.setPrevStage(stage);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
