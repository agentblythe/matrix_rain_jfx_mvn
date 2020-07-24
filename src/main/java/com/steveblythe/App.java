package com.steveblythe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * JavaFX App
 */
public class App extends Application {

    private final Random random = new Random();
    private final Font font = Font.loadFont(getClass().getResource("/fonts/matrix_code_nfi.otf").toExternalForm(), 15);

    private char[][] data = new char[Constants.GLYPHS_PER_ROW][Constants.GLYPHS_PER_COL];
    private Drop[] drops = new Drop[Constants.GLYPHS_PER_ROW];

    Stage primaryStage;
    Group root;

    public static void main(String[] args) {
        launch(args);
    }

    private char getRandChar() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$+-*/=%\"'#&_(),.;:?!\\|{}<>[]^~";
        return characters.charAt(Math.abs(random.nextInt() % characters.length()));
    }

    @Override
    public void start(Stage stage) {
        for (int i = 0; i < Constants.GLYPHS_PER_ROW; i++) {
            drops[i] = new Drop();
        }

        primaryStage = stage;
        primaryStage.resizableProperty().setValue(false);

        root = new Group();
        Scene scene = new Scene(root, 600, 445);

        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(font);

        for (int i = 0; i < Constants.GLYPHS_PER_ROW; i++) {
            for (int j = 0; j < Constants.GLYPHS_PER_COL; j++) {
                data[i][j] = getRandChar();
            }
        }

        // Set up the Animation Timer for the glyph glitches
        new AnimationTimerExt(0) {
            @Override
            public void handle() {
                glitchGlyphs();
                redraw(gc);
            }
        }.start();

        // Set up the Animation Timer for the drops
        new AnimationTimerExt(100) {
            @Override
            public void handle() {
                updateDrops();
                redraw(gc);
            }
        }.start();

        root.getChildren().add(canvas);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void redraw(GraphicsContext gc) {

        gc.clearRect(0, 0, primaryStage.getWidth(), primaryStage.getHeight());
        gc.setFill(Constants.VAMPIRE_BLACK);
        gc.fillRect(0, 0, primaryStage.getWidth(), primaryStage.getHeight());

        for (int i = 0; i < Constants.GLYPHS_PER_ROW; i++) {
            // Get the drop associated with this row
            Drop drop = drops[i];
            DropElement head = drop.getHead();
            List<DropElement> tail = drop.getTail();

            for (int j = 0; j < Constants.GLYPHS_PER_COL; j++) {
                gc.setFill(Constants.DARK_GREEN);

                if (head != null) {
                    if (cellIsHead(j, head)) {
                        gc.setFill(Constants.WHITE);
                    }
                }

                if (tail != null) {
                    if (cellIsFirstTail(j, tail)) {
                        gc.setFill(Constants.MALACHITE);
                    } else if (cellIsNormalTail(j, tail)) {
                        gc.setFill(Constants.ISLAMIC_GREEN);
                    }
                }

                String text = String.valueOf(data[i][j]);
                gc.fillText(text, i * 12 + 1, j * 13);
            }
        }
    }

    private boolean cellIsHead(int j, DropElement head) {
        return head.getY() == j;
    }

    private boolean cellIsFirstTail(int j, List<DropElement> tail) {
        return tail.get(0).getY() == j;
    }

    private boolean cellIsNormalTail(int j, List<DropElement> tail) {
        for (DropElement d : tail) {
            if (d.getY() == j) {
                return true;
            }
        }
        return false;
    }

    private void glitchGlyphs() {
        final float glyphGlitcherLimit = 0.0001f;
        for (int i = 0; i < Constants.GLYPHS_PER_ROW; i++) {
            for (int j = 0; j < Constants.GLYPHS_PER_COL; j++) {
                if (random.nextFloat() < glyphGlitcherLimit) {
                    data[i][j] = getRandChar();
                }
            }
        }
    }

    private void updateDrops() {
        final float dropInitiatorLimit = 0.001f;

        for (int i = 0; i < drops.length; i++) {
            Drop drop = drops[i];
            DropElement dropHead = drop.getHead();

            if (dropHead != null) {
                // Drop has already been spawned so increment its position by one
                // Check whether this increment means that the drop is all off screen now
                // and if so, reset this column to accept new drops
                drop.incrementDrop();
                DropElement lastElement = drop.getLastElement();
                if (lastElement.getY() == Constants.GLYPHS_PER_COL) {
                    drop.reset();
                }

                int headY = dropHead.getY();
                if (headY < Constants.GLYPHS_PER_COL && headY > 0) {
                    data[i][headY] = getRandChar();
                }

                if (drop.getTail() != null) {
                    for (DropElement dropElement : drop.getTail()) {
                        int tailY = dropElement.getY();
                        if (tailY < Constants.GLYPHS_PER_COL && tailY > 0) {
                            float glyphGlitcherLimit = 0.01f;
                            if (random.nextFloat() < glyphGlitcherLimit) {
                                data[i][tailY] = getRandChar();
                            }
                        }
                    }
                }
            } else {
                // Drop has not yet been spawned so see if it passes the test for
                // spawning this update
                if (random.nextFloat() < dropInitiatorLimit) {
                    int length = ThreadLocalRandom.current().nextInt(6, 20);
                    drop.addHead(new DropElement(i, 0));
                    drop.initialiseTail(length);
                }
            }
        }
    }
}