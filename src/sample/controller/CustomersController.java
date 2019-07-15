package sample.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.bean.Customer;
import sample.connection.DBHandler;
import sample.controller.constants.ControllerConstants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CustomersController {

    private static Stage primaryStage;
    private static final Logger LOGGER = LogManager.getLogger(CustomersController.class);
    private ObservableList<Customer> customersData = FXCollections.observableArrayList();

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private Button addTableDataButton;

    @FXML
    private Button pdf;

    @FXML
    private Button refresh;

    @FXML
    private Text infoMessage;

    public void initialize() throws SQLException, ClassNotFoundException {
//        infoMessage.setText("");
        refresh.setOnAction(event -> {
            try {
                customersTable.getItems().clear();
                initData();
            } catch (SQLException e) {
                LOGGER.error("SQLException CustomerController");
            } catch (ClassNotFoundException e) {
                LOGGER.error("ClassNotFoundException CustomerController");
            }
        });
        pdf.setOnAction(event -> {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("C:\\reports\\customersReport.pdf"));
                document.open();
                PdfPTable table = new PdfPTable(5);
                PdfPCell cell;
                ResultSet resultSet = DBHandler.INSTANCE.getCustomerData();
                Paragraph paragraph = new Paragraph("                 Customers \n\n");
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                cell = new PdfPCell(new Phrase("Number"));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Name"));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Surname"));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Address"));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Passport"));
                table.addCell(cell);

                while (resultSet.next())//noinspection Duplicates
                {

                    String idCustomers = resultSet.getString("idcustomers");
                    cell = new PdfPCell(new Phrase(idCustomers));
                    table.addCell(cell);

                    String name = resultSet.getString("name");
                    cell = new PdfPCell(new Phrase(name));
                    table.addCell(cell);

                    String surname = resultSet.getString("surname");
                    cell = new PdfPCell(new Phrase(surname));
                    table.addCell(cell);

                    String address =resultSet.getString("address");
                    cell = new PdfPCell(new Phrase(address));
                    table.addCell(cell);

                    String passport = resultSet.getString("passport");
                    cell = new PdfPCell(new Phrase(passport));
                    table.addCell(cell);
                }
                document.add(table);
                Date date = new Date();
                document.add(new Paragraph(date.toString()));
                document.close();
                if(true) {
                    infoMessage.setText("Успешно");
                }
            } catch (DocumentException e) {
                infoMessage.setText("Ошибка");
                LOGGER.error("DocumentException CustomersController");
            } catch (FileNotFoundException e) {
                infoMessage.setText("Ошибка");
                LOGGER.error("FileNotFoundException CustomersController");
            } catch (SQLException e) {
                infoMessage.setText("Ошибка");
                LOGGER.error("SQLException CustomersController");
            } catch (ClassNotFoundException e) {
                infoMessage.setText("Ошибка");
                LOGGER.error("ClassNotFoundException CustomersController");
            }
        });
        closeButton.setOnAction(event -> primaryStage.close());
        initData();
        TableColumn<Customer, String> idCustomer
                = new TableColumn<>("Номер");
        TableColumn<Customer, String> name
                = new TableColumn<>("Имя");
        TableColumn<Customer, String> surname
                = new TableColumn<>("Фамилия");
        TableColumn<Customer, String> address
                = new TableColumn<>("Адресс");
        TableColumn<Customer, String> passport
                = new TableColumn<>("Пасспорт");
        customersTable.getColumns().addAll(idCustomer, name, surname, address, passport);
        idCustomer.setCellValueFactory(new PropertyValueFactory<>("idCustomers"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        passport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        customersTable.setTooltip(new Tooltip("Нажмите 2 раза для редактирования строки"));
        addTableDataButton.setOnAction(event -> {
            try {
                if(AddCustomerController.getStage() == null) {
                    AddCustomerController.start(new Stage());
                } else{
                    AddCustomerController.getStage().close();
                    AddCustomerController.start(new Stage());
                }
            } catch (IOException e) {
                LOGGER.error("CustomerController start exception");
            }
        });
    }

    @SuppressWarnings("Duplicates")
    static void start(Stage stage) throws IOException {
        LOGGER.info("EventList start");
        Parent root = FXMLLoader.load(CustomersController.class.getResource(ControllerConstants.CUSTOMERS));
        stage.setTitle(ControllerConstants.CUSTOMERS_TITLE);
        Scene scene = new Scene(root,796, 500);
        stage.setScene(scene);
        stage.getIcons().add(new Image(ControllerConstants.ICON_URL));
        stage.show();
        primaryStage = stage;
    }

    private void initData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBHandler.INSTANCE.getCustomerData();
        int idCustomer;
        String name, surname, address, passport;
        while(resultSet.next()){
            idCustomer = resultSet.getInt("idcustomers");
            name = resultSet.getString("name");
            surname = resultSet.getString("surname");
            address = resultSet.getString("address");
            passport = resultSet.getString("passport");
            customersData.add(new Customer(idCustomer,name,surname,address,passport));
        }
        customersTable.setItems(customersData);
    }
}
