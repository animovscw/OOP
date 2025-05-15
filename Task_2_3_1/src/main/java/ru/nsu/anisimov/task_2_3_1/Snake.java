package ru.nsu.anisimov.task_2_3_1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Rectangle> body = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;
    private final Color headColor;
    private final Color bodyColor;

    public Snake(int startX, int startY, int size, Color headColor, Color bodyColor) {
        this.headColor = headColor;
        this.bodyColor = bodyColor;

        Rectangle head = new Rectangle(size, size);
        head.setFill(headColor);
        head.setTranslateX(startX * size);
        head.setTranslateY(startY * size);
        body.add(head);
    }

    public LinkedList<Rectangle> getBody() {
        return body;
    }

    public void move(int size, boolean grow) {
        Rectangle head = body.getFirst();
        double x = head.getTranslateX();
        double y = head.getTranslateY();

        switch (direction) {
            case UP -> y -= size;
            case DOWN -> y += size;
            case LEFT -> x -= size;
            case RIGHT -> x += size;
        }

        Rectangle newHead = new Rectangle(size, size, Color.SADDLEBROWN);
        newHead.setTranslateX(x);
        newHead.setTranslateY(y);
        body.addFirst(newHead);

        if (!grow) {
            body.removeLast();
        }
        updateColors();
    }

    public void updateColors() {
        boolean isFirst = true;
        for (Rectangle part : body) {
            if (isFirst) {
                part.setFill(headColor);
                isFirst = false;
            } else {
                part.setFill(bodyColor);
            }
        }
    }

    public Rectangle getHead() {
        return body.getFirst();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean checkForSelfCollision() {
        Rectangle head = getHead();
        for (int i = 1; i < body.size(); i++) {
            Rectangle part = body.get(i);
            if (head.getTranslateX() == part.getTranslateX() &&
                    head.getTranslateY() == part.getTranslateY()) {
                return true;
            }
        }
        return false;
    }

    public void setNextDirection(Direction direction) {
        if (!isOpposite(this.direction, direction)) {
            this.nextDirection = direction;
        }
    }

    private boolean isOpposite(Direction dir1, Direction dir2) {
        return (dir1 == Direction.UP && dir2 == Direction.DOWN) ||
                (dir1 == Direction.DOWN && dir2 == Direction.UP) ||
                (dir1 == Direction.LEFT && dir2 == Direction.RIGHT) ||
                (dir1 == Direction.RIGHT && dir2 == Direction.LEFT);
    }

    public void applyNextDirection() {
        this.direction = nextDirection;
    }
}