package ss;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CliParser {

    public String staticFile;
    public String dynamicFile;
    public double Rc;
    public boolean periodicBoundary = false;
    public boolean bruteForce = false;
    public int M;
    public Integer chosenId;

    private static Options createOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Shows help");
        options.addOption("M", "cell-number", true, "Number of cells");
        options.addOption("rc", "neighbourhood-radius", true, "Minimum radius of neighbourhood");
        options.addOption("sf", "static-file", true, "Path to static file");
        options.addOption("df", "dynamic-file", true, "Path to dynamic file");
        options.addOption("pb", "periodic-boundary", false, "Enables periodic binary conditions");
        options.addOption("bf", "brute-force", false, "Use brute force algorithm");
        options.addOption("cid", "chosen-id", true, "Chosen Id of the particle whose neighbours will be displayed in a .xyz file");
        return options;
    }

    public void parseOptions(String[] args){
        Options options = createOptions();
        CommandLineParser parser = new BasicParser();

        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("h")){
                help(options);
            }

            if(cmd.hasOption("bf")){
                bruteForce = true;
            }

            if (cmd.hasOption("cid")) {
                this.chosenId = Integer.parseInt(cmd.getOptionValue("cid"));
            }

            if (!cmd.hasOption("df") || !cmd.hasOption("sf")){
                System.out.println("Need static file and dynamic file");
                System.exit(1);
            }

            if (cmd.hasOption("M")) {
                M = Integer.parseInt(cmd.getOptionValue("M"));
            }

            dynamicFile = cmd.getOptionValue("df");
            staticFile = cmd.getOptionValue("sf");

            if (cmd.hasOption("rc")){
                Rc = Double.parseDouble(cmd.getOptionValue("rc"));
            } else {
                Rc = 2.0;
            }

            if (cmd.hasOption("pb")){
                periodicBoundary = true;
            }

        }catch (Exception e){
            System.out.println("Command not recognized");
            help(options);
        }
    }

    private static void help(Options options){
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Main", options);
        System.exit(0);
    }
}