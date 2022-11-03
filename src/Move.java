import java.util.regex.Pattern;

public class Move {
    Piece[][] board;
    boolean playerTurnIsWhite;
    public Move(Piece[][] startBoard){
        this.board = startBoard;
        playerTurnIsWhite = true;
    }



    private boolean checkValidPath(Square fromSquare, int toFileIndex, int toRankIndex, String pawnPromotionPiece){
        return false;
    }


    public void movePiece(String move){
        Square fromSquare = new Square();
        fromSquare.setFileIndex(calcFileIndex(move.charAt(0)));
        fromSquare.setRankIndex(calcRankIndex(Integer.valueOf(move.substring(1,2))));

        int toFileIndex = calcFileIndex(move.charAt(3));
        int toRankIndex = calcRankIndex(Integer.valueOf(move.substring(4,5)));

        String pawnPromotionPiece = null;
        if(move.length() == 6) {
            pawnPromotionPiece = move.substring(5,6);
        }
        //This is my replacement for the capture function with the bool adjusting some behavior
        //within the function
        boolean capture = (move.charAt(2) == 'x');
        //Change the player's turn
        if(movement(fromSquare,toFileIndex,toRankIndex,pawnPromotionPiece, capture)){
            playerTurnIsWhite = !playerTurnIsWhite;
        }
    }

    //This function dos the actual movement
    private boolean movement(Square fromSquare, int toFileIndex, int toRankIndex, String pawnPromotionPiece, boolean capture) {
        int fromFileIndex = fromSquare.getFileIndex();
        int fromRankIndex = fromSquare.getRankIndex();
        Piece fromPiece = board[fromRankIndex][fromFileIndex];

        if(fromPiece == null) {
            System.out.println("Select a square with a piece.");
            return false;
        }
        //validate move returns false if it fails and true if it succeeds
        if(!validateMove(fromPiece, fromFileIndex, fromRankIndex, toFileIndex, toRankIndex, capture)){
            return false;
        }

        if (correctPlayerNotMovingTheirPiece(fromPiece)) return false;

        //Handle the promotion of a pawn.
        if(fromPiece.toString().equalsIgnoreCase("p")) {
            fromPiece = pawnPromotion(toRankIndex, pawnPromotionPiece, fromPiece);
            if (fromPiece == null) return false;
        }

        //If we have gotten here, that means the move is valid and update the board position
        board[toRankIndex][toFileIndex] = fromPiece;
        board[fromRankIndex][fromFileIndex] = null;

        //We return true since our movement was successful;
        return true;


    }

    private Piece pawnPromotion(int toRankIndex, String pawnPromotionPiece, Piece fromPiece) {
        if(playerTurnIsWhite && toRankIndex == 0) {
            if(pawnPromotionPiece == null) {
                System.out.println("Pawn Promotion Piece must be specified for this pawn move.");
                return null;
            }
            if(!pawnPromotionPiece.toUpperCase().equals(pawnPromotionPiece)) {
                System.out.println("Pawn Promotion Piece must be for White. Input should be uppercase.");
                return null;
            }
            fromPiece = Piece.valueOf(pawnPromotionPiece);
        } else if(!playerTurnIsWhite && toRankIndex == 7) {
            if(pawnPromotionPiece == null) {
                System.out.println("Pawn Promotion Piece must be specified for this pawn move.");
                return null;
            }
            if(!pawnPromotionPiece.toLowerCase().equals(pawnPromotionPiece)) {
                System.out.println("Pawn Promotion Piece must be for Black. Input should be lowercase.");
                return null;
            }
            fromPiece = Piece.valueOf(pawnPromotionPiece);
        }
        return fromPiece;
    }

    private boolean validateMove(Piece fromPiece, int fromFileIndex, int fromRankIndex, int toFileIndex, int toRankIndex, boolean capture){
        if (correctPlayerNotMovingTheirPiece(fromPiece)) return false;

        //Validate Piece Movement
        if(fromPiece.toString().equalsIgnoreCase("n")) {
            if (!((Math.abs(fromFileIndex - toFileIndex) == 2 && Math.abs(fromRankIndex - toRankIndex) == 1) || (Math.abs(fromFileIndex - toFileIndex) == 1 && Math.abs(fromRankIndex - toRankIndex) == 2))) {
                System.out.println("Invalid move for Knight.");
                return false;
            }
        } else if(fromPiece.toString().equalsIgnoreCase("r")) {
            validateRookMove(toFileIndex, toRankIndex, fromFileIndex, fromRankIndex);
            return false;
        } else if(fromPiece.toString().equalsIgnoreCase("b")) {
            if (validateBishop(fromFileIndex, fromRankIndex, toFileIndex, toRankIndex)) return false;
        } else if (fromPiece.toString().equalsIgnoreCase("q")) {
            if (validateQueen(fromFileIndex, fromRankIndex, toFileIndex, toRankIndex)) return false;
        } else if(fromPiece.toString().equalsIgnoreCase("k")) {
            if (validateKing(fromFileIndex, fromRankIndex, toFileIndex, toRankIndex)) return false;
        } else if (fromPiece.toString().equalsIgnoreCase("p")) {
            if (validatePawn(fromFileIndex, fromRankIndex, toFileIndex, toRankIndex, capture)) return false;
        }else if(board[toRankIndex][toFileIndex] != null || capture){
            return false;
        }
        return true;
    }
    //All of these other validates return true on failure and false on success.
    private boolean validateKing(int fromFileIndex, int fromRankIndex, int toFileIndex, int toRankIndex) {
        if(fromFileIndex == toFileIndex && toRankIndex == fromRankIndex) {
            System.out.println("Cannot create valid path for King.");
            return true;
        } else if (Math.abs(fromFileIndex - toFileIndex) > 1) {
            System.out.println("Cannot create valid path for King.");
            return true;
        } else if (Math.abs(fromRankIndex - toRankIndex) > 1) {
            System.out.println("Cannot create valid path for King.");
            return true;
        }
        return false;
    }

    private boolean validateQueen(int fromFileIndex, int fromRankIndex, int toFileIndex, int toRankIndex) {
        if(fromFileIndex == toFileIndex && toRankIndex == fromRankIndex) {
            System.out.println("Cannot create valid path for Queen.");
            return true;
        } else if(fromFileIndex == toFileIndex) {
            if(toRankIndex > fromRankIndex) {
                for(int i = fromRankIndex +1; i< toRankIndex; i++) {
                    if(board[i][fromFileIndex] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            } else {
                for(int i = fromRankIndex -1; i> toRankIndex; i--) {
                    if(board[i][fromFileIndex] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            }
        } else if(fromRankIndex == toRankIndex){
            if(toFileIndex > fromFileIndex) {
                for(int i = fromFileIndex +1; i< toFileIndex; i++) {
                    if(board[fromRankIndex][i] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            } else {
                for(int i = fromFileIndex -1; i> fromFileIndex; i--) {
                    if(board[fromRankIndex][i] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            }
        } else if(Math.abs(fromFileIndex - toFileIndex) != Math.abs(fromRankIndex - toRankIndex)) {
            System.out.println("Cannot create valid path for Queen.");
            return true;
        } else {
            if (fromFileIndex < toFileIndex && fromRankIndex < toRankIndex) {
                for (int i = 1; i < toFileIndex - fromFileIndex; i++) {
                    if (board[fromRankIndex + i][fromFileIndex + i] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            } else if (fromFileIndex < toFileIndex && fromRankIndex > toRankIndex) {
                for (int i = 1; i < toFileIndex - fromFileIndex; i++) {
                    if (board[fromRankIndex - i][fromFileIndex + i] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            } else if (fromFileIndex > toFileIndex && fromRankIndex > toRankIndex) {
                for (int i = 1; i < fromFileIndex - toFileIndex; i++) {
                    if (board[fromRankIndex - i][fromFileIndex - i] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            } else if (fromFileIndex > toFileIndex && fromRankIndex < toRankIndex) {
                for (int i = 1; i < fromFileIndex - toFileIndex; i++) {
                    if (board[fromRankIndex + i][fromFileIndex - i] != null) {
                        System.out.println("Cannot create valid path for Queen.");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean validateBishop(int fromFileIndex, int fromRankIndex, int toFileIndex, int toRankIndex) {
        if(fromFileIndex == toFileIndex || toRankIndex == fromRankIndex) {
            System.out.println("Cannot create valid path for Bishop.");
            return true;
        } else if(Math.abs(fromFileIndex - toFileIndex) != Math.abs(fromRankIndex - toRankIndex)) {
            System.out.println("Cannot create valid path for Bishop.");
            return true;
        } else {
            if(fromFileIndex < toFileIndex && fromRankIndex < toRankIndex) {
                for(int i = 1; i < toFileIndex - fromFileIndex; i++) {
                    if(board[fromRankIndex +i][fromFileIndex +i] != null) {
                        System.out.println("Cannot create valid path for Bishop.");
                        return true;
                    }
                }
            } else if(fromFileIndex < toFileIndex && fromRankIndex > toRankIndex) {
                for(int i = 1; i < toFileIndex - fromFileIndex; i++) {
                    if(board[fromRankIndex -i][fromFileIndex +i] != null) {
                        System.out.println("Cannot create valid path for Bishop.");
                        return true;
                    }
                }
            } else if(fromFileIndex > toFileIndex && fromRankIndex > toRankIndex) {
                for(int i = 1; i < fromFileIndex - toFileIndex; i++) {
                    if(board[fromRankIndex -i][fromFileIndex -i] != null) {
                        System.out.println("Cannot create valid path for Bishop.");
                        return true;
                    }
                }
            } else if(fromFileIndex > toFileIndex && fromRankIndex < toRankIndex) {
                for(int i = 1; i < fromFileIndex - toFileIndex; i++) {
                    if(board[fromRankIndex +i][fromFileIndex -i] != null) {
                        System.out.println("Cannot create valid path for Bishop.");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean validatePawn(int fromFileIndex, int fromRankIndex, int toFileIndex, int toRankIndex, boolean capture) {
        //Pawns move slightly different when capturing
        if(capture){
            if(playerTurnIsWhite){
                if(fromRankIndex-toRankIndex != 1){
                    System.out.println("Cannot create valid path for Pawn.");
                    return true;
                }
            }
            else{
                if(toRankIndex-fromRankIndex != 1){
                    System.out.println("Cannot create valid path for Pawn.");
                    return true;
                }

            }
            if(Math.abs(fromFileIndex-toFileIndex) != 1){
                System.out.println("Cannot create valid path for Pawn.");
                return true;
            }
            if(board[toRankIndex][toFileIndex] == null){
                System.out.println("Cannot create valid path for Pawn.");
                return true;
            }
            return false;
        }
        //This is if we aren't capturing
        if(fromFileIndex != toFileIndex) {
            System.out.println("Cannot create valid path for Pawn.");
            return true;
        }
        if(playerTurnIsWhite) {
            int rankDelta = fromRankIndex - toRankIndex;
            if(fromRankIndex == 6) {
                if(rankDelta > 2 || rankDelta < 1) {
                    System.out.println("Cannot create valid path for Pawn.");
                    return true;
                } else if (rankDelta == 1) {
                    if(board[toRankIndex][toFileIndex] != null) {
                        System.out.println("Cannot create valid path for Pawn.");
                        return true;
                    }
                } else if (rankDelta == 2) {
                    if(board[toRankIndex][toFileIndex] != null || board[toRankIndex -1][toFileIndex] != null) {
                        System.out.println("Cannot create valid path for Pawn.");
                        return true;
                    }
                }
            } else {
                if(rankDelta != 1) {
                    System.out.println("Cannot create valid path for Pawn.");
                    return true;
                } else {
                    if(board[toRankIndex][toFileIndex] != null) {
                        System.out.println("Cannot create valid path for Pawn.");
                        return true;
                    }
                }
            }
        } else {
            int rankDelta = fromRankIndex - toRankIndex;
            if(fromRankIndex == 1) {
                if(rankDelta < -2 || rankDelta > -1) {
                    System.out.println("Cannot create valid path for Pawn.");
                    return true;
                } else if (rankDelta == -1) {
                    if(board[toRankIndex][toFileIndex] != null) {
                        System.out.println("Cannot create valid path for Pawn.");
                        return true;
                    }
                } else if (rankDelta == -2) {
                    if(board[toRankIndex][toFileIndex] != null || board[toRankIndex +1][toFileIndex] != null) {
                        System.out.println("Cannot create valid path for Pawn.");
                        return true;
                    }
                }
            } else {
                if(rankDelta != -1) {
                    System.out.println("Cannot create valid path for Pawn.");
                    return true;
                } else {
                    if(board[toRankIndex][toFileIndex] != null) {
                        System.out.println("Cannot create valid path for Pawn.");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean validateRookMove(int toFileIndex, int toRankIndex, int fromFileIndex, int fromRankIndex) {
        if(fromFileIndex == toFileIndex && toRankIndex == fromRankIndex) {
            System.out.println("Rook must move at least 1 square.");
            return false;
        } else if(fromFileIndex == toFileIndex) {
            if(toRankIndex > fromRankIndex) {
                for(int i = fromRankIndex +1; i< toRankIndex; i++) {
                    if(board[i][fromFileIndex] != null) {
                        System.out.println("Cannot create valid path for Rook.");
                        return false;
                    }
                }
            } else {
                for(int i = fromRankIndex -1; i> toRankIndex; i--) {
                    if(board[i][fromFileIndex] != null) {
                        System.out.println("Cannot create valid path for Rook.");
                        return false;
                    }
                }
            }
        } else if(fromRankIndex == toRankIndex){
            if(toFileIndex > fromFileIndex) {
                for(int i = fromFileIndex +1; i< toFileIndex; i++) {
                    if(board[fromRankIndex][i] != null) {
                        System.out.println("Cannot create valid path for Rook.");
                        return false;
                    }
                }
            } else {
                for(int i = fromFileIndex -1; i> fromFileIndex; i--) {
                    if(board[fromRankIndex][i] != null) {
                        System.out.println("Cannot create valid path for Rook.");
                        return false;
                    }
                }
            }
        } else {
            System.out.println("Cannot create valid path for Rook.");
            return false;
        }
        return true;
    }

    private boolean correctPlayerNotMovingTheirPiece(Piece fromPiece) {
        //Check that the piece is owned by the correct player.
        if(playerTurnIsWhite) {
            if(fromPiece.toString().toLowerCase() == fromPiece.toString()) {
                System.out.println("Select a square with a white piece.");
                return true;
            }
        } else {
            if(fromPiece.toString().toUpperCase() == fromPiece.toString()) {
                System.out.println("Select a square with a black piece.");
                return true;
            }
        }
        return false;
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
