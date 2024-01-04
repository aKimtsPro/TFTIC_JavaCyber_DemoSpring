package be.tftic.spring.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;
    private String label;
    private String description;
    private int urgency; // between 0 and 10

}
