package main.java;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                b.move();
                if (b.isInHole()) {
                    Thread.currentThread().interrupt();
                    BounceFrame.changeCountBallsInHoles(1);
                    b.deleteInCanvas();
                    BounceFrame.rePaint();
                    System.out.println("Interrupt Thread names = " + Thread.currentThread().getName());
                }
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){
            System.out.println("Cannot to move the ball in thread name = " + Thread.currentThread().getName());
        }
    }
}