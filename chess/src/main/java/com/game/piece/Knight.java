package com.game.piece;
import java.util.ArrayList;

import com.game.Board;
import com.game.Helper;
import com.game.Pos;

public class Knight extends Piece{
    public Knight(boolean color){
        super(color);
        if(color)
        img = Helper.loadImage("pieces/white_knight.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_knight.png", 100, 100);
    }
    public Knight(int x, int y, boolean color, Board board){
        super(x, y, color, board);
        if(color)
        img = Helper.loadImage("pieces/white_knight.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_knight.png", 100, 100);
    }
    public String toString(){
        return "N";
    }

    public boolean move(int dx, int dy, boolean m){
        if(blocked)return false;
        if(dx < 0 || dx > 7 || dy > 7|| dy < 0)return false;
        if(b.board[dx][dy] != null && (b.board[dx][dy].getColor() == color)) return false;
        if(!((Math.abs(dx-x) == 2 && Math.abs(dy-y) == 1)||(Math.abs(dx-x) == 1 && Math.abs(dy-y) == 2)))return false;
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
        int[] ddx = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] ddy = {2, 1, -1, -2, -2, -1, 1, 2};
        for(int i = 0; i < 8; i++){
            if(move(x+ddx[i], y+ddy[i], false)&&b.ok(x, y, x+ddx[i], y+ddy[i], color))r.add(new Pos(x+ddx[i], y+ddy[i]));
        }
        return r;
    }
}
