package main.java;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    public static ArrayList<Ball> balls = new ArrayList<>();
    public static ArrayList<Hole> holes = new ArrayList<>();

    public void addBall(Ball b){
        this.balls.add(b);
    }

    public void addHole(Hole h) {
        this.holes.add(h);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for(Ball b : balls){
            b.draw(g2);
        }
        for(Hole h : holes){
            h.draw(g2);
        }
    }
}