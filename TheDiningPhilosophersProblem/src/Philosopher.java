public class Philosopher extends Thread{
    /* Class represents philosopher */
    Long startWaiting;
    Long getBothForksTime;
    Long getFirstForkTime;
    private int ID;
    private Fork leftFork;
    private Fork rightFork;
    private TimerAndCounter timerAndCounter = new TimerAndCounter();
    public int should_running = 0;

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

    public TimerAndCounter getTimerAndCounter(){
        return timerAndCounter;
    }

    public void run(){}
}
