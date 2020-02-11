public class LargeBFS {

    public static String join(String[] strings) {
        String result = "";
        for (String string : strings) {
            result += "|" + string;
        }
        return result.substring(1);
    }

    public static int getNode(State state) {
        return Integer.parseInt(state.toString().split("\\|")[0]);
    }

    public static boolean passed() {
        String[] edges = {
            "0 (0): 6 (2), 36 (2), 49 (12)",
            "1 (0): 43 (8), 92 (4)",
            "2 (0):",
            "3 (0): 30 (7), 59 (8)",
            "4 (0):",
            "5 (0): 44 (11)",
            "6 (0): 68 (13)",
            "7 (0): 46 (10)",
            "8 (0): 9 (4), 68 (1)",
            "9 (0): 28 (10), 70 (2), 84 (8), 85 (12), 88 (12)",
            "10 (0): 41 (10), 74 (7), 91 (7)",
            "11 (0): 1 (15), 22 (7), 23 (8), 37 (11), 58 (1)",
            "12 (0): 16 (2), 57 (8), 63 (9), 86 (13)",
            "13 (0): 25 (6), 35 (14), 47 (5)",
            "14 (0):",
            "15 (0): 47 (3)",
            "16 (0):",
            "17 (0): 2 (13), 42 (1), 46 (15), 69 (3)",
            "18 (0): 40 (12), 91 (9)",
            "19 (0): 2 (8), 73 (3)",
            "20 (0): 34 (1), 64 (12)",
            "21 (0): 22 (4), 47 (14), 73 (14), 74 (5), 82 (12), 92 (5)",
            "22 (0): 8 (12)",
            "23 (0): 68 (2)",
            "24 (0): 9 (11), 35 (7), 50 (9)",
            "25 (0): 49 (7)",
            "26 (0): 77 (9), 99 (14)",
            "27 (0): 6 (2), 19 (10), 31 (6), 33 (6), 38 (2), 49 (4)",
            "28 (0): 13 (11), 27 (10), 54 (6)",
            "29 (0): 30 (15)",
            "30 (0): 0 (13), 7 (15), 65 (9), 77 (1)",
            "31 (0): 19 (8), 29 (2)",
            "32 (0): 60 (8), 81 (2), 84 (6)",
            "33 (0):",
            "34 (0): 28 (4), 36 (14)",
            "35 (0): 8 (14), 82 (14), 96 (13)",
            "36 (0): 57 (1), 99 (11)",
            "37 (0):",
            "38 (0): 26 (10), 68 (11), 89 (15)",
            "39 (0): 1 (11)",
            "40 (0): 43 (5), 66 (2)",
            "41 (0): 50 (14)",
            "42 (0): 76 (12), 93 (8), 96 (13)",
            "43 (0): 67 (1)",
            "44 (0): 24 (5)",
            "45 (0): 32 (15), 44 (1), 49 (6), 52 (2), 72 (8)",
            "46 (0):",
            "47 (0): 78 (15), 82 (6)",
            "48 (0): 12 (10), 52 (4)",
            "49 (0): 31 (3)",
            "50 (0): 93 (6)",
            "51 (0): 21 (10), 27 (9)",
            "52 (0): 27 (15), 88 (2)",
            "53 (0):",
            "54 (0): 93 (4)",
            "55 (0): 8 (3)",
            "56 (0): 14 (10), 37 (8), 59 (8)",
            "57 (0): 32 (2), 99 (1)",
            "58 (0):",
            "59 (0): 43 (6), 54 (6)",
            "60 (0): 31 (4), 51 (14)",
            "61 (0): 6 (4), 18 (10)",
            "62 (0):",
            "63 (0): 4 (15), 11 (2), 48 (3), 83 (6)",
            "64 (0): 78 (3)",
            "65 (0): 10 (6), 13 (7), 40 (2), 49 (15), 53 (15)",
            "66 (0): 4 (14), 47 (7), 98 (1)",
            "67 (0): 67 (6)",
            "68 (0): 13 (15), 32 (14), 95 (1)",
            "69 (0): 45 (1), 90 (1)",
            "70 (0):",
            "71 (0):",
            "72 (0): 19 (14), 41 (13)",
            "73 (0): 39 (14), 78 (11), 79 (15)",
            "74 (0):",
            "75 (0): 11 (12)",
            "76 (0): 33 (9), 65 (4), 97 (9)",
            "77 (0): 21 (5), 39 (15), 64 (14)",
            "78 (0):",
            "79 (0): 22 (13), 34 (14), 89 (4)",
            "80 (0): 62 (3), 88 (11)",
            "81 (0): 15 (8), 17 (8), 32 (11), 41 (6), 43 (5)",
            "82 (0): 40 (8), 84 (4), 86 (4), 91 (6)",
            "83 (0): 34 (4), 40 (9)",
            "84 (0): 2 (15), 10 (4), 64 (14), 98 (15)",
            "85 (0): 54 (1), 60 (9)",
            "86 (0): 71 (4), 84 (7)",
            "87 (0):",
            "88 (0): 66 (4)",
            "89 (0): 3 (5), 6 (13), 30 (13), 44 (3)",
            "90 (0): 53 (7), 69 (4)",
            "91 (0):",
            "92 (0): 28 (9), 44 (6)",
            "93 (0):",
            "94 (0): 65 (11), 68 (13)",
            "95 (0): 17 (4), 30 (5), 33 (13)",
            "96 (0):",
            "97 (0): 3 (12), 8 (2)",
            "98 (0): 8 (14), 35 (14), 43 (2), 87 (14)",
            "99 (0): 21 (5), 48 (11)",
        };
        String graphString = join(edges);

        int[][] data = {
            {4, 14, -1, -1},
            {35, 0, 6, 34},
            {10, 92, -1, -1},
            {83, 86, 6, 28},
            {87, 56, -1, -1},
            {88, 84, 5, 21},
            {25, 7, 6, 42},
            {5, 7, 9, 59},
            {38, 84, 4, 31},
            {48, 6, 4, 21},
            {26, 73, 4, 28},
            {64, 43, -1, -1},
            {42, 17, 7, 29},
            {64, 39, -1, -1},
            {28, 34, 6, 52},
            {36, 6, 6, 43},
            {81, 29, 5, 25},
            {43, 57, -1, -1},
            {19, 45, 10, 53},
            {57, 88, 5, 18},
            {23, 45, 6, 11},
            {82, 25, 7, 55},
            {11, 1, 2, 15},
            {63, 46, 7, 32},
            {14, 83, -1, -1},
            {83, 73, 6, 31},
            {51, 44, 4, 21},
            {11, 82, 6, 53},
            {56, 3, -1, -1},
            {43, 37, -1, -1},
            {29, 54, 7, 41},
            {27, 72, 8, 32},
            {51, 5, -1, -1},
            {92, 44, 2, 6},
            {35, 79, 8, 66},
            {21, 30, 5, 46},
            {98, 57, 8, 37},
            {76, 2, 6, 38},
            {68, 38, 6, 47},
            {82, 62, -1, -1},
            {52, 79, 5, 43},
            {43, 82, -1, -1},
            {39, 52, 10, 75},
            {52, 33, 3, 21},
            {67, 71, -1, -1},
            {29, 79, 6, 50},
            {71, 63, -1, -1},
            {34, 99, 3, 25},
            {17, 38, 6, 23},
            {37, 96, -1, -1},
        };

        for (int i = 0; i < data.length; i++) {
            NodeState start = new NodeState(data[i][0] + "|" + graphString);
            NodeState goal = new NodeState(data[i][1] + "|" + graphString);

            Path path = (new BreadthFirstSearch()).search(start, goal);

            /*
            if (path == null) {
                System.out.println("{" + data[i][0] + ", " + data[i][1] + ", -1, -1},");
            } else {
                System.out.println("{" + data[i][0] + ", " + data[i][1] + ", " + path.getStates().size() + ", " + path.getCost() + "},");
            }
            */

            int numStates = data[i][2];
            int cost = data[i][3];
            if (numStates == -1) {
                if (path != null) {
                    System.out.println("no path exists but one was found");
                    return false;
                }
            } else {
                if (path == null) {
                    System.out.println("path exists but none was found");
                    return false;
                }

                int actualNumStates = path.getStates().size();
                int actualCost = path.getCost();

                if (numStates != actualNumStates) {
                    System.out.println("path should have " + numStates + " states but had " + actualNumStates);
                    return false;
                }
                if (cost != actualCost) {
                    System.out.println("path should have cost " + cost + " but had " + actualCost);
                    return false;
                }
            }
        }

        System.out.println("passed");
        return true;
    }

    public static void main(String[] args) {
        System.out.println(passed());
    }

}
