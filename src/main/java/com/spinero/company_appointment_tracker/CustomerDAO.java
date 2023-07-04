package com.spinero.company_appointment_tracker;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class CustomerDAO {
        // Database connection details
        private static final String url = "jdbc:mysql://localhost:3306/c195db"; // replace with your database url
        private static final String user = "admin"; // Db username
        private static final String password = "p@nCak3z!"; // Db password

        public static void addCustomer(Customer customer) throws SQLException {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Country_ID) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                findCustomer(customer, pstmt);

                pstmt.executeUpdate();

            }
        }



        private static void findCustomer(Customer customer, PreparedStatement pstmt) throws SQLException {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPostalCode());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setInt(5, customer.getDivisionId());
            pstmt.setInt(6, customer.getCountryId());
        }


        public static void updateCustomer(Customer customer) throws SQLException {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Country_ID = ? WHERE Customer_ID = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                findCustomer(customer, pstmt);
                pstmt.setInt(7, customer.getId());

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

        public TableView<Customer> getAllCustomers() throws SQLException {
            TableView<Customer> customers = new TableView<>();
            String sql = "SELECT * FROM customers";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getInt("Customer_ID"));
                    customer.setName(rs.getString("Customer_Name"));
                    customer.setAddress(rs.getString("Address"));
                    customer.setPostalCode(rs.getString("Postal_Code"));
                    customer.setPhoneNumber(rs.getString("Phone"));
                    customer.setDivisionId(rs.getInt("Division_ID"));
                    customer.setCountryId(rs.getInt("Country_ID"));

                    customers.setItems((ObservableList<Customer>) customer);
                }
            }

            return customers;
        }


    }
