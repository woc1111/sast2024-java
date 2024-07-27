package homework.Wordle;

import java.util.*;

public class Wordle {
    static final int ALPHABET_SIZE = 26;            // The size of the alphabet
    static final int WORD_LENGTH = 5;               // The length of words
    static final int TOTAL_CHANCES = 6;             // The chances in total

    // Guess `word` at state `s`
    public static State guess(State s) {
        // TODO begin
        s.chancesLeft--;
        int aplhaNum[] = new int[26];
        for(int i=0;i<26;i++){
            aplhaNum[i]=0;
        }
        for(int i=0;i<5;i++){
            s.wordState[i]=Color.GRAY;
        }
        int basenum = Integer.valueOf('A');
        for(int i=0;i<5;i++){
            aplhaNum[Integer.valueOf(s.answer.charAt(i))-basenum]++;
        }
        for(int i=0;i<5;i++){
            if(s.answer.charAt(i) == s.word.charAt(i)){
                s.wordState[i]=Color.GREEN;
                aplhaNum[Integer.valueOf(s.answer.charAt(i))-basenum]--;
            }
        }
        for(int i=0;i<5;i++){
            char right_c = s.answer.charAt(i);
            char guess_c = s.word.charAt(i);
            if(s.wordState[i]!=Color.GREEN){
                if(aplhaNum[Integer.valueOf(guess_c)-basenum] >0 ){
                    aplhaNum[Integer.valueOf(guess_c)-basenum] --;
                    s.wordState[i]=Color.YELLOW;
                }
                else{
                    s.wordState[i]=Color.RED;
                }
            }
        }

        for(int i=0;i<5;i++){   //更改alphaState
            char right_c = s.answer.charAt(i);
            char guess_c = s.word.charAt(i);
            int right_i = Integer.valueOf(right_c) - basenum;
            int guess_i = Integer.valueOf(guess_c) - basenum;
            if(s.wordState[i]==Color.GREEN){
                s.alphabetState[guess_i]=Color.GREEN;
            }
            else if(s.wordState[i]==Color.YELLOW){
                if(s.alphabetState[guess_i]!=Color.GREEN){
                    s.alphabetState[guess_i]=Color.YELLOW;
                }
            }
            else if(s.wordState[i]==Color.RED){
                if(s.alphabetState[guess_i] != Color.GREEN && s.alphabetState[guess_i] != Color.YELLOW){
                    s.alphabetState[guess_i]=Color.RED;
                }
            }
        }
        boolean right =true;
        for(int i=0;i<5;i++){
            if(s.wordState[i] != Color.GREEN){
                right =false;
                break;
            }
        }
        if(right){
            s.status = GameStatus.WON;
        }
        else{
            if(s.chancesLeft == 0){
                s.status = GameStatus.LOST;
            }
            else{
                s.status = GameStatus.RUNNING;
            }
        }
        return s;
    }
    public static void main(String[] args) {
        // Read word sets from files
        WordSet wordSet = new WordSet("C:\\Users\\cnmd\\Desktop\\sast2024-java-main\\assets\\wordle\\FINAL.txt", "C:\\Users\\cnmd\\Desktop\\sast2024-java-main\\assets\\wordle\\ACC.txt");

        Scanner input = new Scanner(System.in);
        // Keep asking for an answer if invalid
        String answer;
        do {
            System.out.print("Enter answer: ");
            answer = input.nextLine().toUpperCase().trim();
            if (wordSet.isNotFinalWord(answer)) {
                System.out.println("INVALID ANSWER");
            }
        } while (wordSet.isNotFinalWord(answer));

        State state = new State(answer);
        while (state.status == GameStatus.RUNNING) {
            // Keep asking for a word guess if invalid
            String word;
            do {
                System.out.print("Enter word guess: ");
                word = input.nextLine().toUpperCase().trim();
                if (wordSet.isNotAccWord(word)) {
                    System.out.println("INVALID WORD GUESS");
                }
            } while (wordSet.isNotAccWord(word));
            // Try to guess a word
            state.word = word;
            state = guess(state);
            state.show();
        }
        if (state.status == GameStatus.LOST) {
            System.out.println("You lost! The correct answer is " + state.answer + ".");
        } else {
            System.out.println("You won! You only used " + (TOTAL_CHANCES - state.chancesLeft) + " chances.");
        }
    }
}
