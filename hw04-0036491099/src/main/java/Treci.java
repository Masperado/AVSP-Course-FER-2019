import java.util.*;
import java.util.stream.Collectors;

public class Treci {

    private Map<StudentCourse, Integer> grades = new HashMap<>();

    public void addEntry(String student, String course, int score) {
        grades.put(new StudentCourse(student, course), score);
    }

    public Integer getScore(String student, String course) {
        return grades.get(new StudentCourse(student, course));
    }

    public Collection<Integer> getNaturallyOrderedScoresByCourse(String course) {

        return grades.entrySet().stream().filter(studentCourseIntegerEntry -> studentCourseIntegerEntry.getKey().getCourse().equals(course)).mapToInt(Map.Entry::getValue).sorted().boxed().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Treci treci = new Treci();

        treci.addEntry("Mile", "Delija", 4);
        treci.addEntry("Mile", "Delija", 5);
        treci.addEntry("Tome", "Delij", 5);
        treci.addEntry("Tome", "Delija", 3);

        System.out.println(treci.getScore("Mile", "Delija"));
        System.out.println(treci.getNaturallyOrderedScoresByCourse("aa"));
    }


    class StudentCourse {

        private String student;

        private String course;

        public StudentCourse(String student, String course) {
            this.student = student;
            this.course = course;
        }

        public String getStudent() {
            return student;
        }

        public String getCourse() {
            return course;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentCourse that = (StudentCourse) o;
            return Objects.equals(student, that.student) &&
                    Objects.equals(course, that.course);
        }

        @Override
        public int hashCode() {
            return Objects.hash(student, course);
        }
    }
}


