package com.game;
import java.util.ArrayList;

import com.game.piece.Bishop;
import com.game.piece.Knight;
import com.game.piece.Piece;
import com.game.piece.Queen;
import com.game.piece.Rook;

public class Bot_1 extends Player {
    public Bot_1(boolean color, Board b){
        super(color, b);
    }

    public boolean move(){
        ArrayList<Move> lm = new ArrayList<Move>();
        for(int i = 0;i < 16; i++){
            if(p[i] != null){
                Pos t = new Pos(p[i].x, p[i].y);
                for(Pos k: p[i].getLegalMoves()){
                    lm.add(new Move(t, k));
                }
            }
        }
        Move r = lm.get((int)(lm.size()*Math.random()));
        return b.move(r.x, r.y, r.dx, r.dy, color);
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
