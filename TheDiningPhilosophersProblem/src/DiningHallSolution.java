import java.util.ArrayList;
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
        while(566 > 0){
            if (arbitrator.requestEat()){
                takeRightFork();
                takeLeftFork();
                System.out.println("Jestem filozofem numer " + getID() + " i jem w jadalni");
                releaseLeftFork();
                releaseRightFork();
                arbitrator.doneEating();
            } else if (arbitrator.requestCorridor()){
                takeLeftFork();
                takeRightFork();
                System.out.println("Jestem filozofem numer " + getID() + " i jem w korytarzu");
                releaseLeftFork();
                releaseRightFork();
                arbitrator.doneCorridor();
            }
        }
    }
}

public class DiningHallSolution {
    public static void main(String[] args) {
        int n = 15;
        List<LockedFork> forks = new ArrayList<>();
        List<DiningHallPhilosopher> philosophers = new ArrayList<>();
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
    }
}

