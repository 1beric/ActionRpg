package citiesGame.util;

public enum Direction {

    NORTH(0, 1), SOUTH(0, -1), EAST(1, 0), WEST(-1, 0);

    public int x, z;

    private Direction(int x, int z) {
        this.x = x;
        this.z = z;
    }
}
