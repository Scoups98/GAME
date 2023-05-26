package model;


public class ChessPiece {
    // the owner of the chess
    public PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    public int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
