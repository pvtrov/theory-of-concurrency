import java.util.ArrayList;
import java.util.List;


public class TimerAndCounter {
    private List<Double> waitingForOneForkTimes = new ArrayList<>();
    private List<Double> waitingForBothForksTimes = new ArrayList<>();
    private int eatingCounter = 0;

    public TimerAndCounter(){
    }

    public void addBothForksTime(Long measuredTime){
        waitingForBothForksTimes.add(measuredTime / 1e6);
    }

    public void addOneForksTime(Long measuredTime){
        waitingForOneForkTimes.add(measuredTime / 1e6);
    }

    public List getOneForkList(){
        return waitingForOneForkTimes;
    }

    public List getBothForkList(){
        return waitingForBothForksTimes;
    }

    public void philosopherAte(){
        this.eatingCounter += 1;
    }

    public int getEatingCounter(){
        return eatingCounter;
    }
}
