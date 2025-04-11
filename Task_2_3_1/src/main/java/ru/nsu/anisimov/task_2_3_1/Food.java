package ru.nsu.anisimov.task_2_3_1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
    public Food(int x, int y, int size) {
        super(size, size, Color.RED);
        setTranslateX(x * size);
        setTranslateY(y * size);
    }
}
