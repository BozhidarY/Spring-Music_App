package com.example.demo.DTO;

public class NameSearchDTO {
    private String choice;

    public NameSearchDTO(String choice) {
        this.choice = choice;
    }

    public NameSearchDTO(){

    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
