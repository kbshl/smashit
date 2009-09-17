package map;

public class Cloud extends Sprite {

	private double moveTime = 0.01, pastMoveTime = 0;
	private boolean left = false, right = true;
	private int distance = 200, pastDistance = 0;

	public Cloud(int x, int y) {
		super(x, y, new String[] { "obj_cloud.gif" }, false);
	}

	public void act(long delay) {

		pastMoveTime += (delay / 1e9);

		if (pastMoveTime >= moveTime) {
			int i = (int) (pastMoveTime / moveTime);
			pastMoveTime -= i * moveTime;

			for (int k = 0; k < i; ++k) {
				if (left) {
					if (pastDistance < distance) {
						x--;
						pastDistance++;
					} else {
						right = true;
						left = false;
						pastDistance = 0;
					}
				}
				if (right) {
					if (pastDistance < distance) {
						x++;
						pastDistance++;
					} else {
						right = false;
						left = true;
						pastDistance = 0;
					}

				}

			}
		}
	}

}
