package com.converter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

/**
 * Created by maksim on 14.11.15.
 */
public class Region implements Serializable {

    private String name;
    private Set<Area> area;

    public Region(String name, Set<Area> area) {
        this.name = name;
        this.area = area;
    }

    public Region() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Area> getArea() {
        if(null!= this.area) {
            return area;
        }else{
            return new TreeSet<Area>();
        }
    }

    public void setArea(Set<Area> area) {
        this.area = area;
    }
}
