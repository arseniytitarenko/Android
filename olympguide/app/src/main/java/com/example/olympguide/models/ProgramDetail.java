package com.example.olympguide.models;

import java.util.List;

public class ProgramDetail {
    private int program_id;
    private String name;
    private String field;
    private int budget_places;
    private int paid_places;
    private int cost;
    private List<String> required_subjects;
    private List<String> optional_subjects;
    private String link;


    public int getProgram_id() { return program_id; }
    public void setProgram_id(int program_id) { this.program_id = program_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public int getBudget_places() { return budget_places; }
    public void setBudget_places(int budget_places) { this.budget_places = budget_places; }

    public int getPaid_places() { return paid_places; }
    public void setPaid_places(int paid_places) { this.paid_places = paid_places; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public List<String> getRequired_subjects() { return required_subjects; }
    public void setRequired_subjects(List<String> required_subjects) { this.required_subjects = required_subjects; }

    public List<String> getOptional_subjects() { return optional_subjects; }
    public void setOptional_subjects(List<String> optional_subjects) { this.optional_subjects = optional_subjects; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}