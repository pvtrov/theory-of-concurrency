import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class StochasticPhilosopher extends Philosopher {
    Random random = new Random();

    public StochasticPhilosopher(int ID, Fork leftFork, Fork rightFork) {
        super(ID, leftFork, rightFork);
    }

    public void run(){
        startWaiting = System.nanoTime();
        while(566 > should_running){
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            int coin = random.nextInt(2); // left -> 0, right -> 1
            if (coin == 0) {
                synchronized (getLeftFork()){
                    getFirstForkTime = System.nanoTime();
                    getTimerAndCounter().addOneForksTime(getFirstForkTime-startWaiting);
                    takeLeftFork();
                    System.out.println("Jestem filozofem numer " + getID() + " i mam lewy widelec");
                    synchronized (getRightFork()) {
                        getBothForksTime = System.nanoTime();
                        getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                        takeRightFork();
                        System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                        System.out.println("Jestem filozofem numer " + getID() + " i jem");
                        getTimerAndCounter().philosopherAte();
                    }
                }
            } else {
                synchronized (getRightFork()) {
                    takeRightFork();
                    getFirstForkTime = System.nanoTime();
                    getTimerAndCounter().addOneForksTime(getFirstForkTime-startWaiting);
                    System.out.println("Jestem filozofem numer " + getID() + " i mam prawy widelec");
                    synchronized (getLeftFork()) {
                        getBothForksTime = System.nanoTime();
                        getTimerAndCounter().addBothForksTime(getBothForksTime-startWaiting);
                        takeLeftFork();
                        System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                        System.out.println("Jestem filozofem numer " + getID() + " i jem");
                        getTimerAndCounter().philosopherAte();
                    }
                }
            }
            releaseLeftFork();
            releaseRightFork();
            System.out.println("Jestem filozofem numer " + getID() + " i skonczylem jesc");
            startWaiting = System.nanoTime();
        }
    }
}


public class Aaa_4_StochasticSolution {
    private void runExperiments(int n, int timer){
        List<Fork> forks = new ArrayList<>();
        List<Philosopher> philosophers = new ArrayList<>();
        CsvWriter csvWriter = new CsvWriter();

        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }

        for (int i = 0; i < n; i++){
            Fork left = forks.get(i);
            Fork right = forks.get(i == 0 ? forks.size() -1 : i - 1);
            philosophers.add(new StochasticPhilosopher(i, left, right));
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
        obliczacz.getResult(csvWriter, "sochastic_data.csv");

    }

    public static void main(String[] args) {
        Aaa_4_StochasticSolution solution = new Aaa_4_StochasticSolution();
        List<Integer> numOfPhilosophers = Arrays.asList(20, 25, 30);
        for (Integer num : numOfPhilosophers){
            solution.runExperiments(num, 1000);
        }
        System.exit(0);
    }
}
