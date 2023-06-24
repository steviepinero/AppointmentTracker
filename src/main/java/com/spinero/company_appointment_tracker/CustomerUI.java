package com.spinero.company_appointment_tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerUI extends Application {
    private TableView<Customer> table;
    private TextField nameField, addressField, postalCodeField, phoneNumberField;
    private ComboBox<String> divisionBox, countryBox;

    @Override
    public void start(Stage primaryStage) {
        // Initialize UI components
        table = new TableView<>();
        nameField = new TextField();
        addressField = new TextField();
        postalCodeField = new TextField();
        phoneNumberField = new TextField();
        divisionBox = new ComboBox<>();
        countryBox = new ComboBox<>();

        // Create columns for table
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // TODO Repeat for other fields...

        table.getColumns().addAll(nameColumn /*, other columns... */);

        // Load data from database
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            table.getItems().addAll(customerDAO.getAllCustomers());
        } catch (SQLException e) {
            // TODO Handle exception
        }

        // Add components to layout and set scene
        VBox layout = new VBox(10, table, nameField, addressField, postalCodeField, phoneNumberField, divisionBox, countryBox);
        Scene scene = new Scene(layout, 800, 600);

        // Set up stage
        primaryStage.setTitle("Customer Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    // Event handlers for add, update, and delete buttons
    Button addButton = new Button("Add Customer");
    addButton.setOnAction(e -> {
        Customer customer = new Customer();
        customer.setName(nameField.getText());
        customer.setAddress(addressField.getText());
        customer.setPostalCode(postalCodeField.getText());
        customer.setPhoneNumber(phoneNumberField.getText());
        customer.setDivisionId(divisionBox.getSelectionModel().getSelectedIndex()); // assuming division IDs correspond to combo box indices

        try {
            customerDAO.addCustomer(customer);
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
            selectedCustomer.setName(nameField.getText());
            selectedCustomer.setAddress(addressField.getText());
            selectedCustomer.setPostalCode(postalCodeField.getText());
            selectedCustomer.setPhoneNumber(phoneNumberField.getText());
            selectedCustomer.setDivisionId(divisionBox.getSelectionModel().getSelectedIndex()); // assuming division IDs correspond to combo box indices

            try {
                customerDAO.updateCustomer(selectedCustomer);
                table.refresh(); // Refresh table to show updated data
            } catch (SQLException ex) {
                // Handle exception
            }
        }
    });

layout.getChildren().add(updateButton);


}
