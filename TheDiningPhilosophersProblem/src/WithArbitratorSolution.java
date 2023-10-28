import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class LockedFork extends Fork {
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

class ControlledPhilosopher extends Philosopher {
    private ControllingArbitrator arbitrator;

    public ControlledPhilosopher(int ID, Fork leftFork, Fork rightFork, ControllingArbitrator arbitrator) {
        super(ID, leftFork, rightFork);
        this.arbitrator = arbitrator;
    }

    public void run(){
        startWaiting = System.nanoTime();
        while(566 > should_running){
            try {
                arbitrator.requestEat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getBothForksTime = System.nanoTime();
            takeRightFork();
            takeLeftFork();
            getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
            getTimerAndCounter().addOneForksTime(getBothForksTime-startWaiting);
            System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
            System.out.println("Jestem filozofem numer " + getID() + " i jem");
            getTimerAndCounter().philosopherAte();
            releaseLeftFork();
            releaseRightFork();
            arbitrator.doneEating();
            startWaiting = System.nanoTime();
        }
    }
}


public class WithArbitratorSolution {
    public void runExperiments(int n){
        List<LockedFork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        CsvWriter csvWriter = new CsvWriter();

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
        TimeMonitor timeMonitor = new TimeMonitor(15000, philosophers);
        timeMonitor.start();

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ResultObliczacz obliczacz = new ResultObliczacz(philosophers);
        obliczacz.getResult(csvWriter, "arbitrator_data.csv");
    }

    public static void main(String[] args) {
        WithArbitratorSolution solution = new WithArbitratorSolution();
        List<Integer> numOfPhilosophers = Arrays.asList(5, 8, 10, 12);
        for (Integer num : numOfPhilosophers){
            solution.runExperiments(num);
        }
        System.exit(0);
    }
}
