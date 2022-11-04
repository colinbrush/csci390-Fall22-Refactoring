import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chess {

    Board currentBoard;

    public Chess() {
       currentBoard = new Board();
    }

    public void play() throws IOException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        Move myMover = new Move(currentBoard);
        while(!gameIsOver()) {
            currentBoard.printBoardToConsole();

            // TODO: Homework - Is this really the chess game's responsibility to do the move conversion?
            //                       Refactor this code to make it the responsibility of a different class.
            // We'll be doing a simpler notation for our chess game. Notation will be a 5 or 6 character length. Form
            //       will take the shape of {a-h}{1-8}(\-x}{a-h}{1-8}{rnbqRNBQ}.
            //       First character is the from file
            //       Second character is the from rank
            //       Third character is - for move, x for capture
            //       Forth character is the to file
            //       Fifth character is the to rank
            //       Sixth character is the promotion of a pawn to a piece type. This is optional
            String move = inputReader.readLine();
            Pattern movePattern = Pattern.compile("^[a-h][1-8][-x][a-h][1-8][rnbqRNBQ]{0,1}");
            Matcher moveMatcher = movePattern.matcher(move);
            if(!moveMatcher.find()) {
                //Move is invalid;
                System.out.println("Move is invalid. Please input a valid move.");
                continue;
            }
            //the mover only has one function and will handle captures inside of move
            myMover.movePiece(move);

            // We are not going to worry about special moves like castling and en passant
        }

        System.out.println("Game over.");
        System.out.println("Thanks for playing!");
    }

    private boolean gameIsOver() {
        return isPositionCheckmate() || isPositionStalemate();
    }

    private boolean isPositionStalemate() {
        return false;
    }

    private boolean isPositionCheckmate() {
        return false;
    }

}
