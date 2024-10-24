package ru.nsu.anisimov;

import java.util.Hashtable;

public class Main {
    public static void main(String[] args) {
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("one", 1);
        hashtable.put("two", 2);

        System.out.println(hashtable.get("one"));
        System.out.println("Hashtable: " + hashtable);
    }
}