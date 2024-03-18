package fr.vannes.ecoboat.utils;

/**
 * Utility class
 */
public class Utils {
    
    /**
     * Method to calculate the water quality
     * @param temperature The temperature
     * @param pH The pH
     * @param nitrates The nitrates
     * @return The water quality
     */
    public static int calculateWaterQuality(double temperature, double pH, double nitrates) {
    // Temperature normalisation
    double T = (temperature - 10) / (35 - 10);

    // pH normalisation
    double P = 1 - Math.abs(pH - 7) / 7;

    // Nitrates normalisation
    double N = 1 - (nitrates / 50);

    // Water quality calculation
    double waterQuality = ((T + P + N) / 3) * 100;

    // Return Math round to get the water quality as an integer
    return (int) Math.round(waterQuality);
}
    
}
