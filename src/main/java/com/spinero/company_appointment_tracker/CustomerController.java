package com.spinero.company_appointment_tracker;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;


public class CustomerController extends Application {
    @FXML
    public TableView<Customer> table;
    @FXML
    public TextField customerName, addressField, postalCodeField, phoneNumberField;
    @FXML
    public ComboBox<String> divisionBox, countryBox;
    @FXML
    public Button addButton, updateButton, deleteButton;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCustomerView.fxml"));

        loader.setController(this);
        VBox layout = loader.load();

        // Set up stage
        primaryStage.setTitle("Customer Management");
        Scene scene = new Scene(layout, 1280, 800);
        primaryStage.setScene(scene);
        primaryStage.show();


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

        // Event handlers for add, update, and delete buttons
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
