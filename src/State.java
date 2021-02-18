import java.util.ArrayList;

public class State {
    private final Nude[][] nudes;
    private final int depth;
    private final int n;
    private final int m;
    private final String path;
    private int iSorter;
    private int jSorter;

    public int getDepth() {
        return depth;
    }

    public String getPath() {
        return path;
    }
    public String getReversePath(){
        String make = new String(path);
        StringBuilder output = new StringBuilder();
        for (char c :
                make.toCharArray()) {
            if (c == 'U')
                output.append('D');
            else if (c == 'D')
                output.append('U');
            else if (c == 'R')
                output.append('L');
            else if (c == 'L')
                output.append('R');
        }
        return output.reverse().toString();
    }

    public Nude[][] getNudes() {
        return nudes;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }


    public State(Nude[][] nudes, int n, int m) {
        this.nudes = nudes;
        this.n = n;
        this.m = m;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (nudes[i][j] == null) {
                    this.iSorter = i;
                    this.jSorter = j;
                }
            }
        }
        this.path = "";
        this.depth = 0;
        this.nudes[iSorter][jSorter] = null;
    }


    public State(Nude[][] nudes, int depth, int n, int m, String path, int iSorter, int jSorter) {
        this.nudes = nudes;
        this.depth = depth;
        this.n = n;
        this.m = m;
        this.path = path;
        this.iSorter = iSorter;
        this.jSorter = jSorter;
        this.nudes[iSorter][jSorter] = null;
    }

    public ArrayList<State> children() {
        ArrayList<State> output = new ArrayList<>();
        Nude[][] copy;
        if (iSorter > 0) {
            copy = copyNude(nudes, n, m);
            copy[iSorter][jSorter] = copy[iSorter - 1][jSorter];
            output.add(new State(copy, depth + 1, n, m, path + "U", iSorter - 1, jSorter));
        }
        if (iSorter < n - 1) {
            copy = copyNude(nudes, n, m);
            copy[iSorter][jSorter] = copy[iSorter + 1][jSorter];
            output.add(new State(copy, depth + 1, n, m, path + "D", iSorter + 1, jSorter));
        }
        if (jSorter > 0) {
            copy = copyNude(nudes, n, m);
            copy[iSorter][jSorter] = copy[iSorter][jSorter - 1];
            output.add(new State(copy, depth + 1, n, m, path + "L", iSorter, jSorter - 1));
        }
        if (jSorter < m - 1) {
            copy = copyNude(nudes, n, m);
            copy[iSorter][jSorter] = copy[iSorter][jSorter + 1];
            output.add(new State(copy, depth + 1, n, m, path + "R", iSorter, jSorter + 1));
        }
        return output;
    }

    private static Nude[][] copyNude(Nude[][] nds, int n, int m) {
        Nude[][] output = new Nude[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (nds[i][j] == null)
                    output[i][j] = null;
                else
                    output[i][j] = new Nude(nds[i][j]);
            }
        }
        return output;
    }

    public boolean equality(State check) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (nudes[i][j] == null) {
                    if (check.nudes[i][j] != null)
                        return false;
                } else if (!nudes[i][j].equals(check.nudes[i][j]))
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Nude[] nds :
                nudes) {
            for (Nude nd :
                    nds) {
                if (nd == null)
                    output.append("# ");
                else
                    output.append(nd.getHeight()).append(nd.getPart()).append(" ");
            }
            output.append('\n');
        }
        return output.toString();
    }
}
