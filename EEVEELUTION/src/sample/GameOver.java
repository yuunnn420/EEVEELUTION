package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameOver {

    @FXML
    private Label label;
    @FXML
    private TextField name;
    @FXML
    private Button OK;
    private Stage prevStage;

    public void setScore(){
        label.setText("Your score is "+GameController.scoreFinal);
        OK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                prevStage.close();
            }
        });
    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
