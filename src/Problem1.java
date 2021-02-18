import javafx.util.Pair;

import java.util.*;

public class Problem1 {
    private static long createdNudes;
    private static long developedNudes;
    private static final int MAX_DEPTH = 100;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        State start;
        ArrayList<State> targets;
        String[] in = scan.nextLine().split(" ");
        int n = Integer.parseInt(in[0]);
        int m = Integer.parseInt(in[1]);
        String[][] inputs = new String[n][];
        for (int i = 0; i < n; i++) {
            inputs[i] = scan.nextLine().split(" ");
        }
        Pair<State, ArrayList<State>> analyzed = analyze(inputs, n, m);
        start = analyzed.getKey();
        targets = analyzed.getValue();
        State finalState = IDS(start, targets , 0);
        printOutput(finalState);
    }

    private static State DLS(State begin, ArrayList<State> targets, int limit) {
        for (State t :
                targets) {
            if (begin.equality(t)) {
                return begin;
            }
        }
        if (limit <= 0)
            return null;
        developedNudes++;
        State output;
        for (State s :
                begin.children()) {
            createdNudes ++;
            output = DLS(s, targets, limit - 1);
            if (output != null) {
                return output;
            }
        }
        return null;
    }

    private static State IDS(State begin, ArrayList<State> targets , int startLimit) {
        State output;
        for (int i = startLimit; i < MAX_DEPTH; i++) {
            createdNudes = 1;
            developedNudes = 0;
            output = DLS(begin, targets, i);
            if (output != null) {
                return output;
            }
        }
        return null;
    }

    private static void printOutput(State finalState){
        if (finalState == null) {
            System.out.println("FAILURE");
            System.out.println(createdNudes);
            System.out.println(developedNudes);
            return;
        }
        System.out.println("depth: " + finalState.getDepth());
        for (char c :
                finalState.getPath().toCharArray()) {
            switch (c) {
                case 'U':
                    System.out.println("UP");
                    break;
                case 'D':
                    System.out.println("DOWN");
                    break;
                case 'R':
                    System.out.println("RIGHT");
                    break;
                case 'L':
                    System.out.println("LEFT");
                    break;
            }
        }
        System.out.println("created nudes: " + createdNudes);
        System.out.println("developed nudes :" + developedNudes);
        System.out.println(finalState);
    }

    private static Pair<State, ArrayList<State>> analyze(String[][] inputs, int n, int m) {
        Nude[][] output = new Nude[n][m];
        Nude[][] key = new Nude[n][m];
        ArrayList<State> targets = new ArrayList<>();
        char part;
        HashMap<Character, ArrayList<Nude>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (inputs[i][j].equals("#")) {
                    output[i][j] = null;
                } else {
                    part = inputs[i][j].charAt(3);
                    output[i][j] = new Nude(Integer.parseInt(inputs[i][j].substring(0, 3)), part);
                    if (!map.containsKey(part)) {
                        map.put(part, new ArrayList<>());
                    }
                    map.get(part).add(output[i][j]);
                }
            }
        }
        int counter = 0;
        for (char c :
                map.keySet()) {
            Collections.sort(map.get(c));
            if (map.get(c).size() < m) {
                map.get(c).add(0, null);
            }
            key[counter] = map.get(c).toArray(key[counter]);
            counter++;
        }
        for (Nude[][] nudes :
                finalStates(key, n)) {
            targets.add(new State(nudes, n, m));
        }
        return new Pair<>(new State(output, n, m), targets);
    }

    private static ArrayList<ArrayList<Nude[]>> makeFinalStates(ArrayList<Nude[]> input, int n, int step) {
        ArrayList<ArrayList<Nude[]>> output = new ArrayList<>();
        ArrayList<Nude[]> temp;
        if (step == 0) {
            temp = new ArrayList<>();
            output.add(temp);
            return output;
        }
        for (ArrayList<Nude[]> siq :
                makeFinalStates(input, n, step - 1)) {
            for (int i = 0; i < step; i++) {
                temp = (ArrayList<Nude[]>) siq.clone();
                if (i < step - 1) {
                    temp.add(i, input.get(step - 1));
                } else temp.add(input.get(step - 1));
                output.add(temp);
            }
        }
        return output;
    }

    private static ArrayList<Nude[][]> finalStates(Nude[][] input, int n) {
        ArrayList<Nude[][]> output = new ArrayList<>();
        ArrayList<Nude[]> changed = new ArrayList<>();
        Nude[][] temp;
        changed.addAll(Arrays.asList(input).subList(0, n));
        for (ArrayList<Nude[]> nudes :
                makeFinalStates(changed, n, n)) {
            temp = new Nude[n][];
            temp = nudes.toArray(temp);
            output.add(temp);
        }
        return output;
    }
}