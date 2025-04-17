package com.game;

public class Move {
    public final int x, y, dx, dy, i;
    public Move(int x, int y, int dx, int dy, int i){
        this.x = x; this.y = y; this.dx = dx; this.dy = dy; this.i = i;
    }

    public Move(String move){
        move = move.trim();
        int[] c = new int[4];
        if(move.length() == 4){
        for(int i = 0; i < 4; i++){
            if(i%2 == 0 && !"abcdefg".contains(move.charAt(i)+""))break;
            else if(i%2 == 1 && !"12345678".contains(move.charAt(i)+""))break;
            c[i] = (i%2 == 0)?move.charAt(i)-97:move.charAt(i)-49;
        }
        }
        x = c[0]; y = c[1]; dx = c[2]; dy = c[3];
        i = 0;
    }

    public Move(Pos p, Pos dp){
        x = p.x; y = p.y; dx = dp.x; dy = dp.y;
        i = dp.i;
    }
}

