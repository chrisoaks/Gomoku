/*
   A complete representation of the game
*/

public class Game {
	public int index;  // 0 = X's turn  
	public int[][][] gamegraph; //a spacetime representation of the game
	public boolean turn;  // false = X's turn;  true = O's turn;
	public boolean over;
    public boolean bIsWin(Coordinate C) {
        int U, D, L, R, RU, LU, RD, LD = 1;
        U =checkU(C,1);
        RU =checkRU(C,1);
        R=checkR(C,1);
        RD=checkRD(C,1);
        D=checkD(C,1);
        LD=checkLD(C,1);
        L=checkL(C,1);
        LU=checkLU(C,1);
        if(U+D==6) {
            System.out.println("Up down wins");
            return true;
        }

        if(RU+LD==6) {
            System.out.println("Oregon Florida diag wins");
            return true;        	
        }
        if(R+L==6) { 
            System.out.println("Leftright wins");
            return true;
        }
        if(RD+LU==6) { 
            System.out.println("Maine Arizona diag wins");
            return true;
        }
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
        };

        //System.out.println("RU n return:" + n);
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
        };
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
        };
       // System.out.println("LD n return:" + n);
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
        

            return checkR(C, n+1);
        }
        return n;
    }
	//creates a move at coordinate C.  updates the index and passes the turn to next player
	public void go(Coordinate C){
        if (over == true) {
			return;
		}
		if (index != 0) {
			if(gamegraph[C.x][C.y][index-1] !=0) return;
			for (int row = 0 ; row < 15; row++)
				for (int col=0;col<15;col++)
					gamegraph[row][col][index] = gamegraph[row][col][index-1]; 
		}
		if(turn==false) gamegraph[C.x][C.y][index] = 1;
		else if(turn==true) gamegraph[C.x][C.y][index] = 2;
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

		index--;
		turn=!turn;
	}

	//starts a game where X is playing first
	public Game(){
		index = 0;
		turn = false;
		over = false;
		gamegraph = new int[15][15][255];
	}

}