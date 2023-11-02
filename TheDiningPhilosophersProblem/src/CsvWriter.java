import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {
    /* Class for handling writing data to csv file */

    private static void write(List<List<? extends Number>> data, String filePath){
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            for (List<? extends Number> row : data) {
                List<String> rowData = new ArrayList<>();
                for (Number value : row) {
                    rowData.add(String.valueOf(value));
                }
                writer.writeNext(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCsv(List<List<? extends Number>> data, String filePath) {
        boolean fileExists = new File(filePath).exists();
        if (!fileExists){
            try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
                writer.writeNext(new String[] { "\n" });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        write(data, filePath);
    }
}
