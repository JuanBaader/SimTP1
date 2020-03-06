import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GridParser {

    private File staticFile;
    private File dynamicFile;
    public List<Particle> particleList;
    public Integer N;
    public Double L;

    /**
     * Constructor.
     * 
     * @param staticFileName  full name of an existing, readable static file.
     * @param dynamicFileName full name of an existing, readable dynamic file.
     */
    public GridParser(String staticFileName, String dynamicFileName) {
        this.staticFile = new File(staticFileName);
        this.dynamicFile = new File(dynamicFileName);
        this.particleList = new ArrayList<>();
    }

    public Grid readParticles() {
        try {
            Scanner staticScanner = new Scanner(staticFile);
            Scanner dynamicScanner = new Scanner(dynamicFile);
            N = staticScanner.nextInt();
            L = staticScanner.nextDouble();
            // leer t0 del archivo dinamico
            dynamicScanner.nextInt();
            
            double rad, prop, x, y;
            for (int i = 0; i < N; i++) {
                rad = staticScanner.nextDouble();
                prop = staticScanner.nextDouble();
                x = dynamicScanner.nextDouble();
                y = dynamicScanner.nextDouble();
                particleList.add(new Particle(i, x, y, rad, prop));
            }
            staticScanner.close();
            dynamicScanner.close();
            Grid grid = new Grid(this.particleList, this.L, this.N);
            return grid;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }   
        return null; 
    }
}