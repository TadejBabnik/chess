package com.game.piece;
import java.util.ArrayList;

import com.game.Board;
import com.game.Helper;
import com.game.Pos;

public class Pawn extends Piece{
    
    private int direction;
    private boolean d = true;
    public Pawn(boolean color){
        super(color);
        if(color)this.direction = 1;
        else this.direction = -1;
        if(color)
        img = Helper.loadImage("pieces/white_pawn.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_pawn.png", 100, 100);
    }
    public Pawn(int x, int y, boolean color, Board board){
        super(x, y, color, board);
        if(color)this.direction = 1;
        else this.direction = -1;
        if(color)
        img = Helper.loadImage("pieces/white_pawn.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_pawn.png", 100, 100);
    }
    public boolean move(int dx, int dy, boolean m){
        if(blocked)return false;
        if(dx < 0 || dx > 7 || dy > 7|| dy < 0)return false;
        if((dy - y) != direction)if(!(d && (dy-y) == 2*direction))return false;
        if(Math.abs(dx - x) > 1) return false;
        if(Math.abs(dx - x) == 1){
            if((b.board[dx][dy] != null && (b.board[dx][dy].getColor() == color)))return false;
            else if(!(b.board[dx][dy] != null) && !(b.enpassant != null && b.enpassant[0] == dx && b.enpassant[1] == dy))return false;
        }
        else if(b.board[dx][dy] != null)return false;

        if(dy-y == 2*direction){
            if(dx != x)return false;
            if(b.board[dx][dy-direction] != null)return false;
            if(((dx+1 < 8 && b.board[dx+1][dy] instanceof Pawn) || (dx-1>0 &&b.board[dx-1][dy] instanceof Pawn)) && m){
                b.enpassant = new int[]{dx, dy-direction};
            }
        }

        if(m){
        b.board[x][y] = null;
        if(b.board[dx][dy] != null)b.board[dx][dy].remove();
        b.board[dx][dy] = this;
        y = dy; x = dx;
        if(y == 7 || y == 0) {
            remove();
            b.getPlayer(color).promote(dx, dy);
        }
        d = false;
        if(b.enpassant != null && b.enpassant[0] == dx && b.enpassant[1] == dy){b.board[dx][dy-direction].remove();
            b.enpassant = null;    
        }
        }
        return true;
    }

    public String toString(){
        return "^";
    }

    public ArrayList<Pos> getLegalMoves(){
        ArrayList<Pos> r = new ArrayList<Pos>();

        for(int i = 0; i < 3; i ++){
            if(move(x-1+i, y+direction, false) && b.ok(x-1+i, y, x, y+direction, color))
            if(((y+direction) == 0 || (y+direction) == 7))
            for(int j = 0; j < 5; j++)r.add(new Pos(x-1+i, y+direction, j));
            else
            r.add(new Pos(x-1+i, y+direction, 0));
        }
        if(move(x, y+2*direction, false) && b.ok(x, y, x, y+2*direction, color))r.add(new Pos(x, y+2*direction));
        return r;
    }    
}