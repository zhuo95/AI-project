
public class EvaluateTest {
	public static void main(String[] args) {
		Evaluate e = new Evaluate();
//		int[][] board = {{0,0,-1,0,0,0,0,0},{0,0,-1,1,0,0,1,0},{0,0,1,-1,1,0,1,0},{0,0,0,0,-1,1,0,0},{0,0,0,0,1,0,1,0},{0,0,0,0,0,0,0,0},{-1,-1,0,0,0,0,0,0},{0,0,-1,-1,0,0,0,0}};
//		System.out.println(e.evaluateGame(1, board, 5));
//		int[][] board1 = {{0,0,-1,0,0,0,0,0},{0,0,-1,1,0,0,1,0},{0,0,1,-1,1,0,1,0},{-1,0,0,0,-1,1,0,0},{0,-1,0,0,1,0,1,0},{0,0,-1,0,0,0,0,0},{-1,-1,0,-1,0,0,0,0},{0,0,-1,-1,-1,0,0,0}};
//		System.out.println(Win.haveAWin(board1, 5));
		//int[][] board1 = {{0,1,-1,1},{0,0,-1,1},{0,0,1,-1},{0,1,0,0}};
		//System.out.println(e.evaluateGame(board1, 3));
		//System.out.println(Win.haveAWin(board1, 3));
		Game game = new Game(13, 5);
		//int[][] board = {{-1,0,0,0,0,0},{0,1,0,-1,1,0},{0,0,1,-1,1,0},{0,0,-1,1,1,0},{0,0,0,0,-1,0},{0,0,0,0,0,0}};
		game.game();
	}
}
