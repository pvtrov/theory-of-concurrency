import java.util.ArrayList;
import java.util.List;



class NaivePhilosopher extends Philosopher {
    public NaivePhilosopher(int ID, Fork leftFork, Fork rightFork) {
        super(ID, leftFork, rightFork);
    }

    public void run(){
        while(566 > 0){
            synchronized (getLeftFork()){
                takeLeftFork();
                System.out.println("Jestem filozofem numer " + getID() + " i mam lewy widelec");
                synchronized (getRightFork()){
                    takeRightFork();
                    System.out.println("Jestem filozofem numer " + getID() + " i mam oba widelce");
                    System.out.println("Jestem filozofem numer " + getID() + " i jem");
                }
            }
            releaseLeftFork();
            releaseRightFork();
            System.out.println("Jestem filozofem numer " + getID() + " i skonczylem jesc");
        }
    }
}


public class Aaa_1_NaiveSolution {
    public static void main(String[] args) {
        int n = 5;
        List<Fork> forks = new ArrayList<>();
        List<NaivePhilosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new NaivePhilosopher(i, left, right));
            philosophers.get(i).start();
        }
    }
}


