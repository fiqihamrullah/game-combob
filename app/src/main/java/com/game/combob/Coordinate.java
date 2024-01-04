package com.game.combob;

public class Coordinate {

    public int i = -1;
    public int j = -1;

    public Coordinate() {}

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void delete() {
        i = -1;
        j = -1;
    }

    public boolean isNotEmpty() {
        return i != -1 && j != -1;
    }

    public boolean isEmpty() {
        return i == -1 || j == -1;
    }

}