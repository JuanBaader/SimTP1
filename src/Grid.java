import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Grid {

    double blockLength;
    double Rc;
    double maxRadius;
    List<Particle> particles;
    ArrayList<List<List<Particle>>> grid;
    double L, N;

    public Grid(List<Particle> particles, double L, double N) {
        this.particles = particles;
        this.L = L;
        this.N = N;
    }

    public void generateGrid(double Rc){
        this.Rc=Rc;
        calculateMaxRadius();
        this.blockLength=calculateBlockLength(this.Rc,this.L,this.maxRadius);
        grid = startGrid();
        addParticlesToGrid();
    }

    private void calculateMaxRadius() {
        for (Particle p : particles) {
            if (p.getRadius() > maxRadius) {
                this.maxRadius = p.getRadius();
            }
        }
    }

    static private double calculateBlockLength(double Rc, double size, double radius){
        double minsize = Rc+radius*2;
        return (size)/floor(size/minsize);
    }

    private ArrayList<List<List<Particle>>> startGrid(){
        ArrayList<List<List<Particle>>> grid=new ArrayList<>();
        for(int i =0;i<L/blockLength;i++){
            grid.add(new ArrayList<>());
            for(int j =0;j<L/blockLength;j++){
                grid.get(i).add(new ArrayList<>());
            }
        }
        return grid;
    }

    static private double calculateDistance(Particle particle1, Particle particle2){
        double x = particle1.getXpos() - particle2.getXpos();
        double y = particle1.getYpos() - particle2.getYpos();
        double temp;
        return (temp=(sqrt(x*x+y*y)-particle1.getRadius()-particle2.getRadius()))<0?0:temp;
    }

    private void addParticlesToGrid(){
        for (Particle particle : this.particles) {
            grid.get((int)(particle.getYpos()/blockLength)).get((int)(particle.getXpos()/blockLength)).add(particle);
        }
    }

    public void calculateNear(){
        int i;
        int j;
        for ( i=0;i<L/blockLength; i++){
            for ( j=0;j<L/blockLength; j++){
                for (Particle particle: grid.get(i).get(j)) {
                    //particle.addAllParticles(grid.get(i).get(j));
                    calculateMultipleDistance(particle,grid.get(i    ).get(j    ),Rc);
                    calculateMultipleDistance(particle,grid.get(i    ).get(j + 1),Rc);
                    calculateMultipleDistance(particle,grid.get(i + 1).get(j    ),Rc);
                    calculateMultipleDistance(particle,grid.get(i + 1).get(j + 1),Rc);
                    //agregar caso de que se una por abajo
                }
            }
        }
    }

    private static void calculateMultipleDistance(Particle fromParticle,List<Particle> block,double Rc){
        for (Particle toParticle: block) {
            if(calculateDistance(fromParticle,toParticle)<Rc){
                fromParticle.addNearParticle(toParticle);
                toParticle.addNearParticle(fromParticle);
            }
        }
    }

    public void addParticle(Particle particle){

    }
}
