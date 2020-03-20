import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class SimHash {

    public static int[] simHash(String tekst) {
        int[] sh = new int[128];
        String[] jedinke = tekst.split("\\s+");

        for (String jedinka: jedinke){
            byte[] digest = DigestUtils.md5(jedinka.getBytes(StandardCharsets.UTF_8));

            for (int i=0;i<16;i++){
                for (int j=7;j>=0;j--){
                    if ((digest[i]>>j & 1)==1){
                        sh[7-j+i*8]++;
                    } else {
                        sh[7-j+i*8]--;
                    }
                }

            }

        }

        for (int i=0;i<128;i++){
            if (sh[i]>=0){
                sh[i]=1;
            } else {
                sh[i]=0;
            }
        }


        return sh;


    }

    public static String bitToHex(int[] sh){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<128;i=i+4) {
            int temp = 8*sh[i]+4*sh[i+1]+2*sh[i+2]+sh[i+3];
            if (temp==15){
                sb.append("f");
            } else if (temp==14){
                sb.append("e");
            } else if (temp==13){
                sb.append("d");
            } else if (temp==12){
                sb.append("c");
            }else if (temp==11){
                sb.append("b");
            }else if (temp==10){
                sb.append("a");
            } else {
                sb.append(temp);
            }
        }
        return sb.toString();

    }


    public static void main(String[] args) throws IOException {


        // TODO: TEST
//         BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("R1.in")));

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

        int n = Integer.parseInt(linije[0]);

        List<int[]> simHashes = new ArrayList<>();

        for (int i=0;i<n;i++){
            simHashes.add(simHash(linije[i+1]));
        }


        for (int j=n+2;j<linije.length;j++){
            String[] parts = linije[j].split(" ");
            int i = Integer.parseInt(parts[0]);
            int k = Integer.parseInt(parts[1]);

            int similars = findSimilars(simHashes,i,k);

            System.out.println(similars);
        }


    }

    private static int findSimilars(List<int[]> simHashes, int i, int k) {
        int counter = 0;
        int[] target = simHashes.get(i);

        OUTER:
        for (int j=0;j<simHashes.size();j++){
            if (i==j) continue;

            int[] candidate = simHashes.get(j);
            int hammingCounter = 0;

            for (int l=0;l<128;l++){
                if (candidate[l]!=target[l]) hammingCounter++;
                if (hammingCounter>k){
                    continue OUTER;
                }

            }

            counter++;

        }

        return counter;
    }


}
