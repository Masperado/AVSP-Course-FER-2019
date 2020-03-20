import java.util.*;

public class Peti {

    private Map<String, TreeSet<Integer>> grades= new LinkedHashMap<>();

    public void addStudentScores(String student, Integer... scores){

        if (!grades.containsKey(student)){
            grades.put(student, new TreeSet<>());
        }

        grades.get(student).addAll(Arrays.asList(scores));
    }

    public Collection<String> getInsertionOrderedStudents(){
        return grades.keySet();
    }

    public Collection<Integer> getNaturallySortedPointsForStudent(String student){
        return grades.get(null);
    }


    public static void main(String[] args) {

        Peti peti = new Peti();

        peti.addStudentScores("Mile",4,2);
        peti.addStudentScores("Mile",5);

        peti.addStudentScores("Branko",4);
        peti.addStudentScores("Ante",2);

        System.out.println(peti.getInsertionOrderedStudents());

        System.out.println(peti.getNaturallySortedPointsForStudent("Mile"));
    }


}
