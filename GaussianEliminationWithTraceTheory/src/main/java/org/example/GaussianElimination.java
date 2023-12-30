package org.example;
import java.io.IOException;


public class GaussianElimination {
    /**
     * Execute main logic of the program.
     * 1. Run Python script to get FNF and matrix values.
     * 2. Parse JSON file to proper objects and format.
     * 3. Execute gaussian elimination based on FNF classes.
     * 4. Prints result matrix in terminal.
     */
    public static void main(String[] args) {
        try {
            int n = 3;
            if (args.length != 0) {
                n = Integer.parseInt(args[0]);
            }
            Parser.runPythonScript(n);
            var calculationDatadata = Parser.parseData(n);
            var fnfClass = calculationDatadata.getFnfClasses();
            var matrixData = calculationDatadata.getMatrix();
            var matrix = new Matrix(matrixData);
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.print("Before Gauss Elimination:\n");
            matrix.print();
            fnfClass.forEach(singleClass -> singleClass.stream().parallel().forEach(matrix::gaussianElimination));
            System.out.print("After Gauss Elimination: \n");
            matrix.print();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
