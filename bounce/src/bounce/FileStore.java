package bounce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileStore {
	
	private int highScore = 0;
	private static String filePath = "src/bounce/resource/score.txt"; 
	
	
	/**
	 *  Initialize the highScore if file exists.
	 */
	public FileStore() {
		FileReader file;
		int line = -1;
		try {
			file = new FileReader(filePath);
			BufferedReader bFile = new BufferedReader(file);
			line = bFile.read();
			if (line != -1) {
				this.highScore = (int)line;
			}
			bFile.close();
		} catch (IOException e) {
			// we don't care much
			System.out.printf("File %s doesn't exist\n", filePath);
		}
		
	}
	
	
	/**
	 * Returns the high score. If no high score is stored it returns zero
	 *
	 */
	public int getHighScore() {
		return highScore;
	}
	
	
	/*
	 *  Writes to file to save the user's high score 
	 *  
	 *  @param score
	 *  the score to save in a file
	 */
	public void saveHighScore(int score) {
		// only save if it's higher than current score
		if (score > highScore) {
			try {
				FileWriter file = new FileWriter(filePath); 
				BufferedWriter bFile = new BufferedWriter(file);
				bFile.write(score);
				bFile.newLine();
				bFile.close();
				this.highScore = score;
				
			} catch (IOException e) {
				System.out.println("An error occurred while saving the high score to file");
			}
		}
		
	}
}
