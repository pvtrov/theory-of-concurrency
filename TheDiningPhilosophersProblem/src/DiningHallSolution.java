import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;


class DiningHallArbitrator{
    private Semaphore diningSeats;
    private Semaphore corridor;

    public DiningHallArbitrator(int n){
        diningSeats = new Semaphore(n-1);
        corridor = new Semaphore(1);
    }

    public boolean requestEat() {
        try{
            diningSeats.acquire();
            return true;
        } catch (InterruptedException e){
            return false;
        }
    }

    public void doneEating() {
        diningSeats.release();
    }

    public boolean requestCorridor() {
        try{
            corridor.acquire();
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void doneCorridor() {
        corridor.release();
    }
}

class DiningHallPhilosopher extends Philosopher {
    private DiningHallArbitrator arbitrator;

    public DiningHallPhilosopher(int ID, Fork leftFork, Fork rightFork, DiningHallArbitrator arbitrator) {
        super(ID, leftFork, rightFork);
        this.arbitrator = arbitrator;
    }

    public void run(){
        while(566 > should_running){
            startWaiting = System.nanoTime();
            if (arbitrator.requestEat()){
                takeRightFork();
                takeLeftFork();
                getBothForksTime = System.nanoTime();
                getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                getTimerAndCounter().addOneForksTime(getBothForksTime-startWaiting);
                System.out.println("Jestem filozofem numer " + getID() + " i jem w jadalni");
                getTimerAndCounter().philosopherAte();
                releaseLeftFork();
                releaseRightFork();
                arbitrator.doneEating();
                startWaiting = System.nanoTime();
            } else if (arbitrator.requestCorridor()){
                takeLeftFork();
                takeRightFork();
                getBothForksTime = System.nanoTime();
                getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                getTimerAndCounter().addOneForksTime(getBothForksTime-startWaiting);
                System.out.println("Jestem filozofem numer " + getID() + " i jem w korytarzu");
                getTimerAndCounter().philosopherAte();
                releaseLeftFork();
                releaseRightFork();
                arbitrator.doneCorridor();
                startWaiting = System.nanoTime();
            }
        }
    }
}

public class DiningHallSolution {
    private void runExperiments(int n){
        List<Fork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        CsvWriter csvWriter = new CsvWriter();

        for (int i = 0; i < n; i++) {
            forks.add(new LockedFork(i));
        }
        DiningHallArbitrator arbitrator = new DiningHallArbitrator(n);
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new DiningHallPhilosopher(i, left, right, arbitrator));
            philosophers.get(i).start();
        }
        TimeMonitor timeMonitor = new TimeMonitor(1000, philosophers);
        timeMonitor.start();

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ResultObliczacz obliczacz = new ResultObliczacz(philosophers);
        obliczacz.getResult(csvWriter, "dining_hall_data.csv");
    }

    public static void main(String[] args) {
        DiningHallSolution solution = new DiningHallSolution();
        List<Integer> numOfPhilosophers = Arrays.asList(5, 10, 15, 20);
        for (Integer num : numOfPhilosophers){
            solution.runExperiments(num);
        }
        System.exit(0);
    }
}

