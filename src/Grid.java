import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Grid {

    double blockLength;
    double size;
    double particleAmmount;
    double distance;
    double MaxRadius;
    List<Particle> particles;
    ArrayList<List<List<Particle>>> grid;

    public Grid(double size, double particleAmmount, double distance) {
        this.size = size;
        this.particleAmmount = particleAmmount;
        this.particles = new ArrayList<Particle>();
    }

    public void generateGrid(double distance){
        this.distance=distance;
        this.blockLength=calculateBlockLength(this.distance,this.size,this.MaxRadius);
         grid = startGrid();

         addParticlesToGrid();
    }

    static private double calculateBlockLength(double distance, double size,double radius){
        double minsize = distance+radius*2;
        return (size)/floor(size/minsize);
    }

    private ArrayList<List<List<Particle>>> startGrid(){
        ArrayList<List<List<Particle>>> grid=new ArrayList<>();
        for(int i =0;i<size/blockLength;i++){
            grid.add(new ArrayList<>());
            for(int j =0;j<size/blockLength;j++){
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
        for ( i=0;i<size/blockLength; i++){
            for ( j=0;j<size/blockLength; j++){
                for (Particle particle: grid.get(i).get(j)) {
                    //particle.addAllParticles(grid.get(i).get(j));
                    calculateMultipleDistance(particle,grid.get(i    ).get(j    ),distance);
                    calculateMultipleDistance(particle,grid.get(i    ).get(j + 1),distance);
                    calculateMultipleDistance(particle,grid.get(i + 1).get(j    ),distance);
                    calculateMultipleDistance(particle,grid.get(i + 1).get(j + 1),distance);
                    //agregar caso de que se una por abajo
                }
            }
        }
    }

    private static void calculateMultipleDistance(Particle fromParticle,List<Particle> block,double distance){
        for (Particle toParticle: block) {
            if(calculateDistance(fromParticle,toParticle)<distance){
                fromParticle.addNearParticle(toParticle);
                toParticle.addNearParticle(fromParticle);
            }
        }
    }

    public void addParticle(Particle particle){

    }
}
