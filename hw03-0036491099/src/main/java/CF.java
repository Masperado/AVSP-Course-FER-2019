import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class CF {

    public static void main(String[] args) throws IOException {

        // TODO: TEST
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("simple.in")));

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

        int m = Integer.parseInt(linije[0].split(" ")[1]);

        double[][] matrixII = new double[n][m];
        double[][] matrixUU = new double[m][n];

        for (int i = 1; i < n + 1; i++) {
            String[] linija = linije[i].split(" ");
            for (int j = 0; j < m; j++) {
                if (linija[j].equals("X")) {
                    matrixII[i - 1][j] = 0;
                    matrixUU[j][i - 1] = 0;
                } else {
                    matrixII[i - 1][j] = Double.parseDouble(linija[j]);
                    matrixUU[j][i - 1] = Double.parseDouble(linija[j]);
                }
            }
        }

        double[][] matrixIInorm = new double[n][m];
        double[][] matrixUUnorm = new double[m][n];

        for (int i = 0; i < n; i++) {
            double temp = 0;
            int cnt = 0;
            for (int j = 0; j < m; j++) {
                if (matrixII[i][j] > 0) {
                    temp += matrixII[i][j];
                    cnt++;
                }
            }
            if (cnt != 0) {
                temp /= cnt;
            }
            for (int j = 0; j < m; j++) {
                if (matrixII[i][j] > 0) {
                    matrixIInorm[i][j] = matrixII[i][j] - temp;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            double temp = 0;
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (matrixUU[i][j] > 0) {
                    temp += matrixUU[i][j];
                    cnt++;
                }
            }
            if (cnt != 0) {
                temp /= cnt;
            }
            for (int j = 0; j < n; j++) {
                if (matrixUU[i][j] > 0) {

                    matrixUUnorm[i][j] = matrixUU[i][j] - temp;
                }
            }
        }

        int q = Integer.parseInt(linije[n + 1]);


        for (int i = 0; i < q; i++) {
            String[] dijelovi = linije[n + 2 + i].split(" ");
            int I = Integer.parseInt(dijelovi[0]);
            int J = Integer.parseInt(dijelovi[1]);
            int T = Integer.parseInt(dijelovi[2]);
            int K = Integer.parseInt(dijelovi[3]);

            double procjena = 0;

            if (T == 0) {
                procjena = procjeni(matrixII, matrixIInorm,I-1, J-1, K);
            } else {
                procjena = procjeni(matrixUU, matrixUUnorm, J-1, I-1, K);
            }

            DecimalFormat df = new DecimalFormat("#.000");
            BigDecimal bd = new BigDecimal(procjena);
            BigDecimal res = bd.setScale(3, RoundingMode.HALF_UP);
            System.out.println(df.format(res));
        }

    }

    private static double procjeni(double[][] matrix, double[][] matrixNorm, int I, int J, int k) {
        List<Par> similarity = new ArrayList<>();

        double[] row = matrixNorm[I];
        double rowNorm = norm(row);

        for (int i = 0; i < matrix.length; i++) {
            similarity.add(new Par(i, dotProduct(matrixNorm[i], row) / Math.sqrt(norm(matrixNorm[i]) * rowNorm)));
        }

        similarity.sort((o1, o2) -> Double.compare(o2.sim, o1.sim));


        double naz = 0;
        double broj = 0;
        for (int i = 0; i < k; i++) {
            if (similarity.get(i + 1).sim > 0) {
                double r = matrix[similarity.get(i+1).index][J];
                if (r>0){
                    broj+=r*similarity.get(i+1).sim;
                    naz+=similarity.get(i+1).sim;
                } else {
                    k++;
                }
            }
        }

        return broj / naz;

    }

    public static double dotProduct(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static double norm(double[] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * a[i];
        }
        return sum;
    }

    private static class Par {

        private int index;
        private double sim;

        public Par(int index, double sim) {
            this.index = index;
            this.sim = sim;
        }

    }
}
