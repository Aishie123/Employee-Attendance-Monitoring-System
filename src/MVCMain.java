import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 EMPLOYEE ATTENDANCE MONITORING SYSTEM for Home Electronics
 @author Aisha Nicole L. Dones
 */

public class MVCMain extends JWindow { // for splash screen
    private final Date today = new Date();
    private final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private final String strToday = formatter.format(today);

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
            new MVCMain().showSplash();
        });
    }  // end of main

    public MVCMain() {
        // sets the background color
        setBackground(Color.WHITE);
    } // end of constructor

    public void showSplash() {
        Color red = new Color(228,47,5, 255); // red
        Color blue = new Color(15,81,168, 255); // blue

        // sets the content panel layout to BorderLayout
        JPanel content = (JPanel)getContentPane();
        content.setLayout(new BorderLayout());
        content.setBackground(Color.WHITE); // sets the background color

        // sets the window's bounds, centering the window
        int width = 900;
        int height = 400;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // builds the splash screen
        JLabel label = new JLabel(new ImageIcon("images/logo.jpeg"));
        content.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        content.add(label, BorderLayout.CENTER);

        // creates a panel for the title labels and progress bar
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setBackground(Color.WHITE);
        loadingPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JLabel hmLabel = new JLabel("Home Electronics");
        JLabel titleLabel = new JLabel("Employee Attendance Monitoring System");
        hmLabel.setFont(new Font("Google Sans", Font.PLAIN, 30));
        hmLabel.setForeground(new Color(15,81,168));
        hmLabel.setHorizontalAlignment(JLabel.CENTER);
        loadingPanel.add(hmLabel, BorderLayout.NORTH);

        titleLabel.setFont(new Font("Google Sans", Font.BOLD, 38));
        titleLabel.setForeground(blue);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        loadingPanel.add(titleLabel, BorderLayout.CENTER);

        // creates a progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        loadingPanel.add(progressBar, BorderLayout.SOUTH);
        content.add(loadingPanel, BorderLayout.SOUTH);

        content.setBorder(BorderFactory.createLineBorder(red, 10)); // sets the border color

        // displays splash screen
        setVisible(true);

        // starts loading resources in the background
        new ResourceLoader(progressBar).execute();
    } // end of method

    public class ResourceLoader extends SwingWorker<Object, Integer> {

        private JProgressBar progressBar;

        public ResourceLoader(JProgressBar progressBar) { this.progressBar = progressBar; }

        @Override
        protected Object doInBackground() throws Exception {
            int progress = 0;
            while (progress < 100) {
                Thread.sleep(300); // Simulate resource loading
                progress += 10; // Increment progress by 10%
                publish(progress); // Update progress bar
            }
            EmployeeView view = new EmployeeView();
            view.setMyFrame(strToday + " Attendance", 900, 400, true);
            return null;
        } // end of method

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int progress = chunks.get(chunks.size() - 1);
            progressBar.setValue(progress);
        } // end of method

        @Override
        protected void done() {
            // closes the splash screen after resources are loaded
            setVisible(false);
            dispose();
        } // end of method

    }

}
