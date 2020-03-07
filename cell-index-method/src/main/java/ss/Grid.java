package ss;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Grid {

    boolean periodicBoundary;
    double blockLength;
    double Rc;
    double maxRadius;
    int M;
    List<Particle> particles;
    ArrayList<List<List<Particle>>> grid;
    double L, N;

    public Grid(List<Particle> particles, double L, double N) {
        this.particles = particles;
        this.L = L;
        this.N = N;
        this.periodicBoundary=false;
    }

    public void generateGrid(double Rc, int M){
        this.Rc=Rc;
        calculateMaxRadius();
        this.blockLength=calculateBlockLength(this.Rc,this.L,this.maxRadius);
        if (M != 0 && L/M > blockLength) {
            this.blockLength = L/M;
            this.M = (int) M;
        } else {
            this.M = (int) (L/blockLength);
        }
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
        for(int i =0;i<M;i++){
            grid.add(new ArrayList<>());
            for(int j =0;j<M;j++){
                grid.get(i).add(new ArrayList<>());
            }
        }
        return grid;
    }

    private double calculateDistanceOutline(Particle fromParticle, Particle toParticle,boolean xOffset,boolean yOffset){
        double x = fromParticle.getXpos() - (toParticle.getXpos() - L * (xOffset?1:0));
        double y = fromParticle.getYpos() - (toParticle.getYpos() - L * (yOffset?1:0));
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
        for ( yIter=0; yIter<M; yIter++){
            for ( xIter=0; xIter<M; xIter++){
                for (Particle p1 : grid.get(yIter).get(xIter)) {
                    for (Particle p2 : grid.get(yIter).get(xIter)) {
                        if (calculateDistance(p1,p2)<Rc && p1.getId() != p2.getId()) {
                            p1.addNearParticle(p2);
                        }
                    }
                }
                for (Particle particle: grid.get(yIter).get(xIter)) {
                    if(xIter < M-1)
                        calculateMultipleDistance(particle,grid.get(yIter).get((xIter + 1)),Rc);
                    if(yIter < M-1)
                        calculateMultipleDistance(particle,grid.get(yIter + 1).get(xIter),Rc);
                    if(xIter < M-1 && yIter < M-1)
                        calculateMultipleDistance(particle,grid.get(yIter + 1).get(xIter + 1),Rc);
                    //agregar caso de que se una por abajo
                    if(periodicBoundary){
                        if(yIter==0)
                            calculateMultipleDistanceOutline(particle,grid.get(M-1).get(xIter),Rc,false,true);
                        if(xIter==0)
                            calculateMultipleDistanceOutline(particle,grid.get(yIter).get(M-1),Rc,true,false);
                        if(yIter==0 && xIter==0)
                            calculateMultipleDistanceOutline(particle,grid.get(M-1).get(M-1),Rc,true,true );
                    }
                }
            }
        }
    }

    public void calculateNearBruteForce() {
        double distance;
        for (Particle p1 : particles) {
            for (Particle p2 : particles) {
                if (p2 != p1) {
                    if (periodicBoundary) {
                        distance = calculateDistanceOutline(p1, p2, p1.getXpos() < blockLength && p2.getXpos() > blockLength * (M-1) ||
                         p2.getXpos() < blockLength && p1.getXpos() > blockLength * (M-1),
                         p1.getYpos() < blockLength && p2.getYpos() > blockLength * (M-1) ||
                         p2.getYpos() < blockLength && p1.getYpos() > blockLength * (M-1));
                    } else {
                        distance = calculateDistance(p1, p2);
                    }
                    if (distance < Rc) {
                        p1.addNearParticle(p2);
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
