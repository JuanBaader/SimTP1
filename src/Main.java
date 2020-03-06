import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Main {

    public static void main(String[] args) {

        GridParser gridParser = new GridParser("data/static-1.ari", "data/dynamic-1.ari");
        Grid grid = gridParser.readParticles();
        
        double distance =Double.parseDouble("4");

        grid.generateGrid(distance);
        grid.calculateNear();
        writeToFile("output",grid);


    }

    private static void writeToFile(String filename,Grid grid){
        try {
            FileWriter neighborWriter = new FileWriter("neighbors.txt");
            List<Particle> particles = grid.getParticles();
            for(Particle fromParticle: particles){
                neighborWriter.write("Particle " + fromParticle.getId() + ":\n");
                for(Particle toParticle : fromParticle.getNearParticles()){
                    neighborWriter.write( toParticle.getId()+"\n");
                }
            }
            neighborWriter.close();
            
            FileWriter allPositions = new FileWriter("allParticles.txt");
            allPositions.write(Double.toString(grid.N)+"\n\n");
            int i;
            int chosenId = 2;
            Particle tmp;
            Set<Long> usedId=new HashSet<>();
            for(i=0;i<grid.N;i++){
                tmp=particles.get(chosenId);
                allPositions.write(tmp.getXpos() + "\t"+tmp.getYpos() + "\t" + tmp.getRadius() + "\t0\t0\t255");
                usedId.add(tmp.getId());
                for (Particle toParticle : tmp.getNearParticles()) {
                    allPositions.write(toParticle.getXpos() + "\t"+toParticle.getYpos() + "\t" + toParticle.getRadius() + "\t0\t255\t0");
                    usedId.add(toParticle.getId());
                }
            }
            for(Particle fromParticle: particles){
                if(usedId.contains(fromParticle.getId())) {
                    allPositions.write(fromParticle.getXpos() + "\t"+fromParticle.getYpos() + "\t" + fromParticle.getRadius() + "\t255\t0\t0");
                }
            }
            allPositions.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
