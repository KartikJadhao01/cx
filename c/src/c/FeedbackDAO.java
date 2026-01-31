package c;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class FeedbackDAO {
	
	public void addFeedback(int cusId, int rating, String comments) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into feedback(cus_id,rating,comments,f_date) values(?,?,?,CURDATE())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cusId);
            ps.setInt(2, rating);
            ps.setString(3, comments);
            ps.executeUpdate();
            System.out.println("Feedback submitted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Customer can view their feedback
    public void viewMyFeedback(int cid) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("select * from feedback where cus_id=?");
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.println("Feedback ID: " + rs.getInt("f_id") +" | Rating: " + rs.getInt("rating") +" | Comments: " + rs.getString("comments"));
            }

            if (!hasData) {
                System.out.println("You have not submitted any feedback yet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Admin can see feedbacks of customers
    public void viewAllFeedbacks() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select f_id, cus_id, rating, comments, f_date from feedback"
            );

            System.out.println("\n--- Customer Feedbacks ---");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                System.out.println("Feedback ID: " + rs.getInt("f_id") +" | Customer ID: " + rs.getInt("cus_id") +" | Rating: " + rs.getInt("rating") +" | Comments: " + rs.getString("comments") +" | Date: " + rs.getDate("f_date"));
            }

            if (!hasData) {
                System.out.println("No feedbacks available.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}