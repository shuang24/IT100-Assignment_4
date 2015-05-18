/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private String wordToGuess;
	private String currentWord;
	private HangmanCanvas canvas;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		println("Welcome to Hangman!");

		HangmanLexicon lexicon = new HangmanLexicon();
		RandomGenerator rgen = RandomGenerator.getInstance();

		wordToGuess = lexicon.getWord(rgen.nextInt(lexicon.getWordCount()));
		currentWord = dashifyWord();
		boolean gameWon = false;
		int numberOfGuessesLeft = 8;
		canvas.reset();

		while (!gameWon && (numberOfGuessesLeft > 0)) {
			canvas.displayWord(currentWord);
			println("The word now looks like this: " + currentWord);
			println("You have "+ numberOfGuessesLeft + "guesses left.");
			String guessInput = readLine("Your guess: ");
			char guess;
			if ((guessInput.length() == 0) || (guessInput.length() > 1) || !Character.isLetter(guessInput.charAt(0))) {
				println("Please enter single letter only!");
				continue;
			}
			if (guessCorrect(guess)) {
				println("That guess is correct");
				if (wordToGuess.equals(currentWord)) {
					gameWon = true;
				} 
			} else {
				canvas.noteIncorrectGuess(guess);
				println("There are no " + guess + "'s in the word.");
				numberOfGuessesLeft --;
			}
		}
		if (gameWon) {
			println("You guessed the word " + wordToGuess);
			println("You Win");
			canvas.displayWord(currentWord);
		} else {
			println("You are completely hung.");
			println("The word was: " + wordToGuess);
			println("You lose.");
		}
	}

	private String dashifyWord() {
		String result = "";
		for (int i = 0; i < wordToGuess.length(); i++) {
			result += "-";
		}
		return result;
	}

	private boolean guessCorrect (char guess) {
		guess = Character.toUpperCase(guess);
		boolean guessCorrect =false;
		String newWord = "";
		for (int i =0; i < wordToGuess.length(); i++) {
			if (wordToGuess.charAt(i) == guess) {
				newWord += guess;
				guessCorrect = true;
			}else{
				newWord += currentWord.charAt(i);
			}
		}
		if (guessCorrect) {
			currentWord = new String(newWord);
		}
		return guessCorrect;
	}
}
