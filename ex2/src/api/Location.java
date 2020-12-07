package api;

public class Location implements geo_location {
    private double x,y,z;

    public Location() {
        this(0,0,0);
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

    @Override
    public double distance(geo_location g) {
        double dx = this.x - g.x();
        double dy = this.y - g.y();
        double dz = this.z - g.z();
        double sum = dx*dx + dy*dy+dz*dz;
        double ans = Math.sqrt(sum);
        return ans;
    }
}
