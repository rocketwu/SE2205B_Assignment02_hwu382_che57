/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */
public class BirdsController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    @FXML
    private Label aName;
    @FXML
    private Label aAbout;
    @FXML
    private Pane imagePane;
    @FXML
    private TextField sName;
    @FXML
    private ComboBox sSize;

    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void loadDictionary(){
        try {
            Scanner input=new Scanner(new File("BirdsDatabase.txt"));
            while(input.hasNextLine()){
                String reader=input.nextLine();
                if (reader.length()<=0) {
                    continue;
                }
                int size=Integer.parseInt(reader);
                String name=input.nextLine();
                String about=input.nextLine();
                String image="file:src/images/"+name+".jpg";
                String sound="file:src/sounds/"+name+".mp3";
            }
        } catch (FileNotFoundException ex) {
            this.error(ex);
        }
    }
    
    @FXML
    public void first(){
        
    }
    
    @FXML
    public void last(){
        
    }
    
    @FXML
    public void next(){
        
    }
    
    @FXML
    public void previous(){
        
    }
    
    @FXML
    public void delete(){
        
    }
    
    @FXML
    public void find(){
        
    }
    
    @FXML
    public void play(){
        
    }

    @FXML
    public void stop(){
        
    }
    
    private void error(String msg){
        try{
            FXMLLoader loader= new FXMLLoader (getClass().getResource("ErrorMessage.fxml"));
            Parent e=loader.load();
            ErrorMessage ctrl=(ErrorMessage) loader.getController();
            
            Scene s = new Scene(e);
            Stage stage=new Stage();
            stage.setScene(s);
            
            stage.getIcons().add(new Image("file:src/birds/WesternLogo.png"));
            ctrl.msgDisplay(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch (IOException ex){
            
        }
    }
    
    private void error(Exception ex){
        error(ex.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
