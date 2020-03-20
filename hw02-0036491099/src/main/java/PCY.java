import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class PCY {

    public static void main(String[] args) throws IOException {

        // TODO: TEST
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("R1.in")));

        // Citanje ulaza
        // TODO: ACTUAL
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
            sb.append("\n");
        }
        br.close();
        String ulaz = sb.toString();
        String[] linije = ulaz.split("\n");

        int brKosara = Integer.parseInt(linije[0]);

        int brPretinaca = Integer.parseInt(linije[2]);

        double s = Double.parseDouble(linije[1]);

        int prag = (int) Math.floor(s * brKosara);

        Map<Integer, Integer> brPredmeta = new HashMap<>();


        for (int i = 3; i < brKosara + 3; i++) {
            String[] dijelovi = linije[i].split(" ");

            for (String dio : dijelovi) {
                int predmet = Integer.parseInt(dio);

                if (brPredmeta.containsKey(predmet)) {
                    int cnt = brPredmeta.get(predmet);
                    brPredmeta.put(predmet, cnt + 1);
                } else {
                    brPredmeta.put(predmet, 1);
                }
            }
        }

        int brPredmetaVelicina = brPredmeta.keySet().size();


        Map<Integer, Integer> pretinci = new HashMap<>();

        for (int i = 3; i < brKosara + 3; i++) {
            String[] dijelovi = linije[i].split(" ");

            for (int iDio=1;iDio<dijelovi.length; iDio++){
                int predmet1 = Integer.parseInt(dijelovi[iDio]);
                int br1 = brPredmeta.get(predmet1);
                if (br1 >= prag) {
                    for (int jDio=0;jDio<iDio;jDio++) {
                        int predmet2 = Integer.parseInt(dijelovi[jDio]);
                        int br2 = brPredmeta.get(predmet2);
                        if (br2 >= prag) {
                            int k = (predmet1 * brPredmetaVelicina + predmet2) % brPretinaca;
                            if (pretinci.containsKey(k)) {
                                int cnt = pretinci.get(k);
                                pretinci.put(k, cnt + 1);
                            } else {
                                pretinci.put(k, 1);
                            }
                        }
                    }
                }
            }
        }

        Map<Par, Integer> parovi = new HashMap<>();

        for (int i = 3; i < brKosara + 3; i++) {
            String[] dijelovi = linije[i].split(" ");

            for (int iDio=1;iDio<dijelovi.length; iDio++){
                int predmet1 = Integer.parseInt(dijelovi[iDio]);
                int br1 = brPredmeta.get(predmet1);
                if (br1 >= prag) {
                    for (int jDio=0;jDio<iDio;jDio++) {
                        int predmet2 = Integer.parseInt(dijelovi[jDio]);
                        int br2 = brPredmeta.get(predmet2);                        if (br2 >= prag) {
                            int k = (predmet1 * brPredmetaVelicina + predmet2) % brPretinaca;
                            if (pretinci.get(k)>=prag){
                                Par key = new Par(predmet1,predmet2);
                                if (parovi.containsKey(key)){
                                    int cnt = parovi.get(key);
                                    parovi.put(key,cnt+1);
                                } else {
                                    parovi.put(key,1);
                                }
                            }

                        }
                    }
                }
            }
        }

        StringBuilder sb1 = new StringBuilder();

        int brojPredmetaVeciOdPraga =
                (int) brPredmeta.keySet().stream().filter(integer-> brPredmeta.get(integer)>=prag).count();

        sb1.append(brojPredmetaVeciOdPraga*(brojPredmetaVeciOdPraga-1)/2);
        sb1.append("\n");
        sb1.append(parovi.size());
        sb1.append("\n");

        List<Integer> valueSet =
                parovi.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        for (Integer value : valueSet){
            sb1.append(value);
            sb1.append("\n");
        }
        Files.write(Paths.get("a.out"),sb1.toString().getBytes(), StandardOpenOption.CREATE);
//        System.out.println(sb1.toString());
    }

    private static class Par {

        private int x;

        private int y;

        Par(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Par par = (Par) o;
            return x == par.x &&
                    y == par.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
