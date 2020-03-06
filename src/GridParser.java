import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class GridParser {
    /**
     Constructor.
     @param fileName full name of an existing, readable file.
     */
    public GridParser(String fileName){
        filePath = Paths.get(fileName);
    }


    /** Template method that calls {@link #processLine(String)}.  */
    public final void processLineByLine() throws IOException {
        try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                processLine(scanner.nextLine());
            }
        }
    }

    /**
     Overridable method for processing lines in different ways.

     */
    protected void processLine(String line){
        //use a second Scanner to parse the content of each line
        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter("=");
            if (scanner.hasNext()){
                //assumes the line has a certain structure
                String name = scanner.next();
                String value = scanner.next();
                log("Name is : " + quote(name.trim()) + ", and Value is : " + quote(value.trim()));
            }
            else {
                log("Empty or invalid line. Unable to process.");
            }
        }
    }

    // PRIVATE

    private final Path filePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private static void log(Object object){
        System.out.println(Objects.toString(object));
    }

    private String quote(String text){
        String QUOTE = "'";
        return QUOTE + text + QUOTE;
    }

    public final Grid generateGrid(double distance) throws IOException {
        double size;
        double particlesAmmount;


        try (Scanner scanner =  new Scanner(filePath, ENCODING.name())){
            if (scanner.hasNextDouble()){
                particlesAmmount=scanner.nextDouble();

                if (scanner.hasNextDouble()){

                    size=scanner.nextDouble();
                    Grid grid=new Grid(size,particlesAmmount,distance);

                    while (scanner.hasNextLine()){
                        grid.addParticle(processParticle(scanner.nextLine()));
                    }
                    return grid;
                }
            }
        }
        return null;
    }

    protected Particle processParticle(String line){
        //use a second Scanner to parse the content of each line
        try(Scanner scanner = new Scanner(line)){
            //scanner.useDelimiter("=");
            if (scanner.hasNext()){
                //assumes the line has a certain structure
                double xpos = scanner.nextDouble();
                double ypos = scanner.nextDouble();
                double radius = scanner.nextDouble();
                double property = scanner.nextDouble();
                return new Particle(xpos,ypos,radius,property);
            }
            else {
                log("Empty or invalid line. Unable to process.");
                return null;
            }
        }
    }

}