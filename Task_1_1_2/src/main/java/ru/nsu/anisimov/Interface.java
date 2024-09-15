package ru.nsu.anisimov;

public class Interface {
    public static String sendBack(String letter) {
        System.out.println(letter + ".");
        return letter + ".";
    }

    public static void main(String[] args) {
        sendBack("Hello");
    }
}
