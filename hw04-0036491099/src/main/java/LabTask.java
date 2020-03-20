import java.time.LocalDate;
import java.util.*;


class LabTask {


    public static Collection<String> getMostPopularPeople(Collection<Pair> pairs) {
        Set<String> returnSet = new HashSet<>();

        if (pairs.isEmpty()){
            return returnSet;
        }

        Map<String, Integer> mapa = new HashMap<>();
        for (Pair counter : pairs) {
            mapa.merge(counter.getName1(), 1, Integer::sum);
            mapa.merge(counter.getName2(), 1, Integer::sum);
        }

        int maxBrojPrijatelja = mapa.entrySet().stream().map(Map.Entry::getValue).max(Integer::compareTo).get();

        for (var entry: mapa.entrySet()){
            if (entry.getValue()==maxBrojPrijatelja){
                returnSet.add(entry.getKey());
            }
        }

        return returnSet;

    }


    static class Pair implements Comparable<Pair> {

        public String name1;
        public String name2;

        public Pair(String name1, String name2) {
            this.name1 = name1;
            this.name2 = name2;
        }

        public String getName1() {
            return name1;
        }

        public String getName2() {
            return name2;
        }

        @Override
        public int compareTo(Pair o) {
            int i = this.getName1().compareTo(o.getName1());

            if (i != 0) {
                return i;
            } else {
                return this.getName2().compareTo(o.getName2());
            }
        }
    }

    public static void main(String[] args) {
        Map<String, LocalDate> birthDays = new HashMap<>();

        birthDays.put("Ana", LocalDate.of(1993, 5, 25));
        birthDays.put("Pero", LocalDate.of(1993, 1, 12));
        birthDays.put("Ivan", LocalDate.of(1945, 5, 9));


        System.out.println();
    }


    public static Collection<String> getTop3(Collection<PlayerScore> scores) {
        Set<PlayerScore> treeSet = new TreeSet<>((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        treeSet.addAll(scores);

        List<String> returnList = new ArrayList<>();

        for (PlayerScore score : treeSet) {
            if (returnList.size() == 3) {
                break;
            }

            returnList.add(score.name);

        }


        return returnList;


    }


    public static Set<String> theMostInformed(Map<String, Set<String>> map) {
        Set<String> returnSet = new TreeSet<>(Collections.reverseOrder());
        int mostNumbers = 0;

        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {

            if (returnSet.isEmpty()) {
                returnSet.add(entry.getKey());
                mostNumbers = entry.getValue().size();
            } else {
                if (entry.getValue().size() == mostNumbers) {
                    returnSet.add(entry.getKey());
                } else if (entry.getValue().size() > mostNumbers) {
                    returnSet.clear();
                    returnSet.add(entry.getKey());
                    mostNumbers = entry.getValue().size();
                }
            }


        }

        return returnSet;

    }


    public static List<Integer> insert(List<Integer> source, List<Integer> dest) {

        List<Integer> returnList = new ArrayList<>();

        for (Integer number : source) {

            boolean added = false;

            for (int i = 0; i < dest.size(); i++) {
                if (number < dest.get(i)) {
                    dest.add(i, number);
                    returnList.add(i);
                    added = true;
                    break;
                }
            }

            if (!added) {
                dest.add(number);
                returnList.add(dest.size() - 1);
            }

        }

        return returnList;

    }


    static class PlayerScore {

        private String name;

        private Integer score;

        public PlayerScore(String name, Integer score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public Integer getScore() {
            return score;
        }
    }

}