import java.util.*;

public class Graph {
    private Map<String, List<Edge>> adjList = new HashMap<>();

    public void addEdge(String src, String dest, int weight) {
        if (!adjList.containsKey(src)) {
            adjList.put(src, new ArrayList<>());
        }
        adjList.get(src).add(new Edge(dest, weight));

        if (!adjList.containsKey(dest)) {
            adjList.put(dest, new ArrayList<>());
        }
        adjList.get(dest).add(new Edge(src, weight));
    }

    public void dijkstra(String src, String dest) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        for (String vertex : adjList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(src, 0);
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            String current = currentNode.name;

            if (current.equals(dest)) {
                printPath(previous, dest);
                System.out.println("Total Distance: " + distances.get(dest));
                return;
            }

            for (Edge edge : adjList.getOrDefault(current, new ArrayList<>())) {
                String neighbor = edge.dest;
                int newDist = distances.get(current) + edge.weight;

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    pq.add(new Node(neighbor, newDist));
                }
            }
        }
        System.out.println("No path exists between " + src + " and " + dest);
    }

    private void printPath(Map<String, String> previous, String dest) {
        if (previous.get(dest) != null) {
            printPath(previous, previous.get(dest));
        }
        System.out.print(dest + "-> ");
    }

    private static class Node {
        String name;
        int distance;

        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }

    private static class Edge {
        String dest;
        int weight;

        Edge(String dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    public void initializeGraph() {
        addEdge("Sum Hospital", "Patrapada", 8);
        addEdge("Sum Hospital", "Khandagiri", 6);
        addEdge("Sum Hospital", "Fire Station", 4);
        addEdge("Patrapada", "Khandagiri", 3);
        addEdge("Patrapada", "Jagamara", 5);
        addEdge("Khandagiri", "Fire Station", 3);
        addEdge("Khandagiri", "Jagamara", 2);
        addEdge("Fire Station", "Soubhagya Nagar", 1);
        addEdge("Fire Station", "Jaydev Bihar", 4);
        addEdge("Jagamara", "Soubhagya Nagar", 4);
        addEdge("Jagamara", "Pokhariput", 4);
        addEdge("Soubhagya Nagar", "Airport", 3);
        addEdge("Pokhariput", "Lingaraj Temple", 5);
        addEdge("Pokhariput", "Airport", 3);
        addEdge("Airport", "Accountant General", 2);
        addEdge("Airport", "Lingaraj Temple", 5);
        addEdge("Accountant General", "Acharya Bihar", 3);
        addEdge("Accountant General", "Jaydev Bihar", 4);
        addEdge("Accountant General", "Lingaraj Temple", 6);
        addEdge("Lingaraj Temple", "Acharya Bihar", 8);
        addEdge("Jaydev Bihar", "Kalinga Hospital", 5);
        addEdge("Jaydev Bihar", "Acharya Bihar", 1);
        addEdge("Acharya Bihar", "Kalinga Hospital", 4);

        String[] OlaCharge_Location = {
            "Sum Hospital", "Patrapada", "Khandagiri", "Fire Station"
        };
        for (String location : OlaCharge_Location) {
            addEdge(location, "Ola Hypercharge", 5);
        }

        String[] TataCharge_Locations = { 
            "Jagamara", "Soubhagya Nagar", "Jaydev Bihar", "Pokhariput"
        };
        for (String location : TataCharge_Locations) {
            addEdge(location, "Tata Power", 5);
        }

        String[] HyundaiCharge_Locations = {
            "Lingaraj Temple", "Kalinga Hospital"
        };
        for (String location : HyundaiCharge_Locations) {
            addEdge(location, "Hyundai Fastcharge", 5);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph g = new Graph();
        g.initializeGraph();

        System.out.print("Enter your current location: ");
        String source = scanner.nextLine().trim();

        System.out.println("Which EV charging station do you want to go to?");
        System.out.println("1. Ola Hypercharge");
        System.out.println("2. Tata Power");
        System.out.println("3. Hyundai Fastcharge");
        int choice;
        
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            return;
        }
        
        String destination;
        switch (choice) {
            case 1:
                destination = "Ola Hypercharge";
                break;
            case 2:
                destination = "Tata Power";
                break;
            case 3:
                destination = "Hyundai Fastcharge";
                break;
            default:
                System.out.println("Invalid choice. Exiting.");
                return;
        }

        g.dijkstra(source, destination);

        System.out.print("Enter the total distance displayed by the algorithm: ");
        double distance;
        
        try {
            distance = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
            return;
        }
        
        double speed = 40.0;
        double time = distance / speed;

        System.out.printf("Minimum time to reach: %.2f hours\n", time);
    }
}
