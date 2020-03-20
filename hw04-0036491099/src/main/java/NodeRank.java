import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.*;

public class NodeRank {

    public static void main(String[] args) throws IOException {

        // TODO: TEST
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("mtest2/R.in")));

        // Citanje ulaza
        // TODO: ACTUAL
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
            sb.append("\n");
        }
        br.close();
        String ulaz = sb.toString();
        String[] linije = ulaz.split("\n");

        int n = Integer.parseInt(linije[0].split(" ")[0]);

        double beta = Double.parseDouble(linije[0].split(" ")[1]);

        List<List<Integer>> adjMatrix = new ArrayList<>();


        for (int i = 1; i < n + 1; i++) {
            String[] linija = linije[i].split(" ");
            List<Integer> nextNodes = new ArrayList<>();
            for (String s : linija) {
                nextNodes.add(Integer.parseInt(s));
            }
            adjMatrix.add(nextNodes);
        }


        int q = Integer.parseInt(linije[n + 1]);

        List<List<Double>> ranks = new ArrayList<>();

        List<Double> zeroRank = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            zeroRank.add(1.0 / n);
        }

        ranks.add(zeroRank);

//        StringBuilder sb1 = new StringBuilder();

        for (int i = n + 2; i < n + 2 + q; i++) {
            double procjena = 0;

            String[] linija = linije[i].split(" ");
            int index = Integer.parseInt(linija[0]);
            int iter = Integer.parseInt(linija[1]);

            while (iter >= ranks.size()) {
                List<Double> currentRank = ranks.get(ranks.size() - 1);
                List<Double> nextRank = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    nextRank.add((1 - beta) / n);
                }
                for (int k=0;k<n;k++) {
                    List<Integer> nextNodes = adjMatrix.get(k);
                    for (Integer next : nextNodes) {
                        double temp = nextRank.get(next);
                        temp += beta * currentRank.get(k) / nextNodes.size();
                        nextRank.set(next, temp);
                    }
                }
                ranks.add(nextRank);
            }

            procjena = ranks.get(iter).get(index);


            DecimalFormat df = new DecimalFormat("0.0000000000");
            BigDecimal bd = new BigDecimal(procjena);
            BigDecimal res = bd.setScale(10, RoundingMode.HALF_UP);
            System.out.println(df.format(res));

//            sb1.append(df.format(res)).append("\n");
        }


//        Files.write(Paths.get("a.out"),sb1.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);


    }
}
