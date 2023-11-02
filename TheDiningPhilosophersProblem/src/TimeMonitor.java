import java.util.List;

public class TimeMonitor extends Thread{
    /* Class for handling program killing */
    List<Philosopher> philosophers;
    int timer;

    public TimeMonitor(int timer, List<Philosopher> philosophers){
        this.philosophers = philosophers;
        this.timer = timer;
    }

    public void run(){
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Philosopher philosopher : philosophers){
            philosopher.should_running = 567;
//            philosopher.interrupt();
        }
        System.out.println("Philosophers killed");
    }
}
