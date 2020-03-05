import java.util.ArrayList;
import java.util.List;

public class Particle {
    private double xpos;
    private double ypos;
    private double radius;
    private double property;
    private List<Particle> nearParticles;

    public Particle (double xpos, double ypos, double radius, double property){
        this.xpos=xpos;
        this.ypos=ypos;
        this.radius=radius;
        this.property=property;
        this.nearParticles=new ArrayList<Particle>();
    }

    public void addNearParticle(Particle particle){
        this.nearParticles.add(particle);
    }

    public List<Particle> getNearParticles() {
        return nearParticles;
    }

    public double getXpos() {
        return xpos;
    }

    public void setXpos(long xpos) {
        this.xpos = xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public void setYpos(long ypos) {
        this.ypos = ypos;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public double getProperty() {
        return property;
    }

    public void setProperty(long property) {
        this.property = property;
    }
}
