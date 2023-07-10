
import com.opencsv.CSVWriter;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 EMPLOYEE ATTENDANCE MONITORING SYSTEM for Home Electronics
 @author Aisha Nicole L. Dones
 */

public class EmployeeController {

    private final ArrayList<HashMap<String, String>> empSignInAttendance, empSignOutAttendance;
    private Date today = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private final String strToday = formatter.format(today);
    private EmployeeModel model;

    public EmployeeController(EmployeeModel model){
        this.empSignInAttendance = model.getSignInAttendanceList();
        this.empSignOutAttendance = model.getSignOutAttendanceList();
        this.model = model;
    } // end of constructor

    public void importDataFromCSV(EmployeeModel model, boolean sameDay) throws FileNotFoundException {

        formatter = new SimpleDateFormat("MM-dd-yyyy");
        String fileDate = formatter.format(today);
        Scanner sc;
        if (sameDay) {
            sc = new Scanner(new File( "attendance-files/" + fileDate+ "-Attendance.csv"));
        } else {
            sc = new Scanner(new File(getLastModifiedFile(new File("attendance-files")).getAbsolutePath()));
        }

        sc.nextLine(); // skips column headers
        String line = null;

        while (sc.hasNext() && !(line = sc.nextLine()).isEmpty())
        {
            Scanner readLine = new Scanner(line);
            readLine.useDelimiter("\"");
            int id = Integer.parseInt(readLine.next()); // reads employee id
            readLine.next(); // skips comma
            String name = readLine.next(); // reads employee name
            readLine.next(); // skips comma
            String dept = readLine.next(); // reads employee department
            readLine.next(); // skips comma

            // for schedule ----------------------------
            String sched = readLine.next();
            sched = sched.substring(1, sched.length() - 1); // excludes the brackets
            ArrayList<String> schedList = new ArrayList<String>(Arrays.asList(sched.split(", ")));

            HashMap<String, String> signIn = new HashMap<>();
            HashMap<String, String> signOut = new HashMap<>();

            if (sameDay) {
                readLine.next(); // skips comma
                String strIn = readLine.next();
                if (!strIn.equals("--")) {
                    signIn.put(strToday, strIn);
                }
                readLine.next(); // skips comma
                String strOut = readLine.next();
                if (!strOut.equals("--")) {
                    signOut.put(strToday, strOut);
                }
            }

            empSignInAttendance.add(signIn);
            empSignOutAttendance.add(signOut);
            model.setEmployee(id, name, dept, schedList, signIn, signOut);
            readLine.close();
        }

        sc.close();  //closes the scanner
    } // end of method

    public void exportDataToCSV(JTable table) throws IOException{

        String[] line;
        List<String[]> csvData = new ArrayList<>();
        String[] header = {"ID", "Name", "Department", "Schedule", "Sign-in", "Sign-out"};
        csvData.add(header);

        for (int i = 0; i < model.getNames().size(); i++){

            line = new String[]{
                    table.getValueAt(i, 0).toString(), // ID
                    table.getValueAt(i, 1).toString(), // name
                    table.getValueAt(i, 2).toString(), // department
                    table.getValueAt(i, 3).toString(), // schedule
                    table.getValueAt(i, 4).toString(), // sign-in
                    table.getValueAt(i, 5).toString(), // sign-out
            };

            csvData.add(line);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String strToday = formatter.format(today);

        String folder = new File("attendance-files").getAbsolutePath();
        try (CSVWriter writer = new CSVWriter(new FileWriter(folder + "/" + strToday + "-Attendance.csv"))) {
            writer.writeAll(csvData);
            System.out.println("Data saved successfully.");
        }
    } // end of method

    private File getLastModifiedFile(File directory) {
        File[] files = directory.listFiles();
        if (files.length == 0) return null;
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return Long.compare(o2.lastModified(), o1.lastModified());
            }});
        return files[0];
    } // end of method

} // end of class
