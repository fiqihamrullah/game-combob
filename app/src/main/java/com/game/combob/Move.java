package com.game.combob;


public class Move implements Comparable<Move>
{


    public Coordinate sourceMove = new Coordinate();
    public Coordinate destinationMove = new Coordinate();

    public int occupied = 0;
    public State player = State.EMPTY;

    public Move() {

    }

    public Move(Coordinate sourceMove, Coordinate destinationMove) {
        this.sourceMove = sourceMove;
        this.destinationMove = destinationMove;
    }

    public int compareTo(Move move) {
        return move.occupied - this.occupied;
    }

    public void delete() {
        this.sourceMove.delete();
        this.destinationMove.delete();
    }

    public boolean isEmpty() {
        return this.sourceMove.isEmpty() && this.destinationMove.isEmpty();
    }



}
