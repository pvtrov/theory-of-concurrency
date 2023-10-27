package lab4;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class LockedFork extends Fork{
    private int ID;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public LockedFork(int ID){
        super(ID);
    }

    @Override
    public boolean take(){
        lock.lock();
        try{
            while (isTaken){
                condition.await();
            }
            isTaken = true;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void release(){
        lock.lock();
        try {
            isTaken = false;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}

class ControllingArbitrator{
    private int n;
    private Semaphore eatingPhilosophers;

    public ControllingArbitrator(int n){
        this.n = n;
        this.eatingPhilosophers = new Semaphore(n-1);
    }

    public void requestEat() throws InterruptedException {
        eatingPhilosophers.acquire();
    }

    public void doneEating(){
        eatingPhilosophers.release();
    }
}

class ControlledPhilosopher extends Philosopher{
    private ControllingArbitrator arbitrator;

    public ControlledPhilosopher(int ID, Fork leftFork, Fork rightFork, ControllingArbitrator arbitrator) {
        super(ID, leftFork, rightFork);
        this.arbitrator = arbitrator;
    }

    public void run(){
        while(566 > 0){
            try {
                arbitrator.requestEat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            takeRightFork();
            takeLeftFork();
            System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
            System.out.println("Jestem filozofem numer " + getID() + " i jem");
            releaseLeftFork();
            releaseRightFork();
            arbitrator.doneEating();
        }
    }
}


public class WithArbitratorSolution {
    public static void main(String[] args) {
        int n = 5;
        List<LockedFork> forks = new ArrayList<>();
        List<ControlledPhilosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            forks.add(new LockedFork(i));
        }
        ControllingArbitrator arbitrator = new ControllingArbitrator(n);
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new ControlledPhilosopher(i, left, right, arbitrator));
            philosophers.get(i).start();
        }
    }
}
