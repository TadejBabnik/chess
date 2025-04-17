package com.game;

import com.game.piece.Piece;

import java.util.ArrayList;

import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

import com.game.piece.*;

/**
 * JavaFX App
 */
public class App extends Application {
    volatile static int n = 0;
    static boolean flip = false;
    static ArrayList<Pos> lm = null;
    static Piece selected = null;
    static boolean move = true;
    volatile static Board b = null;
    static Plane[] tiles = null;
    static boolean blocked = false;
    static Player p1 = null, p2 = null;
    //static boolean select = false;
    volatile static int[] mv = null;
    static volatile boolean end = false;
    volatile static StackPane root = null;

    @Override
    public void start(Stage stage) {
        root = new StackPane();
        GridPane gp = new GridPane();
        root.getChildren().add(gp);
        gp.setPadding(new Insets(0, 0, 0, 0));
        gp.setMinSize(800, 800);
        tiles = new Plane[64];
        for (int i = 0; i < 64; i++) {
            gp.add((tiles[i] = new Plane(i)), (int) (i / 8), i % 8);
            tiles[i].setMinSize(100, 100);
            if ((i + (int) (i / 8)) % 2 == 0)
                tiles[i].setStyle(
                        "-fx-background-color:rgba(0, 183, 255, 0.5);  -fx-background-color:rgb(255, 200, 200);");
            else
                tiles[i].setStyle(
                        "-fx-background-color:rgba(0, 183, 255, 0.5);  -fx-background-color:rgba(119, 29, 1, 0.5);");
            tiles[i].setOnMouseEntered((e) -> {
                Plane p = (Plane) e.getSource();
                // String[] t = p.getStyle().split(" ");
                // p.setStyle(t[1]+ " " +t[0]);
                n = p.i;
            });
            tiles[i].setOnMouseExited((e) -> {
                // Plane p = (Plane)e.getSource();
                // String[] t = p.getStyle().split(" ");
                // p.setStyle(t[1]+ " " +t[0]);
            });
        }

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("MyChessApp");
        stage.setScene(scene);
        stage.show();

        scene.setOnMousePressed((e) -> {
            {
                if (blocked)
                    return;

                if (flip) {
                    if (lm != null)
                        for (Pos p : lm) {
                            String[] t = tiles[7 + p.x * 8 - p.y].getStyle().split("  ");
                            tiles[7 + p.x * 8 - p.y].setStyle(t[1] + "  " + t[0]);
                        }
                    lm = null;

                    if (selected != null) {
                        if (b.board[selected.x][selected.y] != null)
                        if (b.board[selected.x][selected.y] instanceof Pawn && ((63 - n) % 8 == 0 || (63 - n) % 8 == 7)
                                && b.board[selected.x][selected.y].move((int) (n / 8), (63 - n) % 8, false)) {
                            promotion((int) (n / 8), (63 - n) % 8, root, selected.x, selected.y);
                            return;
                        } else if (b.board[selected.x][selected.y].move((int) (n / 8), (63 - n) % 8, false)) {
                            mv = new int[]{selected.x, selected.y, (int) (n / 8), (63 - n) % 8};
                            return;
                        }
                    }

                    if (b.board[(int) (n / 8)][(63 - n) % 8] != null) {
                        lm = b.board[(int) (n / 8)][(63 - n) % 8].getLegalMoves();
                        if (lm != null)
                            for (Pos p : lm) {
                                String[] t = tiles[7 + p.x * 8 - p.y].getStyle().split("  ");
                                tiles[7 + p.x * 8 - p.y].setStyle(t[1] + "  " + t[0]);
                            }
                    }
                    selected = b.board[(int) (n / 8)][(63 - n) % 8];
                } else {
                    if (lm != null)
                        for (Pos p : lm) {
                            String[] t = tiles[56 - p.x * 8 + p.y].getStyle().split("  ");
                            tiles[56 - p.x * 8 + p.y].setStyle(t[1] + "  " + t[0]);
                        }
                    lm = null;

                    if (selected != null) {
                        if (b.board[selected.x][selected.y] != null)
                        if (b.board[selected.x][selected.y] instanceof Pawn && ((n) % 8 == 0 || (n) % 8 == 7)
                                && b.board[selected.x][selected.y].move((int) ((63 - n) / 8), (n) % 8, false)) {
                            promotion((int) ((63 - n) / 8), (n) % 8, root, selected.x, selected.y);
                            return;
                        } else if (b.board[selected.x][selected.y].move((int) ((63 - n) / 8), (n) % 8, false)) {
                            mv = new int[]{selected.x, selected.y, (int) ((63 - n) / 8), (n) % 8};
                            return;
                        }
                    }

                    if (b.board[(int) ((63 - n) / 8)][(n) % 8] != null) {
                        lm = b.board[(int) ((63 - n) / 8)][(n) % 8].getLegalMoves();
                        if (lm != null)
                            for (Pos p : lm) {
                                String[] t = tiles[56 - p.x * 8 + p.y].getStyle().split("  ");
                                tiles[56 - p.x * 8 + p.y].setStyle(t[1] + "  " + t[0]);
                                //int prev = 56 - p.x * 8 + p.y;
                            }
                    }
                    selected = b.board[(int) ((63 - n) / 8)][(n) % 8];
                }
            }
        });

        b = newGame();

        refresh();
    }

    public static void refresh() {
        if (flip)
            for (int i = 0; i < 64; i++) {
                tiles[i].getChildren().clear();
                if (b.board[(int) (i / 8)][(63 - i) % 8] != null) {
                    ImageView iv = new ImageView();
                    iv.setImage(b.board[(int) (i / 8)][(63 - i) % 8].img);
                    iv.setFitWidth(100);
                    iv.setFitHeight(100);
                    iv.setPreserveRatio(true);

                    tiles[i].getChildren().add(iv);
                }

            }
        else
            for (int i = 0; i < 64; i++) {
                tiles[i].getChildren().clear();
                if (b.board[(int) ((63 - i) / 8)][(i) % 8] != null) {
                    ImageView iv = new ImageView();
                    iv.setImage(b.board[(int) ((63 - i) / 8)][(i) % 8].img);
                    iv.setFitWidth(100);
                    iv.setFitHeight(100);
                    iv.setPreserveRatio(true);

                    tiles[i].getChildren().add(iv);
                }

            }
    }

    static void endGame() {
        blocked = true;
        StackPane p = new StackPane();
        VBox hBox = new VBox();
        Button playAgain = new Button("PLAY AGAIN");
        playAgain.setFont(new Font(30));
        playAgain.setTranslateX(50);
        playAgain.setTranslateY(150);
        playAgain.setPrefWidth(300);
        playAgain.setOnAction((e) -> {
            p.setVisible(false);
            newGame();
        });
        hBox.getChildren().add(playAgain);
        Label r = new Label(b.result);
        r.setStyle("-fx-background-color:rgb(255, 0, 43);");
        r.setTranslateX(50);
        r.setFont(new Font(30));
        hBox.getChildren().add(r);
        hBox.setMaxHeight(400);
        hBox.setMaxWidth(400);
        hBox.setStyle("-fx-background-color:rgb(2, 29, 39);");
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        p.getChildren().add(hBox);
        root.getChildren().add(p);
        p.setPrefHeight(800);
        p.setPrefWidth(800);
        p.setStyle("-fx-background-color:rgba(2, 29, 39, 0.5);");
        p.setVisible(true);
    }

    static void promotion(int x, int y, StackPane root, int tx, int ty) {
        blocked = true;
        GridPane m = new GridPane();
        m.setPadding(new Insets(0, 0, 0, 0));
        m.setMinSize(100, 400);
        String[] t = null;
        if (flip)
            t = new String[] { "pieces/white_queen.png", "pieces/white_knight.png", "pieces/white_rook.png",
                    "pieces/white_bishop.png" };
        else
            t = new String[] { "pieces/black_queen.png", "pieces/black_knight.png", "pieces/black_rook.png",
                    "pieces/black_bishop.png" };
        for (int i = 0; i < 4; i++) {
            Planet p = new Planet();
            p.setMinSize(100, 100);
            p.setStyle("-fx-background-color:rgb(255, 255, 255);");
            p.getChildren().add(new ImageView(Helper.loadImage(t[i], 100, 100)));
            m.add(p, 0, i);
            final int j = i;
            p.setOnMouseClicked((e) -> {
                if (((Planet) e.getSource()).in) {
                    p1.c = j;
                    if (b.board[tx][ty].move(x, y, false)) {
                        mv = new int[]{tx, ty, x, y};
                    }
                }
                blocked = false;
                root.getChildren().remove(m);

            });
            p.setOnMouseEntered((e) -> {
                ((Planet) e.getSource()).in = true;
            });
            p.setOnMouseExited((e) -> {
                ((Planet) e.getSource()).in = false;
            });
        }
        root.getChildren().add(m);
        if (flip)
            m.setTranslateX(100 * x);
        else {
            m.setTranslateX(100 * (7 - x));
        }
    }

    static Board newGame() {
        flip = ((int) (Math.random() + 0.5) == 0) ? true : false;
        move = flip;
        b = new Board();
        p1 = new HumanPlayer(flip, b);
        p2 = new Bot_1(!flip, b);
        b.setPlayer(p1, p2);
        refresh();
        blocked = false;
        new Thread(() -> {
            System.out.println(move + " "+ flip);
            while (!b.over && !end) {
                if (move) {
                    if(mv != null){
                    if(p1.move(mv[0], mv[1], mv[2], mv[3])){
                    Platform.runLater(()->{refresh();});
                    move = !move;
                    }
                    mv = null;
                    }
                } else {
                    p2.move();
                    Platform.runLater(()->{refresh();});
                    move = !move;
                }
            }
            Platform.runLater(() -> {endGame();});
        }).start();
        return b;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        end = true;
    }
}

class Plane extends Pane {
    public int i;

    public Plane(int i) {
        super();
        this.i = i;
    }
}

class Planet extends Pane {
    public boolean in;

    public Planet() {
        super();
        in = false;
    }
}