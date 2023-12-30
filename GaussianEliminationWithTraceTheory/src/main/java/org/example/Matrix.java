package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entites.Letter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
@Setter
@Getter
class Matrix{
    private Float[][] matrixData;
    private Map<String, Float> workspace = new ConcurrentHashMap<>();

    Matrix(Float[][] matrixData){
        this.matrixData = matrixData;
    }

    public void gaussianElimination(Letter letter){
        switch (letter.getName()){
            case "A": countA(letter); break;
            case "B": countB(letter); break;
            case "C": countC(letter); break;
        }
    }

    public void print(){
        for (Float[] row : matrixData){
            System.out.print("| ");
            for (Float element : row){
                System.out.print(element + " | ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private void countA(Letter letter){
        Float m = matrixData[letter.getK()-1][letter.getI()-1] / matrixData[letter.getI()-1][letter.getI()-1];
        workspace.put(m(letter.getK(), letter.getI()), m);
    }

    private void countB(Letter letter){
        Float m = workspace.get(m(letter.getK(), letter.getI()));
        Float n = matrixData[letter.getI()-1][letter.getJ()-1] * m;
        workspace.put(n(letter.getK(), letter.getI(), letter.getJ()), n);
    }

    private void countC(Letter letter){
        Float n = workspace.get(n(letter.getK(), letter.getI(), letter.getJ()));
        matrixData[letter.getK()-1][letter.getJ()-1] -= n;
    }

    private static String m(int k, int i) {
        return "m_" + k + "_" + i;
    }

    private static String n(int k, int i, int j) {
        return "n_" + k + "_" + i + "_" + j;
    }
}
