import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

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
                break;
            }
            synchronized (getRightFork()){
                getFirstForkTime = System.nanoTime();
                getTimerAndCounter().addOneForksTime(getFirstForkTime - startWaiting);
                synchronized (getLeftFork()){
                    getBothForksTime = System.nanoTime();
                    takeRightFork();
                    takeLeftFork();
                    getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                    System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                    System.out.println("Jestem filozofem numer " + getID() + " i jem");
                    getTimerAndCounter().philosopherAte();
                    releaseLeftFork();
                }
                releaseRightFork();
            }
            arbitrator.doneEating();
            startWaiting = System.nanoTime();
        }
    }
}


public class Aaa_5_WithArbitratorSolution {
    public void runExperiments(int n, int timer) {
        List<Fork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        CsvWriter csvWriter = new CsvWriter();

        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        ControllingArbitrator arbitrator = new ControllingArbitrator(n);
        for (int i = 0; i < n; i++){
            Fork left = forks.get(i);
            Fork right = forks.get(i == 0 ? forks.size() -1 : i - 1);
            philosophers.add(new ControlledPhilosopher(i, left, right, arbitrator));
            philosophers.get(i).start();
        }
        TimeMonitor timeMonitor = new TimeMonitor(timer, philosophers);
        timeMonitor.start();

        try {
            timeMonitor.join();
        } catch (InterruptedException e){
        }

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
            }
        }

        ResultObliczacz obliczacz = new ResultObliczacz(philosophers);
        obliczacz.getResult(csvWriter, "arbitrator_data.csv");
    }

    public static void main(String[] args) {
        Aaa_5_WithArbitratorSolution solution = new Aaa_5_WithArbitratorSolution();
        List<Integer> numOfPhilosophers = Arrays.asList(5, 10, 15, 20, 30);
        for (Integer num : numOfPhilosophers){
            solution.runExperiments(num, 3000);
        }
        System.exit(0);
    }
}
