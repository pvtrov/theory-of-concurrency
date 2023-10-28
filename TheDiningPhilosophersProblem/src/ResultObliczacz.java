import java.util.ArrayList;
import java.util.List;

public class ResultObliczacz {
    private final List<Philosopher> philosophers;
    private List<Double> averageWaitingTimePerPhilosopherBothForks = new ArrayList<>();
    private List<Double> averageWaitingTimePerPhilosopherOneForks = new ArrayList<>();
    private List<Double> averageWaitingTimes = new ArrayList<>();
    private List<Integer> eatingPerPhilosopher = new ArrayList<>();
    List<List<? extends Number>> data = new ArrayList<>();

    public ResultObliczacz(List<Philosopher> philosophers){
        this.philosophers = philosophers;
    }

    public void countAverageWaitingTimePerPhilosopher() {
        for (Philosopher philosopher : philosophers){
            TimerAndCounter counter = philosopher.getTimerAndCounter();
            List<Long> oneForksTime = counter.getOneForkList();
            long sum = 0;
            for (Long oneTime : oneForksTime){
                sum += oneTime;
            }
            double average = (double) sum / oneForksTime.size();
            averageWaitingTimePerPhilosopherOneForks.add(average);

            List<Long> bothForksTime = counter.getBothForkList();
            sum = 0;
            for (Long bothTime : bothForksTime){
                sum += bothTime;
            }
            average = (double) sum / bothForksTime.size();
            averageWaitingTimePerPhilosopherBothForks.add(average);
        }
    }

    public double getAverageTime(List<Double> times) {
        double sum = 0;
        for (double time : times){
            sum += time;
        }
        return sum / times.size();
    }

    public void getAverageTimes() {
        countAverageWaitingTimePerPhilosopher();
        System.out.println("Average time for all philosophers (first fork): " + getAverageTime(averageWaitingTimePerPhilosopherOneForks));
        System.out.println("Average time for all philosophers (both fork): " + getAverageTime(averageWaitingTimePerPhilosopherBothForks));
        averageWaitingTimes.add(getAverageTime(averageWaitingTimePerPhilosopherOneForks));
        averageWaitingTimes.add(getAverageTime(averageWaitingTimePerPhilosopherBothForks));
        for (Philosopher philosopher : philosophers){
            eatingPerPhilosopher.add(philosopher.getTimerAndCounter().getEatingCounter());
        }
        data.add(averageWaitingTimePerPhilosopherBothForks);
        data.add(averageWaitingTimePerPhilosopherOneForks);
        data.add(eatingPerPhilosopher);
        data.add(averageWaitingTimes);
    }

    public void getResult(CsvWriter csvWriter, String filePath) {
        getAverageTimes();
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        csvWriter.writeCsv(data, filePath);
    }
}
