import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class SimHashBuckets {

    public static int[] simHash(String tekst) {
        int[] sh = new int[128];
        String[] jedinke = tekst.split("\\s+");

        for (String jedinka : jedinke) {
            byte[] digest = DigestUtils.md5(jedinka.getBytes(StandardCharsets.UTF_8));

            for (int i = 0; i < 16; i++) {
                for (int j = 7; j >= 0; j--) {
                    if ((digest[i] >> j & 1) == 1) {
                        sh[7 - j + i * 8]++;
                    } else {
                        sh[7 - j + i * 8]--;
                    }
                }

            }

        }

        for (int i = 0; i < 128; i++) {
            if (sh[i] >= 0) {
                sh[i] = 1;
            } else {
                sh[i] = 0;
            }
        }


        return sh;


    }

    public static String bitToHex(int[] sh) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 128; i = i + 4) {
            int temp = 8 * sh[i] + 4 * sh[i + 1] + 2 * sh[i + 2] + sh[i + 3];
            if (temp == 15) {
                sb.append("f");
            } else if (temp == 14) {
                sb.append("e");
            } else if (temp == 13) {
                sb.append("d");
            } else if (temp == 12) {
                sb.append("c");
            } else if (temp == 11) {
                sb.append("b");
            } else if (temp == 10) {
                sb.append("a");
            } else {
                sb.append(temp);
            }
        }
        return sb.toString();

    }


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

        int n = Integer.parseInt(linije[0]);

        List<int[]> simHashes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            simHashes.add(simHash(linije[i + 1]));
        }

        Map<Integer, Set<Integer>> kandidati = stvoriMapuKandidata(simHashes);

        StringBuilder sb1 = new StringBuilder();

        for (int j = n + 2; j < linije.length; j++) {
            String[] parts = linije[j].split(" ");
            int i = Integer.parseInt(parts[0]);
            int k = Integer.parseInt(parts[1]);

            int similars = findSimilars(simHashes, i, k, kandidati);


//            System.out.println(similars);
            sb1.append(similars+"\n");
        }

        Files.write(Paths.get("a.out"),sb1.toString().getBytes(), StandardOpenOption.CREATE);


    }

    private static Map<Integer, Set<Integer>> stvoriMapuKandidata(List<int[]> simHashes) {

        Map<Integer, Set<Integer>> kandidati = new HashMap<>();

        for (int i = 0; i < simHashes.size(); i++) {
            kandidati.put(i, new HashSet<>());
        }

        for (int i = 0; i < 8; i++) {
            Map<Integer, Set<Integer>> pretinci = new HashMap<>();

            for (int trenutniId = 0; trenutniId < simHashes.size(); trenutniId++) {
                int[] hash = simHashes.get(trenutniId);

//                StringBuilder sb = new StringBuilder();
//                for (int k=0;k<16;k++){
//                    sb.append(hash[i*8+k]);
//                }
//
//                int val = Integer.parseInt(sb.toString(),2);

                int val =
                        32768 * hash[i * 16] + 16384 * hash[i * 16 + 1] + 8192 * hash[i * 16 + 2] + 4096 * hash[i * 16 + 3] + 2048 * hash[i * 16 + 4] + 1024 * hash[i * 16 + 5] + 512 * hash[i * 16 + 6] + 256 * hash[i * 16 + 7] + 128 * hash[i * 16 + 8] + 64 * hash[i * 16 + 9] + 32 * hash[i * 16 + 10] + 16 * hash[i * 16 + 11] + 8 * hash[i * 16 + 12] + 4 * hash[i * 16 + 13] + 2 * hash[i * 16 + 14] + hash[i * 16 + 15];


                if (pretinci.containsKey(val)) {
                    Set<Integer> tekstoviUPretincu = pretinci.get(val);
                    for (Integer tekstId : tekstoviUPretincu) {
                        kandidati.get(tekstId).add(trenutniId);
                        kandidati.get(trenutniId).add(tekstId);
                    }
                } else {
                    pretinci.put(val, new HashSet<>());
                }

                pretinci.get(val).add(trenutniId);

            }

        }


        return kandidati;

    }

    private static int findSimilars(List<int[]> simHashes, int i, int k, Map<Integer, Set<Integer>> kandidati) {
        int counter = 0;
        int[] target = simHashes.get(i);

        Set<Integer> potencijalni = kandidati.get(i);

        OUTER:
        for (Integer j: potencijalni) {
            if (i == j) continue;

            int[] candidate = simHashes.get(j);
            int hammingCounter = 0;

            for (int l = 0; l < 128; l++) {
                if (candidate[l] != target[l]) hammingCounter++;
                if (hammingCounter > k) {
                    continue OUTER;
                }

            }

            counter++;

        }

        return counter;
    }


}
