/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ping_pong_iteration;

/**
 *
 * @author cherk
 */

import java.util.Scanner;

public class PingPong {
    private static final Object lock = new Object();
    private static int turn = 1; // 1 for Ping, 2 for Pong
    private static int pingCount = 0;
    private static int pongCount = 0;
    private static int nb;
    private static int nbPing;
    private static int nbPong;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of iterations: ");
        nb = scanner.nextInt();
        System.out.print("Enter the number of iterations for Ping: ");
        nbPing = scanner.nextInt();
        System.out.print("Enter the number of iterations for Pong: ");
        nbPong = scanner.nextInt();

        Thread pingThread = new Thread(new Ping(), "Ping Thread");
        Thread pongThread = new Thread(new Pong(), "Pong Thread");
        pongThread.start();
        pingThread.start();
    }

    static class Ping implements Runnable {
        public void run() {
            for (int i = 0; i < (nb * (nbPing + nbPong)) - 2; i++) {
                synchronized (lock) {
                    try {
                        while (turn != 1) {
                            lock.wait();

                        }
                        System.out.println("\u001B[32m" + Thread.currentThread().getName() + ": Ping" + "\u001B[0m");

                        pingCount++;
                        if (pingCount == nbPing) {
                            pingCount = 0;
                            turn = 2;
                        }
                        Thread.sleep(1000);
                        lock.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Pong implements Runnable {
        public void run() {
            for (int i = 0; i < (nb * (nbPing + nbPong)) - 2; i++) {
                synchronized (lock) {
                    try {
                        while (turn != 2) {
                            lock.wait();

                        }
                        System.out.println(Thread.currentThread().getName() + ": Pong");
                        pongCount++;
                        if (pongCount == nbPong) {
                            pongCount = 0;
                            turn = 1;
                        }
                        Thread.sleep(1000);
                        lock.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}