package courseworks.model;

import courseworks.sql.*;

import java.util.List;

public class Student {

    public String uni;
    public String name;

    public boolean enrollInCourse(int course_id) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.enrollStudentInCourse(uni, course_id);
    }

    public boolean unEnrollFromCourse(int course_id) {
        ICourseworksWriter wtr = new CourseworksWriter();
        return wtr.unEnrollStudentFromCourse(uni, course_id);
    }
}

