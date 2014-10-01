package offset.dumb;

import java.util.*;

import offset.sim.Pair;
import offset.sim.Point;
import offset.sim.movePair;

public class Player extends offset.sim.Player {
	
	int size =32;

	public Player(Pair prin, int idin) {
		super(prin, idin);
		// TODO Auto-generated constructor stub
	}

	public void init() {

	}

	public movePair move(Point[] grid, Pair pr, Pair pr0, ArrayList<ArrayList> history) {
		//System.out.println(history.size());
		movePair movepr = new movePair();
			
		ArrayList<movePair> possible = possibleMoves(grid, pr);
		if (possible.size() > 0) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(possible.size());
			movepr = possible.get(randomIndex);	
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
					if (isValidBoardIndex(i + d.p, j + d.q)){
						Point possiblePairing = pointAtGridIndex(grid, i + d.p, j + d.q);
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
		directions[1] = new Pair(pr.p, -pr.q);
		directions[2] = new Pair(pr.q, pr.p);
		directions[3] = new Pair(pr.q, -pr.p);
		return directions;
	}	
	
	boolean isValidBoardIndex(int i, int j) {
		if (i < 0 || i >= size || j < 0 || j >= size) {
			return false;
		}
		return true;
	}
	
	Point pointAtGridIndex(Point[] grid, int i, int j) {
		return grid[i*size + j];
	}
	
boolean validateMove(movePair movepr, Pair pr) {
    	
    	Point src = movepr.src;
    	Point target = movepr.target;
    	boolean rightposition = false;
    	if (Math.abs(target.x-src.x)==Math.abs(pr.p) && Math.abs(target.y-src.y)==Math.abs(pr.q)) {
    		rightposition = true;
    	}
    	if (Math.abs(target.x-src.x)==Math.abs(pr.p) && Math.abs(target.y-src.y)==Math.abs(pr.q)) {
    		rightposition = true;
    	}
        if (rightposition && src.value == target.value && src.value >0) {
        	return true;
        }
        else {
        	return false;
        }
    }
}