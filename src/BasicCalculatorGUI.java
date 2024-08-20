import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public BasicCalculatorGUI() {
        // Set up the frame
        setTitle("Basic Calculator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create GridBagConstraints for layout management
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Input field
        inputField = new JTextField(20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(inputField, gbc);

        // Calculate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.setBackground(new Color(100, 149, 237));
        calculateButton.setForeground(Color.WHITE);
        add(calculateButton, gbc);

        // Result label
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.LIGHT_GRAY);
        resultLabel.setPreferredSize(new Dimension(300, 50));
        add(resultLabel, gbc);

        // Button click event
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                try {
                    int result = evaluateExpression(expression);
                    resultLabel.setText("Result: " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: Invalid expression");
                }
            }
        });
    }

    // Evaluation algorithm
    private int evaluateExpression(String expression) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int num = 0;
        char prevOp = '+';

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            }

            if (!Character.isDigit(ch) && ch != ' ' || i == expression.length() - 1) {
                if (prevOp == '+') {
                    nums.push(num);
                } else if (prevOp == '-') {
                    nums.push(-num);
                } else if (prevOp == '*') {
                    nums.push(nums.pop() * num);
                } else if (prevOp == '/') {
                    nums.push(nums.pop() / num);
                }
                prevOp = ch;
                num = 0;
            }
        }

        int result = 0;
        while (!nums.isEmpty()) {
            result += nums.pop();
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasicCalculatorGUI calculator = new BasicCalculatorGUI();
            calculator.setVisible(true);
        });
    }
}
