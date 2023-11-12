package com.example.demo.DTO;

public class ChoiceDTO {
    private String choice;

    public ChoiceDTO(String choice) {
        this.choice = choice;
    }

    public ChoiceDTO(){

    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
