import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileConverterApp {

    private JFrame frame;
    private JProgressBar overallProgressBar;
    private JTextArea statusTextArea;
    private JButton cancelButton;
    private ExecutorService executor;
    private File[] selectedFiles; // Declare selectedFiles as an instance variable
    private File outputDirectory; // Declare outputDirectory as an instance variable

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileConverterApp::new);
    }

    public FileConverterApp() {
        frame = new JFrame("File Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(new Color(200, 200, 200)); // Light gray panel background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton fileChooserButton = new JButton("Select Files");
        fileChooserButton.setBackground(new Color(100, 150, 255)); // Blue background
        fileChooserButton.setForeground(Color.WHITE); // White text
        fileChooserButton.setFocusPainted(false);

        JButton outputDirButton = new JButton("Select Output Directory");
        outputDirButton.setBackground(new Color(100, 150, 255)); // Blue background
        outputDirButton.setForeground(Color.WHITE); // White text
        outputDirButton.setFocusPainted(false);

        JComboBox<String> formatComboBox = new JComboBox<>(new String[]{"PDF to DOCX", "Image Resize"});
        formatComboBox.setBackground(new Color(255, 255, 255)); // White background
        formatComboBox.setForeground(new Color(50, 50, 50)); // Dark text

        JButton startButton = new JButton("Start Process");
        startButton.setBackground(new Color(100, 200, 100)); // Green background
        startButton.setForeground(Color.WHITE); // White text
        startButton.setFocusPainted(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 100, 100)); // Red background
        cancelButton.setForeground(Color.WHITE); // White text
        cancelButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(fileChooserButton, gbc);

        gbc.gridx = 1;
        topPanel.add(outputDirButton, gbc);

        gbc.gridx = 2;
        topPanel.add(formatComboBox, gbc);

        gbc.gridx = 3;
        topPanel.add(startButton, gbc);

        gbc.gridx = 4;
        topPanel.add(cancelButton, gbc);

        frame.add(topPanel, BorderLayout.NORTH);

        // Create a smaller, compact progress bar
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new BorderLayout());
        progressPanel.setBackground(new Color(240, 240, 240)); // Match frame background

        overallProgressBar = new JProgressBar(0, 100);
        overallProgressBar.setStringPainted(true);
        overallProgressBar.setPreferredSize(new Dimension(300, 20)); // Smaller size
        overallProgressBar.setForeground(new Color(0, 150, 0)); // Green progress color
        overallProgressBar.setBackground(new Color(200, 200, 200)); // Light gray background

        statusTextArea = new JTextArea(15, 50);
        statusTextArea.setEditable(false);
        statusTextArea.setBackground(new Color(255, 255, 255)); // White background
        statusTextArea.setForeground(new Color(50, 50, 50)); // Dark text
        statusTextArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Light gray border

        progressPanel.add(overallProgressBar, BorderLayout.NORTH);
        progressPanel.add(new JScrollPane(statusTextArea), BorderLayout.CENTER);

        frame.add(progressPanel, BorderLayout.CENTER);

        fileChooserButton.addActionListener(this::onFileChooserButtonClick);
        outputDirButton.addActionListener(this::onOutputDirButtonClick);
        startButton.addActionListener(this::onStartButtonClick);
        cancelButton.addActionListener(this::onCancelButtonClick);

        frame.setVisible(true);
    }

    private void onFileChooserButtonClick(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFiles = fileChooser.getSelectedFiles(); // Assign selected files to the instance variable
            statusTextArea.append("Selected files:\n");
            for (File file : selectedFiles) {
                statusTextArea.append(file.getAbsolutePath() + "\n");
            }
        }
    }

    private void onOutputDirButtonClick(ActionEvent e) {
        JFileChooser dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = dirChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            outputDirectory = dirChooser.getSelectedFile(); // Assign output directory to the instance variable
            statusTextArea.append("Output directory: " + outputDirectory.getAbsolutePath() + "\n");
        }
    }

    private void onStartButtonClick(ActionEvent e) {
        if (selectedFiles == null || selectedFiles.length == 0) {
            JOptionPane.showMessageDialog(frame, "No files selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (outputDirectory == null) {
            JOptionPane.showMessageDialog(frame, "No output directory selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Start the process using SwingWorker and ExecutorService
        executor = Executors.newFixedThreadPool(4);
        overallProgressBar.setValue(0);
        statusTextArea.append("Starting process...\n");
        for (File file : selectedFiles) {
            FileProcessWorker worker = new FileProcessWorker(file);
            executor.execute(worker);
        }
    }

    private void onCancelButtonClick(ActionEvent e) {
        // Cancel the ongoing tasks
        executor.shutdownNow();
        statusTextArea.append("Process cancelled.\n");
    }

    private class FileProcessWorker extends SwingWorker<Void, FileStatus> {
        private final File file;

        public FileProcessWorker(File file) {
            this.file = file;
        }

        @Override
        protected Void doInBackground() throws Exception {
            // Simulate processing and save a placeholder message
            for (int i = 0; i <= 100; i += 10) {
                if (isCancelled()) {
                    break;
                }
                Thread.sleep(100); // Simulate time-consuming task

                // Simulate saving a placeholder file
                File outputFile = new File(outputDirectory, "processed_" + file.getName());
                try (FileWriter writer = new FileWriter(outputFile)) {
                    writer.write("Placeholder content for " + file.getName());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                publish(new FileStatus(file.getName(), i, "Processing..."));
            }
            return null;
        }

        @Override
        protected void process(List<FileStatus> chunks) {
            // Update GUI with progress information
            for (FileStatus status : chunks) {
                statusTextArea.append("File: " + status.getFileName() + " - " + status.getMessage() + " (" + status.getProgress() + "%)\n");
                overallProgressBar.setValue(status.getProgress());
            }
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                statusTextArea.append("Process cancelled for " + file.getName() + "\n");
            } else {
                statusTextArea.append("Process complete for " + file.getName() + "\n");
            }
        }
    }

    private static class FileStatus {
        private final String fileName;
        private final int progress;
        private final String message;

        public FileStatus(String fileName, int progress, String message) {
            this.fileName = fileName;
            this.progress = progress;
            this.message = message;
        }

        public String getFileName() {
            return fileName;
        }

        public int getProgress() {
            return progress;
        }

        public String getMessage() {
            return message;
        }
    }
}
