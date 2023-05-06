package main.java;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

class Ball {
    private Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;

    private Color color = Color.GREEN;


    public Ball(Component c){
        this.canvas = c;

        x = new Random().nextInt(this.canvas.getWidth());
        y = new Random().nextInt(this.canvas.getHeight());

    }

    public Ball(Component c, Color cl, int posX, int posY){
        this.canvas = c;
        this.color = cl;
        this.x = posX;
        this.y = posY;
    }

    public void draw (Graphics2D g2){
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public boolean isInHole() {
        ArrayList<Hole> holes = BallCanvas.holes;

        int posX = x + XSIZE/2;
        int posY = y + YSIZE/2;

        for (Hole hole : holes) {
            if (hole.canBeInHole(posX, posY)) {
                return true;
            }
        }

        return false;
    }

    public void deleteInCanvas() {
        BallCanvas.balls.remove(this);
    }
}