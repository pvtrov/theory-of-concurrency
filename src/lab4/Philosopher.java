package lab4;

public class Philosopher extends Thread{
    /* Class represents philosopher */
    private int ID;
    private Fork leftFork;
    private Fork rightFork;

    public Philosopher(int ID, Fork leftFork, Fork rightFork) {
        this.ID = ID;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public int getID() {
        return ID;
    }

    public Fork getLeftFork() {
        return leftFork;
    }

    public Fork getRightFork() {
        return rightFork;
    }

    public void takeLeftFork() {
        leftFork.take();
    }

    public void takeRightFork() {
        rightFork.take();
    }

    public void releaseLeftFork() {
        leftFork.release();
    }

    public void releaseRightFork() {
        rightFork.release();
    }

    public void run(){}
}
