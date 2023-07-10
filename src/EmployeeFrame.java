import javax.swing.*;
import java.awt.*;

public class
EmployeeFrame extends JFrame {

    //data fields for width and height
    private int W, H;
    public EmployeeFrame() {
        super(); //calls superclass JFrame
        H=800; W=500;
        setWindowSize(W, H);
        setResizable(false);
    }

    public void setWindowSize(int width, int height) {
        H = height;
        W = width;
        setSize(width, height);
    }

    public void setMyFrame(String title, int width, int height) {
        setTitle(title);
        setWindowSize(width, height);
        setLocationRelativeTo(null);
    }

    public void setMyFrame(String title, int width, int height, boolean visible) {
        setMyFrame(title, width, height);
        setVisible(visible);
    }

    public void setMyFrame (String title,int width, int height, boolean visible, int close_operation){
        setMyFrame(title, width, height, visible);
        setDefaultCloseOperation(close_operation);
    }

    public void setMyFrame (String title,int width, int height, boolean visible, int close_operation, boolean resize){
        setMyFrame(title, width, height, visible, close_operation);
        setResizable(resize);
    }

    public JPanel setBackgroundImage (String file){

        ImageIcon background=new ImageIcon(file);
        Image image = background.getImage();
        Image temp = image.getScaledInstance(900,400,Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);

        JPanel panelBG = new JPanel();
        JLabel img = new JLabel(background);//set image to Jlabel

        panelBG.add(img); //add label to panelBG
        return panelBG;
    }
} // end of class
