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

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);

        ArrayList<Hole> holes = new ArrayList<>();
        holes.add(new Hole(canvas, 0, 0));
        holes.add(new Hole(canvas, WIDTH/2 - 22, 0));
        holes.add(new Hole(canvas, WIDTH - 45, 0));
        holes.add(new Hole(canvas, 0, HEIGHT - 130));
        holes.add(new Hole(canvas, WIDTH/2 - 22, HEIGHT - 130));
        holes.add(new Hole(canvas, WIDTH - 45, HEIGHT - 130));

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