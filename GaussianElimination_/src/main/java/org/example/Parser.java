package org.example;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


class Letter extends Thread{
    //    private Matrix matrix;
    private String name;
    private int i;
    private int j;
    private int k;
}

public class Utils {
    public static List<List<Letter>> parseFNF (int matrix_size) throws IOException{
        String filename = "fnfs/fnf_" + matrix_size + ".json";
        File file = new File(filename);
        ObjectMapper objectMapper = new ObjectMapper();

//        FNFClasses fnfClasses = objectMapper.readValue(file, FNFClasses.class);
        try {
            FNFClasses fnfClasses = objectMapper.readValue(filename, FNFClasses.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
