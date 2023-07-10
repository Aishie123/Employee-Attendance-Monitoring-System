import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 EMPLOYEE ATTENDANCE MONITORING SYSTEM for Home Electronics
 @author Aisha Nicole L. Dones
 */

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class EmployeeModel {

    private final ArrayList<String> empNameList, empDeptList;
    private final ArrayList<Integer> empIdList;
    private final ArrayList<ArrayList<String>> empSchedList, tableData;
    private final ArrayList<HashMap<String, String>> empSignInAttList, empSignOutAttList;

    private final Date today = new Date();
    private final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private final String strToday = formatter.format(today);

    public EmployeeModel(){
        empNameList = new ArrayList<>();
        empIdList = new ArrayList<>();
        empDeptList = new ArrayList<>();
        empSchedList = new ArrayList<>();
        empSignInAttList = new ArrayList<>();
        empSignOutAttList = new ArrayList<>();
        tableData = new ArrayList<>();
    }  // end of constructor

    public String getName(int index) { return empNameList.get(index); }

    public ArrayList<String> getTableData(int row) { return tableData.get(row); }

    public ArrayList<String> getNames() { return empNameList; }
    public ArrayList<HashMap<String, String>> getSignInAttendanceList() { return empSignInAttList; }
    public ArrayList<HashMap<String, String>> getSignOutAttendanceList() { return empSignOutAttList; }
    public ArrayList<ArrayList<String>> getTableData() { return tableData; }

    public void setSignIn(int index, String date, String time){
        empSignInAttList.get(index).put(date, time);
        tableData.get(index).set(4, time);
        System.out.println(tableData.get(index));
    } // end of method

    public void setSignOut(int index, String date, String time){
        empSignOutAttList.get(index).put(date, time);
        tableData.get(index).set(5, time);
        System.out.println(tableData.get(index));
    } // end of method

    public void setEmployee(int index, int id, String name, String dept, ArrayList<String> sched, String signIn, String signOut){
        empIdList.set(index, id);
        empNameList.set(index, name);
        empDeptList.set(index, dept);
        empSchedList.set(index, sched);
        empSignInAttList.get(index).put(strToday, signIn); // get existing sign in
        empSignOutAttList.get(index).put(strToday, signOut); // get existing sign out
        setRowData(index, id, name, dept, sched, signIn, signOut);
    } // end of method

    public void setEmployee(int id, String name, String dept, ArrayList<String> sched){
        empIdList.add(id);
        empNameList.add(name);
        empDeptList.add(dept);
        empSchedList.add(sched);
        empSignInAttList.add(new HashMap<>());
        empSignOutAttList.add(new HashMap<>());
        setRowData(id, name, dept, sched, "--", "--");
        System.out.println("empNameList: " + empNameList);
    } // end of method

    public void setEmployee(int id, String name, String dept, ArrayList<String> sched,
                            HashMap<String, String> signIn, HashMap<String, String> signOut){
        empIdList.add(id);
        empNameList.add(name);
        empDeptList.add(dept);
        empSchedList.add(sched);
        empSignInAttList.add(signIn);
        empSignOutAttList.add(signOut);
        System.out.println(empSignInAttList.get(empSignInAttList.size()-1).get(strToday));
        setRowData(id, name, dept, sched, empSignInAttList.get(empSignInAttList.size()-1).get(strToday),
                empSignOutAttList.get(empSignOutAttList.size()-1).get(strToday));
        System.out.println("empNameList: " + empNameList);
    } // end of method

    public void setRowData(int index, int id, String name, String dept, ArrayList<String> sched, String signIn, String signOut){
        ArrayList<String> rowData = new ArrayList<>();
        rowData.add(String.valueOf(id));
        rowData.add(name);
        rowData.add(dept);
        rowData.add(sched.toString());
        rowData.add(signIn);
        rowData.add(signOut);
        tableData.set(index, rowData); // change data in row
        System.out.println("rowData: " + rowData);
    } // end of method

    public void setRowData(int id, String name, String dept, ArrayList<String> sched,
                           String signIn, String signOut){
        ArrayList<String> rowData = new ArrayList<>();
        rowData.add(String.valueOf(id));
        rowData.add(name);
        rowData.add(dept);
        rowData.add(sched.toString());
        rowData.add(Objects.requireNonNullElse(signIn, "--"));
        rowData.add(Objects.requireNonNullElse(signOut, "--"));
        tableData.add(rowData);
        System.out.println("rowData: " + rowData);
    } // end of method

    public void deleteEmployee(int idx){
        empNameList.remove(idx);
        empIdList.remove(idx);
        empDeptList.remove(idx);
        empSchedList.remove(idx);
        empSignInAttList.remove(idx);
        empSignOutAttList.remove(idx);
        tableData.remove(idx);
    } // end of method

} // end of class
