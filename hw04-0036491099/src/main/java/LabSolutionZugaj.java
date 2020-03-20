import java.util.*;

public class LabSolutionZugaj {

    private Map<String, Integer> students = new HashMap<>();


    void addStudentPoints(String student, Integer... points) {


        for (Integer point : points) {
            students.merge(student, point, Integer::sum);
        }

    }


    Integer getTotalPoints() {

        return students.values().stream().reduce(0, Integer::sum);

    }

    Integer getStudentSize() {
        return students.size();
    }

    Integer getTotalPointsForStudent(String student) {
        return students.get(student);
    }


    public static void main(String[] args) {
        LabSolutionZugaj zugaj = new LabSolutionZugaj();

        zugaj.addStudentPoints("Luka",1,2);
        zugaj.addStudentPoints("Mile",4);
        zugaj.addStudentPoints("Luka",10);

        System.out.println(zugaj.getTotalPoints());
        System.out.println(zugaj.getStudentSize());
        System.out.println(zugaj.getTotalPointsForStudent("Luka"));
    }

}