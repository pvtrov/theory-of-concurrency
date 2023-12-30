package org.example.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CalculationData {
    @JsonProperty("fnf_classes")
    private List<List<Letter>> fnfClasses;

    private Float[][] matrix;
}
