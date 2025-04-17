package com.game;

import com.game.piece.Bishop;
import com.game.piece.King;
import com.game.piece.Knight;
import com.game.piece.Pawn;
import com.game.piece.Piece;
import com.game.piece.Queen;
import com.game.piece.Rook;

import java.util.ArrayList;
import java.util.HashMap;

public class Board{
    private HashMap<String, Integer> positions = new HashMap<String, Integer>();
    public ArrayList<Move> moves = new ArrayList<Move>();
    volatile public Piece[][] board = null;
    private Player p1;
    private Player p2;
    private int counter = 0;
    public boolean over = false;
    public int[] enpassant = null;
    public String result = null;
    private int lastPawn = 0;

    public Board(){
        board = new Piece[8][8];
        for(int i = 0; i < 8; i++){
            board[i][1] = new Pawn(i, 1, true, this);
        }
        for(int i = 0; i < 8; i++){
            board[i][6] = new Pawn(i, 6, false, this);
        }

        board[0][0] = new Rook(0, 0, true, this);
        board[7][0] = new Rook(7, 0, true, this);
        board[0][7] = new Rook(0, 7, false, this);
        board[7][7] = new Rook(7, 7, false, this);

        board[2][0] = new Bishop(2, 0, true, this);
        board[5][0] = new Bishop(5, 0, true, this);
        board[2][7] = new Bishop(2, 7, false, this);
        board[5][7] = new Bishop(5, 7, false, this);

        board[1][0] = new Knight(1, 0, true, this);
        board[6][0] = new Knight(6, 0, true, this);
        board[1][7] = new Knight(1, 7, false, this);
        board[6][7] = new Knight(6, 7, false, this);
        
        board[3][0] = new Queen(3, 0, true, this);
        board[3][7] = new Queen(3, 7, false, this);

        board[4][0] = new King(4, 0, true, this);
        board[4][7] = new King(4, 7, false, this);
    }

    public void setPlayer(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player getPlayer(boolean p){
        if(p == p1.color)return p1;
        else return p2;
    }

    public Player getCurrentPlayer(){
        if(counter%2 == 0)return p1;
        else return p2;
    }

    public void show(){
        System.out.println();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.print(((board[j][i] == null)?".":board[j][i]) + " ");
            }
            System.out.println();
        }
    }

    public boolean move(int x, int y, int dx, int dy, boolean color){
        if(over) return false;
        if((counter%2==1) == color)return false;
        Player cp = getPlayer(color);
        if(board[x][y] == null)return false;
        if(board[x][y].color != color)return false;
        if(!board[x][y].move(dx, dy, false))return false;
        if(!(board[x][y] instanceof Pawn))enpassant = null;
        else if(!(enpassant != null && dx == enpassant[0] && dy == enpassant[1]))enpassant = null;
        if(board[dx][dy] != null)board[dx][dy].blocked = true;
        int kx = ((King)cp.p[0]).x;
        int ky = ((King)cp.p[0]).y;
        if(board[x][y] instanceof King){
            kx = dx; ky = dy;
        }
        Piece t = board[dx][dy];
        board[dx][dy] = board[x][y];
        board[x][y] = null;
        if(((King)cp.p[0]).check(kx, ky)){
            board[x][y] = board[dx][dy];
            board[dx][dy] = t;
            if(board[dx][dy] != null)board[dx][dy].blocked = false;
            return false;
        }
        board[x][y] = board[dx][dy];
        board[dx][dy] = t;
        if(board[dx][dy] != null)board[dx][dy].blocked = false;
        counter++;
        if (board[x][y] instanceof Pawn || board[dx][dy] != null)
            lastPawn = counter;
        board[x][y].move(dx, dy, true);
        over = checkMate();
        return true;
    }

    public boolean ok(int x, int y, int dx, int dy, boolean color){
        if(dx < 0 || dx > 7 || dy > 7|| dy < 0)return false;
        if(board[dx][dy] != null)board[dx][dy].blocked = true;
        Player cp = getPlayer(color);
        int kx = ((King)cp.p[0]).x;
        int ky = ((King)cp.p[0]).y;
        if(board[x][y] instanceof King){
            kx = dx; ky = dy;
        }
        Piece t = board[dx][dy];
        board[dx][dy] = board[x][y];
        board[x][y] = null;
        if(((King)cp.p[0]).check(kx, ky)){
            board[x][y] = board[dx][dy];
            board[dx][dy] = t;
            if(board[dx][dy] != null)board[dx][dy].blocked = false;
            return false;
        }
        board[x][y] = board[dx][dy];
        board[dx][dy] = t;
        if(board[dx][dy] != null)board[dx][dy].blocked = false;
        return true;
    }

    public boolean checkMate() {
        Player p = getPlayer(counter % 2 == 0);
        Player p2 = getPlayer((counter + 1) % 2 == 0);

        String t = getPosition();
        int n = 0;
        if (positions.containsKey(t)) {
            positions.put(t, (n = positions.get(t) + 1));
        } else {
            positions.put(t, 1);
        }

        if (n >= 3) {
            result = "Draw by repetition!";
            return true;
        }

        if (counter - lastPawn > 100) {
            result = "Draw by 50 move rule!";
            return true;
        }

        int c = 0;
        for (int i = 1; i < 16; i++) {
            if (p.p[i] != null) {
                if (p.p[i] instanceof Queen)
                    c += 9;
                else if (p.p[i] instanceof Pawn)
                    c += 9;
                else if (p.p[i] instanceof Rook)    
                    c += 5;
                else if (p.p[i] instanceof Bishop || p.p[i] instanceof Knight)
                    c += 3;
            }
        }

        int c2 = 0;
        for (int i = 1; i < 16; i++) {
            if (p2.p[i] != null) {
                if (p2.p[i] instanceof Queen)
                    c2 += 9;
                else if (p2.p[i] instanceof Pawn)
                    c2 += 9;
                else if (p2.p[i] instanceof Rook)
                    c2 += 5;
                else if (p2.p[i] instanceof Bishop || p.p[i] instanceof Knight)
                    c2 += 3;
            }
        }

        if (c < 4 && c2 < 4) {
            result = "Draw!";
            return true;
        }

        for (int i = 0; i < 16; i++)
            if (p.p[i] != null)
                if (p.p[i].getLegalMoves().size() != 0)
                    return false;

        if (((King) (p.p[0])).check())
            if (counter % 2 == 0)
                result = "Black won!";
            else
                result = "White won!";
        else
            result = "Draw by stalemate!";

        return true;
    }

    public String getPosition() {
        String r = "";
        for (int i = 0; i < 64; i++) {
            r += (board[(int) (i / 8)][i % 8] != null) ? board[(int) (i / 8)][i % 8] : ".";
        }
        return r;
    }
}