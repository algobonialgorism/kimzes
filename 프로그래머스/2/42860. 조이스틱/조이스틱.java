import java.util.*;

class Solution {
    public int solution(String name) {
        int n = name.length();
        int totalAlphabetMove = 0;
        
        // 1. 수정이 필요한 인덱스만 추출
        List<Integer> targetIndices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (name.charAt(i) != 'A') {
                targetIndices.add(i);
                // 상하 이동은 여기서 바로 계산
                totalAlphabetMove += Math.min(name.charAt(i) - 'A', 'Z' - name.charAt(i) + 1);
            }
        }

        // 모든 글자가 'A'인 경우 조기 종료
        if (targetIndices.isEmpty()) return 0;

        // 2. 좌우 이동 최솟값 계산
        int minCursorMove = n - 1; // 기본값: 오른쪽 끝까지 가기
        
        // 인덱스 리스트를 순회하며 각 지점에서 '턴'할 때의 비용 계산
        for (int i = 0; i < targetIndices.size(); i++) {
            int currentPos = targetIndices.get(i);
            
            // 마지막 타겟인 경우, 다음 타겟은 없으므로 계산 생략 혹은 루프 종료
            int nextTargetPos = (i + 1 < targetIndices.size()) ? targetIndices.get(i + 1) : n;
            
            int forward = currentPos;
            int backward = n - nextTargetPos;

            // 왕복 거리 중 짧은 쪽을 먼저 선택하는 공식
            minCursorMove = Math.min(minCursorMove, forward + backward + Math.min(forward, backward));
        }

        return totalAlphabetMove + minCursorMove;
    }
}