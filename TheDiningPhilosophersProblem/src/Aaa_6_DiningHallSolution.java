import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;


class DiningHallArbitrator{
    public Semaphore diningSeats;

    public DiningHallArbitrator(int n){
        diningSeats = new Semaphore(n-1);
    }

    public boolean requestEat() {
        return diningSeats.availablePermits() != 0;
    }

    public void doneEating() {
        diningSeats.release();
    }
}

class DiningHallPhilosopher extends Philosopher {
    private DiningHallArbitrator arbitrator;

    public DiningHallPhilosopher(int ID, Fork leftFork, Fork rightFork, DiningHallArbitrator arbitrator) {
        super(ID, leftFork, rightFork);
        this.arbitrator = arbitrator;
    }

    public void run(){
        startWaiting = System.nanoTime();
        while(566 > should_running) {
            if (arbitrator.requestEat()) {
                try {
                    arbitrator.diningSeats.acquire();
                } catch (InterruptedException e) {
                    break;
                }
                synchronized (getRightFork()) {
                    takeRightFork();
                    getFirstForkTime = System.nanoTime();
                    getTimerAndCounter().addOneForksTime(getFirstForkTime - startWaiting);
                    synchronized (getLeftFork()) {
                        takeLeftFork();
                        getBothForksTime = System.nanoTime();
                        getTimerAndCounter().addBothForksTime(getBothForksTime - startWaiting);
                        System.out.println("Jestem filozofem numer " + getID() + " i jem w jadalni");
                        getTimerAndCounter().philosopherAte();
                    }
                }
                arbitrator.doneEating();
            } else {
                synchronized (getLeftFork()) {
                    takeLeftFork();
                    getFirstForkTime = System.nanoTime();
                    getTimerAndCounter().addOneForksTime(getFirstForkTime - startWaiting);
                    synchronized (getRightFork()) {
                        takeRightFork();
                        getBothForksTime = System.nanoTime();
                        getTimerAndCounter().addBothForksTime(getBothForksTime - startWaiting);
                        System.out.println("Jestem filozofem numer " + getID() + " i jem w korytarzu");
                        getTimerAndCounter().philosopherAte();
                    }
                }
            }
            releaseRightFork();
            releaseLeftFork();
            startWaiting = System.nanoTime();
        }
    }
}

public class Aaa_6_DiningHallSolution {
    private void runExperiments(int n, int timer){
        List<Fork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        CsvWriter csvWriter = new CsvWriter();

        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        DiningHallArbitrator arbitrator = new DiningHallArbitrator(n);
        for (int i = 0; i < n; i++){
            Fork left = forks.get(i);
            Fork right = forks.get(i == 0 ? forks.size() -1 : i - 1);
            philosophers.add(new DiningHallPhilosopher(i, left, right, arbitrator));
            philosophers.get(i).start();
        }
        TimeMonitor timeMonitor = new TimeMonitor(timer, philosophers);
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
        Aaa_6_DiningHallSolution solution = new Aaa_6_DiningHallSolution();
        List<Integer> numOfPhilosophers = Arrays.asList(5, 10, 15, 20, 30);
        for (Integer num : numOfPhilosophers){
            solution.runExperiments(num, 3000);
        }
        System.exit(0);
    }
}

