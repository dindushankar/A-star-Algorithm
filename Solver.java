package interview;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Solver {
private int moves = 0;
private SearchNode finalNode;
private Stack<Board> boards;


public Solver(Board initial) {
    if (!initial.isSolvable()) throw new IllegalArgumentException("Unsolvable puzzle");

    // this.initial = initial;
    PriorityQueue<SearchNode> minPQ = new PriorityQueue<SearchNode>(initial.size() + 10);

    Set<Board> previouses = new HashSet<Board>(50);
    Board dequeuedBoard = initial;
    Board previous = null;
    SearchNode dequeuedNode = new SearchNode(initial, 0, null);
    Iterable<Board> boards;

    while (!dequeuedBoard.isGoal()) {
        boards = dequeuedBoard.neighbors();
        
        moves++;

        for (Board board : boards) {
            if (!board.equals(previous) && !previouses.contains(board)) {
                minPQ.add(new SearchNode(board, moves, dequeuedNode));
            }
        }

        previouses.add(previous);
        previous = dequeuedBoard;
        dequeuedNode = minPQ.poll();
        dequeuedBoard = dequeuedNode.current;
        
        for(int i =0; i<3;i++){
        	for(int j=0;j<3;j++)	
        		System.out.print(dequeuedBoard.tileAt(i,j)+" ");
        	System.out.println();
        }
        System.out.println();
    }
    finalNode = dequeuedNode;
    
}

// min number of moves to solve initial board
public int moves() {
    if (boards != null) return boards.size()-1;
    solution();
    return boards.size() - 1;
}

public Iterable<Board> solution() {
    if (boards != null) return boards;
    boards = new Stack<Board>();
    SearchNode pointer = finalNode;
    while (pointer != null) {
        boards.push(pointer.current);
        pointer = pointer.previous;
    }
    return boards;
}

private class SearchNode implements Comparable<SearchNode> {
    private final int priority;
    private final SearchNode previous;
    private final Board current;


    public SearchNode(Board current, int moves, SearchNode previous) {
        this.current = current;
        this.previous = previous;
        this.priority = moves + current.manhattan();
    }

    @Override
    public int compareTo(SearchNode that) {
        int cmp = this.priority - that.priority;
        return Integer.compare(cmp, 0);
    }


}
public static void main(String[] args) {
//    int[][] tiles = {{4, 1, 3},
//            {0, 2, 6},
//            {7, 5, 8}};
	
	int[][] tiles = {{3, 1, 2},
            {4, 7, 5},
            {0, 6, 8}};

	System.out.println("Artificial Intelligence");
	System.out.println("MP1: A* for Sliding Puzzle");
	System.out.println("SEMESTER: ***** ");
	System.out.println("NAME: *****");
	System.out.println("");
	System.out.println("START");
	
//	Scanner sc = null;
//	try {
//		sc = new Scanner(new BufferedReader(new FileReader("mp1input.txt")));
//	} catch (FileNotFoundException e) {
//		
//		e.printStackTrace();
//	}
//    int rows = 3;
//    int columns =3;
//    int [][] tiles = new int[rows][columns];
//    while(sc.hasNextLine()) {
//       for (int i=0; i<tiles.length; i++) {
//          String[] line = sc.nextLine().trim().split(" ");
//          for (int j=0; j<line.length; j++) {
//        	  tiles[i][j] = Integer.parseInt(line[j]);
//          }
//       }
//    }

    double start = System.currentTimeMillis();
    Board board = new Board(tiles);
    Solver solve = new Solver(board);
    System.out.printf("# of moves = %d && # of actual moves %d & time passed %f\n, ", solve.moves(), solve.moves, (System.currentTimeMillis() - start) / 1000);


}
}