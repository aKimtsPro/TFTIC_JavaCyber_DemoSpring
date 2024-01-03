package be.tftic.spring.demo.domain;

import lombok.Data;

@Data
public class Task {

    private Long id;
    private String label;
    private String description;
    private int urgency; // between 0 and 10

}
