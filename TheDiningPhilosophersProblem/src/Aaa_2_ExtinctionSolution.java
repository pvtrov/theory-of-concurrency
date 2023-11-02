import java.util.ArrayList;
import java.util.List;


class ExtinctionPhilosopher extends Philosopher {
    public ExtinctionPhilosopher(int ID, Fork leftFork, Fork rightFork) {
        super(ID, leftFork, rightFork);
    }

    @Override
    public void run(){
        while(566 > 0){
            synchronized (getLeftFork()) {
                synchronized (getRightFork()){
                takeLeftFork();
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


public class Aaa_2_ExtinctionSolution {
    public static void main(String[] args) {
        int n = 5;
        List<Fork> forks = new ArrayList<>();
        List<ExtinctionPhilosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            forks.add(new Fork(i));
        }
        for (int i = 0; i < n; i++){
            Fork left = forks.get((i+1)%n);
            Fork right = forks.get((i-1+5)%n);
            philosophers.add(new ExtinctionPhilosopher(i, left, right));
            philosophers.get(i).start();
        }
    }
}

