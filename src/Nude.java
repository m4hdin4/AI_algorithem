import java.util.Objects;

public class Nude implements Comparable<Nude> {
    private final int height;
    private final char part;

    public Nude(int height, char part) {
        this.height = height;
        this.part = part;
    }

    public Nude(Nude nude) {
        this.height = nude.getHeight();
        this.part = nude.getPart();
    }

    public int getHeight() {
        return height;
    }

    public char getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nude)) return false;
        Nude nude = (Nude) o;
        return getHeight() == nude.getHeight() &&
                getPart() == nude.getPart();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), getPart());
    }


    @Override
    public int compareTo(Nude o) {
        return o.height - height;
    }
}