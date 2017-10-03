/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.workspace;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Chris
 */
public class fileString {
    private StringProperty filePath;
    private StringProperty fileName;
    
    public fileString (String filePath, String fileName) {
        this.filePath = new SimpleStringProperty(filePath);
        this.fileName = new SimpleStringProperty(fileName);
    }
    public String getFilePath() {
        return filePath.get();
    }
    public void setFilePath (String filePath) {
        this.filePath.set(filePath);
    }
    public String getFileName() {
        return fileName.get();
    }
    public void setFileName(String fName) {
        fileName.set(fName);
    }
    
}
