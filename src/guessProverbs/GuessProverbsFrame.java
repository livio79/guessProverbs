package guessProverbs; 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class GuessProverbsFrame extends Application {
	
 private static final double WIDTH = 750.0;
 private static final double HEIGHT = 350.0;
 private static final double PADDING = 10.0; 
 
 private GuessProverbs game = null; 
 private String message = "";
  private String input = "";
  
 AnchorPane root = new AnchorPane(); 
 private Button newGame = new Button("New Game");
 private Button info = new Button("Info"); 
 private TextArea textArea = new TextArea();
 private TextField inputField = new TextField();
 private TextField messageField = new TextField();
 private TextField allLetters = new TextField();
 private TextField turn = new TextField(); 
 private Alert solution = new Alert(AlertType.WARNING);

 
   @Override
   public void start(Stage primaryStage) throws Exception {
	   
	   final ComboBox <String> languages = new ComboBox<>();
	   languages.getItems().addAll("DEU","ITA","ENG"); 
	   languages.setValue("DEU");
       
       inputField.setAlignment(Pos.CENTER_RIGHT); 
       allLetters.setAlignment(Pos.CENTER);
       messageField.setAlignment(Pos.CENTER);
       textArea.setWrapText(true);
       
       inputField.setStyle("-fx-font: 40px Tahoma; "); 
       textArea.setStyle("-fx-font: 35px Tahoma ; "); 
       messageField.setStyle("-fx-font: 30px Tahoma; ");
       allLetters.setStyle("-fx-font: 23px Tahoma; "); 
       turn.setStyle("-fx-font: 20px Tahoma; ");
       newGame.setStyle("-fx-font: 15px Tahoma; ");
       info.setStyle("-fx-font: 20px Tahoma; ");
       languages.setStyle("-fx-font: 15px Tahoma; ");
       
       messageField.setEditable(false);
       inputField.setEditable(false);
       textArea.setEditable(false);
       allLetters.setEditable(false);	
       turn.setEditable(false);
		
       
       inputField.setOnAction(new EventHandler<ActionEvent>() { 
		@Override
		public void handle(ActionEvent event) { 
			allLetters.setText(""); 
			
			 input = inputField.getText().toUpperCase() ;
			 
			if(input.length() >0) { 
					 input = input.substring(input.length()-3,input.length()-2);  //input.substring get the last input without the letter spacing
					if(game.validInput(input) && !game.enteredLetter(input)) {  
					   if( game.isThere(input)) {
						   game.updateDisplayThisLetters(input);
					   }
					   else {										
						   game.increaseTurn();							
					   }  
					}
				 
				textArea.setText(game.displayThisLetters()); 
				allLetters.setText(writeEnteredLetters());
				turn.setText("Übrig: " + (game.getAttempts()-game.getTurn()));
				inputField.setText("");
				
				 
				if(game.win()) {
					message = "Du hast gewonnen";
					messageField.setText(message);
					inputField.setEditable(false);
				} 
				else if(game.lose()) { 
					messageField.setText("Du hast verloren");
					solution.setTitle("Game Over");
					solution.setHeaderText("Das war das Sprichwort");
					solution.setContentText( game.getText().toUpperCase());
					solution.showAndWait();
					inputField.setEditable(false);
				}
				else if(!game.validInput(input)) { 
					messageField.setText("Ungültige Eingabe" );
				} 
				else { 
					messageField.setText( "Gebe einen Buchstabe ein");
				}
			}
		}
     });
       
       //add two spaces in the input text field
       inputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
        	   inputField.appendText("  "); 
        	    
            }
       });
       
   
       
       newGame.setOnAction(new EventHandler<ActionEvent>() {
    	   @Override
   		public void handle(ActionEvent event) { 
    		   inputField.setEditable(true);
    		   messageField.setText("Gebe einen Buchstabe ein");
    		   allLetters.setText(""); 
    		   game = new GuessProverbs();
    		   game.setTurn(1);
    		   game.setFile(languages.getValue());
    		   game.resetEntered();
    		   
    		   try {
				game.chooseProverb();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
    		  
    		 game.initDisplayThisLetters(game.getText());  									
    		 textArea.setText(game.displayThisLetters());
    	   }
       }); 
       
       
       info.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
           	Image infoImage;
           	ImageView imageView = new ImageView();
           	
				try {
					infoImage = new Image   (new FileInputStream("info.pdf"));
					 imageView = new ImageView(infoImage)  ;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
   			
               StackPane infoPane = new StackPane();
               infoPane.getChildren().add(imageView);

               Scene secondScene = new Scene(infoPane, 230, 100);

               Stage wertungWindow = new Stage();       
               wertungWindow.setTitle("Info");
               wertungWindow.setScene(secondScene);
               wertungWindow.setHeight(570);
               wertungWindow.setWidth(510);
               wertungWindow.setX(  200);
               wertungWindow.setY(  100);
               wertungWindow.setResizable(false);
               wertungWindow.show();
               
           }
       });
       
        
					       
       AnchorPane.setTopAnchor(newGame, PADDING); 
       AnchorPane.setRightAnchor(newGame, WIDTH - 2*PADDING - 80);
       AnchorPane.setLeftAnchor(newGame, PADDING);
       AnchorPane.setBottomAnchor(newGame, HEIGHT -50);			       
					      
       AnchorPane.setTopAnchor(languages, PADDING);
       AnchorPane.setRightAnchor(languages, PADDING); 
       AnchorPane.setLeftAnchor(languages, WIDTH - 2*PADDING - 80);
       AnchorPane.setBottomAnchor(languages, HEIGHT -50);	       
			 	       
       AnchorPane.setTopAnchor(messageField, PADDING );
       AnchorPane.setLeftAnchor(messageField, 4*PADDING + 80);
       AnchorPane.setBottomAnchor(messageField, HEIGHT -50);
       AnchorPane.setRightAnchor(messageField, 4*PADDING + 80);
       
       AnchorPane.setTopAnchor(textArea, 2 * PADDING + 50);
       AnchorPane.setLeftAnchor(textArea, PADDING);
       AnchorPane.setRightAnchor(textArea, PADDING);
       AnchorPane.setBottomAnchor(textArea,80.0);
       
       AnchorPane.setTopAnchor(inputField, 200.0);
       AnchorPane.setLeftAnchor(inputField, WIDTH/2 -30);
       AnchorPane.setRightAnchor(inputField, WIDTH/2 -30);
       AnchorPane.setBottomAnchor(inputField, 80.0);
       
       AnchorPane.setTopAnchor(turn, 3* PADDING + 260.0 );
       AnchorPane.setLeftAnchor(turn, PADDING);
       AnchorPane.setRightAnchor(turn, WIDTH - 2*PADDING - 110);
       AnchorPane.setBottomAnchor(turn, PADDING);
       
       AnchorPane.setTopAnchor(info,  3* PADDING + 260.0);
       AnchorPane.setLeftAnchor(info, WIDTH - 2*PADDING - 80);
       AnchorPane.setRightAnchor(info, PADDING);
       AnchorPane.setBottomAnchor(info, PADDING);
       
       AnchorPane.setTopAnchor(allLetters,   3* PADDING + 260.0);
       AnchorPane.setLeftAnchor(allLetters, 4*PADDING + 110);
       AnchorPane.setBottomAnchor(allLetters, PADDING);
       AnchorPane.setRightAnchor(allLetters, 4*PADDING + 80);
  
       
       root.getChildren().addAll (newGame, info, languages, allLetters,messageField, textArea, inputField, turn); 
       Scene scene = new Scene(root, WIDTH, HEIGHT);  
       primaryStage.setTitle("Game");
       primaryStage.setScene(scene);
       primaryStage.setResizable(false);
       primaryStage.show();
   }
   
   
   public String writeEnteredLetters() {
		String allLetters = "";
		ArrayList<String> enteredLetters = game.getEnterd();
		Collections.sort(enteredLetters);
		for(String tmp : enteredLetters)
		allLetters += tmp + " "; 
		return allLetters ;
	}
 
   public static void main(String[] args) {
       launch(args);
   }
 
}