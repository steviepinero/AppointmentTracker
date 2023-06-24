package com.spinero.company_appointment_tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;


public class CustomerUI extends Application {
    private TableView<Customer> table;
    private TextField customerName, addressField, postalCodeField, phoneNumberField;
    private ComboBox<String> divisionBox, countryBox;

    @Override
    public void start(Stage primaryStage) {
        // Initialize UI components
        table = new TableView<>();
        customerName = new TextField();
        addressField = new TextField();
        postalCodeField = new TextField();
        phoneNumberField = new TextField();
        divisionBox = new ComboBox<>();
        countryBox = new ComboBox<>();

        // Create columns for table
        TableColumn<Customer, String> customerNameColumn = new TableColumn<>("Name");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> postalCodeColumn = new TableColumn<>("Postal Code");
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Customer, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customer, String> divisionColumn = new TableColumn<>("Division");
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

        TableColumn<Customer, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));



        table.getColumns().addAll(customerNameColumn, addressColumn, postalCodeColumn, phoneNumberColumn, divisionColumn, countryColumn);

        // Load data from database
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            table.getItems().addAll(customerDAO.getAllCustomers());
        } catch (SQLException e) {
            // TODO Handle exception
        }

        // Add components to layout and set scene
        VBox layout = new VBox(10, table, customerName, addressField, postalCodeField, phoneNumberField, divisionBox, countryBox);
        Scene scene = new Scene(layout, 1280, 800);

        // Set up stage
        primaryStage.setTitle("Customer Management");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handlers for add, update, and delete buttons
        Button addButton = new Button("Add Customer");
        addButton.setOnAction(e -> {
            Customer customer = new Customer();
            customer.setName(customerName.getText());
            customer.setAddress(addressField.getText());
            customer.setPostalCode(postalCodeField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());
            customer.setDivisionId(divisionBox.getSelectionModel().getSelectedIndex()); // assuming division IDs correspond to combo box indices

            try {
                CustomerDAO.addCustomer(customer);
                table.getItems().add(customer);
            } catch (SQLException ex) {
                // Handle exception
            }
        });

        layout.getChildren().add(addButton);

        Button updateButton = new Button("Update Customer");
        updateButton.setOnAction(e -> {
            Customer selectedCustomer = table.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                selectedCustomer.setName(customerName.getText());
                selectedCustomer.setAddress(addressField.getText());
                selectedCustomer.setPostalCode(postalCodeField.getText());
                selectedCustomer.setPhoneNumber(phoneNumberField.getText());
                selectedCustomer.setDivisionId(divisionBox.getSelectionModel().getSelectedIndex()); // assuming division IDs correspond to combo box indices

                try {
                    CustomerDAO.updateCustomer(selectedCustomer);
                    table.refresh(); // Refresh table to show updated data
                } catch (SQLException ex) {
                    // TODO Handle exception
                }
            }
        });

        layout.getChildren().add(updateButton);
    }

    public static void main(String[] args) {
        launch(args);
    }





}
