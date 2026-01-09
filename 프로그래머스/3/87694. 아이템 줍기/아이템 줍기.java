import java.util.*;

//좌표
class Point {
    final int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
}

//사각형
class Rectangle {
    private final int x1, y1, x2, y2;

    public Rectangle(int[] coords) {
        // 좌표 2배 확대
        this.x1 = coords[0] * 2;
        this.y1 = coords[1] * 2;
        this.x2 = coords[2] * 2;
        this.y2 = coords[3] * 2;
    }

    // 특정 점이 사각형의 내부에 있는지 판정 (테두리 제외)
    public boolean isStrictlyInside(int x, int y) {
        return x > x1 && x < x2 && y > y1 && y < y2;
    }

    // 특정 점이 사각형의 테두리에 있는지 판정
    public boolean isOnBoundary(int x, int y) {
        return (x >= x1 && x <= x2 && y >= y1 && y <= y2) &&
               (x == x1 || x == x2 || y == y1 || y == y2);
    }
}

// 
class Terrain {
    private final List<Rectangle> rectangles = new ArrayList<>();

    public void addRectangle(int[] coords) {
        rectangles.add(new Rectangle(coords));
    }

    public boolean isOnPath(int x, int y) {
        boolean onAnyBoundary = false;

        for (Rectangle rect : rectangles) {
            // 어느 한 사각형이라도 내부에 해당하면 이동 불가 경로
            if (rect.isStrictlyInside(x, y)) return false;
            // 어느 한 사각형의 테두리에라도 걸쳐있으면 후보가 됨
            if (rect.isOnBoundary(x, y)) onAnyBoundary = true;
        }
        return onAnyBoundary;
    }
}

// 4. 실행 및 탐색 클래스
class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        Terrain terrain = new Terrain();
        for (int[] r : rectangle) terrain.addRectangle(r);

        return findShortestPath(terrain, characterX * 2, characterY * 2, itemX * 2, itemY * 2);
    }

    private int findShortestPath(Terrain terrain, int startX, int startY, int targetX, int targetY) {
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        
        // 거리 저장용 맵 (2배 확대 반영하여 102x102)
        int[][] distance = new int[102][102];
        Queue<Point> queue = new ArrayDeque<>();

        queue.add(new Point(startX, startY));
        distance[startX][startY] = 1;

        while (!queue.isEmpty()) {
            Point curr = queue.poll();

            if (curr.x == targetX && curr.y == targetY) {
                return (distance[curr.x][curr.y] - 1) / 2;
            }

            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];

                if (nx >= 0 && nx <= 100 && ny >= 0 && ny <= 100) {
                    if (terrain.isOnPath(nx, ny) && distance[nx][ny] == 0) {
                        distance[nx][ny] = distance[curr.x][curr.y] + 1;
                        queue.add(new Point(nx, ny));
                    }
                }
            }
        }
        return 0;
    }
}