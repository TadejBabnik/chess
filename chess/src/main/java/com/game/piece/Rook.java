package com.game.piece;
import java.util.ArrayList;

import com.game.Board;
import com.game.Helper;
import com.game.Pos;

public class Rook extends Piece{

    public Rook(boolean color){
        super(color);
        if(color)
        img = Helper.loadImage("pieces/white_rook.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_rook.png", 100, 100);
    }
    public Rook(int x, int y, boolean color, Board board){
        super(x, y, color, board);
        if(color)
        img = Helper.loadImage("pieces/white_rook.png", 100, 100);
        else
        img = Helper.loadImage("pieces/black_rook.png", 100, 100);
    }
    public String toString(){
        return "R";
    }
    public boolean move(int dx, int dy, boolean m){
        if(blocked)return false;
        if(dx < 0 || dx > 7 || dy > 7|| dy < 0)return false;
        if(dx != x && dy != y)return false;
        if(b.board[dx][dy] != null && (b.board[dx][dy].getColor() == color)) return false;
        if(dy == y){
            int tx = x;
            int d = (dx-x<0)?-1:1;
            tx += d;
            while(tx != dx){
                if(b.board[tx][y] != null)return false;
                tx += d;
            }
        }
        else if(dx == x){
            int ty = y;
            int d = (dy-y < 0)?-1:1;
            ty += d;
            while(ty != dy){
                if(b.board[x][ty] != null)return false;
                ty += d;
            }
        }

        if(m){
            b.board[x][y] = null;
            if(b.board[dx][dy] != null)b.board[dx][dy].remove();
            b.board[dx][dy] = this;
            if(x == 7)
            ((King)b.getPlayer(color).p[0]).castleR = false;
            else
            ((King)b.getPlayer(color).p[0]).castleL = false;
            x = dx; y = dy;
            
            
            //write castle for king!!!
        }
        return true;
    }

    public void remove(){
        if(x == 7)
            ((King)b.getPlayer(color).p[0]).castleR = false;
        else if(x == 0)
        ((King)b.getPlayer(color).p[0]).castleL = false;
        for(int i = 0; i < 16; i++)
        if(b.getPlayer(color).p[i] == this){b.getPlayer(color).p[i] = null; break;}
        b.board[x][y] = null;
    }

    public ArrayList<Pos> getLegalMoves(){
        ArrayList<Pos> r = new ArrayList<Pos>();
        for(int i = 0; i < 8; i++)if(move(i, y, false)&&b.ok(x, y, i, y, color))r.add(new Pos(i, y));
        for(int i = 0; i < 8; i++)if(move(x, i, false)&&b.ok(x, y, x, i, color))r.add(new Pos(x, i));
        return r;
    }
}
