import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Main {

    public static void main(String[] args) {

        double distance =Double.parseDouble(args[1]);

        GridParser gridParser= new GridParser("");

        try{
            Grid grid = gridParser.generateGrid(distance);
            if(grid != null) {
                grid.calculateNear();
            }
            writeToFile("output",grid);
        }catch (Exception e){

        }

    }

    private static void writeToFile(String filename,Grid grid){
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            List<Particle> particles = grid.getParticles();
            int i=0;
            for(Particle fromParticle: particles){
                myWriter.write("Particle" + i + "\n");
                i++;
                for(Particle toParticle : fromParticle.getNearParticles()){
                    myWriter.write("");
                }
            }




        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

//    static private double calculateBlockLength(double distance, double size,double radius){
//        double minsize = distance+radius*2;
//        return (size)/floor(size/minsize);
//    }
//
//    static private double calculateDistance(Particle particle1, Particle particle2){
//        double x = particle1.getXpos() - particle2.getXpos();
//        double y = particle1.getYpos() - particle2.getYpos();
//        double temp;
//        return (temp=(sqrt(x*x+y*y)-particle1.getRadius()-particle2.getRadius()))<0?0:temp;
//
//    }
//
//    static private ArrayList<List<List<Particle>>> generateGrid(double blockLength,double size){
//        ArrayList<List<List<Particle>>> grid=new ArrayList<>();
//        for(int i =0;i<size/blockLength;i++){
//            grid.add(new ArrayList<>());
//            for(int j =0;j<size/blockLength;j++){
//                grid.get(i).add(new ArrayList<>());
//            }
//        }
//        return grid;
//    }

//    static private void addParticles(List<List<List<Particle>>> grid, List<Particle> particles,double blockLength){
//        for (Particle particle : particles) {
//            grid.get((int)(particle.getYpos()/blockLength)).get((int)(particle.getXpos()/blockLength)).add(particle);
//        }
//    }
//
//    static private void calculateNear(ArrayList<List<List<Particle>>> grid, double blockLength, double size, double distace){
//        int i;
//        int j;
//        for ( i=0;i<size/blockLength; i++){
//            for ( j=0;j<size/blockLength; j++){
//                for (Particle particle: grid.get(i).get(j)) {
//                    //particle.addAllParticles(grid.get(i).get(j));
//                    calculateMultipleDistance(particle,grid.get(i    ).get(j    ),distace);
//                    calculateMultipleDistance(particle,grid.get(i    ).get(j + 1),distace);
//                    calculateMultipleDistance(particle,grid.get(i + 1).get(j    ),distace);
//                    calculateMultipleDistance(particle,grid.get(i + 1).get(j + 1),distace);
//                    //agregar caso de que se una por abajo
//                }
//            }
//        }
//    }
//
//    private static void calculateMultipleDistance(Particle fromParticle,List<Particle> block,double distance){
//        for (Particle toParticle: block) {
//            if(calculateDistance(fromParticle,toParticle)<distance){
//                fromParticle.addNearParticle(toParticle);
//                toParticle.addNearParticle(fromParticle);
//            }
//        }
//    }


}
