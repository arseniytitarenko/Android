package com.example.olympguide.models;

import java.util.List;

public class ProgramGroup {
    private int faculty_id;
    private String name;
    private List<ProgramItem> programs;

    public int getFaculty_id() { return faculty_id; }
    public void setFaculty_id(int faculty_id) { this.faculty_id = faculty_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ProgramItem> getPrograms() { return programs; }
    public void setPrograms(List<ProgramItem> programs) { this.programs = programs; }
}
