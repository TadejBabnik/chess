package com.game.piece;
import java.util.ArrayList;

import com.game.Board;
import com.game.Helper;
import com.game.Pos;

public class King extends Piece{
    boolean castleL = true, castleR = true;
    Rook l, r;
    public King(boolean color){
        super(color);
        if(color)
        img = Helper.loadImage("pieces/white_king.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_king.png", 100, 100);
    }
    public King(int x, int y, boolean color, Board board){
        super(x, y, color, board);
        if(color)
        img = Helper.loadImage("pieces/white_king.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_king.png", 100, 100);
    }
    public boolean move(int dx, int dy, boolean m){
        if(dx < 0 || dx > 7 || dy > 7|| dy < 0)return false;
        if((Math.abs(dx-x) > 1 || Math.abs(dy-y) > 1)&&!((castleL||castleR) && (dy-y) == 0 && Math.abs(x-dx) == 2))return false;
        if(!castleL && dx-x == -2)return false;
        if(!castleR && dx-x == 2)return false;
        if(b.board[dx][dy] != null && (b.board[dx][dy].getColor() == color)) return false;
        if(Math.abs(dx-x) == 2){
            if(check((dx+x)/2,dy))return false;
            if(check(x, y))return false;
            if(b.board[(dx+x)/2][dy] != null || !(b.board[dx+((dx-x)/2)][dy] == null || b.board[dx+((dx-x)/2)][dy] instanceof Rook))return false;
        }
        //if(check(dx, dy))return false;
        if(m){
            b.board[x][y] = null;
            if(b.board[dx][dy] != null)b.board[dx][dy].remove();
            b.board[dx][dy] = this;
            if(dx-x == 2){b.board[5][y] = b.board[7][y]; b.board[5][y].x = 5; b.board[7][y] = null;}
            if(dx-x == -2){b.board[3][y] = b.board[0][y]; b.board[3][y].x = 3; b.board[0][y] = null;}
            y = dy; x = dx;
            castleL = false;
            castleR = false;
        }
        return true;
    }
 
    public boolean check(){return check(x, y);}
    public boolean check(int dx, int dy){
        Piece t = b.board[dx][dy];
        b.board[x][y] = null;
        b.board[dx][dy] = this;
        
        boolean r = false;
        for(int i = 0; i < 16; i++){
            if(b.getPlayer(!color).p[i] != null && b.getPlayer(!color).p[i].move(dx, dy, false))r = true;
            if(r)break;
        }
        b.board[x][y] = this;
        b.board[dx][dy] = t;
        return r;
    }

    public String toString(){
        return "K";
    }

    public ArrayList<Pos> getLegalMoves(){
        ArrayList<Pos> r = new ArrayList<Pos>();
        for(int i = 0; i < 9; i++){
            int tx = x-1+i%3;
            int ty = y-1+(int)(i/3);
            if(move(tx, ty, false) && b.ok(x, y, tx, ty, color))r.add(new Pos(tx, ty));
        }
        if(move(x+2, y, false) && b.ok(x, y, x+2, y, color))r.add(new Pos(x+2, y));
        if(move(x-2, y, false) && b.ok(x, y, x-2, y, color))r.add(new Pos(x-2, y));
        return r;
    }
}