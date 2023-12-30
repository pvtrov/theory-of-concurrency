package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entites.CalculationData;

import java.io.File;
import java.io.IOException;


public class Parser {
    public static CalculationData parseData(int matrix_size) throws IOException{
        String filename = "GaussianEliminationWithTraceTheory/calculation_data/calculation_data_" + matrix_size + ".json";
        File file = new File(filename);
        ObjectMapper objectMapper = new ObjectMapper();
        CalculationData calculationData;

        try {
            calculationData = objectMapper.readValue(file, CalculationData.class);
            return calculationData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void runPythonScript(int size){
        try{
            Process process = new ProcessBuilder("python3", "../GaussianEliminationWithTraceTheory/get_calculation_data.py", String.valueOf(size)).start();
            process.waitFor();
        } catch (Exception e){
            e.printStackTrace(System.out);
            System.exit(566);
        }
    }
}
