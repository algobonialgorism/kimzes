/*
출발지점-distance
중간에 바위가 있음
바위를 제거함
바위를 제거하고
각 지점 사이의 거리의 최솟값 중 가장 큰 값을 리턴하면됨

이분탐색이니까 전체 distance에서 줄여나가면서 슬라이딩 윈도우하면 될듯?

가장 작은 길이 4개 구하고 그중에 2개빼고
*/
import java.util.*;

class Solution {
    
    public boolean check(int mid, int[] rocks, int distance, int n) {
        int removedRocks = 0;
        int prevPoint = 0; // 시작 지점 (0)

        for (int rock : rocks) {
            if (rock - prevPoint < mid) {
                // 1. 거리가 mid보다 작으면 현재 바위를 제거
                removedRocks++;

                //이미 제거한 바위가 n을 초과했다면 실패
                if (removedRocks > n) return false; 

            } else {
                // 2. 거리가 충분하면 바위를 남겨두고, 기준점을 현재 바위 위치로 옮긴다.
                prevPoint = rock;
            }
        }

        // 3. 마지막 바위와 도착 지점(distance) 사이의 거리도 확인한다.
        if (distance - prevPoint < mid) {
            removedRocks++;
        }

        return removedRocks <= n;
    }

    public int solution(int distance, int[] rocks, int n) {
        Arrays.sort(rocks); // 바위 위치 정렬 필수

        int left = 1;
        int right = distance;
        int answer = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (check(mid, rocks, distance, n)) {
                // 이 거리(mid)가 최소 거리인 상태로 n개 이하 제거가 가능하다면
                answer = mid; 
                left = mid + 1; // 더 큰 최솟값이 있는지 확인
            } else {
                // 바위를 너무 많이 제거해야 한다면 (거리가 너무 멀다면)
                right = mid - 1; // 거리를 좁힘
            }
        }
        return answer;
    }
}