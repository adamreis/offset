package offset.ourGroup;

import java.util.*;

import offset.sim.Pair;
import offset.sim.Point;
import offset.sim.movePair;

public class Player extends offset.sim.Player {
	int size = 32;
	
	boolean initialized;
	Point[] grid;
	
	
	public Player(Pair prin, int idin) {
		super(prin, idin);
		// TODO Auto-generated constructor stub
		
		this.initialized = false;
	}

	public void init() {
		
	}

	public movePair move(Point[] grid, Pair pr, Pair pr0, ArrayList<ArrayList> history) {
		if (!this.initialized) {
			this.initialized = true;
			// I assume we'll have to do some stuff here the first time
		}
		this.grid = grid;
		
		ArrayList<movePair> possibleMoves = possibleMoves(grid, pr);
		System.out.println("there are " + possibleMoves.size() + " possible moves rn");

		// Everything else here is recycled from dumbPlayer
		movePair movepr = new movePair();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int i_pr=0; i_pr<size; i_pr++) {
				for (int j_pr=0; j_pr <size; j_pr++) {
					movepr.move = false;
					movepr.x = grid[i*size+j];
					movepr.y = grid[i_pr*size+j_pr];
					if (validateMove(movepr, pr)) {
						movepr.move = true;
						return movepr;
					}
				}
				}

			}
		}
		return movepr;
	}

ArrayList<movePair> possibleMoves(Point[] grid, Pair pr) {
	ArrayList<movePair> possible = new ArrayList<movePair>();
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			Point currentPoint = pointAtGridIndex(grid, i, j);
			if (currentPoint.value == 0) {
				continue;
			}
			for (Pair d : directionsForPair(pr)) {
				if (isValidBoardIndex(i + d.x, j + d.y)){
					Point possiblePairing = pointAtGridIndex(grid, i + d.x, j + d.y);
					if (currentPoint.value == possiblePairing.value) {
						possible.add(new movePair(true, currentPoint, possiblePairing));
						possible.add(new movePair(true, possiblePairing, currentPoint));
					}
				}
				
			}
		}
	}
	
	return possible;
}

Pair[] directionsForPair(Pair pr) {
	Pair[] directions = new Pair[4];
	directions[0] = new Pair(pr); 
	directions[1] = new Pair(pr.x, -pr.y);
	directions[2] = new Pair(-pr.x, pr.y);
	directions[3] = new Pair(-pr.x, -pr.y);
	return directions;
}

Point pointAtGridIndex(Point[] grid, int i, int j) {
	return grid[i*size + j];
}

boolean isValidBoardIndex(int i, int j) {
	if (i < 0 || i >= size || j < 0 || j >= size) {
		return false;
	}
	return true;
}

boolean validateMove(movePair movepr, Pair pr) {
    	// This is also from dumbPlayer, and seems like something we won't have to use
    	Point src = movepr.x;
    	Point target = movepr.y;
    	boolean rightposition = false;
    	if (Math.abs(target.x-src.x)==Math.abs(pr.x) && Math.abs(target.y-src.y)==Math.abs(pr.y)) {
    		rightposition = true;
    	}
    	if (Math.abs(target.x-src.x)==Math.abs(pr.y) && Math.abs(target.y-src.y)==Math.abs(pr.x)) {
    		rightposition = true;
    	}
        if (rightposition  && src.value == target.value && src.value >0) {
        	return true;
        }
        else {
        	return false;
        }
    }
}