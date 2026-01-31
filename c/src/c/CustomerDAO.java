package c;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDAO {

    // Validations

    private boolean isValidName(String name) {
        return name.matches("[A-Za-z ]+");
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    //Customer Registration
    public void addCustomer(String name, String email, String phone, String password) {

        //Validation for name
        if (!isValidName(name)) {
            System.out.println("Invalid Name! Name should contain only alphabets.");
            return;
        }

        //Validation for email
        if (!isValidEmail(email)) {
            System.out.println("Invalid Email! Email must end with @gmail.com");
            return;
        }

        //Validation for phone no.
        if (!isValidPhone(phone)) {
            System.out.println("Invalid Phone Number! Must contain only 10 digits.");
            return;
        }

        //Validation for password
        if (!isValidPassword(password)) {
            System.out.println("Password is too short! Minimum 6 characters required.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into customer(name,email,phone_no,password) values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);
            ps.executeUpdate();

            System.out.println("Customer registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Customer Login
    public int loginCustomer(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select cus_id from customer where email=? and password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("cus_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Customers can be seen by admin
    public void viewCustomers() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select cus_id,name,email,phone_no from customer"
            );

//            System.out.println("\n--- Customers ---");
            while (rs.next()) {
                System.out.println(rs.getInt("cus_id") + " | " +rs.getString("name") + " | " +rs.getString("email") + " | " +rs.getString("phone_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}