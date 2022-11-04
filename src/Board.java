public class Board {
    Piece[][] currentState = new Piece[8][8];

    public Board(){
        setupBlackPieces();

        currentState[6][0] = Piece.P;
        currentState[6][1] = Piece.P;
        currentState[6][2] = Piece.P;
        currentState[6][3] = Piece.P;
        currentState[6][4] = Piece.P;
        currentState[6][5] = Piece.P;
        currentState[6][6] = Piece.P;
        currentState[6][7] = Piece.P;
        currentState[7][0] = Piece.R;
        currentState[7][1] = Piece.N;
        currentState[7][2] = Piece.B;
        currentState[7][3] = Piece.Q;
        currentState[7][4] = Piece.K;
        currentState[7][5] = Piece.B;
        currentState[7][6] = Piece.N;
        currentState[7][7] = Piece.R;
    }

    private void setupBlackPieces() {
        currentState[0][0] = Piece.r;
        currentState[0][1] = Piece.n;
        currentState[0][2] = Piece.b;
        currentState[0][3] = Piece.q;
        currentState[0][4] = Piece.k;
        currentState[0][5] = Piece.b;
        currentState[0][6] = Piece.n;
        currentState[0][7] = Piece.r;
        currentState[1][0] = Piece.p;
        currentState[1][1] = Piece.p;
        currentState[1][2] = Piece.p;
        currentState[1][3] = Piece.p;
        currentState[1][4] = Piece.p;
        currentState[1][5] = Piece.p;
        currentState[1][6] = Piece.p;
        currentState[1][7] = Piece.p;
    }

    public Piece getPiece(int rank, int file){
        return currentState[rank][file];
    }

    public void printBoardToConsole() {
        StringBuilder sb = new StringBuilder();
        int rankNum = 8;
        for (Piece[] rank : this.currentState) {
            sb.append(rankNum + " ");
            for(Piece piece : rank) {
                if(piece != null) {
                    sb.append(piece);
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
            rankNum--;
        }
        sb.append("  abcdefgh");
        System.out.println(sb);
    }

    public void setPiece(int toRankIndex, int toFileIndex, Piece fromPiece) {
        currentState[toRankIndex][toFileIndex] = fromPiece;
    }
}
