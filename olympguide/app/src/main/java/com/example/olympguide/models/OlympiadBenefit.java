package com.example.olympguide.models;

import java.util.List;

public class OlympiadBenefit {
    private List<Benefit> benefits;
    private ProgramItem program;

    public List<Benefit> getBenefits() { return benefits; }
    public void setBenefits(List<Benefit> benefits) { this.benefits = benefits; }

    public ProgramItem getProgram() { return program; }
    public void setProgram(ProgramItem program) { this.program = program; }
}