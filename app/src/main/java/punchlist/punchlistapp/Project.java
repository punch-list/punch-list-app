package punchlist.punchlistapp;

/**
 * Created by joshg on 11/19/16.
 */

public class Project {
    private String name;
    private Schematic schematic;

    public Project(String name, Schematic schematic) {
        this.name = name;
        this.schematic = schematic;
    }

    public String getName() {
        return name;
    }

    public Schematic getSchematic() {
        return schematic;
    }
}
