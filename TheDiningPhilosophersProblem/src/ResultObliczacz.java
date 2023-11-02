import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultObliczacz {
    private final List<Philosopher> philosophers;
    private List<Double> averageWaitingTimePerPhilosopherBothForks = new ArrayList<>();
    private List<Double> averageWaitingTimePerPhilosopherOneForks = new ArrayList<>();
    private List<Double> averageWaitingTimes = new ArrayList<>();
    private List<Integer> eatingPerPhilosopher = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#.####");
    List<List<? extends Number>> data = new ArrayList<>();

    public ResultObliczacz(List<Philosopher> philosophers){
        this.philosophers = philosophers;
    }

    public void countAverageWaitingTimePerPhilosopher() {
        for (Philosopher philosopher : philosophers){
            TimerAndCounter counter = philosopher.getTimerAndCounter();
            List<Double> oneForksTime = counter.getOneForkList();
            long sum = 0;
            for (Double oneTime : oneForksTime){
                sum += oneTime;
            }
            double average = (double) sum / oneForksTime.size();
            String formattedTime = df.format(average);
            averageWaitingTimePerPhilosopherOneForks.add(Double.parseDouble(formattedTime.replace(',', '.')));

            List<Double> bothForksTime = counter.getBothForkList();
            sum = 0;
            for (Double bothTime : bothForksTime){
                sum += bothTime;
            }
            average = (double) sum / bothForksTime.size();
            String formattedTime2 = df.format(average);
            averageWaitingTimePerPhilosopherBothForks.add(Double.parseDouble(formattedTime2.replace(',', '.')));
        }
    }

    public double getAverageTime(List<Double> times) {
        double sum = 0;
        for (double time : times){
            sum += time;
        }
        double average = sum / times.size();
        String formattedTime = df.format(average);
        return Double.parseDouble(formattedTime.replace(',', '.'));
    }

    public void getAverageTimes() {
        countAverageWaitingTimePerPhilosopher();
//        averageWaitingTimes.add(getAverageTime(averageWaitingTimePerPhilosopherOneForks));
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
