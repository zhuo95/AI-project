import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Npuzzle {
    //game board
    int[][] board;
    //n*n
    int n;

    public Npuzzle(int[][] board){
        this.board =board;
        n = board.length;
    }

    public Npuzzle(){
        //initialize a puzzle
        board = new int[][]{{0, 3, 8}, {4, 1, 7}, {2, 6, 5}};
        n = board.length;


//        int[] random = new int[9];
//        //generate a ordered sequence
//        for(int i=0;i<9;i++){
//           random[i] = i;
//        }
//        //randomly exchange index
//        for(int i=0;i<9;i++){
//            int temp = random[i];
//            int exchangeIndex = (int)(Math.random()*9);
//            random[i] = random[exchangeIndex];
//            random[exchangeIndex] = temp;
//        }
//
//        int index = 0;
//        for(int i=0;i<3;i++){
//            for(int j=0;j<3;j++){
//                board[i][j] = random[index++];
//            }
//        }
    }

    public void search(){
        //set stores the states that have been searched
        HashSet<String> searchedSet = new HashSet<>();

        //priority q, search the game
        PriorityQueue<SearchNode> q = new PriorityQueue<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.score-o2.score;
            }
        });

        SearchNode root = new SearchNode(0,0+heuristic(board),board);
        q.offer(root);
        while(!q.isEmpty()){
            //find the empty position
            SearchNode current = q.poll();
            //store the state into set
            String state = getState(current.board);
            searchedSet.add(state);

            int[] positionOfEmpty = findZero(current.board);
            int i = positionOfEmpty[0],j = positionOfEmpty[1];
            //check four ways that the empty position can move
            //go up
            if(i-1>=0){
                //make a move
                swap(current.board,i,j,i-1,j);
                if(!searchedSet.contains(getState(current.board))) {
                    SearchNode next = new SearchNode(current.searchLevel + 1, current.searchLevel + 1 + heuristic(current.board), current.board,current);
                    if (heuristic(current.board) == 0){
                        swap(current.board,i,j,i-1,j);
                        SearchNode sn = next;
                        while(next.pre!=null){
                            System.out.println(next.searchLevel+":\n");
                            print(next.board);
                            next = next.pre;
                        }
                        break;
                    }
                    //add into q
                    q.offer(next);
                    searchedSet.add(getState(next.board));
                }
                //go back , change back
                swap(current.board,i,j,i-1,j);
            }
            //go left
            if(j-1>=0){
                //make a move
                swap(current.board,i,j,i,j-1);
                if(!searchedSet.contains(getState(current.board))) {
                    SearchNode next = new SearchNode(current.searchLevel + 1, current.searchLevel + 1 + heuristic(current.board), current.board,current);
                    if (heuristic(current.board) == 0){
                        swap(current.board,i,j,i,j-1);
                        SearchNode sn = next;
                        while(next.pre!=null){
                            System.out.println(next.searchLevel+":\n");
                            print(next.board);
                            next = next.pre;
                        }
                        break;
                    }
                    //add into q
                    q.offer(next);
                    searchedSet.add(getState(next.board));
                }
                //go back , change back
                swap(current.board,i,j,i,j-1);
            }
            //go down
            if(i+1<=n-1){
                //make a move
                swap(current.board,i,j,i+1,j);
                if(!searchedSet.contains(getState(current.board))) {
                    SearchNode next = new SearchNode(current.searchLevel + 1, current.searchLevel + 1 + heuristic(current.board), current.board,current);
                    if (heuristic(current.board) == 0){
                        swap(current.board,i,j,i+1,j);
                        SearchNode sn = next;
                        while(next.pre!=null){
                            System.out.println(next.searchLevel+":\n");
                            print(next.board);
                            next = next.pre;
                        }
                        break;
                    }
                    //add into q
                    q.offer(next);
                    searchedSet.add(getState(next.board));
                }
                //go back , change back
                swap(current.board,i,j,i+1,j);
            }
            //go right
            if(j+1<=n-1){
                //make a move
                swap(current.board,i,j,i,j+1);
                if(!searchedSet.contains(getState(current.board))) {
                    SearchNode next = new SearchNode(current.searchLevel + 1, current.searchLevel + 1 + heuristic(current.board), current.board,current);
                    if (heuristic(current.board) == 0){
                        swap(current.board,i,j,i,j+1);
                        SearchNode sn = next;
                        while(next.pre!=null){
                            System.out.println(next.searchLevel+":\n");
                            print(next.board);
                            next = next.pre;
                        }
                        break;
                    }
                    //add into q
                    q.offer(next);
                    searchedSet.add(getState(next.board));
                }
                //go back , change back
                swap(current.board,i,j,i,j+1);
            }
        }
    }

//heuristic function
    private int heuristic(int[][] board){
        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                //compare the right position of the number with its real position
               int num = board[i][j];
               if(num!=0){
                   //get the right position
                   int row = num/n;
                   int colum = num%n;
//            ======================
                   if(num%n==0){
                       row = row - 1;
                       colum = n-1;
                   }else{
                       colum--;
                   }
//            =======================
                   res += Math.abs(i-row) + Math.abs(j-colum);
               }
            }
        }
            return res;
    }

    //find zero on the board, zero represents an empty position
    private int[] findZero(int[][] board){
        int[] res = new int[2];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(board[i][j]==0){
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }

    //swap two position on the board to make a move
    private void swap(int[][] board,int pi,int pj,int ai,int aj){
        int temp = board[pi][pj];
        board[pi][pj] = board[ai][aj];
        board[ai][aj] = temp;
    }

    //store the state of the board to avoid duplicated search
    private String getState(int[][] board){
        String res="";
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                res +=board[i][j]+" ";
            }
        }
        return res;
    }

    // Print the game board
    public void print(int[][] board) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(board[i][j]==0&&j!=n-1){
                    if(board[i][j]<10){
                        System.out.print("   "+" "+"|");
                    }else
                    System.out.print("   "+"|");
                }else if(j!=n-1&&board[i][j]!=0){
                    if(board[i][j]<10){
                        System.out.print(" "+board[i][j]+" " +" "+"|");
                    }else
                    System.out.print(" "+board[i][j] +" "+"|");
                }else if(j==n-1&&board[i][j]==0){
                    System.out.print("  "+"  ");
                }
                else{
                    if(board[i][j]<10){
                        System.out.print(" "+" "+board[i][j]);
                    }else
                    System.out.print(" "+board[i][j]);
                }
            }
            if(i<n-1){
                System.out.print("\n");
                System.out.print("--------------");
                if(n==3){
                    System.out.print("\n");
                }else {
                    if(n==4){
                        System.out.print("----\n");
                    }
                    if(n==5){
                        System.out.print("-----------\n");
                    }
                }
            }
        }
        System.out.println("\n");
    }

    //read file
    public static int[][] readFile(String textName){
        int[][] board;
        int count = 0;
        List<Integer> numOrder = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try{
            fr = new FileReader(textName);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine())!=null){
                String[] num = line.split("\t");
                for(String e : num){
                    numOrder.add(Integer.valueOf(e));
                }
                count++;
            }
        }catch (IOException e){
            System.out.println("Read File Error");
        }
        int num = 0;
        board = new int[count][count];
        for(int i=0;i<count;i++){
            for(int j=0;j<count;j++){
                board[i][j] = numOrder.get(num++);
            }
        }
        return board;
    }

    public static void main(String[] args){
        int[][] board = readFile("n-puzzle.txt");
        //test for 4*4
        int[][] board2 = {{13,7,4,10},{14,0,8,9},{6,12,11,2},{5,3,1,15}};
        //test for 5*5
        int[][] board3 = {{2,7,3,4,5},{1,19,8,9,10},{23,16,21,20,22},{18,6,13,11,15},{12,0,14,17,24}};

        Npuzzle test = new Npuzzle(board);
        test.search();
    }

}

class SearchNode{
    int[][] board;
    //f()
    int searchLevel;
    //socre = f() + h()
    int score;
    //previous state
    SearchNode pre;

    public SearchNode(int level,int score,int[][] board){
        searchLevel = level;
        this.score = score;
        this.board = new int[board.length][board.length];

        for (int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                this.board[i][j] = board[i][j];
            }
        }

    }

    public SearchNode(int level,int score,int[][]board,SearchNode pre){
        this(level,score,board);
        this.pre = pre;
    }
}
