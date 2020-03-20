import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ClosestBlackNode {

    public static void main(String[] args) throws IOException {

        // TODO: TEST
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("B/stest2/R.in")));
//
//         Citanje ulaza
//         TODO: ACTUAL
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
            sb.append("\n");
        }
        br.close();
        String ulaz = sb.toString();
        String[] linije = ulaz.split("\n");

        int n = Integer.parseInt(linije[0].split(" ")[0]);

        int e = Integer.parseInt(linije[0].split(" ")[1]);


        List<Node> nodes = new ArrayList<>();

        List<Node> blackNodes = new ArrayList<>();


        for (int i = 1; i < n + 1; i++) {
            int black = Integer.parseInt(linije[i]);
            if (black == 0) {
                nodes.add(new Node(i - 1, false));
            } else {
                nodes.add(new Node(i - 1, true));
                blackNodes.add(new Node(i - 1, true));
            }
        }

        List<Set<Integer>> adjMatrix = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjMatrix.add(new HashSet<>());
        }


        for (int i = n + 1; i < n + 1 + e; i++) {
            String[] linija = linije[i].split(" ");
            int from = Integer.parseInt(linija[0]);
            int to = Integer.parseInt(linija[1]);

            adjMatrix.get(from).add(to);
            adjMatrix.get(to).add(from);
        }

        for (Node node : blackNodes) {
            boolean[] visited = new boolean[n];

            LinkedList<Par> queue = new LinkedList<>();

            visited[node.index] = true;
            queue.add(new Par(node, 0));

            while (queue.size() != 0) {
                Par par = queue.poll();

                if (par.getDistanceFromStart() > 10) {
                    continue;
                }

                Node nodePar = par.getNode();

                if (nodePar.isBlack() && par.getDistanceFromStart() != 0) {
                    continue;
                }

                if (nodePar.getDistanceToNearestBlack() == par.getDistanceFromStart()) {
                    if (nodePar.getNearestBlack() > node.index) {
                        nodePar.setNearestBlack(node.index);
                    }
                } else if (nodePar.getDistanceToNearestBlack() > par.getDistanceFromStart()) {
                    nodePar.setNearestBlack(node.index);
                    nodePar.setDistanceToNearestBlack(par.getDistanceFromStart());
                } else {
                    continue;
                }

                Set<Integer> neighbours = adjMatrix.get(nodePar.index);

                for (Integer index : neighbours) {
                    if (!visited[index]) {
                        visited[index] = true;
                        queue.add(new Par(nodes.get(index), par.getDistanceFromStart() + 1));
                    }
                }


            }

        }

//        StringBuilder sb1 = new StringBuilder();


        for (Node node : nodes) {
            if (node.getDistanceToNearestBlack() > 10) {
//                sb1.append("-1 -1\n");
                System.out.println("-1 -1");
            } else {
//                sb1.append(node.getNearestBlack() + " " + node.getDistanceToNearestBlack() + "\n");

                System.out.println(node.getNearestBlack() + " " + node.getDistanceToNearestBlack());
            }
        }


//        Files.write(Paths.get("a.out"), sb1.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);


    }

    private static class Par {
        private Node node;

        private int distanceFromStart;

        public Par(Node node, int distanceFromStart) {
            this.node = node;
            this.distanceFromStart = distanceFromStart;
        }

        public Node getNode() {
            return node;
        }

        public int getDistanceFromStart() {
            return distanceFromStart;
        }
    }

    private static class Node {
        private int index;

        private boolean black;

        private int nearestBlack;

        private int distanceToNearestBlack;

        public Node(int index, boolean black) {
            this.index = index;
            this.black = black;
            if (black) {
                this.nearestBlack = index;
                this.distanceToNearestBlack = 0;
            } else {
                this.nearestBlack = 20;
                this.distanceToNearestBlack = 20;
            }
        }

        public int getIndex() {
            return index;
        }

        public boolean isBlack() {
            return black;
        }

        public int getNearestBlack() {
            return nearestBlack;
        }

        public int getDistanceToNearestBlack() {
            return distanceToNearestBlack;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setBlack(boolean black) {
            this.black = black;
        }

        public void setNearestBlack(int nearestBlack) {
            this.nearestBlack = nearestBlack;
        }

        public void setDistanceToNearestBlack(int distanceToNearestBlack) {
            this.distanceToNearestBlack = distanceToNearestBlack;
        }
    }
}
