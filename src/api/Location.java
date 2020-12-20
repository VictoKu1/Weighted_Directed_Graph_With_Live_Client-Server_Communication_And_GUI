package api;

import java.util.Objects;

public class Location implements geo_location {
    private double x, y, z;

    public Location() {
        this(0, 0, 0);
    }

    public Location(String s) {
        String[] arr = s.split(",");
        this.x = Double.parseDouble(arr[0]);
        this.y = Double.parseDouble(arr[1]);
        this.z = Double.parseDouble(arr[2]);
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    /**
     * return the distance
     * @param g
     * @return
     */
    @Override
    public double distance(geo_location g) {
        double dx = this.x - g.x();
        double dy = this.y - g.y();
        double dz = this.z - g.z();
        double sum = dx * dx + dy * dy + dz * dz;
        double ans = Math.sqrt(sum);
        return ans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0 &&
                Double.compare(location.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
