package com.game.piece;
import java.util.ArrayList;

import com.game.Board;
import com.game.Helper;
import com.game.Pos;

public class Bishop extends Piece{
    public Bishop(boolean color){
        super(color);
        if(color)
        img = Helper.loadImage("pieces/white_bishop.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_bishop.png", 100, 100);
    }
    public Bishop(int x, int y, boolean color, Board board){
        super(x, y, color, board);
        if(color)
        img = Helper.loadImage("pieces/white_bishop.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_bishop.png", 100, 100);
    }
    public String toString(){
        return "B";
    }

    public boolean move(int dx, int dy, boolean m){
        if(blocked)return false;
        if(dx < 0 || dx > 7 || dy > 7|| dy < 0)return false;
        if(Math.abs(dx-x) != Math.abs(dy-y))return false;
        if(b.board[dx][dy] != null && (b.board[dx][dy].getColor() == color)) return false;
        
        boolean sf = true;
        for(int i = x, j = y; i != dx && j != dy; i+=(dx-x<0)?-1:1, j+=(dy-y < 0)?-1:1){
            if(sf)sf = false;
            else if(b.board[i][j] != null) return false;
        }

        if(m){
            b.board[x][y] = null;
            if(b.board[dx][dy] != null)b.board[dx][dy].remove();
            b.board[dx][dy] = this;
            x = dx; y = dy;
        }

        return true;
    }

    public ArrayList<Pos> getLegalMoves(){
        ArrayList<Pos> r = new ArrayList<Pos>();
        for(int i = x, j = y; i < 8 && j < 8; i++, j++)if(move(i, j, false)&&b.ok(x, y, i, j, color))r.add(new Pos(i, j));
        for(int i = x, j = y; i > -1 && j < 8; i--, j++)if(move(i, j, false)&&b.ok(x, y, i, j, color))r.add(new Pos(i, j));
        for(int i = x, j = y; i < 8 && j > -1; i++, j--)if(move(i, j, false)&&b.ok(x, y, i, j, color))r.add(new Pos(i, j));
        for(int i = x, j = y; i > -1 && j > -1; i--, j--)if(move(i, j, false)&&b.ok(x, y, i, j, color))r.add(new Pos(i, j));
        return r;   
    }
}