/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.file;

import cc.CodeCheckApp;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.IOException;
import javax.json.JsonObject;

/**
 *
 * @author Chris
 */
public class CodeCheckFiles implements AppFileComponent {
    // THIS IS THE APP ITSELF
    CodeCheckApp app;
    
    public CodeCheckFiles(CodeCheckApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        JsonObject j = null;
        return j;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}