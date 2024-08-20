import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RouteOptimizationApp extends JFrame {
    private JTextArea deliveryListArea;
    private JTextField vehicleCapacityField;
    private JTextField drivingDistanceField;
    private JComboBox<String> algorithmComboBox;
    private JButton optimizeButton;
    private JPanel mapPanel;

    public RouteOptimizationApp() {
        setTitle("Route Optimization for Delivery Service");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.WEST);

        mapPanel = new JPanel();
        mapPanel.setBackground(Color.WHITE); // Placeholder for map visualization
        add(mapPanel, BorderLayout.CENTER);

        // Make sure the UI is responsive
        setLocationRelativeTo(null);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Delivery Points
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Delivery Points (address, priority):"), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        deliveryListArea = new JTextArea(10, 30);
        deliveryListArea.setLineWrap(true);
        deliveryListArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(deliveryListArea);
        panel.add(scrollPane, gbc);

        // Vehicle Capacity
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        panel.add(new JLabel("Vehicle Capacity:"), gbc);

        gbc.gridx = 1;
        vehicleCapacityField = new JTextField(10);
        panel.add(vehicleCapacityField, gbc);

        // Driving Distance Constraint
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Driving Distance Constraint:"), gbc);

        gbc.gridx = 1;
        drivingDistanceField = new JTextField(10);
        panel.add(drivingDistanceField, gbc);

        // Algorithm Selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Optimization Algorithm:"), gbc);

        gbc.gridx = 1;
        String[] algorithms = {"Algorithm 1", "Algorithm 2"}; // Replace with actual algorithms
        algorithmComboBox = new JComboBox<>(algorithms);
        panel.add(algorithmComboBox, gbc);

        // Optimize Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        optimizeButton = new JButton("Optimize Route");
        optimizeButton.addActionListener(new OptimizeButtonListener());
        panel.add(optimizeButton, gbc);

        return panel;
    }

    private class OptimizeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Read input values
                String deliveryList = deliveryListArea.getText();
                int vehicleCapacity = Integer.parseInt(vehicleCapacityField.getText());
                int drivingDistance = Integer.parseInt(drivingDistanceField.getText());
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

                // Perform route optimization using the selected algorithm
                // Placeholder message for now
                JOptionPane.showMessageDialog(null, "Optimizing route using " + selectedAlgorithm);

                // Here you would implement the algorithm and update the mapPanel
                // For example, visualize the route on mapPanel
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for vehicle capacity and driving distance.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RouteOptimizationApp app = new RouteOptimizationApp();
            app.setVisible(true);
        });
    }
}
