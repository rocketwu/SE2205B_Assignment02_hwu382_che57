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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */
public class BirdsController implements Initializable {
    
    private OrderedDictionary data;
    private MediaPlayer sound;
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
    private Button playBtn;
    @FXML
    private Button stopBtn;

    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void loadDictionary(){
        data=new OrderedDictionary();
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
                String image="src/images/"+name+".jpg";
                String sound="src/sounds/"+name+".mp3";
                data.insert(new BirdRecord(new DataKey(name,size),about,sound,image));
            }
        } catch (FileNotFoundException | DictionaryException ex) {
            this.error(ex);
        }
        first();
    }
    
    @FXML
    public void first(){
        try {
            showBird(data.smallest());
        } catch (DictionaryException ex) {
            this.error(ex);
        }
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
        if(sound==null) return;
        sound.play();
        stopBtn.setDisable(false);
        playBtn.setDisable(true);
        
    }

    @FXML
    public void stop(){
        if(sound==null) return;
        sound.stop();
        stopBtn.setDisable(true);
        playBtn.setDisable(false);
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
        stopBtn.setDisable(true);
        playBtn.setDisable(true);
    }

    private void showBird(BirdRecord bird) {
        aName.setText(bird.getDataKey().getBirdName());
        aAbout.setText(bird.getAbout());
        imagePane.getChildren().add(new ImageView(new Image(new File(bird.getImage()).toURI().toString())));
        sound=new MediaPlayer(new Media(new File(bird.getSound()).toURI().toString()));
        stopBtn.setDisable(true);
        playBtn.setDisable(false);
    }

}
