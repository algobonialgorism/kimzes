import java.util.Arrays;

class Solution {
    public int solution(int[] citations) {
        // 1. 성능을 위해 원시 타입 배열을 Dual-Pivot Quicksort로 정렬 (O(N log N))
        // 정렬 결과: [0, 1, 3, 5, 6]
        Arrays.sort(citations);
        
        int n = citations.length;
        
        // 2. 오름차순 정렬이므로 앞에서부터 순회
        for (int i = 0; i < n; i++) {
            // h: 현재 논문(i)을 포함하여 인용 횟수가 더 많은 논문의 개수
            int h = n - i;
            
            // citations[i]: 현재 논문의 인용 횟수
            // 현재 논문의 인용 횟수가 남은 논문 수(h)보다 크거나 같다면
            // 이 시점의 h가 가능한 H-Index의 최댓값이 됨.
            // (오름차순이므로 뒤로 갈수록 h는 줄어들고 citations[i]는 커짐 -> 교차점이 정답)
            if (citations[i] >= h) {
                return h;
            }
        }
        
        // 모든 논문의 인용 횟수가 0이거나, h 조건을 만족하는 값이 없을 경우 0 리턴
        return 0;
    }
}