import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AsymmetricPhilosopher extends Philosopher {
    public AsymmetricPhilosopher(int ID, Fork leftFork, Fork rightFork) {
        super(ID, leftFork, rightFork);
    }

    public void run(){
        startWaiting = System.nanoTime();
        while(566 > should_running){
            if (getID() % 2 == 0){
                synchronized (getRightFork()){
                    getFirstForkTime = System.nanoTime();
                    takeRightFork();
                    getTimerAndCounter().addOneForksTime(getFirstForkTime-startWaiting);
                    System.out.println("Jestem filozofem numer " + getID() + " i mam prawy widelec");
                    synchronized (getLeftFork()) {
                        getBothForksTime = System.nanoTime();
                        takeLeftFork();
                        getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                        System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                        System.out.println("Jestem filozofem numer " + getID() + " i jem");
                        getTimerAndCounter().philosopherAte();
                    }
                }
            } else {
                synchronized (getLeftFork()){
                    getFirstForkTime = System.nanoTime();
                    takeLeftFork();
                    getTimerAndCounter().addOneForksTime(getFirstForkTime-startWaiting);
                    System.out.println("Jestem filozofem numer " + getID() + " i mam lewy widelec");
                    synchronized (getRightFork()) {
                        getBothForksTime = System.nanoTime();
                        takeRightFork();
                        getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                        System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                        System.out.println("Jestem filozofem numer " + getID() + " i jem");
                        getTimerAndCounter().philosopherAte();
                    }
                }
            }
            releaseLeftFork();
            releaseRightFork();
            startWaiting = System.nanoTime();
            System.out.println("Jestem filozofem numer " + getID() + " i skonczylem jesc");
        }
    }
}

public class AsymmetricSolution {
    public void runExperiments(int n){
        List<Fork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        CsvWriter csvWriter = new CsvWriter();
        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new AsymmetricPhilosopher(i, left, right));
            philosophers.get(i).start();
        }
        TimeMonitor timeMonitor = new TimeMonitor(10000, philosophers);
        timeMonitor.start();

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ResultObliczacz obliczacz = new ResultObliczacz(philosophers);
        obliczacz.getResult(csvWriter, "asymetric_data.csv");
    }

    public static void main(String[] args) {
        AsymmetricSolution solution = new AsymmetricSolution();
        List<Integer> numOfPhilosophers = Arrays.asList(5, 10, 15, 20);
        for (Integer num : numOfPhilosophers){
            solution.runExperiments(num);
        }
        System.exit(0);
    }
}
