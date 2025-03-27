package com.example.olympguide.models;

public class OlympiadDetail {
    private int olympiad_id;
    private String name;
    private int level;
    private String profile;
    private String description;
    private String link;

    public int getOlympiad_id() { return olympiad_id; }
    public void setOlympiad_id(int olympiad_id) { this.olympiad_id = olympiad_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
