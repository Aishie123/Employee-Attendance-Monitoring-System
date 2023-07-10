import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
EMPLOYEE ATTENDANCE MONITORING SYSTEM for Home Electronics
@author Aisha Nicole L. Dones
 */

@SuppressWarnings({"unchecked", "ConstantConditions", "rawtypes", "FieldCanBeLocal"})
public class EmployeeView extends EmployeeFrame implements ActionListener, MouseListener, KeyListener, WindowListener {

    private JLabel lblID, lblName, lblSched, lblDept, lblSelect, lblSearch, lblGreetings;
    private JTextField txtID, txtName, txtSearch;
    private JComboBox departmentBox;
    private JCheckBox dayBox1, dayBox2, dayBox3, dayBox4, dayBox5, dayBox6, dayBox7;
    private JButton btnAdd, btnClear, btnSignIn, btnSignOut, btnUpdate, btnDelete, btnClose;
    private JPanel panelInput, panelAttendance, panelTable, panelButtons, panelSearch, panelGreetings;

    private JTable tblEmp;
    private DefaultTableModel modelEmp;

    private final Font fTitle = new Font("Google Sans", Font.BOLD, 16);
    private final Font fPlain = new Font("Google Sans", Font.PLAIN, 14);
    private final Font fBold = new Font("Google Sans", Font.BOLD, 14);

    private final EmployeeModel model = new EmployeeModel();
    private final EmployeeController controller = new EmployeeController(model);

    private Date today = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private final String strToday = formatter.format(today);

    public EmployeeView() throws FileNotFoundException {

        init();
        panelEmpInput();
        panelEmpButtons();
        checkRecords();
        panelEmpAttendance();

        // adds panels to Frame
        add(panelInput).setBounds(20,10,300,250);
        add(panelAttendance).setBounds(20, 260, 300, 100);
        add(panelEmpSearch()).setBounds(330, 10, 300, 30);
        add(panelEmpGreetings()).setBounds(640, 15, 200, 30);
        add(panelTable).setBounds(330, 50, 550, 280);
        add(panelButtons).setBounds(330, 330, 550, 30);

        add(setBackgroundImage("images/bg.png")); // sets background

        // registers buttons to listeners
        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnSignIn.addActionListener(this);
        btnSignOut.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClose.addActionListener(this);
        txtSearch.addKeyListener(this);
        txtName.addKeyListener(this);
        txtID.addKeyListener(this);
        tblEmp.addMouseListener(this);

        addWindowListener(this);
        resetComponents();

    }  // end of constructor

    // JPanel-related Methods --------------------------------------------------------------------------

    private void panelEmpInput() {

        panelInput = new JPanel();
        TitledBorder border = new TitledBorder("Employee Information");
        border.setTitleFont(fTitle);
        panelInput.setBorder(BorderFactory.createTitledBorder(border));
        panelInput.setOpaque(false); // creates a transparent panel

        GridLayout layout = new GridLayout(8, 2); // 8 rows and 2 columns
        panelInput.setLayout(layout);
        layout.setVgap(5);

        //adding components
        panelInput.add(lblID); panelInput.add(txtID);
        panelInput.add(lblName); panelInput.add(txtName);
        panelInput.add(lblDept); panelInput.add(departmentBox);
        panelInput.add(lblSched); panelInput.add(dayBox1);
        panelInput.add(dayBox2); panelInput.add(dayBox3);
        panelInput.add(dayBox4); panelInput.add(dayBox5);
        panelInput.add(dayBox6); panelInput.add(dayBox7);
        panelInput.add(btnAdd); panelInput.add(btnClear);

    } //end of method

    public void panelEmpButtons(){

        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 3, 5,2)) ;
        panelButtons.setOpaque(false); // creates a transparent panel
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClose);

    } //end of method

    private void panelEmpAttendance() {

        panelAttendance = new JPanel();
        TitledBorder border = new TitledBorder("Employee Attendance");
        border.setTitleFont(fTitle);
        panelAttendance.setBorder(BorderFactory.createTitledBorder(border));
        panelAttendance.setOpaque(false); // creates a transparent panel
        panelAttendance.setLayout(new GridLayout(2, 1)); // 2 rows and 1 column

        JPanel panelAttButtons = new JPanel();
        panelAttButtons.setOpaque(false);
        panelAttButtons.setLayout(new GridLayout(1, 2)); // 1 row and 2 columns

        // adding components
        panelAttButtons.add(btnSignIn); panelAttButtons.add(btnSignOut);
        panelAttendance.add(lblSelect);
        panelAttendance.add(panelAttButtons);


    } // end of method

    private void panelEmpTable(){

        panelTable = new JPanel();
        tblEmp = new JTable();
        modelEmp = new DefaultTableModel();

        panelTable.setLayout(new BorderLayout());
        panelTable.add(new JScrollPane(tblEmp), BorderLayout.CENTER);

        String[] cols = {"ID", "Name", "Department", "Schedule", "Sign-In", "Sign-Out"};

        Vector columns = new Vector<>();
        //for each loop, store array elements to vector columns
        columns.addAll(Arrays.asList(cols));

        modelEmp.setColumnIdentifiers(columns);
        tblEmp.setModel(modelEmp);
        tblEmp.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblEmp.setDefaultEditor(Object.class, null);
        tblEmp.setFont(fPlain);
        tblEmp.setAutoCreateRowSorter(false); // sorting of the rows on a particular column

        tblEmp.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblEmp.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblEmp.getColumnModel().getColumn(2).setPreferredWidth(175);
        tblEmp.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblEmp.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblEmp.getColumnModel().getColumn(5).setPreferredWidth(100);

        JTableHeader tableHeader = tblEmp.getTableHeader();
        tableHeader.setFont(fBold);

    } // end of method

    public JPanel panelEmpSearch() {

        panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 2,1));
        panelSearch.add(lblSearch);
        panelSearch.add(txtSearch);
        panelSearch.setOpaque(false);
        return panelSearch;

    } //end of method

    public JPanel panelEmpGreetings() {

        panelGreetings = new JPanel();
        panelGreetings.setLayout(new FlowLayout(FlowLayout.LEFT, 2,1));
        panelGreetings.add(lblGreetings);
        panelGreetings.setOpaque(false);
        return panelGreetings;

    } //end of method


    // ActionListener Method -----------------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(btnAdd)){ // adds an employee --------------------------------------
            dataToModel(-1);
            resetComponents();

        } else if (e.getSource().equals(btnClear)) { // clears user input and deselects selections ------------------
            resetComponents();

        } else if (e.getSource().equals(btnUpdate)) { // updates employee info --------------------------------------
            int i = tblEmp.getSelectedRow();
            System.out.println("Selected index: " + i);
            ArrayList<String> list = new ArrayList<>();
            dataToModel(i);

            for(int col = 0; col < tblEmp.getColumnCount(); col++) {
                tblEmp.setValueAt(model.getTableData(i).get(col), i, col);
                list.add((String) tblEmp.getValueAt(i, col));
            }

            resetComponents();

        } else if (e.getSource().equals(btnDelete)) { // deletes employee --------------------------------------
            int i = tblEmp.getSelectedRow();
            modelEmp.removeRow(i);
            model.deleteEmployee(i);
            resetComponents();

        } else if(e.getSource().equals(btnClose)) { // closes program --------------------------------------
            try {
                controller.exportDataToCSV(tblEmp);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);

        } else if(e.getSource().equals(btnSignIn)) { // employee signs in --------------------------------------
            int row = tblEmp.getSelectedRow();

            today = new Date();
            formatter = new SimpleDateFormat("HH:mm");
            String strTime = formatter.format(today);

            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            String greetings;

            if(timeOfDay >= 0 && timeOfDay < 12){
                greetings = "Good morning, " + model.getTableData(row).get(1) + "!";

            } else if(timeOfDay >= 12 && timeOfDay < 18){
                greetings = "Good afternoon, " + model.getTableData(row).get(1) + "!";

            } else {
                greetings = "Good evening, " + model.getTableData(row).get(1) + "!";

            } // end of if

            if(tblEmp.getValueAt(row, 4).equals("--")){
                signIn(row, strTime, greetings);

            } else {
                int choice = JOptionPane.showConfirmDialog(null,
                        "This employee has already signed in for this date.\n" +
                                "Do you wish to overwrite the previous time input?",
                        "Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    signIn(row, strTime, greetings);
                }
            }
            resetComponents();

        } else if(e.getSource().equals(btnSignOut)){ // employee signs out --------------------------------------
            int row = tblEmp.getSelectedRow();

            today = new Date();
            formatter = new SimpleDateFormat("HH:mm");
            String strTime = formatter.format(today);

            if(tblEmp.getValueAt(row, 4).equals("--")){
                JOptionPane.showMessageDialog(null, "ERROR. Employee has not signed in yet.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

            } else if (tblEmp.getValueAt(row, 5).equals("--")){
                signOut(row, strTime);

            }  else {
                int choice = JOptionPane.showConfirmDialog(null,
                        "This employee has already signed out for this date.\n" +
                                "Do you wish to overwrite the previous time input?",
                        "Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    signOut(row, strTime);
                }
            }
            resetComponents();
        } //end of if
    } // end of method


    // KeyListener Methods -----------------------------------------------------------------------------

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(txtName)) { // only allowing letters, spaces, and periods
            char ch = e.getKeyChar();
            if (!((Character.isWhitespace(ch) || e.getKeyChar() == '.' || e.getKeyChar() >= 'a' || e.getKeyChar() >= 'A')
                    && (e.getKeyChar() <= 'z' || e.getKeyChar() <= 'Z'))) {
                e.consume();
            }
        } else if (e.getSource().equals(txtID)) { // only allowing digits
            char ch = e.getKeyChar();
            if (!Character.isDigit(ch)){
                e.consume();
            }
        }
    } // end of method

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource().equals(txtSearch)){
            String search=txtSearch.getText();
            // binds TableRowSorter and DefaltTableModel
            TableRowSorter tblSort = new TableRowSorter(modelEmp);

            // sets the Table with TableRowSorter for sorting/searching
            tblEmp.setRowSorter(tblSort);

            tblSort.setRowFilter(RowFilter.regexFilter(search,0,1,2));
        } // end of if
    } // end of method

    @Override
    public void keyPressed(KeyEvent e) {}


    // MouseListener Methods -----------------------------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getSource().equals(tblEmp)){
            resetComponents();

            int i = tblEmp.getSelectedRow();

            txtID.setText(tblEmp.getValueAt(i,0).toString());
            txtName.setText(tblEmp.getValueAt(i, 1).toString());
            departmentBox.setSelectedItem(tblEmp.getValueAt(i, 2));

            String sched = tblEmp.getValueAt(i, 3).toString();
            sched = sched.substring(1, sched.length() - 1); // excludes the brackets
            ArrayList<String> asList = new ArrayList<String>(Arrays.asList(sched.split(", ")));

            if (asList.contains("SN")){ dayBox1.setSelected(true); }
            if (asList.contains("M")){ dayBox2.setSelected(true); }
            if (asList.contains("T")){ dayBox3.setSelected(true); }
            if (asList.contains("W")){ dayBox4.setSelected(true); }
            if (asList.contains("TH")){ dayBox5.setSelected(true); }
            if (asList.contains("F")){ dayBox6.setSelected(true); }
            if (asList.contains("ST")){ dayBox7.setSelected(true); }

            lblSelect.setText(tblEmp.getValueAt(i, 1).toString() + " is selected.");
            btnAdd.setEnabled(false);
            btnSignIn.setEnabled(true);
            btnSignOut.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    } // end of method

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}


    // WindowListener Methods -----------------------------------------------------------------------------

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            controller.exportDataToCSV(tblEmp);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}


    // OTHER METHODS ----------------------------------------------------------------------------------------

    // method for initializing components
    public void init(){
        String[] departments = {"Whitegoods", "Health & Digital Marketer", "TV & HiFi", "Home Appliances", "Homewares",
                "Digital/IT", "Toys", "Floor Supervisor", "Customer Service", "Security", "Baggers/Warehouse"};
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        lblID = new JLabel("ID: ");
        lblName = new JLabel("Name: ");
        lblDept = new JLabel("Department: ");
        lblSched = new JLabel("Schedule: ");
        lblSelect = new JLabel("Select an employee on the table.");
        lblSearch = new JLabel("Search: ");
        lblGreetings = new JLabel("");
        txtID = new JTextField (20);
        txtName = new JTextField (20);
        txtSearch = new JTextField(10);

        departmentBox = new JComboBox(departments);

        dayBox1 = new JCheckBox(days[0]);
        dayBox2 = new JCheckBox(days[1]);
        dayBox3 = new JCheckBox(days[2]);
        dayBox4 = new JCheckBox(days[3]);
        dayBox5 = new JCheckBox(days[4]);
        dayBox6 = new JCheckBox(days[5]);
        dayBox7 = new JCheckBox(days[6]);

        btnAdd = new JButton("Add New");
        btnClear = new JButton("Clear");
        btnSignIn = new JButton("Sign In");
        btnSignOut = new JButton("Sign Out");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");

        lblID.setFont(fPlain);
        lblName.setFont(fPlain);
        lblDept.setFont(fPlain);
        lblSched.setFont(fPlain);
        lblSelect.setFont(fPlain);
        lblSearch.setFont(fBold);
        lblGreetings.setFont(fTitle);

        txtID.setFont(fPlain);
        txtName.setFont(fPlain);
        txtSearch.setFont(fPlain);

        departmentBox.setFont(fPlain);
        dayBox1.setFont(fPlain);
        dayBox2.setFont(fPlain);
        dayBox3.setFont(fPlain);
        dayBox4.setFont(fPlain);
        dayBox5.setFont(fPlain);
        dayBox6.setFont(fPlain);
        dayBox7.setFont(fPlain);

        btnAdd.setFont(fBold);
        btnClear.setFont(fBold);
        btnSignIn.setFont(fBold);
        btnSignOut.setFont(fBold);
        btnUpdate.setFont(fBold);
        btnDelete.setFont(fBold);
        btnClose.setFont(fBold);

    } // end of method

    // method for checking existing attendance records
    private void checkRecords() throws FileNotFoundException {
        String folder = new File("attendance-files").getAbsolutePath();
        File parentDir = new File(folder);
        formatter = new SimpleDateFormat("MM-dd-yyyy");
        String fileDate = formatter.format(today);

        File f = new File("attendance-files/" + fileDate + "-Attendance.csv");
        System.out.println(f);
        if(f.exists()) {
            controller.importDataFromCSV(model, true);
        } else if(parentDir.list().length != 0){
            controller.importDataFromCSV(model, false);
        }

        panelEmpTable();

        if (!model.getTableData().isEmpty()){
            for (ArrayList<String> row : model.getTableData()){
                Vector rowData = new Vector(row);
                if(rowData.get(4) == null) {
                    rowData.set(4, "--");
                }
                if(rowData.get(5) == null) {
                    rowData.set(5, "--");
                }
                System.out.println(rowData);
                modelEmp.addRow(rowData);
            }
        }
    } // end of method

    // method for transferring data to model
    private void dataToModel(int i) {

        ArrayList<String> schedList = new ArrayList<>();
        if (dayBox1.isSelected()){ schedList.add("SN"); }
        if (dayBox2.isSelected()){ schedList.add("M"); }
        if (dayBox3.isSelected()){ schedList.add("T"); }
        if (dayBox4.isSelected()){ schedList.add("W"); }
        if (dayBox5.isSelected()){ schedList.add("TH"); }
        if (dayBox6.isSelected()){ schedList.add("F"); }
        if (dayBox7.isSelected()){ schedList.add("ST"); }


        try {
            if (i == -1) { // for adding new data
                model.setEmployee(Integer.parseInt(txtID.getText()), txtName.getText(),
                        departmentBox.getSelectedItem().toString(), schedList);
                Vector rowData = new Vector<String>();
                rowData.add(txtID.getText());
                rowData.add(txtName.getText());
                rowData.add(departmentBox.getSelectedItem());
                rowData.add(schedList);
                rowData.add("--");
                rowData.add("--");
                modelEmp.addRow(rowData);
            } else { // for changing existing data
                // passing on the index, ID, name, department, schedule, sign-in time, and sign-out time
                model.setEmployee(i, Integer.parseInt(txtID.getText()), txtName.getText(),
                        departmentBox.getSelectedItem().toString(), schedList,
                        tblEmp.getValueAt(i, 4).toString(), tblEmp.getValueAt(i, 5).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR. Please enter the required values.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    } //end of method

    // method for resetting components
    private void resetComponents() {

        btnAdd.setEnabled(true);
        btnClear.setEnabled(true);
        btnClose.setEnabled(true);

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSignIn.setEnabled(false);
        btnSignOut.setEnabled(false);

        // clears the text fields
        txtID.setText("");
        txtName.setText("");

        lblSelect.setText("Select an employee on the table.");

        // sets values by default to first index
        departmentBox.setSelectedIndex(0);

        // deselects check boxes
        dayBox1.setSelected(false);
        dayBox2.setSelected(false);
        dayBox3.setSelected(false);
        dayBox4.setSelected(false);
        dayBox5.setSelected(false);
        dayBox6.setSelected(false);
        dayBox7.setSelected(false);

    } //end of method

    public void displayGreetings(String greetings) throws InterruptedException {
        lblGreetings.setText(greetings);
        lblGreetings.setVisible(true);

        int delay = 10000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lblGreetings.setVisible(false);
            }
        };
        new javax.swing.Timer(delay, taskPerformer).start();
    } // end of method

    private void signIn(int row, String strTime, String greetings){
        model.setSignIn(row, strToday, strTime);
        tblEmp.setValueAt(model.getTableData(row).get(4), row, 4);
        try {
            displayGreetings(greetings);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    } // end of method

    private void signOut(int row, String strTime) {
        model.setSignOut(row, strToday, strTime);
        tblEmp.setValueAt(model.getTableData(row).get(5), row, 5);
        try {
            displayGreetings("Good job today, " + model.getTableData(row).get(1) + "!");
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    } // end of method

} // end of class
