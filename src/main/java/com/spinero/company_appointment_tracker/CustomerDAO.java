package com.spinero.company_appointment_tracker;

import java.util.ArrayList;
import java.util.List;

    public class CustomerDAO {
        // Database connection details
        private final String url = "jdbc:mysql://localhost:3306/c195db"; // replace with your database url
        private final String user = "admin"; // Db username
        private final String password = "p@nCak3z!"; // Db password

        public void addCustomer(Customer customer) throws SQLException {
            String sql = "INSERT INTO customers (Name, Address, PostalCode, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, customer.getName());
                pstmt.setString(2, customer.getAddress());
                pstmt.setString(3, customer.getPostalCode());
                pstmt.setString(4, customer.getPhoneNumber());
                pstmt.setInt(5, customer.getDivisionId()); // assuming division ID is an integer

                pstmt.executeUpdate();
            }
        }



        public void updateCustomer(Customer customer) throws SQLException {
            String sql = "UPDATE customers SET Name = ?, Address = ?, PostalCode = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, customer.getName());
                pstmt.setString(2, customer.getAddress());
                pstmt.setString(3, customer.getPostalCode());
                pstmt.setString(4, customer.getPhoneNumber());
                pstmt.setInt(5, customer.getDivisionId()); // assuming division ID is an integer
                pstmt.setInt(6, customer.getId());

                pstmt.executeUpdate();
            }
        }

        public void deleteCustomer(int id) throws SQLException {
            String deleteAppointmentsSql = "DELETE FROM appointments WHERE Customer_ID = ?";
            String deleteCustomerSql = "DELETE FROM customers WHERE Customer_ID = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement deleteAppointmentsStmt = conn.prepareStatement(deleteAppointmentsSql);
                 PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSql)) {

                // Delete appointments
                deleteAppointmentsStmt.setInt(1, id);
                deleteAppointmentsStmt.executeUpdate();

                // Delete customer
                deleteCustomerStmt.setInt(1, id);
                deleteCustomerStmt.executeUpdate();
            }
        }

        public List<Customer> getAllCustomers() throws SQLException {
            List<Customer> customers = new ArrayList<>();
            String sql = "SELECT * FROM customers";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("Customer_ID"));
                    customer.setName(rs.getString("Name"));
                    customer.setAddress(rs.getString("Address"));
                    customer.setPostalCode(rs.getString("PostalCode"));
                    customer.setPhoneNumber(rs.getString("Phone"));
                    customer.setDivisionId(rs.getInt("Division_ID")); // assuming division ID is an integer

                    customers.add(customer);
                }
            }

            return customers;
        }

    }
