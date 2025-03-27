package com.example.olympguide.models;

import java.util.List;

public class ProgramBenefit {
    private List<Benefit> benefits;
    private Olympiad olympiad;

    public List<Benefit> getBenefits() { return benefits; }
    public void setBenefits(List<Benefit> benefits) { this.benefits = benefits; }

    public Olympiad getOlympiad() { return olympiad; }
    public void setOlympiad(Olympiad olympiad) { this.olympiad = olympiad; }
}

