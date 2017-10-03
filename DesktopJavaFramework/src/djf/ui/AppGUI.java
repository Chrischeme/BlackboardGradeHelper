package djf.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import djf.controller.AppFileController;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.net.URL;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * This class provides the basic user interface for this application,
 * including all the file controls, but not including the workspace,
 * which would be customly provided for each app.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class AppGUI {
    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    protected AppFileController fileController;

    // THIS IS THE APPLICATION WINDOW
    protected Stage primaryStage;

    protected String initString;
    // THIS IS THE STAGE'S SCENE GRAPH
    protected Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION AppGUI. NOTE THAT THE WORKSPACE WILL GO
    // IN THE CENTER REGION OF THE appPane
    protected BorderPane appPane;
    
    // THIS IS THE TOP PANE WHERE WE CAN PUT TOOLBAR
    protected FlowPane topToolbarPane;
    
    // THIS IS THE FILE TOOLBAR AND ITS CONTROLS
    protected FlowPane fileToolbar;

    // FILE TOOLBAR BUTTONS
    protected Button newButton;
    protected Button loadButton;
    protected Button renameButton;
    protected Button aboutButton;
    
    // THIS DIALOG IS USED FOR GIVING FEEDBACK TO THE USER
    protected AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // THIS TITLE WILL GO IN THE TITLE BAR
    protected String appTitle;
    
    /**
     * This constructor initializes the file toolbar for use.
     * 
     * @param initPrimaryStage The window for this application.
     * 
     * @param initAppTitle The title of this application, which
     * will appear in the window bar.
     * 
     * @param app The app within this gui is used.
     */
    public AppGUI(  Stage initPrimaryStage, 
		    String initAppTitle, 
		    AppTemplate app,
                    int num){
	// SAVE THESE FOR LATER
	primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.DECORATED);
	appTitle = initAppTitle;
	       
        // INIT THE TOOLBAR
        initTopToolbar(app);
        		
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow();
        primaryStage.setTitle("Code Check - " + initAppTitle);
        // INIT THE STYLESHEET AND THE STYLE FOR THE FILE TOOLBAR
        initStylesheet(app);
        initFileToolbarStyle();
        initString = initAppTitle;
        if (num == 0) {
            newButton.setDisable(false);
            loadButton.setDisable(false);
            renameButton.setDisable(true);
            aboutButton.setDisable(false);
        }
        else {
            newButton.setDisable(false);
            loadButton.setDisable(false);
            renameButton.setDisable(false);
            aboutButton.setDisable(false);
        }
    }
    public void startWorkspace () {
        fileController.handleWorkspaceRequest(initString);
    }
    
    public boolean checkIfInitialized() {
        if (renameButton.isDisabled()) {
            return false;
        }
        return true;
    }
    public void setTitle(String title) {
        primaryStage.setTitle("Code Check - " + title);
    }
    public String getTitle() {
        return primaryStage.getTitle();
    }
    /**
     * Accessor method for getting the file toolbar controller.
     */
    public AppFileController getFileController() { return fileController; }
    
    /**
     * Accessor method for getting the application pane, within which all
     * user interface controls are ultimately placed.
     * 
     * @return This application GUI's app pane.
     */
    public BorderPane getAppPane() { return appPane; }
    public void setAppPane(BorderPane app) { appPane = app;}
    
    /**
     * Accessor method for getting the toolbar pane in the top, within which
     * other toolbars are placed.
     * 
     * @return This application GUI's app pane.
     */    
    public FlowPane getTopToolbarPane() {
        return topToolbarPane;
    }
    
    /**
     * Accessor method for getting the file toolbar pane, within which all
     * file controls are ultimately placed.
     * 
     * @return This application GUI's app pane.
     */    
    public FlowPane getFileToolbar() {
        return fileToolbar;
    }
    
    /**
     * Accessor method for getting this application's primary stage's,
     * scene.
     * 
     * @return This application's window's scene.
     */
    public Scene getPrimaryScene() { return primaryScene; }
    
    /**
     * Accessor method for getting this application's window,
     * which is the primary stage within which the full GUI will be placed.
     * 
     * @return This application's primary stage (i.e. window).
     */    
    public Stage getWindow() { return primaryStage; }

    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
     * 
     * @param saved Describes whether the loaded Page has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        renameButton.setDisable(saved);
        aboutButton.setDisable(false);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
	newButton.setDisable(false);
        loadButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    /****************************************************************************/
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR AppGUI */
    /****************************************************************************/
    
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initTopToolbar(AppTemplate app) {
        fileToolbar = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newButton = new Button("New");
        loadButton = new Button("Load");
        renameButton = new Button("Rename");
        aboutButton = new Button("About");
        fileToolbar.getChildren().addAll(newButton, loadButton, renameButton, aboutButton);

	// AND NOW SETUP THEIR EVENT HANDLERS
        fileController = new AppFileController(app);
        newButton.setOnAction(e -> {
            fileController.handleNewRequest();
        });
        loadButton.setOnAction(e -> {
            fileController.handleLoadRequest();
        });
        renameButton.setOnAction(e -> {
            fileController.handleRenameRequest();
        });
        aboutButton.setOnAction(e->{
            fileController.handleAboutRequest();
        });
        
        // NOW PUT THE FILE TOOLBAR IN THE TOP TOOLBAR, WHICH COULD
        // ALSO STORE OTHER TOOLBARS
        topToolbarPane = new FlowPane();
        topToolbarPane.getChildren().add(fileToolbar);
    }

    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Page IS CREATED OR LOADED
    private void initWindow() {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(appTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        appPane = new BorderPane();
        appPane.setTop(topToolbarPane);
        primaryScene = new Scene(appPane);
        
        // SET THE APP ICON
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
        primaryStage.getIcons().add(new Image(appIcon));

        // NOW TIE THE SCENE TO THE WINDOW
        primaryStage.setScene(primaryScene);
        
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> {
            e.consume();
            try {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setHeaderText("Program exiting");
                alert.setContentText("Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    System.exit(0);
                } else {
                    
                }
            }catch (Exception ex) {
            }
        });
    }
    
    /**
     * This is a public helper method for initializing a simple button with
     * an icon and tooltip and placing it into a toolbar.
     * 
     * @param toolbar Toolbar pane into which to place this button.
     * 
     * @param icon Icon image file name for the button.
     * 
     * @param tooltip Tooltip to appear when the user mouses over the button.
     * 
     * @param disabled true if the button is to start off disabled, false otherwise.
     * 
     * @return A constructed, fully initialized button placed into its appropriate
     * pane container.
     */
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
   /**
     *  Note that this is the default style class for the top file toolbar
     * and that style characteristics for this type of component should be 
     * put inside app_properties.xml.
     */
    public static final String CLASS_BORDERED_PANE = "bordered_pane";

   /**
     *  Note that this is the default style class for the file buttons in
     * the top file toolbar and that style characteristics for this type
     * of component should be put inside app_properties.xml.
     */
    public static final String CLASS_FILE_BUTTON = "file_button";
    
    /**
     * This function sets up the stylesheet to be used for specifying all
     * style for this application. Note that it does not attach CSS style
     * classes to controls, that must be done separately.
     */
    private void initStylesheet(AppTemplate app) {
	// SELECT THE STYLESHEET
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String stylesheet = props.getProperty(APP_PATH_CSS);
	stylesheet += props.getProperty(APP_CSS);
        Class appClass = app.getClass();
	URL stylesheetURL = appClass.getResource(stylesheet);
	String stylesheetPath = stylesheetURL.toExternalForm();
	primaryScene.getStylesheets().add(stylesheetPath);	
    }
    
    /**
     * This function specifies the CSS style classes for the controls managed
     * by this framework.
     */
    private void initFileToolbarStyle() {
	topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
	
        makeButton(newButton);
        makeButton(loadButton);
        makeButton(renameButton);
        makeButton(aboutButton);
        
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
    }
    public void newGUI(String initAppTitle, int num, AppTemplate app) {
        primaryStage.hide();
        primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.DECORATED);
	appTitle = initAppTitle;
	       
        // INIT THE TOOLBAR
        initTopToolbar(app);
        		
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow();
        primaryStage.setTitle("Code Check - " + initAppTitle);
        // INIT THE STYLESHEET AND THE STYLE FOR THE FILE TOOLBAR
        initStylesheet(app);
        initFileToolbarStyle();
        initString = initAppTitle;
        if (num == 0) {
            newButton.setDisable(false);
            loadButton.setDisable(false);
            renameButton.setDisable(true);
            aboutButton.setDisable(false);
        }
        else {
            newButton.setDisable(false);
            loadButton.setDisable(false);
            renameButton.setDisable(false);
            aboutButton.setDisable(false);
        }
        app.buildAppComponentsHook();
        startWorkspace();
    }
}
