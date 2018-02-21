import java.util.Scanner;
import java.util.function.BinaryOperator;

public class TICTACTOE {
    public Best dfs(char[][] board,int turn){
        Best Bestmove=new Best();
        Best reply;
        int situation=checkend(board);
        if(situation!=-1){
            if(situation==0) Bestmove.score=1;
            if(situation==1) Bestmove.score=-1;
            if(situation==2) Bestmove.score=0;
            return Bestmove;
        }
        // pre-set the score of Bestmove
        if(turn==0){
            Bestmove.score=-1;
        }else{
            Bestmove.score=1;
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==0){
                    if(turn==0) board[i][j]='O';
                    else board[i][j]='X';
                    reply=dfs(board,1-turn);
                    board[i][j]=0;     //delete the move
                    // max-min algorithm to get the Bestmove
                    if((turn==0&&reply.score>Bestmove.score)||(turn!=0&&reply.score<Bestmove.score)){
                        Bestmove.p=new int[]{i,j};
                        Bestmove.score=reply.score;
                    }
                }
            }
        }
        return Bestmove;
    }

    // Check if the game ends, if X wins return 1, if O wins return 0 , if draw return 2, if it is not end return -1
    public int checkend(char[][] board){
        for(int i=0;i<3;i++){
            if(board[i][0]==board[i][1]&&board[i][0]==board[i][2]&&board[i][0]=='X') return 1;
            if(board[0][i]==board[1][i]&&board[0][i]==board[2][i]&&board[0][i]=='X') return 1;
            if(board[i][0]==board[i][1]&&board[i][0]==board[i][2]&&board[i][0]=='O') return 0;
            if(board[0][i]==board[1][i]&&board[0][i]==board[2][i]&&board[0][i]=='O') return 0;
        }
        if(board[0][0]==board[1][1]&&board[0][0]==board[2][2]&&board[0][0]=='X') return 1;
        if(board[0][2]==board[1][1]&&board[1][1]==board[2][0]&&board[0][2]=='X') return 1;
        if(board[0][0]==board[1][1]&&board[0][0]==board[2][2]&&board[0][0]=='O') return 0;
        if(board[0][2]==board[1][1]&&board[1][1]==board[2][0]&&board[0][2]=='O') return 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]==0) return -1;
            }
        }
        return 2;
    }

    // Print the game board
    public void print(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if(board[i][j]==0&&j!=2){
                   System.out.print("   "+"|");
               }else if(j!=2){
                   System.out.print(" "+board[i][j] +" "+"|");
               }else{
                   System.out.print(" "+board[i][j]);
               }
            }
            if(i<2){
                System.out.print("\n");
                System.out.println("-----------");
            }
        }
        System.out.println("\n");
    }

    // Get the result when the game ends
    public void getresult(char[][] board,int result){
            if(result==2) System.out.println("DRAW");
            if(result==0) System.out.println("Player1 wins");
            if(result==1) System.out.println("Player2 wins");
    }


    public static void main(String[] args){
        char[][] board=new char[3][3];
        TICTACTOE test=new TICTACTOE();
        int count=0;
        while(true){
//            Scanner scan=new Scanner(System.in);
//            System.out.println("input:");
//            String position=scan.nextLine();
//            String[] p=position.split(",");
//            board[Integer.parseInt(p[0])][Integer.parseInt(p[1])]='X';
//            test.print(board);
//            System.out.print("\n");
            Best player1=test.dfs(board,0);
            board[player1.p[0]][player1.p[1]]='O';
            test.print(board);
            int result=test.checkend(board);
            if(result!=-1) {
                test.getresult(board, result);
                break;
            }
            Best player2=test.dfs(board,1);
            board[player2.p[0]][player2.p[1]]='X';
            test.print(board);
            result=test.checkend(board);
            if(result!=-1) {
                test.getresult(board, result);
                break;
            }

        }

    }
    // To make a best move , stores next move and its score
    public class Best{
        int[] p;
        int score;
        public Best(int[] p,int score){
            this.p=p;
            this.score=score;
        }
        public Best(){
        }
    }
}

