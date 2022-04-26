package application;

import javafx.application.Application;    
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.util.Random;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Main extends Application {
	private static final int width = 800;
	private static final int height = 600;
	private static final int Player_Height = 100;
	private static final int Player_Width = 15;
	private static final double Ball_R = 15;
	private int ballYSpeed = 1;
	private int ballXSpeed = 1;
	private double playerOneYPos = height / 2;
	private double playerTwoYPos = height / 2;
	private double ballXPos = width / 2;
	private double ballYPos = width / 2;
	private int scoreP1 = 0;
	private int scoreP2 = 0;
	private boolean gameStarted;
	private int playerOneXPos = 0;
	private double playerTwoXPos = width - Player_Width;
	
	public void start(Stage primaryStage) {
		try {
			Canvas canvas = new Canvas(width, height);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
			t1.setCycleCount(Timeline.INDEFINITE);
			
			canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
			canvas.setOnMouseClicked(e -> gameStarted = true);
			primaryStage.setTitle("Pong");
			primaryStage.setScene(new Scene(new StackPane(canvas)));
			t1.play();
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void run(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);
		
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font(25));
		
		if(gameStarted) {
			ballXPos+=ballXSpeed;
			ballYPos+=ballYSpeed;
			
			if(ballXPos < width - width/4) {
				playerTwoYPos = ballYPos - Player_Height / 2;
			} else {
				playerTwoYPos = ballYPos > playerTwoYPos + Player_Height / 2 ? playerTwoYPos +=1 : playerTwoYPos - 1;
			}
			
			gc.fillOval(ballXPos, ballYPos, Ball_R, Ball_R);
		} else {
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("On Click", width / 2, height / 2);
			
			ballXPos = width / 2;
			ballYPos = height / 2;
			
			ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
		}
		
		if(ballYPos > height || ballYPos < 0) {
			ballYSpeed *= -1;
		}
		
		if(ballXPos < playerOneXPos - Player_Width) {
			scoreP2++;
			gameStarted = false;
		}
		
		if(ballXPos > playerTwoXPos + Player_Width) {
			scoreP1++;
			gameStarted = false;
		}
		
		if( ((ballXPos + Ball_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + Player_Height) || ((ballXPos < playerOneXPos + Player_Width) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos +  Player_Height)) {
			ballYSpeed += 1 * Math.signum(ballYSpeed);
			ballXSpeed += 1 * Math.signum(ballXSpeed);
			ballXSpeed *= -1;
			ballYSpeed *= -1;
		}
		
		gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
		
		gc.fillRect(playerTwoXPos, playerTwoYPos, Player_Width, Player_Height);
		gc.fillRect(playerOneXPos, playerOneYPos, Player_Width, Player_Height);
	}
	public static void main(String[] args) {
		launch(args);
	}
}