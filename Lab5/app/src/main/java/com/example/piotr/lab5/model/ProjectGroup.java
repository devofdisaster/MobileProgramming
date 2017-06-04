package com.example.piotr.lab5.model;

public class ProjectGroup {
    private long id;
    private String name;

    public ProjectGroup(String name) {
        this.name = name;
    }
    public ProjectGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectGroup that = (ProjectGroup) o;

        return id == that.id && getName().equals(that.getName());

    }

    @Override
    public int hashCode() {
        int result = (int) id;

        result = 31 * result + getName().hashCode();

        return result;
    }

    @Override
    public String toString() {
        return getName();
    }
}
