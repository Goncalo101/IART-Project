package iart.city_plan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;

public class InputProcessor {

    public int city_rows, city_columns, max_walking_dist, num_projects;

    public String proj_type;
    public int proj_rows, proj_columns, proj_capacity;

    public Pair openFile() throws Exception {

        File file = new File("input/a_example.in");

        BufferedReader br = new BufferedReader(new FileReader(file));

        // City
        parse_city(br.readLine());

        // Projects
        LinkedList projs = new LinkedList<String[]>();

        for (int i = 0; i < this.num_projects; i++) {

            this.parse_project(br.readLine());

            String[] proj_plan = new String[this.proj_rows];

            // Proj plan
            for (int j = 0; j < this.proj_rows; j++) {
                proj_plan[j] = br.readLine();
                System.out.println(proj_plan[j]);
            }
            System.out.println();
        }

        br.close();
    }

    private void parse_city(String line) {
        String[] aux = line.split(" ");

        this.city_rows = Integer.parseInt(aux[0]);

        this.city_columns = Integer.parseInt(aux[1]);

        this.max_walking_dist = Integer.parseInt(aux[2]);

        this.num_projects = Integer.parseInt(aux[3]);

    }

    private void parse_project(String line) {
        String[] aux = line.split(" ");

        this.proj_type = aux[0];

        this.proj_rows = Integer.parseInt(aux[1]);

        this.proj_columns = Integer.parseInt(aux[2]);

        this.proj_capacity = Integer.parseInt(aux[3]);
    }
}
