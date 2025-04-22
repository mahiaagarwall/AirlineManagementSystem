import main.LoginFrame; // Import the LoginFrame class from the gui package

public class Main {

    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread-safety for Swing components
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Create an instance of the LoginFrame and make it visible
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
