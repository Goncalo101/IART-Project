package iart.city_plan.util.io;

import iart.city_plan.solver.Solution;

import java.io.*;

public class OutputProcessor {
    private BufferedWriter bw;

    public OutputProcessor(String outputFileName, Solution solution) throws IOException {
        File file = new File(outputFileName);
        if (file.createNewFile()) {
            System.out.println("File " + outputFileName + " doesn't exist. Creating new file.");
        } else {
            System.out.println("File " + outputFileName + " already exists. Skipping creation.");
        }

        this.bw = new BufferedWriter(new FileWriter(file));
    }

    public void writeSolution(Solution solution) throws IOException {
        this.bw.write(solution.toString());
        this.bw.close();
    }
}
