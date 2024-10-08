package atmospherecalculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AtmosphereCalculatorGUI extends JFrame implements ActionListener {

	JLabel labTemp, labAlt, labPres, labDens;
	JButton butCalc;
	JTextField altInput;
	Font font = new Font("Arial", Font.BOLD, 16);

	// Constants for atmospheric calculations
	final static double T0 = 288.15;
	final static double P0 = 101325;
	final static double g = 9.80665;
	final static double a0_a1 = -0.0065;
	final static double a1_a2 = 0;
	final static double a2_a3 = 0.001;
	final static double a3_a4 = 0.0028;
	final static double R = 287.00;

	public AtmosphereCalculatorGUI() {
		// Set up the frame
		setTitle("Atmosphere Calculator Plus");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		Container conPan = getContentPane();
		conPan.setLayout(new GridLayout(5, 2)); // 5 rows, 2 columns

		// Create and add components
		JLabel labAltInput = new JLabel("Enter Altitude (m): ");
		labAltInput.setFont(font);
		altInput = new JTextField();
		altInput.setFont(font);

		labTemp = new JLabel("Temperature = ");
		labTemp.setFont(font);

		labPres = new JLabel("Pressure = ");
		labPres.setFont(font);

		labDens = new JLabel("Density = ");
		labDens.setFont(font);

		butCalc = new JButton("Calculate");
		butCalc.addActionListener(this);
		butCalc.setFont(font);

		// Add components to the container
		conPan.add(labAltInput);
		conPan.add(altInput);
		conPan.add(labTemp);
		conPan.add(new JLabel()); // Empty placeholder for layout
		conPan.add(labPres);
		conPan.add(new JLabel()); // Empty placeholder for layout
		conPan.add(labDens);
		conPan.add(new JLabel()); // Empty placeholder for layout
		conPan.add(butCalc);

		setVisible(true);

	}

	public void actionPerformed(ActionEvent ae) {
		try {
			double altitude = Double.parseDouble(altInput.getText());

			// Perform calculations
			double temperature = getTemp(altitude);
			double pressure = getPres(altitude);
			double density = getDens(altitude);

			// Update labels with calculated values
			labTemp.setText("Temperature = " + String.format("%.5f", temperature) + " K / " + String.format("%.5f", (temperature - 273.15)) + " °C");
			labPres.setText("Pressure = " + String.format("%.5f", pressure) + " Pa");
			labDens.setText("Density = " + String.format("%.5f", density) + " kg/m³");

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number for altitude.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	 // Method to calculate temperature based on altitude
    public static double getTemp(double alt) {
        double temperature = 0;
        if (alt <= 11000) {
            temperature = T0 + (a0_a1 * alt);
            return temperature;
        } else if (alt > 11000 && alt <= 20000) {
            temperature = T0 + (a0_a1 * 11000);
            return temperature;
        } else if (alt > 20000 && alt <= 32000) {
            temperature = T0 + (a0_a1 * 11000) + (a2_a3) * (alt - 20000);
            return temperature;
        } else if (alt > 32000 && alt <= 47000) {
            temperature = T0 + (a0_a1 * 11000) + (a2_a3) * (32000 - 20000) + (a3_a4) * (alt - 32000);
            return temperature;
        }
        return 0;
    }
    
    // Method to calculate pressure based on altitude
    public static double getPres(double alt) {
        double pressure = 0;
        if (alt <= 11000) {
            pressure = P0 * Math.pow(((getTemp(alt) / T0)), (-g) / ((a0_a1) * (R)));
            return pressure;
        } else if (alt > 11000 && alt <= 20000) {
            pressure = getPres(11000) * Math.pow(Math.E, (-g) * (alt - 11000) / ((R) * getTemp(alt)));
            return pressure;
        } else if (alt > 20000 && alt <= 32000) {
            pressure = getPres(20000) * Math.pow(getTemp(alt) / getTemp(20000), (-g) / ((a2_a3) * (R)));
            return pressure;
        } else if (alt > 32000 && alt <= 47000) {
            pressure = getPres(32000) * Math.pow(getTemp(alt) / getTemp(32000), (-g) / ((a3_a4) * (R)));
            return pressure;
        }
        return 0;
    }
    // Method to calculate density based on altitude
    public static double getDens(double alt) {
        double density = getPres(alt) / (R * getTemp(alt));
        return density;
    }

}
