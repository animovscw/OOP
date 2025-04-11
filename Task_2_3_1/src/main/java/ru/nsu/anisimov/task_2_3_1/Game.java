package ru.nsu.anisimov.task_2_3_1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    @FXML
    private Pane gamePane;

    private Button restartButton;
    private final int rows = 20;
    private final int cols = 20;
    private final int cellSize = 20;
    private Label labelStatus;

    private Snake snake;
    private Snake botSnake;
    private final ArrayList<Food> foodList = new ArrayList<>();
    private final Random random = new Random();
    private Timeline timeline;

    @FXML
    public void initialize() {
        snake = new Snake(cols / 2, rows / 2, cellSize, Color.CHOCOLATE, Color.SADDLEBROWN);
        gamePane.getChildren().addAll(snake.getBody());
        botSnake = new Snake(cols / 2 - 2, rows / 2, cellSize, Color.WHITE, Color.BLACK);
        gamePane.getChildren().addAll(botSnake.getBody());

        int foodCount = 3;
        for (int i = 0; i < foodCount; i++) {
            spawnFood();
        }

        setupControls();
        gamePane.setStyle("-fx-background-color: darkgreen;");

        restartButton = new Button("Restart");
        restartButton.setFont(new Font("Arial", 22));
        restartButton.setVisible(false);
        restartButton.setTranslateX((cols * cellSize) / 2.0 - 60);
        restartButton.setTranslateY((rows * cellSize) / 2.0 + 30);
        restartButton.setOnAction(e -> restartGame());
        gamePane.getChildren().add(restartButton);

        labelStatus = new Label();
        labelStatus.setFont(new Font("Arial", 36));
        labelStatus.setTextFill(Color.WHITE);
        labelStatus.setVisible(false);
        labelStatus.setAlignment(Pos.CENTER);

        labelStatus.setTranslateX((cols * cellSize) / 2.0 - 100);
        labelStatus.setTranslateY((rows * cellSize) / 2.0 - 20);
        gamePane.getChildren().add(labelStatus);


        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void gameLoop() {
        snake.applyNextDirection();
        moveBot();

        Rectangle curHead = snake.getHead();
        double nextX = curHead.getTranslateX();
        double nextY = curHead.getTranslateY();

        switch (snake.getDirection()) {
            case UP -> nextY -= cellSize;
            case DOWN -> nextY += cellSize;
            case LEFT -> nextX -= cellSize;
            case RIGHT -> nextX += cellSize;
        }

        Food eaten = null;
        for (Food food : foodList) {
            if (food.getTranslateX() == nextX &&
                    food.getTranslateY() == nextY) {
                eaten = food;
                break;
            }
        }

        snake.move(cellSize, eaten != null);

        if (eaten != null) {
            foodList.remove(eaten);
            gamePane.getChildren().remove(eaten);
            spawnFood();
        }
        gamePane.getChildren().removeIf(node -> node instanceof Rectangle);
        gamePane.getChildren().addAll(snake.getBody());
        gamePane.getChildren().addAll(botSnake.getBody());
        gamePane.getChildren().addAll(foodList);


        int winLength = 11;
        if (checkGameOver()) {
            timeline.stop();
            showStatus("Game Over!");
        } else if (snake.getBody().size() >= winLength) {
            timeline.stop();
            showStatus("You Win!");
        }
    }

    private void showStatus(String message) {
        labelStatus.setText(message);
        labelStatus.setTextFill(Color.YELLOW);
        labelStatus.setVisible(true);
        restartButton.setVisible(true);
    }

    private boolean checkGameOver() {
        Rectangle head = snake.getHead();
        if (head.getTranslateX() < 0 || head.getTranslateX() >= cols * cellSize ||
                head.getTranslateY() < 0 || head.getTranslateY() >= rows * cellSize ||
                snake.checkForSelfCollision()) {
            return true;
        }

        for (Rectangle part : botSnake.getBody()) {
            if (part != botSnake.getHead() &&
                    part.getTranslateX() == head.getTranslateX() &&
                    part.getTranslateY() == head.getTranslateY()) {
                return true;
            }
        }

        Rectangle botHead = botSnake.getHead();
        return head.getTranslateX() == botHead.getTranslateX() &&
                head.getTranslateY() == botHead.getTranslateY();
    }

    private void spawnFood() {
        int x, y;
        boolean occupied;
        do {
            x = random.nextInt(cols);
            y = random.nextInt(rows);
            int finalX = x;
            int finalY = y;
            occupied = snake.getBody().stream().anyMatch(
                    part -> part.getTranslateX() == finalX * cellSize &&
                            part.getTranslateY() == finalY * cellSize);
        } while (occupied);

        Food food = new Food(x, y, cellSize);
        foodList.add(food);
        gamePane.getChildren().add(food);
    }

    private void setupControls() {
        gamePane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> snake.setNextDirection(Direction.UP);
                case DOWN -> snake.setNextDirection(Direction.DOWN);
                case LEFT -> snake.setNextDirection(Direction.LEFT);
                case RIGHT -> snake.setNextDirection(Direction.RIGHT);
            }
        });
        gamePane.setFocusTraversable(true);
    }

    private void restartGame() {
        gamePane.getChildren().clear();
        foodList.clear();
        labelStatus.setVisible(false);
        restartButton.setVisible(false);
        initialize();
    }

    private void moveBot() {
        if (foodList.isEmpty()) {
            return;
        }
        Food target = foodList.get(0);
        Rectangle botHead = botSnake.getHead();

        double dx = target.getTranslateX() - botHead.getTranslateX();
        double dy = target.getTranslateY() - botHead.getTranslateY();

        Direction dir = botSnake.getDirection();

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0 &&
                    dir != Direction.LEFT) {
                botSnake.setDirection(Direction.RIGHT);
            } else if (dx < 0 &&
                    dir != Direction.RIGHT) {
                botSnake.setDirection(Direction.LEFT);
            }
        } else {
            if (dy > 0 &&
                    dir != Direction.UP) {
                botSnake.setDirection(Direction.DOWN);
            } else if (dy < 0 && dir != Direction.DOWN) {
                botSnake.setDirection(Direction.UP);
            }
        }

        Rectangle curHead = botSnake.getHead();
        double nextX = curHead.getTranslateX();
        double nextY = curHead.getTranslateY();

        switch (botSnake.getDirection()) {
            case UP -> nextY -= cellSize;
            case DOWN -> nextY += cellSize;
            case LEFT -> nextX -= cellSize;
            case RIGHT -> nextX += cellSize;
        }

        if (nextX < 0 || nextX >= cols * cellSize || nextY >= rows * cellSize || nextY < 0) {
            resetBotSnake();
            return;
        }

        for (Rectangle part : snake.getBody()) {
            if (part.getTranslateX() == nextX &&
                    part.getTranslateY() == nextY) {
                resetBotSnake();
                return;
            }
        }

        for (Rectangle part : botSnake.getBody()) {
            if (part != curHead &&
                    part.getTranslateX() == nextX &&
                    part.getTranslateY() == nextY) {
                resetBotSnake();
                return;
            }
        }

        boolean ate = false;
        Food eaten = null;
        for (Food food : foodList) {
            if (food.getTranslateX() == nextX &&
                    food.getTranslateY() == nextY) {
                eaten = food;
                ate = true;
                break;
            }
        }

        botSnake.move(cellSize, ate);

        if (eaten != null) {
            foodList.remove(eaten);
            gamePane.getChildren().remove(eaten);
            spawnFood();
        }
    }

    private void resetBotSnake() {
        gamePane.getChildren().removeAll(botSnake.getBody());
        botSnake = new Snake(cols / 2 - 2, rows / 2, cellSize, Color.WHITE, Color.BLACK);
        gamePane.getChildren().addAll(botSnake.getBody());
    }
}
