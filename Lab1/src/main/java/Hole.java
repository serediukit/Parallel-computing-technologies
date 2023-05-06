package main.java;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Hole {
    private Component canvas;
    private static final int XSIZE = 28;
    private static final int YSIZE = 28;
    private int x;
    private int y;

    public Hole(Component c, int posX, int posY) {
        this.canvas = c;
        this.x = posX;
        this.y = posY;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public boolean canBeInHole(int posX, int posY) {
        if (posX >= x && posX < x + XSIZE
                && posY >= y && posY < y + YSIZE) {
            return true;
        }
        return false;
    }
}
