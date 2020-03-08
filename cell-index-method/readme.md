1. Compilar:
mvn package

2. Generar archivos estaticos y dinamicos de prueba:
python3 pythonScripts/generateFiles [pathToFiles]

3. Correr el archivo:
java -jar target/cell-index-method-version1.0.jar [args]
 -bf,--brute-force                  Use brute force algorithm
 -cid,--chosen-id <arg>             Chosen Id of the particle whose
                                    neighbours will be displayed in a .xyz
                                    file
 -df,--dynamic-file <arg>           Path to dynamic file
 -h,--help                          Shows help
 -M,--cell-number <arg>             Number of cells
 -pb,--periodic-boundary            Enables periodic binary conditions
 -rc,--neighbourhood-radius <arg>   Minimum radius of neighbourhood
 -sf,--static-file <arg>            Path to static file

4. Correr pruebas con distintos M y Brute Force automaticamente (Linux):
sudo ./runner.sh

5. Graficar los tiempos a partir de los resultados obtenidos con el comando anterior (4.):
python3 pythonScripts/plotTimes.py [pathToTimeFilesDirectory]

6. Graficar una particula y sus vecinos entre todas las particulas (si no se quiere usar ovito):
python3 pythonScripts/drawDots.py [staticFilePath] [dynamicFilePath] [resultsFilePath] [selectedId]