import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AtmosphereCalculatorGUI extends JFrame implements ActionListener {
    JLabel labTemp, labPres, labDens;
    JButton butCalc;
    JTextField altInput;
    Font font = new Font("Arial", Font.BOLD, 16);

    public AtmosphereCalculatorGUI() {
        // Set up the frame
        setTitle("Atmosphere Calculator");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel labAltInput = new JLabel("Enter Altitude (m): ");
        labAltInput.setFont(font);

        altInput = new JTextField(10);
        altInput.setFont(font);
        altInput.setMaximumSize(new Dimension(200, 30)); // Set input size

        inputPanel.add(labAltInput);
        inputPanel.add(altInput);

        // Result panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows for temperature, pressure, density

        labTemp = new JLabel("Temperature = ");
        labTemp.setFont(font);

        labPres = new JLabel("Pressure = ");
        labPres.setFont(font);

        labDens = new JLabel("Density = ");
        labDens.setFont(font);

        resultPanel.add(labTemp);
        resultPanel.add(labPres);
        resultPanel.add(labDens);

        // Button panel
        JPanel buttonPanel = new JPanel();
        butCalc = new JButton("Calculate");
        butCalc.addActionListener(this);
        butCalc.setFont(font);
        buttonPanel.add(butCalc);

        // Adding panels to main layout
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Action handler for the Calculate button
    public void actionPerformed(ActionEvent ae) {
        try {
            double altitude = Double.parseDouble(altInput.getText());

            // Get the calculated values from AtmosphereCalculator
            double temperature = AtmosphereCalculator.getTemp(altitude);
            double pressure = AtmosphereCalculator.getPres(altitude);
            double density = AtmosphereCalculator.getDens(altitude);

            // Update labels with calculated values rounded to 5 decimal places
            labTemp.setText("Temperature = " + String.format("%.5f", temperature) + " K / "
                    + String.format("%.5f", (temperature - 273.15)) + " °C");
            labPres.setText("Pressure = " + String.format("%.5f", pressure) + " Pa");
            labDens.setText("Density = " + String.format("%.5f", density) + " kg/m³");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for altitude.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
