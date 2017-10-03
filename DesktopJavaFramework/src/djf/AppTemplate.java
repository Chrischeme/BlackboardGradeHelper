package djf;

import djf.ui.*;
import djf.components.*;
import javafx.application.Application;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import properties_manager.InvalidXMLFileFormatException;

/**
 * This is the framework's JavaFX application. It provides the start method
 * that begins the program initialization, which delegates component
 * initialization to the application-specific child class' hook function.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public abstract class AppTemplate extends Application {

    // THIS IS THE APP'S FULL JavaFX GUI. NOTE THAT ALL APPS WOULD
    // SHARE A COMMON UI EXCEPT FOR THE CUSTOM WORKSPACE
    protected AppGUI gui;

    // THIS CLASS USES A COMPONENT ARCHITECTURE DESIGN PATTERN, MEANING IT
    // HAS OBJECTS THAT CAN BE SWAPPED OUT FOR OTHER COMPONENTS
    // THIS APP HAS 4 COMPONENTS
    
    // THE COMPONENT FOR MANAGING CUSTOM APP DATA
    protected AppDataComponent dataComponent;
    
    // THE COMPONENT FOR MANAGING CUSTOM FILE I/O
    protected AppFileComponent fileComponent;

    // THE COMPONENT FOR THE GUI WORKSPACE
    protected AppWorkspaceComponent workspaceComponent;
        
    // THIS METHOD MUST BE OVERRIDDEN WHERE THE CUSTOM BUILDER OBJECT
    // WILL PROVIDE THE CUSTOM APP COMPONENTS

    /**
     * This function must be overridden, it should initialize all
     * of the components used by the app in the proper order according
     * to the particular app's dependencies.
     */
    public abstract void buildAppComponentsHook();
    
    // COMPONENT ACCESSOR METHODS

    /**
     *  Accessor for the data component.
     */
    public AppDataComponent getDataComponent() { return dataComponent; }

    /**
     *  Accessor for the file component.
     */
    public AppFileComponent getFileComponent() { return fileComponent; }

    /**
     *  Accessor for the workspace component.
     */
    public AppWorkspaceComponent getWorkspaceComponent() { return workspaceComponent; }
    
    /**
     *  Accessor for the gui. Note that the GUI would contain the workspace.
     */
    public AppGUI getGUI() { return gui; }

    /**
     * This is where our Application begins its initialization, it will load
     * the custom app properties, build the components, and fully initialize
     * everything to get the app rolling.
     *
     * @param primaryStage This application's window.
     */
    @Override
    public void start(Stage primaryStage) {
	// LET'S START BY INITIALIZING OUR DIALOGS
	AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
	messageDialog.init(primaryStage);
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
	yesNoDialog.init(primaryStage);
	PropertiesManager props = PropertiesManager.getPropertiesManager();

	try {
	    // LOAD APP PROPERTIES, BOTH THE BASIC UI STUFF FOR THE FRAMEWORK
	    // AND THE CUSTOM UI STUFF FOR THE WORKSPACE
	    boolean success = loadProperties(APP_PROPERTIES_FILE_NAME);
	    
	    if (success) {
                // GET THE SIZE OF THE SCREEN
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();

                // AND USE IT TO SIZE THE WINDOW
                primaryStage.setX(bounds.getMinX());
                primaryStage.setY(bounds.getMinY());
                primaryStage.setWidth(bounds.getWidth());
                primaryStage.setHeight(bounds.getHeight());
                
                BorderPane appPane = new BorderPane();
                StackPane top = new StackPane();
                HBox temp = new HBox();
                HBox temp1 = new HBox();
                top.setStyle("-fx-background-color: #FF00FF;");
                Label title = new Label ("Welcome to Code Check");
                title.setStyle("-fx-font: bold 16px \"Serif\"; -fx-padding: 10;");
                title.setAlignment(Pos.CENTER_LEFT);
                Button x;
                x = new Button("X");
                x.setFont(Font.font("Monospaced", 20));
                x.setAlignment(Pos.CENTER_RIGHT);
                x.setOnMouseEntered(e -> {
                    x.setCursor(Cursor.HAND);
                });
                x.setOnMouseExited(e-> {
                    x.setCursor(Cursor.DEFAULT);
                });
                x.setStyle("-fx-background-color: transparent;");
                x.setOnAction(e-> {
                    primaryStage.hide();
                    gui = new AppGUI(primaryStage, "", this, 0);
                    buildAppComponentsHook();
                });
                temp.getChildren().add(title);
                temp1.getChildren().add(x);
                temp1.setAlignment(Pos.CENTER_RIGHT);
                temp.setAlignment(Pos.CENTER_LEFT);
                top.getChildren().addAll(temp, temp1);
                
                VBox left = new VBox();
                left.setSpacing(50);
                left.setPrefWidth(400);
                Label leftText = new Label("Recent Work");
                leftText.setStyle("-fx-font: bold 24px \"Serif\"; -fx-padding: 10;");
                left.getChildren().add(leftText);
                Hyperlink[] recentHyper = new Hyperlink[10];
                try {
                    FileReader fr = new FileReader("saved/recent.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String recents = br.readLine();
                    int i = 0;
                    while (recents.length() > 0 && i < 10) {
                        recentHyper[i] = new Hyperlink(recents.substring(0, recents.indexOf("@&")));
                        recentHyper[i].setStyle("-fx-font-size: 20;");
                        String tempName = recentHyper[i].getText();
                        recentHyper[i].setOnAction(e->{
                            String path = "work";
                            File dir = new File(path);
                            File[] directoryListing = dir.listFiles();
                            boolean hasAlready = false;
                            for (File f : directoryListing) {
                                if (f.getName().equals(tempName)) {
                                    hasAlready = true;
                                }
                            }
                            if (hasAlready) {
                                File f = new File(path + "/" + tempName);
                                f.mkdir();
                                f = new File(path + "/" + tempName + "/blackboard");
                                f.mkdir();
                                f = new File(path + "/" + tempName + "/code");
                                f.mkdir();
                                f = new File(path + "/" + tempName + "/projects");
                                f.mkdir();
                                f = new File(path + "/" + tempName + "/submissions");
                                f.mkdir();
                                gui = new AppGUI(primaryStage, tempName, this, 1);
                                buildAppComponentsHook();
                                gui.startWorkspace();
                                primaryStage.hide();

                                try {
                                    FileReader fr1 = new FileReader("saved/recent.txt");
                                    BufferedReader br1 = new BufferedReader(fr1);
                                    String recents1 = br1.readLine();
                                    br1.close();
                                    String finalString = tempName + "@&" + recents1;
                                    FileWriter fw = new FileWriter("saved/recent.txt");
                                    fw.write(finalString);
                                    fw.close();
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(AppTemplate.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(AppTemplate.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else {
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("Directory Error");
                                alert.setHeaderText("Error with directory");
                                alert.setContentText("Directory does not exist!");
                                alert.showAndWait();
                            }
                        });
                        left.getChildren().add(recentHyper[i]);
                        recents = recents.substring(recents.indexOf("@&") + 2);
                        i++;
                    }
                    br.close();
                }
                catch(FileNotFoundException ex) {
                    
                }
                left.setStyle("-fx-background-color: #DDA0DD;");
                
                StackPane cent = new StackPane();
                VBox center = new VBox();
                center.setAlignment(Pos.BOTTOM_CENTER);
                Hyperlink link = new Hyperlink("Create New Code Check");
                link.setStyle("-fx-font-size: 20;");
                link.setOnAction(e-> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Enter Directory");
                    dialog.setHeaderText("Enter the new directory name that you wish to work in");
                    dialog.setContentText("Name: ");
                    // Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        String path = "work";
                        File dir = new File(path);
                        File[] directoryListing = dir.listFiles();
                        boolean hasAlready = false;
                        for (File f : directoryListing) {
                            if (f.getName().equals(result.get())) {
                                hasAlready = true;
                            }
                        }
                        if (!hasAlready) {
                            File f = new File(path + "/" + result.get());
                            f.mkdir();
                            f = new File(path + "/" + result.get() + "/blackboard");
                            f.mkdir();
                            f = new File(path + "/" + result.get() + "/code");
                            f.mkdir();
                            f = new File(path + "/" + result.get() + "/projects");
                            f.mkdir();
                            f = new File(path + "/" + result.get() + "/submissions");
                            f.mkdir();
                            gui = new AppGUI(primaryStage, result.get(), this, 1);
                            buildAppComponentsHook();
                            gui.startWorkspace();
                            primaryStage.hide();
                           
                            try {
                                FileReader fr = new FileReader("saved/recent.txt");
                                BufferedReader br = new BufferedReader(fr);
                                String recents = br.readLine();
                                br.close();
                                String finalString = result.get() + "@&" + recents;
                                FileWriter fw = new FileWriter("saved/recent.txt");
                                fw.write(finalString);
                                fw.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(AppTemplate.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(AppTemplate.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Directory Error");
                            alert.setHeaderText("The directory name already exists!");
                            alert.setContentText("Retry with a different name");
                            alert.showAndWait();
                        }
                    }
                });
                
                
                center.getChildren().add(link);
                cent.getChildren().add(center);
                cent.setPadding(new Insets(50, 0, 100, 0));
                
                appPane.setTop(top);
                appPane.setLeft(left);
                appPane.setCenter(cent);
                Scene primaryScene = new Scene(appPane);
                
                // SET THE APP ICON
                String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
                primaryStage.getIcons().add(new Image(appIcon));

                // NOW TIE THE SCENE TO THE WINDOW
                primaryStage.setScene(primaryScene);
                primaryStage.initStyle(StageStyle.UNDECORATED);
                props = PropertiesManager.getPropertiesManager();
                String stylesheet = props.getProperty(APP_PATH_CSS);
                stylesheet += props.getProperty(APP_CSS);
                Class appClass = this.getClass();
                URL stylesheetURL = appClass.getResource(stylesheet);
                String stylesheetPath = stylesheetURL.toExternalForm();
                primaryScene.getStylesheets().add(stylesheetPath);
        
                primaryStage.show();
	    } 
	}catch (Exception e) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	}
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     *
     * @param propertiesFileName The XML file containing properties to be
     * loaded in order to initialize the UI.
     * 
     * @return true if the properties file was loaded successfully, false
     * otherwise.
     */
    public boolean loadProperties(String propertiesFileName) {
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	try {
	    // LOAD THE SETTINGS FOR STARTING THE APP
	    props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
	    props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
	    return true;
	} catch (InvalidXMLFileFormatException ixmlffe) {
	    // SOMETHING WENT WRONG INITIALIZING THE XML FILE
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	    return false;
	}
    }
}