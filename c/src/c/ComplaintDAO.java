package c;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ComplaintDAO {

    public void addComplaint(int cusId, String desc) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into complaint(cus_id,description,status,c_date) values(?,?,?,CURDATE())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cusId);
            ps.setString(2, desc);
            ps.setString(3, "Pending");
            ps.executeUpdate();
            System.out.println("Complaint registered!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Customer can view their complaints
    public void viewMyComplaints(int cid) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("select * from complaint where cus_id=?");
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.println("Complaint ID: " + rs.getInt("com_id") +" | Description: " + rs.getString("description") +" | Status: " + rs.getString("status"));
            }

            if (!hasData) {
                System.out.println("You have not raised any complaint yet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Admin can view complaints raised by customers
    public void viewComplaints() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from complaint");

            System.out.println("\n--- All Complaints ---");
            
            boolean hasData = false;
            
            while (rs.next()) {
            	hasData = true;
                System.out.println(rs.getInt("com_id") + " | " +rs.getInt("cus_id") + " | " +rs.getString("description") + " | " +rs.getString("status"));
            }
            
            if (!hasData) {
                System.out.println("You have not raised any complaint yet.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int comId, String status) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "update complaint set status=? where com_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, comId);
            ps.executeUpdate();
            System.out.println("Status updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}