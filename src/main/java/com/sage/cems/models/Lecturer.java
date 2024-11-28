package com.sage.cems.models;

import com.sage.cems.models.user.Role;
import com.sage.cems.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class Lecturer extends User {

    private List<Course> courses;
    private List<Report> reports;

    public Lecturer(){}

    public Lecturer(String ID, String password, String firstName, String lastName,
                    String email, String phoneNumber, Role role, ArrayList<Course> courses,List<Report> reports) {
        super(ID,password,firstName,lastName,email,phoneNumber,role);
        this.courses = courses;
        this.reports = reports;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
