package com.game;
import java.util.ArrayList;

public class Bot_2 extends Player {
    Board d = new Board();

    public Bot_2(boolean color, Board b){
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

    private Move getMove(ArrayList<Move> lm){
        Move r = null;
        return r;
    }
}
