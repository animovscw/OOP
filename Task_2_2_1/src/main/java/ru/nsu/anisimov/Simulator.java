package ru.nsu.anisimov;

public class Simulator {
    public static void main(String[] args) throws InterruptedException {
        int[] bakerSpeeds = {2, 3, 1};
        int[] courierCapacities = {2, 3};
        int storageCapacity = 5;

        Pizzeria pizzeria = new Pizzeria(bakerSpeeds.length, bakerSpeeds, courierCapacities.length, courierCapacities, storageCapacity);

        for (int i = 1; i < 21; ++i) {
            pizzeria.addOrder(new Order(i));
            Thread.sleep(300);
        }

        Thread.sleep(9000);
        pizzeria.shutdown();
        System.out.println();
        System.out.println("Рабочий день закончился, иди готовь рис.");
    }
}
