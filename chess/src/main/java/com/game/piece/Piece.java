package com.game.piece;

import java.util.ArrayList;

import com.game.Board;
import com.game.Pos;

import javafx.scene.image.Image;


public abstract class Piece {
    public boolean blocked = false;
    public Image img = null;
    public boolean color;
    public int x, y;
    protected Board b;
    public Piece(boolean color){
        x = -1; y = -1;
        this.color = color;
    }
    public Piece(int x, int y, boolean color, Board board){
        this.x = x;
        this.y = y;
        this.color = color;
        this.b = board;
    }
    public boolean move(int dx, int dy, boolean m){return false;}
    public boolean getColor(){return color;}
    public void remove(){
        for(int i = 0; i < 16; i++)
        if(b.getPlayer(color).p[i] == this){b.getPlayer(color).p[i] = null; break;}
        b.board[x][y] = null;
    }
    public ArrayList<Pos> getLegalMoves(){
        return null;
    }
}
