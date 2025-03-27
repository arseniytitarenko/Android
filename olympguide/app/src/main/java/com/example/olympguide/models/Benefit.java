package com.example.olympguide.models;

import java.util.List;

public class Benefit {
    private List<ConfirmationSubject> confirmation_subjects;
    private List<String> full_score_subjects;
    private boolean is_bvi;
    private int min_class;
    private int min_diploma_level;

    public List<ConfirmationSubject> getConfirmation_subjects() { return confirmation_subjects; }
    public void setConfirmation_subjects(List<ConfirmationSubject> confirmation_subjects) { this.confirmation_subjects = confirmation_subjects; }

    public List<String> getFull_score_subjects() { return full_score_subjects; }
    public void setFull_score_subjects(List<String> full_score_subjects) { this.full_score_subjects = full_score_subjects; }

    public boolean isIs_bvi() { return is_bvi; }
    public void setIs_bvi(boolean is_bvi) { this.is_bvi = is_bvi; }

    public int getMin_class() { return min_class; }
    public void setMin_class(int min_class) { this.min_class = min_class; }

    public int getMin_diploma_level() { return min_diploma_level; }
    public void setMin_diploma_level(int min_diploma_level) { this.min_diploma_level = min_diploma_level; }
}
