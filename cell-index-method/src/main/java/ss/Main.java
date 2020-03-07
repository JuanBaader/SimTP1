package ss;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ss.CliParser;

public class Main {

    public static void main(String[] args) {

        
        CliParser cliParser = new CliParser();
        cliParser.parseOptions(args);
        
        GridParser gridParser = new GridParser(cliParser.staticFile, cliParser.dynamicFile);
        Grid grid = gridParser.readParticles();
        grid.periodicBoundary = cliParser.periodicBoundary;
        grid.generateGrid(cliParser.Rc);
        
        long startTime = System.currentTimeMillis();
        
        if (cliParser.bruteForce) {
            grid.calculateNearBruteForce();
        } else {
            grid.calculateNear();
        }

        long totalTime = System.currentTimeMillis() - startTime;

        writeToFile("output", grid, totalTime);
    }

    private static void writeToFile(String filename, Grid grid, long totalTime){
        try {
            FileWriter neighborWriter = new FileWriter("neighbors.txt");
            List<Particle> particles = grid.getParticles();
            neighborWriter.write("Total Time Ellapsed (millis): " + totalTime + "\n");
            for(Particle fromParticle: particles){
                neighborWriter.write("Particle " + fromParticle.getId() + ":\n");
                for(Particle toParticle : fromParticle.getNearParticles()){
                    neighborWriter.write( toParticle.getId()+"\n");
                }
            }
            neighborWriter.close();
            
            FileWriter allPositions = new FileWriter("allParticles.xyz");
            allPositions.write(Integer.toString((int) grid.N) + "\n\n");
            // int i;
            int chosenId = 2;

            for (Particle p : particles) {
                if (particles.get(chosenId).getNearParticles().contains(p)) {
                    allPositions.write("Particle" + p.getId() + "\t" + p.getXpos() + "\t"+p.getYpos() + "\t" + p.getRadius() + "\t255\t0\t0\n");
                } else if (p != particles.get(chosenId)) {
                    allPositions.write("Particle" + p.getId() + "\t" + p.getXpos() + "\t"+p.getYpos() + "\t" + p.getRadius() + "\t0\t255\t0\n");
                } else {
                    allPositions.write("Particle" + p.getId() + "\t" + p.getXpos() + "\t"+p.getYpos() + "\t" + p.getRadius() + "\t255\t200\t0\n");
                }
            }
            allPositions.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
