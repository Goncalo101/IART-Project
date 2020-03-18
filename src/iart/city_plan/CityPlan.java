package iart.city_plan;

import iart.city_plan.util.InputProcessor;

public class CityPlan {
    public static void main(String[] args) {
        InputProcessor ip = new InputProcessor();

        try {
            ip.openFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
