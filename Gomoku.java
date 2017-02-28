import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/*
A complete representation of the Gomoku
*/

public class Gomoku {
    public int index;  // indexes how many moves have occurred.  0 to 255, with X on evens
    public int[][][] gamegraph; //a spacetime representation of the Gomoku
    public boolean turn;  // false = X's turn;  true = O's turn;
    public boolean over;
    public Set<Coordinate> blackthreatspace;
    public Set<Coordinate> whitethreatspace;

    
    /* Logic for determining if a move has transpired.  Checks all directions recursively */
    public boolean bIsWin(Coordinate C) {
        System.out.format("From Gomoku %d, %d%n", C.x,C.y);
        int U, D, L, R, RU, LU, RD, LD = 1;
        U =checkU(C,1);
        RU =checkRU(C,1);
        R=checkR(C,1);
        RD=checkRD(C,1);
        D=checkD(C,1);
        LD=checkLD(C,1);
        L=checkL(C,1);
        LU=checkLU(C,1);
        if((U+D)==6) {
            //System.out.println("Up down wins");
            return true;
        }
        if((RU+LD)==6) {
            //System.out.println("Oregon Florida diag wins");
            return true;
        }
        if((R+L)==6) {
            //System.out.println("Leftright wins");
            return true;
        }
        if((RD+LU)==6) {
            //System.out.println("Maine Arizona diag wins");
            return true;
        }
        // System.out.println("U:" +U );
        // System.out.println("RU:"+RU);
        // System.out.println("R:"+R);
        // System.out.println("RD:"+RD);
        // System.out.println("D:"+D);
        // System.out.println("LD:"+LD);
        // System.out.println("L:"+L);
        // System.out.println("LU:"+LU);
        return false;
        
    }
    public int checkU(Coordinate C, int n){
        if(C.y+n>=15) return n;
        if(gamegraph[C.x][C.y+n][index]==gamegraph[C.x][C.y][index]) {
            
            return checkU(C, n+1);
        }
        return n;
    }
    public int checkRU(Coordinate C, int n){
        if(C.x+n>=15 || C.y+n>=15) return n;
        if(gamegraph[C.x+n][C.y+n][index]==gamegraph[C.x][C.y][index]) {
            
            return checkRU(C, n+1);
        }
        
        return n;
    }
    public int checkR(Coordinate C, int n){
        if(C.x+n>=15) return n;
        if(gamegraph[C.x+n][C.y][index]==gamegraph[C.x][C.y][index]) {
            
            return checkR(C, n+1);
        }
        return n;
    }
    public int checkRD(Coordinate C, int n){
        if(C.x+n>=15 || C.y-n<=-1) return n;
        if(gamegraph[C.x+n][C.y-n][index]==gamegraph[C.x][C.y][index]) {
            
            return checkRD(C, n+1);
        }
        return n;
    }
    public int checkD(Coordinate C, int n){
        if(C.y-n<=-1) return n;
        if(gamegraph[C.x][C.y-n][index]==gamegraph[C.x][C.y][index]) {
            
            return checkD(C, n+1);
        }
        return n;
    }
    public int checkLD(Coordinate C, int n){
        if(C.x-n<=-1 || C.y-n<=-1) return n;
        if(gamegraph[C.x-n][C.y-n][index]==gamegraph[C.x][C.y][index]) {

            return checkLD(C, n+1);
        }
        return n;
    }
    public int checkL(Coordinate C, int n){
        if(C.x-n<=-1) return n;
        if(gamegraph[C.x-n][C.y][index]==gamegraph[C.x][C.y][index]) {
            
            return checkL(C, n+1);
        }
        return n;
    }
    public int checkLU(Coordinate C, int n){
        if(C.x-n<=-1 || C.y+n>=15) return n;
        if(gamegraph[C.x-n][C.y+n][index]==gamegraph[C.x][C.y][index]) {
            
            return checkLU(C, n+1);
        }
        return n;
    }
    public int threatcheckLU(Coordinate C, int n, int color){
        if(C.x-n<=-1 || C.y+n>=15) return n;
        if(gamegraph[C.x-n][C.y+n][index]==gamegraph[C.x][C.y][index]) {
            
            return checkLU(C, n+1);
        }        
        if(gamegraph[C.x-n][C.y+n][index]== 0) {
            
            return checkLU(C, n+1);
        }
        return n;
    }

    //update threatspace
    public void updatethreatspace(Coordinate C) {
        //start by setting gamegraph[C.x][C.y] to zero
        //so that we can re-use code
        //int temp = gamegraph[C.x][C.y];
        //gamegraph[C.x][C.y] = 0;


    }


    // Creates a move at coordinate C.
    // Increment index, copy previous level to current level
    // and pass turn to other player
    public void go(Coordinate C){
        if (over == true) {
            return;
        }
        if (index != 0) {

            // Disallow going in an occupied square
            if(gamegraph[C.x][C.y][index-1] !=0) return;

            // Copy level of previous level to current level
            for (int row = 0 ; row < 15; row++)
                for (int col=0;col<15;col++)
                    gamegraph[row][col][index] = gamegraph[row][col][index-1];
        }

            // Fill location with 1 (black) or 2 (white) 
            if (turn==false) gamegraph[C.x][C.y][index] = 1;
            else if(turn==true) gamegraph[C.x][C.y][index] = 2;
            //updatethreatspace();
            if(bIsWin(C)) over = true;
            index++;
            turn=!turn;
    }
        
    //undoes a move by resetting every member at the current index level to zero. updates index and player
    public void undo(){
        if(index == 0) return;
        for(int j=0; j<15;j++) {
            for (int k=0; k<15; k++) {
                gamegraph[j][k][index-1]=0;
            }
        }
        over = false;
        index--;
        turn=!turn;
    }
        
        
    /* serializes state */
    public void save() {
        FileOutputStream out = null;
        
        try {
            out = new FileOutputStream("save.txt");
            PrintWriter pw = new PrintWriter(out);
            pw.println(over);
            pw.println(turn);
            pw.println(index);
            
            for (int i = 0; i < 255; i++) {
                for (int x = 0; x < 15; x++) {
                    for(int y = 0; y < 15; y++) {
                        pw.println(gamegraph[x][y][i]);
                    }
                }
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Could not read from file");
        }
    }
    
    /* recovers state */
    public void load() {
        
        
        FileReader in = null;
        try {
            in = new FileReader("save.txt");
            BufferedReader bfin = new BufferedReader(in);
            over = bfin.readLine() != "0";
            turn = bfin.readLine() != "0";
            index = Integer.parseInt(bfin.readLine());
            for (int i = 0; i < 200; i++) {
                for (int x = 0; x < 15; x++) {
                    for(int y = 0; y < 15; y++) {
                        gamegraph[x][y][i] = Integer.parseInt(bfin.readLine());
                    }
                }
            }
            in.close();
            
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
        
    }
    
    //Construct a Gomoku object where X is playing first
    public Gomoku(){
        index = 0;
        turn = false;
        over = false;
        gamegraph = new int[15][15][255];
        blackthreatspace = new TreeSet<Coordinate>();
        whitethreatspace = new TreeSet<Coordinate>();
    }
        
}