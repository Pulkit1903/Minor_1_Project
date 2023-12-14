import java.util.*;

public class One {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the start node: ");
        String startNodeValue = scanner.nextLine();

        System.out.println("Enter the goal node: ");
        String goalNodeValue = scanner.nextLine();

        // Initialize the graph based on the Romania map
        Node n1 = new Node("Manhole-1", 366);
        Node n2 = new Node("Manhole-2", 374);
        Node n3 = new Node("Manhole-3", 380);
        Node n4 = new Node("Manhole-4", 253);
        Node n5 = new Node("Manhole-5", 178);
        Node n6 = new Node("Manhole-6", 193);
        Node n7 = new Node("Manhole-7", 98);
        Node n8 = new Node("Manhole-8", 329);
        Node n9 = new Node("Manhole-9", 244);
        Node n10 = new Node("Manhole-10", 241);
        Node n11 = new Node("Manhole-11", 242);
        Node n12 = new Node("Manhole-12", 160);
        Node n13 = new Node("Manhole-13", 0);
        Node n14 = new Node("Manhole-14", 77);

        // Initialize the edges
        n1.adjacencies = new Edge[]{
                new Edge(n2, 75),
                new Edge(n4, 140),
                new Edge(n8, 118)
        };

        n2.adjacencies = new Edge[]{
                new Edge(n1, 75),
                new Edge(n3, 71)
        };

        n3.adjacencies = new Edge[]{
                new Edge(n2, 71),
                new Edge(n4, 151)
        };

        n4.adjacencies = new Edge[]{
                new Edge(n1, 140),
                new Edge(n5, 99),
                new Edge(n3, 151),
                new Edge(n6, 80),
        };

        n5.adjacencies = new Edge[]{
                new Edge(n4, 99),
                new Edge(n13, 211)
        };

        n6.adjacencies = new Edge[]{
                new Edge(n4, 80),
                new Edge(n7, 97),
                new Edge(n12, 146)
        };

        n7.adjacencies = new Edge[]{
                new Edge(n6, 97),
                new Edge(n13, 101),
                new Edge(n12, 138)
        };

        n8.adjacencies = new Edge[]{
                new Edge(n1, 118),
                new Edge(n9, 111)
        };

        n9.adjacencies = new Edge[]{
                new Edge(n8, 111),
                new Edge(n10, 70)
        };

        n10.adjacencies = new Edge[]{
                new Edge(n9, 70),
                new Edge(n11, 75)
        };

        n11.adjacencies = new Edge[]{
                new Edge(n10, 75),
                new Edge(n12, 120)
        };

        n12.adjacencies = new Edge[]{
                new Edge(n11, 120),
                new Edge(n6, 146),
                new Edge(n7, 138)
        };

        n13.adjacencies = new Edge[]{
                new Edge(n7, 101),
                new Edge(n14, 90),
                new Edge(n5, 211)
        };

        n14.adjacencies = new Edge[]{
                new Edge(n13, 90)
        };

        Node startNode = null;
        Node goalNode = null;

        for (Node node : new Node[]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14}) {
            if (node.value.equals(startNodeValue)) {
                startNode = node;
            }
            if (node.value.equals(goalNodeValue)) {
                goalNode = node;
            }
        }

        if (startNode == null || goalNode == null) {
            System.out.println("Invalid start or goal node. Exiting program.");
            return;
        }

        AstarSearch(startNode, goalNode);

        List<Node> path = printPath(goalNode);

        System.out.println("Path: " + path);
    }

    private static Node getNode(String nodeValue) {
        Node[] nodes = {
                new Node("Manhole-1", 366),
                new Node("Manhole-2", 374),
                new Node("Manhole-3", 380),
                new Node("Manhole-4", 253),
                new Node("Manhole-5", 178),
                new Node("Manhole-6", 193),
                new Node("Manhole-7", 98),
                new Node("Manhole-8", 329),
                new Node("Manhole-9", 244),
                new Node("Manhole-10", 241),
                new Node("Manhole-11", 242),
                new Node("Manhole-12", 160),
                new Node("Manhole-13", 0),
                new Node("Manhole-14", 77)
        };

        for (Node node : nodes) {
            if (node.value.equals(nodeValue)) {
                return node;
            }
        }

        System.out.println("Node not found: " + nodeValue);
        return null;
    }

    public static List<Node> printPath(Node target) {
        List<Node> path = new ArrayList<>();

        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    public static void AstarSearch(Node source, Node goal) {
        Set<Node> explored = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f_scores));

        source.g_scores = 0;
        queue.add(source);

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Node current = queue.poll();
            explored.add(current);

            if (current.value.equals(goal.value)) {
                found = true;
            }

            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;

                if (explored.contains(child) && temp_f_scores >= child.f_scores) {
                    continue;
                }

                if (!queue.contains(child) || temp_f_scores < child.f_scores) {
                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);
                }
            }
        }
    }
}

class Node {
    public final String value;
    public double g_scores;
    public final double h_scores;
    public double f_scores = 0;
    public Edge[] adjacencies;
    public Node parent;

    public Node(String val, double hVal) {
        value = val;
        h_scores = hVal;
    }

    public String toString() {
        return value;
    }
}

class Edge {
    public final double cost;
    public final Node target;

    public Edge(Node targetNode, double costVal) {
        target = targetNode;
        cost = costVal;
    }
}