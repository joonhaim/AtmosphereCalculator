public class AtmosphereCalculator {
    // Constants for atmospheric calculations
    public static final double T0 = 288.15;
    public static final double P0 = 101325;
    public static final double g = 9.80665;
    public static final double a0_a1 = -0.0065;
    public static final double a2_a3 = 0.001;
    public static final double a3_a4 = 0.0028;
    public static final double R = 287.00;

    // Method to calculate temperature based on altitude
    public static double getTemp(double alt) {
        double temperature = 0;
        if (alt <= 11000) {
            temperature = T0 + (a0_a1 * alt);
        } else if (alt <= 20000) {
            temperature = T0 + (a0_a1 * 11000);
        } else if (alt <= 32000) {
            temperature = T0 + (a0_a1 * 11000) + (a2_a3 * (alt - 20000));
        } else if (alt <= 47000) {
            temperature = T0 + (a0_a1 * 11000) + (a2_a3 * (32000 - 20000)) + (a3_a4 * (alt - 32000));
        }
        return temperature;
    }

    // Method to calculate pressure based on altitude
    public static double getPres(double alt) {
        double pressure = 0;
        if (alt <= 11000) {
            pressure = P0 * Math.pow((getTemp(alt) / T0), (-g / (a0_a1 * R)));
        } else if (alt <= 20000) {
            pressure = getPres(11000) * Math.pow(Math.E, (-g * (alt - 11000)) / (R * getTemp(alt)));
        } else if (alt <= 32000) {
            pressure = getPres(20000) * Math.pow(getTemp(alt) / getTemp(20000), (-g / (a2_a3 * R)));
        } else if (alt <= 47000) {
            pressure = getPres(32000) * Math.pow(getTemp(alt) / getTemp(32000), (-g / (a3_a4 * R)));
        }
        return pressure;
    }

    // Method to calculate density based on altitude
    public static double getDens(double alt) {
        return getPres(alt) / (R * getTemp(alt));
    }
}
