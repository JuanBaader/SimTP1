import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Grid {

    boolean outlineEnabled;
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
        this.outlineEnabled=false;
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

    private double calculateDistanceOutline(Particle fromParticle, Particle toParticle,boolean xOffset,boolean yOffset){
        double x = fromParticle.getXpos() - (toParticle.getXpos() - L * (xOffset?1:0));
        double y = fromParticle.getYpos() - (toParticle.getYpos() - L * (xOffset?1:0));
        double temp;
        return (temp=(sqrt(x*x+y*y)-fromParticle.getRadius()-toParticle.getRadius()))<0?0:temp;
    }

    static private double calculateDistance(Particle fromParticle, Particle toParticle){
        double x = fromParticle.getXpos() - toParticle.getXpos();
        double y = fromParticle.getYpos() - toParticle.getYpos();
        double temp;
        return (temp=(sqrt(x*x+y*y)-fromParticle.getRadius()-toParticle.getRadius()))<0?0:temp;

    }

    private void addParticlesToGrid(){
        for (Particle particle : this.particles) {
            grid.get((int)(particle.getYpos()/blockLength)).get((int)(particle.getXpos()/blockLength)).add(particle);
        }
    }

    public void calculateNear(){
        int yIter;
        int xIter;
        for ( yIter=0; yIter<L/blockLength; yIter++){
            for ( xIter=0; xIter<L/blockLength; xIter++){
                for (Particle particle: grid.get(yIter).get(xIter)) {
                    //particle.addAllParticles(grid.get(i).get(j));
                    calculateMultipleDistance(particle,grid.get(yIter).get(xIter),Rc);
                    if(xIter < (int)(L/blockLength))
                        calculateMultipleDistance(particle,grid.get(yIter    ).get(xIter + 1),Rc);
                    if(yIter < (int)(L/blockLength))
                        calculateMultipleDistance(particle,grid.get(yIter + 1).get(xIter    ),Rc);
                    if(xIter < (int)(L/blockLength) && yIter < (int)(L/blockLength))
                        calculateMultipleDistance(particle,grid.get(yIter + 1).get(xIter + 1),Rc);
                    //agregar caso de que se una por abajo
                    if(outlineEnabled){
                        if(yIter==0)
                            calculateMultipleDistanceOutline(particle,grid.get((int)(L/blockLength)).get(xIter               ),Rc,false,true);
                        if(xIter==0)
                            calculateMultipleDistanceOutline(particle,grid.get(yIter               ).get((int)(L/blockLength)),Rc,true,false);
                        if(yIter==0 && xIter==0)
                            calculateMultipleDistanceOutline(particle,grid.get((int)(L/blockLength)).get((int)(L/blockLength)),Rc,true,true );
                    }
                }
            }
        }
    }

    private void calculateMultipleDistanceOutline(Particle fromParticle,List<Particle> block,double distance,boolean xOfset,boolean yOfset){
        for (Particle toParticle: block) {
            if(calculateDistanceOutline(fromParticle,toParticle,xOfset,yOfset)<distance){
                fromParticle.addNearParticle(toParticle);
                toParticle.addNearParticle(fromParticle);
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
        particles.add(particle);
    }

    public List<Particle> getParticles(){
        return particles;
    }


}
