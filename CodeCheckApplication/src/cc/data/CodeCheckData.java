/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.data;

import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import cc.CodeCheckApp;

/**
 * This is the data component for SlideshowCreatorApp. It has all the data needed
 * to be set by the user via the User Interface and file I/O can set and get
 * all the data from this object
 * 
 * @author Richard McKenna
 */
public class CodeCheckData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CodeCheckApp app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW

    /**
     * This constructor will setup the required data structures for use.
     * 
     * @param initApp The application this data manager belongs to. 
     */
    public CodeCheckData(CodeCheckApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {

    }
}