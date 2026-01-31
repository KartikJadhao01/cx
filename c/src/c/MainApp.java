package c;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CustomerDAO customerDAO = new CustomerDAO();
        ComplaintDAO complaintDAO = new ComplaintDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        AdminDAO adminDAO = new AdminDAO();

        while (true) {
            System.out.println("\n=== CUSTOMER EXPERIENCE SYSTEM ===");
            System.out.println("1. Customer Registration");
            System.out.println("2. Customer Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Password(min. 6 chars): ");
                    String pass = sc.nextLine();
                    customerDAO.addCustomer(name, email, phone, pass);
                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Email: ");
                    String e = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();

                    int cusId = customerDAO.loginCustomer(e, p);

                    if (cusId != 1) {
                        System.out.println("Login successful!");

                        while (true) {
                            System.out.println("\n1. Register Complaint");
                            System.out.println("2. View My Complaints");
                            System.out.println("3. Give Feedback");
                            System.out.println("4. View My Feedback");
                            System.out.println("5. Logout");
                            System.out.print("Choose: ");

                            int ch = sc.nextInt();

                            switch (ch) {
                                case 1:
                                    sc.nextLine();
                                    System.out.print("Complaint: ");
                                    complaintDAO.addComplaint(cusId, sc.nextLine());
                                    break;
                                case 2:
                                    complaintDAO.viewMyComplaints(cusId);
                                    break;
                                case 3:
                                    sc.nextLine();

                                    System.out.print("Rating (1-5): ");
                                    String ratingInput = sc.nextLine();

                                    int rating;
                                    try {
                                        rating = Integer.parseInt(ratingInput);
                                    } catch (NumberFormatException ex) {
                                        System.out.println("Rating must be a number between 1 and 5.");
                                        break;
                                    }

                                    if (rating < 1 || rating > 5) {
                                        System.out.println("Rating must be between 1 and 5.");
                                        break;
                                    }

                                    System.out.print("Comments: ");
                                    String comments = sc.nextLine();

                                    feedbackDAO.addFeedback(cusId, rating, comments);
                                    break;


                                case 4:
                                    feedbackDAO.viewMyFeedback(cusId);
                                    break;
                                case 5:
                                    System.out.println("Logged out!");
                                    break;
                            }
                            if (ch == 5) break;
                        }
                    } else {
                        System.out.println("Invalid customer login!");
                    }
                    break;

                case 3:
                    sc.nextLine();
                    System.out.print("Admin Username: ");
                    String au = sc.nextLine();
                    System.out.print("Admin Password: ");
                    String ap = sc.nextLine();

                    if (adminDAO.login(au, ap)) {
                        System.out.println("Admin Login Successful!");

                        while (true) {
                            System.out.println("\n=== ADMIN DASHBOARD ===");
                            System.out.println("1. View Complaints");
                            System.out.println("2. View Feedbacks");
                            System.out.println("3. View Customers");
                            System.out.println("4. Logout");
                            System.out.print("Choose: ");

                            int adminChoice = sc.nextInt();

                            switch (adminChoice) {

                                case 1: // view complaint
                                    complaintDAO.viewComplaints();

                                    System.out.print("\nDo you want to update complaint status? (yes/no): ");
                                    sc.nextLine();
                                    String opt = sc.nextLine();

                                    if (opt.equalsIgnoreCase("yes")) {
                                        System.out.print("Enter Complaint ID: ");
                                        int cid = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Enter New Status: ");
                                        String status = sc.nextLine();
                                        complaintDAO.updateStatus(cid, status);
                                    }
                                    break;

                                    
                                case 2: //view feedback
                                    feedbackDAO.viewAllFeedbacks();
                                    break;
                                    
                                case 3:
                                    System.out.println("\n--- Customers ---");
                                    customerDAO.viewCustomers();
                                    break;
                                    
                                case 4:
                                    System.out.println("Admin Logged out!");
                                    break;
                            }

                            if (adminChoice == 4) break;
                        }

                    } else {
                        System.out.println("Invalid admin login!");
                    }
                    break;

                case 4:
                    System.out.println("Thank you!");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}