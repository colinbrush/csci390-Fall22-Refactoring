import java.util.regex.Pattern;

public class Move {
    Piece[][] board;
    public Move(Piece[][] startBoard){
        this.board = startBoard;
    }

    private boolean checkValidPath(Square fromSquare, int toFileIndex, int toRankIndex, String pawnPromotionPiece){
        return false;
    }


    public boolean movePiece(String move){
        Square fromSquare = new Square();
        fromSquare.setFileIndex(calcFileIndex(move.charAt(0)));
        fromSquare.setRankIndex(calcRankIndex(Integer.valueOf(move.substring(1,2))));

        int toFileIndex = calcFileIndex(move.charAt(3));
        int toRankIndex = calcRankIndex(Integer.valueOf(move.substring(4,5)));

        String pawnPromotionPiece = null;
        if(move.length() == 6) {
            pawnPromotionPiece = move.substring(5,6);
        }
        return false;
    }

    public boolean capturePiece(String move){
        Square fromSquare = new Square();
        fromSquare.setFileIndex(calcFileIndex(move.charAt(0)));
        fromSquare.setRankIndex(calcRankIndex(Integer.valueOf(move.substring(1,2))));

        int toFileIndex = calcFileIndex(move.charAt(3));
        int toRankIndex = calcRankIndex(Integer.valueOf(move.substring(4,5)));

        String pawnPromotionPiece = null;
        if(move.length() == 6) {
            pawnPromotionPiece = move.substring(5,6);
        }
        return false;
    }

    private boolean diagonalValidate(int fromFileIndex, int fromRankIndex, int toFileIndex, int toRankIndex){
        //Check this is a diagonal
        if(Math.abs(fromFileIndex-toFileIndex) != Math.abs(fromRankIndex-toRankIndex)){
            return false;
        }

        for(int i = 1; i <=toFileIndex-fromFileIndex; i++){

        }
        return true;
    }




    private static int calcFileIndex(Character file) {
        // Files are associated as follows: a->7, b->6, c->5, d->4, e->3, f->2, g->1, h->0
        switch(file) {
            case 'a' :
                return 0;
            case 'b' :
                return 1;
            case 'c' :
                return 2;
            case 'd' :
                return 3;
            case 'e' :
                return 4;
            case 'f' :
                return 5;
            case 'g' :
                return 6;
            case 'h' :
                return 7;
            default :
                throw new IllegalArgumentException("File Character '" + file + "' is invalid.");
        }
    }

    private static int calcRankIndex(int rankNumber) {
        // Ranks are associated as follows: 1->7, 2->6, 3->5, 4->4, 5->3, 6->2, 7->1, 8->0
        switch(rankNumber) {
            case 1 :
                return 7;
            case 2 :
                return 6;
            case 3 :
                return 5;
            case 4 :
                return 4;
            case 5 :
                return 3;
            case 6 :
                return 2;
            case 7 :
                return 1;
            case 8 :
                return 0;
            default:
                throw new IllegalArgumentException("Rank Value '" + rankNumber + "' is invalid.");

        }
    }

}
