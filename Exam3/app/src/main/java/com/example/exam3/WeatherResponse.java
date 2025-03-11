package com.example.exam3;

public class WeatherResponse {
    private Main main;
    public Main getMain() {
        return main;
    }
    public void setMain(Main main) {
        this.main = main;
    }

    public static class Main {
        private double temp;
        public double getTemp() {
            return temp;
        }
        public void setTemp(double temp) {
            this.temp = temp;
        }
    }
}