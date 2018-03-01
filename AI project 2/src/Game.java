import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	private static final int WHITE = -1;
	private static final int BLACK = 1;
	private int boardSize;
	private int[][] board;
	private static final int RECURSIVEDEPTH = 6;
	private int m;
	private Evaluate evaluate = new Evaluate();
	private EvaluatePosition evaluatePosition = new EvaluatePosition();

	public Game(int n, int m) {
		boardSize = n;
		this.m = m;
		board = new int[n][n];
	}
	
	public void setBoardSize (int boardSize) {
		this.boardSize = boardSize;
	}
	
//begin a new game.
	public void game() {
		Status move = new Status(0,0,0);
		//randomly put the first piece
		move.column = boardSize/2-1;
		move.row = boardSize/2-1;
		board[move.row][move.column] = BLACK;
		System.out.println("( " + move.row + " , " + move.column + " )");
		int x = move.row+1,y = move.column+1;
		String first = "http://localhost:3000/post?x="+x+"&&y="+y;
		Get.get(first);
		//count the turn number
		int turn = 0;
		//second player is white, set color to WHITE
		int color = WHITE;
		//if turn number bigger than 8, end the game.
		while(Win.haveAWin(board, m) == 0 && turn<(boardSize*boardSize-1)) {
			//call nextStep to get best next move position.
			if(color == WHITE){
				move = nextStep(color, 0,Integer.MIN_VALUE);
			}else{
				move = nextStep(color, 0,Integer.MAX_VALUE);
			}

			board[move.row][move.column] = color;
			turn++;
			
			//switch player
			color = 0-color;
			System.out.println("( " + move.row + " , " + move.column + " )");
			//System.out.println(Win.haveAWin(board, m));
			String param = "x="+String.valueOf(move.row+1)+"&&"+"y="+String.valueOf(move.column+1);
			String url = "http://localhost:3000/post?"+ param;
			Get.get(url);
		}
		
		//clean the game board
		for(int i = 0; i<boardSize ; i++) {
			for(int j =0; j<boardSize; j++) {
				board[i][j] = 0;
			}
		}
		System.out.println("");
		System.out.println("Board cleaned! ");
	}
	
//******************  MinMax  ******************
	//Calculate the best next move position
	//this is a recursive method.
	private Status nextStep(int color, int depth,int alphaBeta) {

		//store two status, bestMove and bestNextMove
		//in Black player's turn we need to find the max value
		//in White player's turn we need to find the  negative max value
		//1 means black win and -1 means white win.
		//set original bestMove point to their color.(B -1, W 1).

		//初始化
		//Initializing
		Status bestMove;
		Status bestNextMove;
		int nextLevelAlphaBeta;

		if(color == BLACK) {
			bestMove = new Status(0,0,-(int)Math.pow(10, m));
			nextLevelAlphaBeta = Integer.MIN_VALUE;
		}else {
			bestMove = new Status(0,0,(int)Math.pow(10, m));
			nextLevelAlphaBeta = Integer.MAX_VALUE;
		}
		//判断递归终止条件
		//Judge if the game has a win or lose status.
		int winStatus = Win.haveAWin(board, m);
		if(winStatus != 0) {
			bestMove.point = winStatus*Integer.MAX_VALUE;
			return bestMove;
		}
		//Judge if we meet the recursive depth.
		if(depth == RECURSIVEDEPTH) {
			bestMove.point = evaluate.evaluateGame(board, m);
			return bestMove;
		}
		//put piece on the board and iteratively call next(color) to get evaluate score.
		//增加启发式搜索 position1->x 2->y 3->score
		List<Status> positions = new ArrayList<>();
		/*for(int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j] == 0) {
					board[i][j] = color;
					int score = evaluate.evaluateGame(board,m);
					Status s = new Status(i, j, score);
					positions.add(s);
					board[i][j] = 0;
				}
			}
		}*/
		positions = evaluatePosition.evaluatePosition(board,m,color);
		//sort positions 把最大的放前面
		Collections.sort(positions);
		//find the blank point.
		if(color == BLACK) {
			for( Status s :positions) {
				int i = s.row, j = s.column;
				board[i][j] = color;
				int nextColor = 0 - color;
				//recursively call the method to get its score from next board status.
				bestNextMove = nextStep(nextColor, depth + 1, nextLevelAlphaBeta);
				if (bestNextMove.point >=alphaBeta) {
					board[i][j] = 0;
					bestNextMove.row = i;
					bestNextMove.column = j;
					return bestNextMove;
				}
				if (bestMove.point <bestNextMove.point) {
					//set point and position.
					bestMove.point = bestNextMove.point;
					bestMove.row = i;
					bestMove.column = j;
					nextLevelAlphaBeta = bestMove.point;
					//System.out.println("new point: " + bestMove.point);
				}
				//delete the temp piece.
				board[i][j] = 0;
			}
		}
			//if it is white player's turn, we need to find the negative max value.
		if(color == WHITE) {
			for (int p=positions.size()-1;p>=0;p--) {
				int i = positions.get(p).row,j=positions.get(p).column;
				board[i][j] = color;
				int nextColor = 0 - color;
				//recursively call the method to get its score from next board status.
				bestNextMove = nextStep(nextColor, depth + 1, nextLevelAlphaBeta);
				if (bestNextMove.point <= alphaBeta) {
					board[i][j] = 0;
					bestNextMove.row = i;
					bestNextMove.column = j;
					return bestNextMove;
				}
				if (bestMove.point > bestNextMove.point) {
					//set point and position.
					bestMove.point = bestNextMove.point;
					bestMove.row = i;
					bestMove.column = j;
					nextLevelAlphaBeta = bestMove.point;
				}
				//delete the temp piece.
				board[i][j] = 0;
			}
		}


		//end the loop, we found the best move.
		return bestMove;
	}
	
}


