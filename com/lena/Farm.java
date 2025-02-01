package com.lena;

import java.util.Scanner;
import java.util.Random;

public class Farm {
	
    private static String secretNumber;
    
    
    private static int totalGuesses;
    
    
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of guesses: (between 1 and 30): ");
        totalGuesses = scanner.nextInt();

        
        // max and minimum number of guesses
        if (totalGuesses < 1 || totalGuesses > 30) {
            System.out.println("Invalid number of guesses.");
            return;
        }

        
        // Generate the secret number
        secretNumber = generateSecretNumber();

        
        // Game loop
        System.out.println("You have " + totalGuesses + " guesses. Start guessing!");

        while (totalGuesses > 0) {
        	
            System.out.print("Enter your guess: ");
            String guess = scanner.next();

            if (guess.length() != 4 || !isValidGuess(guess)) {
                System.out.println("Please enter a valid 4-digit number with unique digits.");
                continue;
            }

            
            
            // Calculates number of bulls and cows
            int bulls = calculateBulls(guess);
            int cows = calculateCows(guess);

            System.out.println(bulls + " Bulls, " + cows + " Cows.");

            // Check if the user has guessed correctly
            if (bulls == 4) {
                System.out.println("Congratulations! You've guessed the secret number: " + secretNumber);
                break;
            }

            // Decrease the remaining guesses
            totalGuesses--;
            System.out.println("Guesses remaining: " + totalGuesses);

            // If the user runs out of guesses
            if (totalGuesses == 0) {
                System.out.println("You've run out of guesses. The secret number was: " + secretNumber);
            }
        }

        // Close scanner
        scanner.close();
    }

    
    
    // Generates a random 4-digit number with unique digits
    private static String generateSecretNumber() {
        Random rand = new Random();
        StringBuilder number = new StringBuilder();

        while (number.length() < 4) {
            int digit = rand.nextInt(10);
            String digitStr = Integer.toString(digit);

            // Ensure the number has unique digits
            if (!number.toString().contains(digitStr)) {
                number.append(digit);
            }
        }

        return number.toString();
    }

    
    
    // Method to check if a guess is valid (4 digits, no repeated digits)
    private static boolean isValidGuess(String guess) {
        if (guess.length() != 4) return false;

        // Ensure all digits are unique
        for (int i = 0; i < guess.length(); i++) {
            for (int j = i + 1; j < guess.length(); j++) {
                if (guess.charAt(i) == guess.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    
    
    
    // Method to calculate the number of bulls (correct digit in the correct position)
    private static int calculateBulls(String guess) {
        int bulls = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secretNumber.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    
    
    
    // Method to calculate the number of cows (correct digit in the wrong position)
    private static int calculateCows(String guess) {
        int cows = 0;
        boolean[] secretUsed = new boolean[4];  // Track used digits in secretNumber
        boolean[] guessUsed = new boolean[4];   // Track used digits in guess

        // First pass: Identify bulls and mark used positions
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secretNumber.charAt(i)) {
                secretUsed[i] = true;
                guessUsed[i] = true;
                cows--;  // Decrease cows count for bulls
            }
        }

        // Second pass: Identify cows
        for (int i = 0; i < 4; i++) {
            if (guessUsed[i]) continue;  // Skip already used digits in guess

            for (int j = 0; j < 4; j++) {
                if (!secretUsed[j] && !guessUsed[i] && guess.charAt(i) == secretNumber.charAt(j)) {
                    cows++;
                    secretUsed[j] = true;
                    guessUsed[i] = true;
                    break;
                }
            }
        }

        return cows;
    }
}