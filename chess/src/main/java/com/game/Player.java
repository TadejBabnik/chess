package com.game;

import java.util.Scanner;

import com.game.piece.Bishop;
import com.game.piece.King;
import com.game.piece.Knight;
import com.game.piece.Piece;
import com.game.piece.Queen;
import com.game.piece.Rook;
public abstract class Player {
    public int c = 0;
    public boolean color;
    protected Board b;
    public Piece[] p = new Piece[16];
    public Player(boolean color, Board b){
        this.color = color;
        this.b = b;
        int c = 0;
        for(int i = 0; i < 64; i++){
            if(b.board[(int)(i/8)][i%8] != null && b.board[(int)(i/8)][i%8].getColor() == color){p[c++] = b.board[(int)(i/8)][i%8];
            if(p[c-1] instanceof King){
                Piece t = p[0];
                p[0] = p[c-1];
                p[c-1] = t;
            }
            }
        }
    }

    public boolean move(){
        return false;
    }

    public boolean move(int x, int y, int dx, int dy){
        return false;
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

class HumanPlayerSc extends Player{
    private Scanner sc;
    public HumanPlayerSc(boolean color, Board b, Scanner sc){
        super(color, b);
        this.sc = sc;
    }

    public boolean move(){
       String t = sc.nextLine();
        if(t.length() != 4) return false;
        int[] c = new int[4];
        for(int i = 0; i < 4; i++){
            if(i%2 == 0 && !"abcdefg".contains(t.charAt(i)+""))return false;
            else if(i%2 == 1 && !"12345678".contains(t.charAt(i)+""))return false;
            c[i] = (i%2 == 0)?t.charAt(i)-97:t.charAt(i)-49;
        }
        return b.move(c[0], c[1], c[2], c[3], color);
    }

    public Piece promotion(int x, int y, int i){
        b.board[x][y].remove();
        return null;

    }
}

