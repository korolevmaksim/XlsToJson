package com.converter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

/**
 * Created by maksim on 14.11.15.
 */
public class Area implements Serializable, Comparable{

    private String name;
    private Set<String> city;

    public Area() {
    }

    public Area(String name, Set<String> city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getCity() {
        if(null!=this.city) {
            return city;
        }else{
            return new TreeSet<String>();
        }
    }

    public void setCity(Set<String> city) {
        this.city = city;
    }


    public int compareTo(Object o) {
        Area a = (Area) o;

        return this.name.compareTo(a.getName());
    }
}
