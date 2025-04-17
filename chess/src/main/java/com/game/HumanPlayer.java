package com.game;

import com.game.piece.*;

public class HumanPlayer extends Player{
    public HumanPlayer(boolean color, Board b){
        super(color, b);
    }

    public boolean move(int x, int y, int dx, int dy){
        return b.move(x, y, dx, dy, color);
    }

    public void promote(int x, int y){
        int i = 0;
        while(p[i++] != null){}
        i--;
        Piece r = null;
        switch(c){
            case 0: r = new Queen(x, y, color, b); break;
            case 1: r = new Knight(x, y, color, b); break;
            case 2: r = new Rook(x, y, color, b); break;
            case 3: r = new Bishop(x, y, color, b); break;
            default: break;
        }
        p[i] = r;
        b.board[x][y] = r;
    }
}
