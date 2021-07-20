package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameController extends Thread {

    public Stage prevStage;
    static final int tube = 70;
    static final int size = 8;
    @FXML
    private GridPane gridpane = new GridPane();
    @FXML
    private Label label;
    @FXML
    private ProgressBar progressBar;
    private String text = "0";
    static int[] score = new int[99999];
    static int scoreFinal = 0;
    int countScore = 0;
    Random rand = new Random();
    int[][] map = new int[size][size];
    int[][] map2 = new int[size][size];
    int[][][] map3 = new int[99999][size][size];
    int[][] map4 = new int[size][size];
    int[][] click = new int[size][size];
    Button[][] button = new Button[size][size];
    ImageView[][] imageView = new ImageView[size][size];
    Image zeroImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/yellow.png")), tube, tube, false, false);
    Image firstImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Flareon.png")), tube, tube, false, false);
    Image secondImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Vaporeon.png")), tube, tube, false, false);
    Image thirdImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Jolteon.png")), tube, tube, false, false);
    Image fourthImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Espeon.png")), tube, tube, false, false);
    Image fifthImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Umbreon.png")), tube, tube, false, false);
    Image sixthImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Leafeon.png")), tube, tube, false, false);
    Image seventhImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Eevee.png")), tube, tube, false, false);
    public int i = 0, j = 0;
    public int clickTimes = 0;

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int i1, j1, i2, j2;
    int x, y;
    boolean flag = true;

    public void printMap2() {
        for (int i = 0; i < map.length; i++) {//h
            for (int j = 0; j < map[0].length; j++) {//w
                System.out.print(map2[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printMap3() {
        System.out.println("map3 " + count3);
        for (int i = 0; i < size; i++) {//h
            for (int j = 0; j < size; j++) {//w
                System.out.print(map3[count3][i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void isZero() {
        if (map2[x][y] == 0) {
            if (x > 0) {
                x -= 1;
                isZero();
            } else if (x == 0) {
                x -= 1;
            }
        }
    }

    /*public void run() {
        while(true){
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }*/
    boolean isChange = false;
    boolean eliminate = false;
    boolean drawMap = true;
    static int count3 = 0;
    static int c = 0;
    boolean isNotText = false;
    double time = 30;
    int timeCount = 0;
    int t = 1;
    @FXML
    private Label timeLabel;

    void timer() {
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.millis(1000), new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                time -= t;
                if (time < 0.0) {
                    time = 0;
                    timeline.pause();
                    toGameOver();
                }
                timeCount += 1;
                if (timeCount == 60 / t) {
                    t += 1;
                    timeCount = 0;
                }
                String s = "TIME ";
                //timeLabel.setText(s.concat(Double.toString(time)));
                progressBar.setProgress(time / 60);
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public synchronized void anime() {
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.millis(500), new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                String s = "Score:";
                if (isNotText) {
                    scoreFinal += score[countScore];
                    label.setText(s.concat(Integer.toString(scoreFinal)));
                    countScore += 1;
                    isNotText = false;
                }
                drawMap3(c);
                c++;
                if (c == count3) {
                    count3 = 0;
                    c = 0;
                    countScore = 0;
                    Arrays.fill(score, 0);
                    timeline.pause();
                }
            }
        });
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    boolean changeOnce = false;

    public void animation() {
        flag = true;
        eliminate = false;
        if (isChange) {
            int t = map[i1][j1];
            map[i1][j1] = map[i2][j2];
            map[i2][j2] = t;
            t = map2[i1][j1];
            map2[i1][j1] = map2[i2][j2];
            map2[i2][j2] = t;
            for (int i = 0; i < size; i++) {//h
                for (int j = 0; j < size; j++) {//w
                    map4[i][j] = map2[i][j];
                }
            }
        }
        int count = 1;
        int a = -1;
        for (int i = 0; i < map.length; i++) {//h
            for (int j = 0; j < map[0].length; j++) {//w
                if (j == 0) {
                    a = map[i][j];
                    count = 1;
                } else if (a == map[i][j]) {
                    count++;
                    if (count == 3) {
                        for (int k = 0; k < count; k++) {
                            map2[i][j - k] = 0;
                            flag = false;
                            eliminate = true;
                        }
                        score[countScore] += count * 10;
                        if (time + count <= 60)
                            time += count;
                        else if (time + count > 60)
                            time = 60;
                        String s = "TIME ";
                        //timeLabel.setText(s.concat(Double.toString(time)));
                    }
                    if (count > 3) {
                        map2[i][j] = 0;
                        flag = false;
                        eliminate = true;
                        score[countScore] += (count - 3) * 10;
                        if (time + count - 3 <= 60)
                            time += count - 3;
                        else if (time + count - 3 > 60)
                            time = 60;
                        String s = "TIME ";
                        //timeLabel.setText(s.concat(Double.toString(time)));
                    }
                } else {
                    a = map[i][j];
                    count = 1;
                }
            }
        }
        a = -1;
        count = 1;
        for (int j = 0; j < map[0].length; j++) {//w
            for (int i = 0; i < map.length; i++) {//h
                if (i == 0) {
                    a = map[i][j];
                    count = 1;
                } else if (a == map[i][j]) {
                    count++;
                    if (count == 3) {
                        for (int k = 0; k < count; k++) {
                            map2[i - k][j] = 0;
                            flag = false;
                            eliminate = true;
                        }
                        score[countScore] += count * 10;
                        if (time + count <= 60)
                            time += count;
                        else if (time + count > 60)
                            time = 60;
                        String s = "TIME ";
                        //timeLabel.setText(s.concat(Double.toString(time)));
                    }
                    if (count > 3) {
                        map2[i][j] = 0;
                        flag = false;
                        eliminate = true;
                        score[countScore] += (count - 3) * 10;
                        if (time + count - 3 <= 60)
                            time += count - 3;
                        else if (time + count - 3 > 60)
                            time = 60;
                        String s = "TIME ";
                        //timeLabel.setText(s.concat(Double.toString(time)));
                    }

                } else {
                    a = map[i][j];
                    count = 1;
                }
            }
        }
        progressBar.setProgress(time / 60);
        isNotText = true;
        //System.out.println("countScore=" + countScore);
        //System.out.println("score=" + score[countScore]);
        if (isChange) {
            int t = map[i1][j1];
            map[i1][j1] = map[i2][j2];
            map[i2][j2] = t;
            isChange = false;
            if (flag) {
                t = map2[i1][j1];
                map2[i1][j1] = map2[i2][j2];
                map2[i2][j2] = t;
            }
        }
        if (flag) {
            //System.out.println("count3=" + count3);

            if (count3 != 0)
                anime();
        } else {
            if (drawMap) {
                eliminate = false;
            }
            if (flag == false && eliminate && changeOnce) {
                for (int i = 0; i < size; i++) {//h
                    for (int j = 0; j < size; j++) {//w
                        map3[count3][i][j] = map4[i][j];
                    }
                }
                //printMap3();
                count3 += 1;
                eliminate = false;
                changeOnce = false;
            }
            for (int i = 0; i < size; i++) {//h
                for (int j = 0; j < size; j++) {//w
                    map3[count3][i][j] = map2[i][j];
                }
            }
            //printMap3();
            count3 += 1;
            for (int j = 0; j < size; j++) {//w
                for (int i = size - 1; i >= 0; i--) {//h
                    if (map2[i][j] == 0) {
                        x = i;
                        y = j;
                        isZero();
                        if (x != -1) {
                            map2[i][j] = map2[x][y];
                            map2[x][y] = 0;
                        }
                    }
                }
            }
            for (int i = 0; i < map.length; i++) {//h
                for (int j = 0; j < map[0].length; j++) {//w
                    if (map2[i][j] == 0) {
                        map2[i][j] = rand.nextInt(7) + 1;//1~7
                    }
                }
            }
            for (int i = 0; i < size; i++) {//h
                for (int j = 0; j < size; j++) {//w
                    map3[count3][i][j] = map2[i][j];
                }
            }
            //printMap3();
            count3 += 1;
            for (int i = 0; i < size; i++) {//h
                for (int j = 0; j < size; j++) {//w
                    map[i][j] = map2[i][j];
                }
            }
            animation();
        }
    }

    public boolean change() {
        if (clickTimes == 2) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    button[i][j].setStyle("-fx-background-color: transparent ;-fx-border-color: white;");
                }
            }
        }
        for (int i = 0; i < map.length; i++) {//h
            for (int j = 0; j < map[0].length - 1; j++) {//w
                if (click[i][j] == 1 && click[i][j + 1] == 1) {
                    //System.out.println("change 1");
                    click[i][j] = 0;
                    click[i][j + 1] = 0;
                    i1 = i;
                    j1 = j;
                    i2 = i;
                    j2 = j + 1;
                    clickTimes = 0;
                    isChange = true;
                    eliminate = true;
                    changeOnce = true;
                    return true;
                }
            }
        }
        for (int i = 0; i < map[0].length; i++) {//w
            for (int j = 0; j < map.length - 1; j++) {//h
                if (click[j][i] == 1 && click[j + 1][i] == 1) {
                    //System.out.println("change 2");
                    click[j][i] = 0;
                    click[j + 1][i] = 0;
                    i1 = j;
                    j1 = i;
                    i2 = j + 1;
                    j2 = i;
                    clickTimes = 0;
                    isChange = true;
                    eliminate = true;
                    changeOnce = true;
                    return true;
                }
            }
        }
        if (clickTimes == 2) {
            //System.out.println("error");
            clickTimes = 0;
            for (int[] row : click)
                Arrays.fill(row, 0);
            return false;
        }
        return false;
    }

    public void action() {
        for (int i = 0; i < size; i++) {//h
            for (int j = 0; j < size; j++) {//w
                this.i = i;
                this.j = j;
                button[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    int i = getI();
                    int j = getJ();

                    @Override
                    public void handle(ActionEvent e) {
                        //button[i][j].setStyle("-fx-background-color: transparent ;-fx-border-color: blue;");
                        click[i][j] = 1;
                        clickTimes += 1;
                        if (change()) {
                            animation();
                        }
                    }
                });
            }
        }
    }

    public void drawMap() {
        for (int i = 0; i < size; i++) {//h
            for (int j = 0; j < size; j++) {//w
                map[i][j] = rand.nextInt(7) + 1;//1~7
                map2[i][j] = map[i][j];
            }
        }
        for (int i = 0; i < map.length; i++) {//h
            for (int j = 0; j < map[0].length; j++) {//w
                switch (map[i][j]) {
                    case 0:
                        imageView[i][j] = new ImageView(zeroImage);
                        break;
                    case 1:
                        imageView[i][j] = new ImageView(firstImage);
                        break;
                    case 2:
                        imageView[i][j] = new ImageView(secondImage);
                        break;
                    case 3:
                        imageView[i][j] = new ImageView(thirdImage);
                        break;
                    case 4:
                        imageView[i][j] = new ImageView(fourthImage);
                        break;
                    case 5:
                        imageView[i][j] = new ImageView(fifthImage);
                        break;
                    case 6:
                        imageView[i][j] = new ImageView(sixthImage);
                        break;
                    case 7:
                        imageView[i][j] = new ImageView(seventhImage);
                        break;
                    default:
                        break;
                }
                GridPane.setConstraints(imageView[i][j], j, i);
                gridpane.getChildren().add(imageView[i][j]);
            }
        }
        for (int i = 0; i < map.length; i++) {//h
            for (int j = 0; j < map[0].length; j++) {//w
                button[i][j] = new Button();
                button[i][j].setPrefSize(tube, tube);
                gridpane.add(button[i][j], j, i);
                button[i][j].setStyle("-fx-background-color: transparent ;-fx-border-color: white;");//rgba(255, 255, 255, .5)
            }
        }
        //progressBar.setMinWidth(60);
        progressBar.setProgress(30 / 60);
        timer();

        animation();
        drawMap = false;
        action();
    }

    public synchronized void drawMap3(int c) {
        for (int i = 0; i < size; i++) {//h
            for (int j = 0; j < size; j++) {//w
                //if (map3[count3][i][j] != map[i][j] && count3==0 || map3[count3][i][j] != map3[count3-1][i][j] && count3!=0) {
                gridpane.getChildren().remove(imageView[i][j]);
                gridpane.getChildren().remove(button[i][j]);
                switch (map3[c][i][j]) {
                    case 0:
                        imageView[i][j] = new ImageView(zeroImage);
                        break;
                    case 1:
                        imageView[i][j] = new ImageView(firstImage);
                        break;
                    case 2:
                        imageView[i][j] = new ImageView(secondImage);
                        break;
                    case 3:
                        imageView[i][j] = new ImageView(thirdImage);
                        break;
                    case 4:
                        imageView[i][j] = new ImageView(fourthImage);
                        break;
                    case 5:
                        imageView[i][j] = new ImageView(fifthImage);
                        break;
                    case 6:
                        imageView[i][j] = new ImageView(sixthImage);
                        break;
                    case 7:
                        imageView[i][j] = new ImageView(seventhImage);
                        break;
                    default:
                        break;
                }
                GridPane.setConstraints(imageView[i][j], j, i);
                gridpane.getChildren().add(imageView[i][j]);
                gridpane.add(button[i][j], j, i);
                //}*/
            }
        }
    }

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public void toGameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
            Parent root = loader.load();
            root.requestLayout();
            Scene gameover = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(gameover);
            stage.setTitle("GAMEOVER");
            GameOver controller = loader.getController();
            prevStage.close();
            controller.setScore();
            controller.setPrevStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


/*@Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView imageView = new ImageView(new Image("/image/Eevee.png"));
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("This is checkmark");
                a.show();
            }
        });
        gridpane.getChildren().add(imageView);
    }*/