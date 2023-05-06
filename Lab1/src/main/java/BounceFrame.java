package main.java;

import javax.swing.*;;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BounceFrame extends JFrame {
    private static BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private static int countBallsInHoles = 0;

    private static JLabel countBalls = new JLabel("Balls in holes: " + countBallsInHoles);

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.lightGray);
        textPanel.add(countBalls, BorderLayout.CENTER);
        content.add(textPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        JButton buttonStartRed = new JButton("Add 1 Red");
        JButton buttonStartBlue = new JButton("Add 10 Blue");
        JButton buttonStartTogether = new JButton(("Start together"));

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas);
                canvas.addBall(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonStartRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas, Color.RED, 100, 100);
                canvas.addBall(b);

                BallThread thread = new BallThread(b);
                thread.setPriority(10);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonStartBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10; i++) {
                    Ball b = new Ball(canvas, Color.BLUE, 100, 100);
                    canvas.addBall(b);

                    BallThread thread = new BallThread(b);
                    thread.setPriority(1);
                    thread.start();
                    System.out.println("Thread name = " + thread.getName());
                }
            }
        });

        buttonStartTogether.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b1 = new Ball(canvas, Color.RED, 100, 100);
                canvas.addBall(b1);

                BallThread thread1 = new BallThread(b1);
                thread1.setPriority(10);
                thread1.start();
                System.out.println("Thread name = " + thread1.getName());

                for (int i = 0; i < 100; i++) {
                    Ball b2 = new Ball(canvas, Color.BLUE, 100, 100);
                    canvas.addBall(b2);

                    BallThread thread2 = new BallThread(b2);
                    thread2.setPriority(1);
                    thread2.start();
                    System.out.println("Thread name = " + thread2.getName());
                }
            }
        });

        buttonPanel.add(buttonStartTogether);
        buttonPanel.add(buttonStartRed);
        buttonPanel.add(buttonStartBlue);
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);

        ArrayList<Hole> holes = new ArrayList<>();
        holes.add(new Hole(canvas, 0, 0));
        holes.add(new Hole(canvas, WIDTH - 45, 0));

        for (Hole h : holes) {
            canvas.addHole(h);
        }
    }

    public static void changeCountBallsInHoles(int amount) {
        countBallsInHoles += amount;
    }

    public static void rePaint() {
        canvas.repaint();
        countBalls.setText("Balls in holes: " + countBallsInHoles);
    }
}