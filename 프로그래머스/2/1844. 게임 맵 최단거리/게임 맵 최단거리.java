import java.util.ArrayDeque;

class Solution {
    public int solution(int[][] maps) {
        final int N = maps.length;
        final int M = maps[0].length;
        final int TARGET = N * M - 1; // 1차원 인덱스 기준 목적지

        //방향 배열 (상하좌우)
        final int[] dr = {-1, 1, 0, 0};
        final int[] dc = {0, 0, -1, 1};

        // ArrayDeque 초기 용량 최적화
        ArrayDeque<Integer> queue = new ArrayDeque<>(N * M);

        // 시작점 설정 (0, 0) -> 1차원 인덱스: 0 * M + 0 = 0
        queue.offer(0);
        maps[0][0] = 1; 

        

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            // 1차원 인덱스를 다시 2차원 좌표로 복원
            int r = curr / M;
            int c = curr % M;
            int dist = maps[r][c];

            // 5. 목적지 도달 시 즉시 반환
            if (curr == TARGET) return dist;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                // 경계선 및 이동 가능 여부 확인
                if (nr >= 0 && nr < N && nc >= 0 && nc < M && maps[nr][nc] == 1) {
                    // 6. 큐에 넣기 전 방문 처리
                    maps[nr][nc] = dist + 1;
                    queue.offer(nr * M + nc);
                }
            }
        }

        // 목적지에 도달하지 못한 경우
        return -1;
    }
}