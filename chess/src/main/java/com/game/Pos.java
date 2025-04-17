package com.game;

public final class Pos{
    public final int x, y, i;
    public Pos(int x, int y){
        this.x = x; this.y = y;
        i = 0;
    }

    public Pos(int x, int y, int i){
        this.x = x; this.y = y;
        this.i = i;
    }
}
