/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.workspace;

import cc.CodeCheckApp;
import cc.data.CodeCheckData;
import static cc.style.CodeCheckStyle.BORDERED_PANE;
import static cc.style.CodeCheckStyle.CLASS_EDIT_BUTTON;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import properties_manager.PropertiesManager;

/**
 *
 * @author Chris
 */
public class CodeCheckWorkspace extends AppWorkspaceComponent {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CodeCheckApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CodeCheckController controller;

    CodeCheckData data;
    
    GridPane splashScreen;
    ImageView splashImage;
    
    String path;
    ScrollPane work;
    HBox additionalToolbar;
    Button homeButton;
    Button previousButton;
    Button nextButton;
    Button renameButton;
    Button aboutButton;
    int currentStage;
    StackPane allStages;
    Button removeB1;
    Button refreshB1;
    Button viewB1;
    Button removeB2;
    Button refreshB2;
    Button viewB2;
    Button removeB3;
    Button refreshB3;
    Button viewB3;
    Button removeB4;
    Button refreshB4;
    Button viewB4;
    Button removeB5;
    Button refreshB5;
    Button viewB5;
    GridPane stage1;
    TableView s1Table;
    Button s1ExtractB;
    ProgressBar s1ProgressBar;
    TextArea s1TextArea;
    GridPane stage2;
    TableView s2Table;
    Button s2RenameB;
    ProgressBar s2ProgressBar;
    TextArea s2TextArea;
    GridPane stage3;
    TableView s3Table;
    Button s3UnzipB;
    ProgressBar s3ProgressBar;
    TextArea s3TextArea;
    GridPane stage4;
    TableView s4Table;
    Button s4ExtractB;
    ProgressBar s4ProgressBar;
    TextArea s4TextArea;
    TextField s4TextField;
    CheckBox[] s4CheckBoxes;
    GridPane stage5;
    TableView s5Table;
    Button s5CCB;
    Button s5ResultsB;
    ProgressBar s5ProgressBar;
    TextArea s5TextArea;
    TableColumn studentWork;
    TableColumn studentZip;
    TableColumn bbSubmissions;
    TableColumn studentWork5;
    TableColumn studentSubmissions;
    ObservableList<fileString> s1Data;
    ObservableList<fileString> s2Data;
    ObservableList<fileString> s3Data;
    ObservableList<fileString> s4Data;
    ObservableList<fileString> s5Data;
    String s1Text1;
    String s2Text1;
    String s3Text1;
    String s4Text1;
    String s5Text1;
    String s1Text2;
    String s2Text2;
    String s3Text2;
    String s4Text2;
    String s5Text2;
    
    
    /**
     * The constructor initializes the user interface for the
     * workspace area of the application.
     */
    public CodeCheckWorkspace(CodeCheckApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        
        // LAYOUT THE APP
        initLayout();
        
        // HOOK UP THE CONTROLLERS
        initControllers();
        
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
    }
    
    private void initLayout() {
        // WE'LL USE THIS TO GET UI TEXT
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        data = new CodeCheckData(app);
        
        path = new String();
        additionalToolbar = new HBox();
        homeButton = new Button ("Home");
        previousButton = new Button ("Previous");
        nextButton = new Button("Next");
        makeButton(homeButton);
        makeButton(previousButton);
        makeButton(nextButton);
        currentStage = 1;
        allStages = new StackPane();
        work = new ScrollPane();
        work.setContent(allStages);
        removeB1 = new Button("Remove");
        refreshB1 = new Button("Refresh");
        viewB1 = new Button("View");
        makeButton(removeB1, 1);
        makeButton(refreshB1, 1);
        makeButton(viewB1, 1);
        stage1 = new GridPane();
        s1Table = new TableView();
        s1ExtractB = new Button("Extract");
        makeButton(s1ExtractB);
        s1ProgressBar = new ProgressBar();
        s1TextArea = new TextArea();
        stage2 = new GridPane();
        s2Table = new TableView();
        s2RenameB = new Button("Rename");
        makeButton(s2RenameB);
        s2ProgressBar = new ProgressBar();
        s2TextArea = new TextArea();
        stage3 = new GridPane();
        s3Table = new TableView();
        s3UnzipB = new Button("Unzip");
        makeButton(s3UnzipB);
        s3ProgressBar = new ProgressBar();
        s3TextArea = new TextArea();
        stage4 = new GridPane();
        s4Table = new TableView();
        s4ExtractB = new Button("Extract");
        makeButton(s4ExtractB);
        s4ProgressBar = new ProgressBar();
        s4TextArea = new TextArea();
        s4TextField = new TextField();
        s4CheckBoxes = new CheckBox[5];
        stage5 = new GridPane();
        s5Table = new TableView();
        s5CCB = new Button("Code Check");
        makeButton(s5CCB);
        s5ResultsB = new Button("View Results");
        makeButton(s5ResultsB);
        s5ResultsB.setDisable(true);
        s5ProgressBar = new ProgressBar();
        s5TextArea = new TextArea();
        homeButton.setDisable(true);
        previousButton.setDisable(true);
        nextButton.setDisable(false);
        s1Data = FXCollections.observableArrayList();
        s2Data = FXCollections.observableArrayList();
        s3Data = FXCollections.observableArrayList();
        s4Data = FXCollections.observableArrayList();
        s5Data = FXCollections.observableArrayList();
        s1Text1 = "Successfully extracted files: \n";
        s1Text2 = "Submission Errors: \n";
        if (!app.getGUI().checkIfInitialized()) {
            nextButton.setDisable(true);
        }
        init3Buttons();
        additionalToolbar.getChildren().addAll(homeButton, previousButton, nextButton);
        additionalToolbar.getStyleClass().add(BORDERED_PANE);
        
        Label l1 = new Label ("Step 1: Extract Submissions");
        Label l2 = new Label ("Select the submissions below from which to extract student work");
        Label l3 = new Label ("Extraction Progress");
        l1.setStyle("-fx-font: bold 16px \"Serif\";");
        l3.setStyle("-fx-font: bold 16px \"Serif\";");
        s1ProgressBar.setProgress(0);
        VBox v1 = new VBox();
        v1.getChildren().addAll(l1, l2);
        VBox h1 = new VBox();
        HBox h2 = new HBox();
        h2.getChildren().addAll(removeB1, refreshB1, viewB1);
        h1.getChildren().addAll(s1Table, h2);
        VBox h3 = new VBox();
        HBox h4 = new HBox();
        h4.getChildren().addAll(l3, s1ProgressBar);
        h3.getChildren().addAll(h4, s1ExtractB);
        v1.setSpacing(20);
        h1.setSpacing(20);
        h3.setSpacing(10);
        h2.setSpacing(20);
        bbSubmissions = new TableColumn ("Blackboard Submissions");
        bbSubmissions.setMinWidth(400);
        s1Table.getColumns().add(bbSubmissions);
        stage1.add(v1, 0, 0);
        stage1.add(h1, 0, 1);
        stage1.add(s1TextArea, 1, 1);
        stage1.add(h3, 1, 0);
        
        l1 = new Label ("Step 2: Rename Student Submissions");
        l2 = new Label ("Click the Rename button to rename all submissions");
        l3 = new Label ("Rename Progress");
        l1.setStyle("-fx-font: bold 16px \"Serif\";");
        l3.setStyle("-fx-font: bold 16px \"Serif\";");
        s2ProgressBar.setProgress(0);
        v1 = new VBox();
        v1.getChildren().addAll(l1, l2);
        h1 = new VBox();
        h2 = new HBox();
        removeB2 = new Button ("Remove");
        refreshB2 = new Button ("Refresh");
        viewB2 = new Button ("View");
        makeButton(removeB2, 2);
        makeButton(refreshB2, 2);
        makeButton(viewB2, 2);
        h2.getChildren().addAll(removeB2, refreshB2, viewB2);
        h1.getChildren().addAll(s2Table, h2);
        h3 = new VBox();
        h4 = new HBox();
        h4.getChildren().addAll(l3, s2ProgressBar);
        h3.getChildren().addAll(h4, s2RenameB);
        v1.setSpacing(20);
        h1.setSpacing(20);
        h3.setSpacing(10);
        h2.setSpacing(20);
        studentSubmissions = new TableColumn ("Student Submissions");
        studentSubmissions.setMinWidth(400);
        s2Table.getColumns().add(studentSubmissions);
        stage2.add(v1, 0, 0);
        stage2.add(h1, 0, 1);
        stage2.add(s2TextArea, 1, 1);
        stage2.add(h3, 1, 0);
        
        l1 = new Label ("Step 3: Unzip Student Submissions");
        l2 = new Label ("Select student submissions and click Unzip");
        l3 = new Label ("Unzip Progress");
        l1.setStyle("-fx-font: bold 16px \"Serif\";");
        l3.setStyle("-fx-font: bold 16px \"Serif\";");
        s3ProgressBar.setProgress(0);
        v1 = new VBox();
        v1.getChildren().addAll(l1, l2);
        h1 = new VBox();
        h2 = new HBox();
        removeB3 = new Button ("Remove");
        refreshB3 = new Button ("Refresh");
        viewB3 = new Button ("View");
        makeButton(removeB3, 3);
        makeButton(refreshB3, 3);
        makeButton(viewB3, 3);
        h2.getChildren().addAll(removeB3, refreshB3, viewB3);
        h1.getChildren().addAll(s3Table, h2);
        h3 = new VBox();
        h4 = new HBox();
        h4.getChildren().addAll(l3, s3ProgressBar);
        h3.getChildren().addAll(h4, s3UnzipB);
        v1.setSpacing(20);
        h1.setSpacing(20);
        h3.setSpacing(10);
        h2.setSpacing(20);
        studentZip = new TableColumn ("Student Submissions");
        studentZip.setMinWidth(400);
        s3Table.getColumns().add(studentZip);
        stage3.add(v1, 0, 0);
        stage3.add(h1, 0, 1);
        stage3.add(s3TextArea, 1, 1);
        stage3.add(h3, 1, 0);
        
        l1 = new Label ("Step 4: Extract Source Code");
        l2 = new Label ("Select students and click Extract Code");
        l3 = new Label ("Extract Progress");
        l1.setStyle("-fx-font: bold 16px \"Serif\";");
        l3.setStyle("-fx-font: bold 16px \"Serif\";");
        s4ProgressBar.setProgress(0);
        v1 = new VBox();
        v1.getChildren().addAll(l1, l2);
        h1 = new VBox();
        h2 = new HBox();
        removeB4 = new Button ("Remove");
        refreshB4 = new Button ("Refresh");
        viewB4 = new Button ("View");
        makeButton(removeB4, 4);
        makeButton(refreshB4, 4);
        makeButton(viewB4, 4);
        h2.getChildren().addAll(removeB4, refreshB4, viewB4);
        h3 = new VBox();
        h4 = new HBox();
        h4.getChildren().addAll(l3, s4ProgressBar);
        h3.getChildren().addAll(h4, s4ExtractB);
        v1.setSpacing(20);
        h1.setSpacing(20);
        h3.setSpacing(10);
        h2.setSpacing(20);
        studentWork = new TableColumn ("Student Submissions");
        studentWork.setMinWidth(400);
        s4Table.getColumns().add(studentWork);
        s4CheckBoxes[0] = new CheckBox(".java          ");
        s4CheckBoxes[1] = new CheckBox(".js            ");
        s4CheckBoxes[2] = new CheckBox(".c, .h, .cpp   ");
        s4CheckBoxes[3] = new CheckBox(".cs            ");
        s4CheckBoxes[4] = new CheckBox();
        Label l4 = new Label ("Source File Types");
        l4.setStyle("-fx-font: bold 16px \"Serif\";");
        HBox h11 = new HBox();
        h11.getChildren().addAll(s4CheckBoxes[0], s4CheckBoxes[1]);
        HBox h21 = new HBox();
        h21.getChildren().addAll(s4CheckBoxes[2], s4CheckBoxes[3]);
        HBox h31 = new HBox();
        h31.getChildren().addAll(s4CheckBoxes[4], s4TextField);
        h1.getChildren().addAll(s4Table, h2, l4, h11, h21, h31);
        stage4.add(v1, 0, 0);
        stage4.add(h1, 0, 1);
        stage4.add(s4TextArea, 1, 1);
        stage4.add(h3, 1, 0);
        
        l1 = new Label ("Step 5: Code Check");
        l2 = new Label ("Select student and click Code Check");
        l3 = new Label ("Check Progress");
        l1.setStyle("-fx-font: bold 16px \"Serif\";");
        l3.setStyle("-fx-font: bold 16px \"Serif\";");
        
        s5ProgressBar.setProgress(0);
        v1 = new VBox();
        v1.getChildren().addAll(l1, l2);
        h1 = new VBox();
        h2 = new HBox();
        removeB5 = new Button ("Remove");
        refreshB5 = new Button ("Refresh");
        viewB5 = new Button ("View");
        makeButton(removeB5, 5);
        makeButton(refreshB5, 5);
        makeButton(viewB5, 5);
        h2.getChildren().addAll(removeB5, refreshB5, viewB5);
        h1.getChildren().addAll(s5Table, h2);
        h3 = new VBox();
        h4 = new HBox();
        HBox h14 = new HBox();
        h14.getChildren().addAll(s5CCB, s5ResultsB);
        h14.setSpacing(10);
        h4.getChildren().addAll(l3, s5ProgressBar);
        h3.getChildren().addAll(h4, h14);
        v1.setSpacing(20);
        h1.setSpacing(20);
        h3.setSpacing(10);
        h2.setSpacing(20);
        studentWork5 = new TableColumn ("Student Work");
        studentWork5.setMinWidth(400);
        s5Table.getColumns().add(studentWork5);
        stage5.add(v1, 0, 0);
        stage5.add(h1, 0, 1);
        stage5.add(s5TextArea, 1, 1);
        stage5.add(h3, 1, 0);
        
        updateTables();        
        allStages.getChildren().add(stage1);
        allStages.setStyle("-fx-background-color: #FFFFFF;");
        
        // AND THEN PUT EVERYTHING INSIDE THE WORKSPACE
        app.getGUI().getTopToolbarPane().getChildren().add(additionalToolbar);
        BorderPane workspaceBorderPane = new BorderPane();
        workspaceBorderPane.setCenter(allStages);
        
        // AND SET THIS AS THE WORKSPACE PANE
        workspace = workspaceBorderPane;
    }
    
    private void initControllers() {
        controller = new CodeCheckController(app);
        s1Table.setOnMousePressed(e-> {
            setButtonDisabled();
        });
        s2Table.setOnMousePressed(e-> {
            setButtonDisabled();
        });
        s3Table.setOnMousePressed(e-> {
            setButtonDisabled();
        });
        s4Table.setOnMousePressed(e-> {
            setButtonDisabled();
        });
        s5Table.setOnMousePressed(e-> {
            setButtonDisabled();
        });
        s1ExtractB.setOnAction(e-> {
            ObservableList<fileString> ol = s1Table.getSelectionModel().getSelectedItems();
            try {
                if (!ol.isEmpty()){
                    for (fileString s: ol) {
                        if ((s.getFileName().endsWith(".zip"))) {
                            ZipFile zipFile = new ZipFile(s.getFilePath());
                            zipFile.extractAll("work/" + app.getGUI().getTitle().substring(13) + "/submissions");
                        }
                    }
                    new Thread(){
                        public void run() {
                            for (int i = 0; i < 101; i++){
                                final int step = i;
                                Platform.runLater(() -> s1ProgressBar.setProgress( step  * 0.01 ));
                                try {
                                    Thread.sleep(10); 
                                } catch(InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            File dir = new File ("work/" + app.getGUI().getTitle().substring(13) + "/submissions");
                            File[] listOfFiles = dir.listFiles();
                            s1Text1 = "Successfully extracted files: \n";
                            for (File f : listOfFiles) {
                                s1Text1 = s1Text1.concat("-" + f.getName() + "\n");
                            }
                            s1TextArea.setText(s1Text1 + "\n\n" + s1Text2);
                        }
                    }.start();
                }
            }catch (ZipException ex) {
                if (!ol.isEmpty()){
                    for (fileString s: ol) {
                        s1Text2 = s1Text2.concat("-" + s.getFileName() + "\n");
                    }
                }
                s1TextArea.setText(s1Text1 + "\n" + s1Text2);
            }
        }); 
        s2RenameB.setOnAction(e -> {
            ObservableList<fileString> ol = s2Table.getItems();
            s2Text1 = "Successfully renamed submissions: \n";
            s2Text2 = "Rename Errors: \n";
            if (!ol.isEmpty()) {
                for (fileString s: ol) {
                    if (s.getFileName().contains("_")) {
                        File file = new File (s.getFilePath());
                        String newStr = s.getFilePath().replace(s.getFileName(), "");
                        newStr = newStr.concat(s.getFileName().
                                substring(s.getFileName().indexOf("_") + 1, s.getFileName()
                                        .indexOf("_", s.getFileName().indexOf("_") + 1)) + ".zip");
                        
                        File file2 = new File(newStr);
                        if (file2.exists()) {
                            s2Text2 = s2Text2.concat("-" + s.getFileName() + "\n");
                        }
                        else {
                            file.renameTo(file2);
                            s2Text1 = s2Text1.concat("-" + s.getFileName() + " becomes " + s.getFileName().
                                substring(s.getFileName().indexOf("_") + 1, s.getFileName()
                                        .indexOf("_", s.getFileName().indexOf("_") + 1)) + ".zip" + "\n");
                        }
                    }
                    else {
                        s2Text2 = s2Text2.concat("-" + s.getFileName() + "\n");
                    }
                }
                new Thread(){
                    public void run() {
                        for (int i = 0; i < 101; i++){
                            final int step = i;
                            Platform.runLater(() -> s2ProgressBar.setProgress( step  * 0.01 ));
                            try {
                                Thread.sleep(10); 
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        s2TextArea.setText(s2Text1 + "\n\n" + s2Text2);
                        updateTables();
                    }
                }.start();
            }
        });
        s3UnzipB.setOnAction(e-> {
            ObservableList<fileString> ol = s3Table.getSelectionModel().getSelectedItems();
            s3Text1 = "Successfully unzipped files: \n";
            s3Text2 = "Unzip Errors: \n";
            try {
                if (!ol.isEmpty()){
                    for (fileString s: ol) {
                        if ((s.getFileName().endsWith(".zip"))) {
                            File tempF = new File("work/" + app.getGUI().getTitle().substring(13) + "/projects/" + s.getFileName().replace(".zip", ""));
                            if (tempF.exists()) {
                                s3Text2 = s3Text2.concat("-" + s.getFileName() + "\n");
                            }
                            else {
                                tempF.mkdir();
                                ZipFile zipFile = new ZipFile(s.getFilePath());
                                zipFile.extractAll("work/" + app.getGUI().getTitle().substring(13) + "/projects/" + s.getFileName().replace(".zip", ""));
                                s3Text1 = s3Text1.concat("-" + s.getFileName() + "\n");
                            }
                        }
                    }
                    new Thread(){
                        public void run() {
                            for (int i = 0; i < 101; i++){
                                final int step = i;
                                Platform.runLater(() -> s3ProgressBar.setProgress( step  * 0.01 ));
                                try {
                                    Thread.sleep(10); 
                                } catch(InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            s3TextArea.setText(s3Text1 + "\n\n" + s3Text2);
                        }
                    }.start();
                }
            }catch (ZipException ex) {
                if (!ol.isEmpty()){
                    for (fileString s: ol) {
                        s3Text2 = s3Text2.concat("-" + s.getFileName() + "\n");
                    }
                }
                s3TextArea.setText(s3Text1 + "\n" + s3Text2);
            }
        });
        s4ExtractB.setOnAction(e-> {
            ObservableList<fileString> ol = s4Table.getSelectionModel().getSelectedItems();
            s4Text1 = "Successful code extraction: \n";
            s4Text2 = "Code extraction errors: \n";
            if (!ol.isEmpty()) {
                for (fileString s: ol) {
                    findCode(s.getFilePath(), s.getFileName());                    
                }
                new Thread(){
                    public void run() {
                        for (int i = 0; i < 101; i++){
                            final int step = i;
                            Platform.runLater(() -> s4ProgressBar.setProgress( step  * 0.01 ));
                            try {
                                Thread.sleep(10); 
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        s4TextArea.setText(s4Text1 + "\n\n" + s4Text2);
                    }
                }.start();
            }
        });
        s5CCB.setOnAction(e-> {
            ObservableList<fileString> ol = s5Table.getSelectionModel().getSelectedItems();
            s5Text1 = "Student Plagiarism Check results can be found at \n" + "http://www.google.com";  
            if (!ol.isEmpty()) {
                for (fileString s: ol) {                
                }
                new Thread(){
                    public void run() {
                        for (int i = 0; i < 101; i++){
                            final int step = i;
                            Platform.runLater(() -> s5ProgressBar.setProgress( step  * 0.01 ));
                            try {
                                Thread.sleep(10); 
                            } catch(InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        s5TextArea.setText(s5Text1);
                        s5ResultsB.setDisable(false);
                    }
                }.start();
            }
        });
        s5ResultsB.setOnAction(e -> {            
            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();
            webEngine.load("http://www.google.com");
            
            Stage stage = new Stage();
            stage.setTitle("Code Check Results");
            stage.setScene(new Scene(browser, 600, 600));
            stage.show();
        });
    }
    
    private void findCode(String folderP, String folderN) {
        File[] loopFiles = new File(folderP).listFiles();
        for (File tempF : loopFiles) {
            if (tempF.isDirectory()) {
                findCode(tempF.getPath(), folderN);
            }
            else {
                if (s4CheckBoxes[0].isSelected()) {
                    if (tempF.getName().endsWith(".java")) {
                        extract(tempF, folderN);
                    }
                }
                if (s4CheckBoxes[1].isSelected()) {
                    if (tempF.getName().endsWith(".js")) {
                        extract(tempF, folderN);
                    }
                }
                if (s4CheckBoxes[2].isSelected()) {
                    if (tempF.getName().endsWith(".c") || tempF.getName().endsWith(".h") || tempF.getName().endsWith(".cpp")) {
                        extract(tempF, folderN);
                    }
                }
                if (s4CheckBoxes[3].isSelected()) {
                    if (tempF.getName().endsWith(".cs")) {
                        extract(tempF, folderN);
                    }
                }
                if (s4CheckBoxes[4].isSelected()) {
                    if (tempF.getName().endsWith(s4TextField.getText())) {
                        extract(tempF, folderN);
                    }
                }
            }
        }
    }
    
    private void extract(File f, String folderN) {
        try {
            File temF = new File("work/" + app.getGUI().getTitle().substring(13) + "/code/" + folderN);
            if (!temF.exists()) {
                temF.mkdir();
            }
            temF = new File("work/" + app.getGUI().getTitle().substring(13) + "/code/" + folderN + "/" + f.getName());
            if (temF.exists()) {
                s4Text2 = s4Text2.concat("-" + temF.getName() + "\n");
            }
            else {
                copyFile(f, temF);
                s4Text1 = s4Text1.concat("-" + f.getName() + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void copyFile(File source, File dest) throws IOException {
    InputStream is = null;
    OutputStream os = null;
    try {
        is = new FileInputStream(source);
        os = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    } finally {
        is.close();
        os.close();
    }
}
    
    private void init3Buttons() {
        homeButton.setOnAction(e-> {
            changeStages((currentStage * -1) + 1);
            nextButton.setDisable(false);
            previousButton.setDisable(true);
            homeButton.setDisable(true);
        });
        previousButton.setOnAction(e-> {
            changeStages(-1);
            nextButton.setDisable(false);
            if (currentStage == 1) {
                previousButton.setDisable(true);
                homeButton.setDisable(true);
            }
            else {
                homeButton.setDisable(false);
                previousButton.setDisable(false);
            }
        });
        nextButton.setOnAction(e-> {
            changeStages(1);
            if (currentStage == 5) {
                nextButton.setDisable(true);
            }
            previousButton.setDisable(false);
            homeButton.setDisable(false);
        });
    }
    
    private void changeStages(int stageChange) {
        updateTables();
        currentStage = currentStage + stageChange;
        redrawStage();
        setButtonDisabled();
    }
    
    private void updateTables() {
        s1Data = FXCollections.observableArrayList();
        s2Data = FXCollections.observableArrayList();
        s3Data = FXCollections.observableArrayList();
        s4Data = FXCollections.observableArrayList();
        s5Data = FXCollections.observableArrayList();
        
        File dir = new File ("work/" + app.getGUI().getTitle().substring(13) + "/blackboard");
        File[] listOfFiles = dir.listFiles();
        for (File f : listOfFiles) {
            if (f.getName().endsWith(".zip")) {
                fileString fs = new fileString(f.getPath(), f.getName());
                s1Data.add(fs);
            }
        }
        bbSubmissions.setCellValueFactory(new PropertyValueFactory<fileString, String>("fileName"));
        s1Table.setItems(s1Data);
        
        dir = new File ("work/" + app.getGUI().getTitle().substring(13) + "/submissions");
        listOfFiles = dir.listFiles();
        for (File f : listOfFiles) {
            if (f.getName().endsWith(".zip")) {
                fileString fs = new fileString(f.getPath(), f.getName());
                s2Data.add(fs);
            }
        }
        studentSubmissions.setCellValueFactory(new PropertyValueFactory<fileString, String>("fileName"));
        s2Table.setItems(s2Data);
        
        dir = new File ("work/" + app.getGUI().getTitle().substring(13) + "/submissions");
        listOfFiles = dir.listFiles();
        for (File f : listOfFiles) {
            if (f.getName().endsWith(".zip")) {
                fileString fs = new fileString(f.getPath(), f.getName());
                s3Data.add(fs);
            }
        }
        studentZip.setCellValueFactory(new PropertyValueFactory<fileString, String>("fileName"));
        s3Table.setItems(s3Data);
        
        dir = new File ("work/" + app.getGUI().getTitle().substring(13) + "/projects");
        listOfFiles = dir.listFiles(); 
        for (File f : listOfFiles) {
            fileString fs = new fileString(f.getPath(), f.getName());
            s4Data.add(fs);
        }
        studentWork.setCellValueFactory(new PropertyValueFactory<fileString, String>("fileName"));
        s4Table.setItems(s4Data);
        
        dir = new File ("work/" + app.getGUI().getTitle().substring(13) + "/code");
        listOfFiles = dir.listFiles();
        for (File f : listOfFiles) {
            fileString fs = new fileString(f.getPath(), f.getName());
            s5Data.add(fs);
        }
        studentWork5.setCellValueFactory(new PropertyValueFactory<fileString, String>("fileName"));
        s5Table.setItems(s5Data);
        
        s1Table.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        s2Table.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        s3Table.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        s4Table.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        s5Table.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
               
    }
    
    private void setButtonDisabled() {
        s1ExtractB.setDisable(true);
        s3UnzipB.setDisable(true);
        s4ExtractB.setDisable(true);
        s5CCB.setDisable(true);
        if (s1Table.getSelectionModel().getSelectedItems().size() == 1){
            viewB1.setDisable(false);
            removeB1.setDisable(false);
            s1ExtractB.setDisable(false);
        }
        else if (s1Table.getSelectionModel().getSelectedItems().size() > 1) {
            viewB1.setDisable(true);
            removeB1.setDisable(true);
            s1ExtractB.setDisable(false);
        }
        else {
            viewB1.setDisable(true);
            removeB1.setDisable(true);
        }
        if (s2Table.getSelectionModel().getSelectedItems().size() == 1){
            viewB2.setDisable(false);
            removeB2.setDisable(false);
        }
        else{
            viewB2.setDisable(true);
            removeB2.setDisable(true);
        }
        if (s3Table.getSelectionModel().getSelectedItems().size() == 1){
            viewB3.setDisable(false);
            removeB3.setDisable(false);
            s3UnzipB.setDisable(false);
        }
        else if (s3Table.getSelectionModel().getSelectedItems().size() > 1) {
            viewB3.setDisable(true);
            removeB3.setDisable(true);
            s3UnzipB.setDisable(false);
        }
        else{
            viewB3.setDisable(true);
            removeB3.setDisable(true);
        }
        if (s4Table.getSelectionModel().getSelectedItems().size() == 1){
            viewB4.setDisable(false);
            removeB4.setDisable(false);
            s4ExtractB.setDisable(false);
        }
        else if (s4Table.getSelectionModel().getSelectedItems().size() > 1) {
            viewB4.setDisable(true);
            removeB4.setDisable(true);
            s4ExtractB.setDisable(false);
        }
        else{
            viewB4.setDisable(true);
            removeB4.setDisable(true);
        }
        if (s5Table.getSelectionModel().getSelectedItems().size() == 1){
            viewB5.setDisable(false);
            removeB5.setDisable(false);
            s5CCB.setDisable(false);
        }
        else if (s5Table.getSelectionModel().getSelectedItems().size() > 1) {
            viewB5.setDisable(true);
            removeB5.setDisable(true);
            s5CCB.setDisable(false);
        }
        else{
            viewB5.setDisable(true);
            removeB5.setDisable(true);
        }
    }
    
    private void redrawStage() {
        allStages.getChildren().remove(0);
        if (currentStage == 1) {
            allStages.getChildren().add(stage1);
        }
        if (currentStage == 2) {
            allStages.getChildren().add(stage2);
        }
        if (currentStage == 3) {
            allStages.getChildren().add(stage3);
        }
        if (currentStage == 4) {
            allStages.getChildren().add(stage4);
        }
        if (currentStage == 5) {
            allStages.getChildren().add(stage5);
        }
    }
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    
    private void initStyle() {
        additionalToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        homeButton.getStyleClass().add(CLASS_EDIT_BUTTON);
        previousButton.getStyleClass().add(CLASS_EDIT_BUTTON);
        nextButton.getStyleClass().add(CLASS_EDIT_BUTTON);
    }
    public void makeButton(Button b, int stage) {
        b.setStyle("-fx-background-color: \n" +
"        linear-gradient(#ffd65b, #e68400),\n" +
"        linear-gradient(#ffef84, #f2ba44),\n" +
"        linear-gradient(#ffea6a, #efaa22),\n" +
"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
"        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
"    -fx-background-radius: 30;\n" +
"    -fx-background-insets: 0,1,2,3,0;\n" +
"    -fx-text-fill: #654b00;\n" +
"    -fx-font-weight: bold;\n" +
"    -fx-font-size: 14px;\n" +
"    -fx-padding: 10 20 10 20;");
        if (b.getText().equals("Refresh")) {
            b.setOnAction(e-> { 
                updateTables();
            });
        }
        if (b.getText().equals("Remove")) { 
            if (stage == 1) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s1Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty()) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Delete File(s)");
                        alert.setHeaderText("File deletion");
                        alert.setContentText("Are you sure you want to delete this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            for (fileString s: ol) {
                                File delete = new File (s.getFilePath());
                                delete.delete();
                            }
                        }
                    }
                    updateTables();
                });
            }
            if (stage == 2) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s2Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty()) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Delete File(s)");
                        alert.setHeaderText("File deletion");
                        alert.setContentText("Are you sure you want to delete this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            for (fileString s: ol) {
                                File delete = new File (s.getFilePath());
                                delete.delete();
                            }
                        }
                    }
                    updateTables();
                });
            }
            if (stage == 3) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s3Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty()) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Delete File(s)");
                        alert.setHeaderText("File deletion");
                        alert.setContentText("Are you sure you want to delete this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            for (fileString s: ol) {
                                File delete = new File (s.getFilePath());
                                delete.delete();
                            }
                        }
                    }
                    updateTables();
                });
            }
            if (stage == 4) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s4Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty()) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Delete File(s)");
                        alert.setHeaderText("File deletion");
                        alert.setContentText("Are you sure you want to delete this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            for (fileString s: ol) {
                                Path dire = Paths.get(s.getFilePath());
                                try {
                                    Files.walkFileTree(dire, new SimpleFileVisitor<Path>()
                                    {
                                        @Override
                                        public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                                        {
                                            Files.delete(path);
                                            return FileVisitResult.CONTINUE;
                                        }
                                        
                                        @Override
                                        public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException
                                        {
                                            Files.delete(directory);
                                            return FileVisitResult.CONTINUE;
                                        }
                                    });
                                } catch (IOException ex) {
                                    Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    updateTables();
                });
            }
            if (stage == 5) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s5Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty()) {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Delete File(s)");
                        alert.setHeaderText("File deletion");
                        alert.setContentText("Are you sure you want to delete this?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            for (fileString s: ol) {
                                Path dire = Paths.get(s.getFilePath());
                                try {
                                    Files.walkFileTree(dire, new SimpleFileVisitor<Path>()
                                    {
                                        @Override
                                        public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException
                                        {
                                            Files.delete(path);
                                            return FileVisitResult.CONTINUE;
                                        }
                                        
                                        @Override
                                        public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException
                                        {
                                            Files.delete(directory);
                                            return FileVisitResult.CONTINUE;
                                        }
                                    });
                                } catch (IOException ex) {
                                    Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    updateTables();
                });
            }
        }
        if (b.getText().equals("View")) { 
            if (stage == 1) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s1Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty() && ol.size() == 1) {
                        String str = "";
                        fileString fs = ol.get(0);
                        try {
                            ZipFile zipFile = new ZipFile(fs.getFilePath());
                            List<FileHeader> zipEntries = zipFile.getFileHeaders();
                            for (FileHeader s : zipEntries) {
                                str = str + s.getFileName() + "\n\n";
                            }
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("View");
                            alert.setHeaderText("Show detail to view files");
                            TextArea ta = new TextArea (str);
                            alert.getDialogPane().setExpandableContent(ta);
                            alert.showAndWait();
                        } catch (ZipException ex) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("File Corrupted");
                            alert.setHeaderText("Cannot view ZIP file");
                            alert.setContentText("This file might have been corrupted");

                            alert.showAndWait();
                        }
                    }
                    updateTables();
                });
            }
            if (stage == 2) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s2Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty() && ol.size() == 1) {
                        String str = "";
                        fileString fs = ol.get(0);
                        try {
                            ZipFile zipFile = new ZipFile(fs.getFilePath());
                            List<FileHeader> zipEntries = zipFile.getFileHeaders();
                            for (FileHeader s : zipEntries) {
                                str = str + s.getFileName() + "\n\n";
                            }
                        } catch (ZipException ex) {
                            Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("View");
                        alert.setHeaderText("Show detail to view files");
                        TextArea ta = new TextArea (str);
                        alert.getDialogPane().setExpandableContent(ta);
                        alert.showAndWait();
                    }
                    updateTables();
                });
            }
            if (stage == 3) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s3Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty() && ol.size() == 1) {
                        String str = "";
                        fileString fs = ol.get(0);
                        try {
                            ZipFile zipFile = new ZipFile(fs.getFilePath());
                            List<FileHeader> zipEntries = zipFile.getFileHeaders();
                            for (FileHeader s : zipEntries) {
                                str = str + s.getFileName() + "\n\n";
                            }
                        } catch (ZipException ex) {
                            Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("View");
                        alert.setHeaderText("Show detail to view files");
                        TextArea ta = new TextArea (str);
                        alert.getDialogPane().setExpandableContent(ta);
                        alert.showAndWait();
                    }
                    updateTables();
                });
            }
            if (stage == 4) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s4Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty() && ol.size() == 1) {
                        String str = "";
                        fileString fs = ol.get(0);
                        File f = new File (fs.getFilePath());
                        File[] fArr = f.listFiles();
                        for (File fi : fArr) {
                            str = str + fi.getName() + "\n\n";
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("View");
                        alert.setHeaderText("Show detail to view files");
                        TextArea ta = new TextArea (str);
                        alert.getDialogPane().setExpandableContent(ta);
                        alert.showAndWait();
                    }
                    updateTables();
                });
            }
            if (stage == 5) {
                b.setOnAction(e-> {
                    ObservableList<fileString> ol = s5Table.getSelectionModel().getSelectedItems();
                    if (!ol.isEmpty() && ol.size() == 1) {
                        String str = "";
                        fileString fs = ol.get(0);
                        File f = new File (fs.getFilePath());
                        File[] fArr = f.listFiles();
                        for (File fi : fArr) {
                            str = str + fi.getName()+ "\n\n";
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("View");
                        alert.setHeaderText("Show detail to view files");
                        TextArea ta = new TextArea (str);
                        alert.getDialogPane().setExpandableContent(ta);
                        alert.showAndWait();
                    }
                    updateTables();
                });
            }
        }
        
    }
    public void makeButton(Button b) {
        b.setStyle("-fx-background-color: \n" +
"        linear-gradient(#ffd65b, #e68400),\n" +
"        linear-gradient(#ffef84, #f2ba44),\n" +
"        linear-gradient(#ffea6a, #efaa22),\n" +
"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
"        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
"    -fx-background-radius: 30;\n" +
"    -fx-background-insets: 0,1,2,3,0;\n" +
"    -fx-text-fill: #654b00;\n" +
"    -fx-font-weight: bold;\n" +
"    -fx-font-size: 14px;\n" +
"    -fx-padding: 10 20 10 20;");
        if (b.getText().equals("Refresh")) {
            b.setOnAction(e-> { 
                updateTables();
            });
        }
    }

    @Override
    public void resetWorkspace() {
        data.resetData();
        changeStages((currentStage * -1) + 1);
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
    }
    
    @Override
    public void setDirectory(String title) {
        path = title;
    }
    @Override
    public void activateButtons () {
        homeButton.setDisable(true);
        previousButton.setDisable(true);
        nextButton.setDisable(false);
    }
}
