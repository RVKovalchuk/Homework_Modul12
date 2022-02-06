package Zadacha1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//класс наследуем от Thread.
public class Zadacha1_Thread extends Thread {
    //создаем список элементов, initial capacity с default меняем на 100.
    private final List<Integer> listOfElements = new ArrayList<>(100);
    //создаем объект класса Random.
    Random random = new Random();
    //объявляем переменные для сравнения значения [0;5] с первым элементом массива.
    private int check;

    public void threadWork() {
        //создаем первый поток.
        Thread thread1 = new Thread() {
            //переопределяем метод run().
            @Override
            public void run() {
                //заполняем список рандомными элементами в диапазоне [0;100].
                    for (int i = 0; i < 100; i++) {
                        listOfElements.add(random.nextInt(101));
                    }
                    //для ручной сверки выводим все элементы списка в консоль.
                    for (Integer i : listOfElements) {
                        System.out.print(i + " ");
                    }
                    //добавили пустую строку после вывода всего списка.
                    System.out.println("\n");
            }
        };
        //создаем второй поток.
        Thread thread2 = new Thread() {
            //переопределяем метод run().
            @Override
            public void run() {
                //отрабатываем исключение для метода sleep(long millis).
                try {
                    //запускаем цикл до тех пор, пока он не окажется пустым.
                    while (!listOfElements.isEmpty()) {
                        //заставляем поток уснуть на 0,1 секунды.
                        sleep(100);
                        //рандомим значение в диапазоне [0;5]
                        check = random.nextInt(6);
                        //проверяем значение на равенство 5.
                        if (check == 5) {
                            //выводим в консоль оповещение о получении 5 и выводим удаляемый элемент.
                            System.out.println("It was 5. I removed: " + listOfElements.get(0));
                            //удаляем первый элемент списка.
                            listOfElements.remove(0);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //запускаем таймер выполнения программы.
        //объявляем переменные для засекания времени выполнения программы.
        long start = System.currentTimeMillis();
        //запускаем потоки.
        thread1.start();
        thread2.start();
        //отрабатываем исключения для метода join().
        try {
            //ожидаем завершение потоков.
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //останавливаем таймер выполнения программы.
        long end = System.currentTimeMillis();
        //рассчитываем время выполнения в секундах.
        long time = (end - start) / 1000;
        //выводим информацию о затраченном времени. проверяем количество элементов массива.
        System.out.println("It takes " + time + " sec. Size of listOfElements is : " + listOfElements.size());
    }
}
