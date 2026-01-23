import java.util.*;

/*
선물 주고받은 기록을 바탕으로 
선물을 가장 많이 받을 사람이 몇개 받을지

두사람이 선물을 주고받은 기록이 있으면
    이번달까지 두 사람 사이에 더 많은 선물을 준 사람이 많이 받은 사람에게 다음달에 선물을 하나 받음
두사람이 선물을 주고받은 기록이 없거나 둘이 주고받은 수가 같으면
    선물 지수가 큰사람이 작은 사람에게 선물을 하나 받음
    만약 선물 지수도 같으면
        선물을 주고받지 않음

선물 지수란
    이번달까지 자신이 친구들에게 준 선물의 수에서 받은 수를 뺀 값    
    
    

*/

class Friend {
    final String name;
    int sent;
    int received;
    int nextReceived;

    // key: 상대 인덱스, value: 내가 그 친구에게 준 횟수
    final Map<Integer, Integer> givenToCount = new HashMap<>();

    Friend(String name) {
        this.name = name;
    }

    int giftIndex() {
        return sent - received;
    }

    void giveTo(int toIdx) {
        sent++;
        givenToCount.merge(toIdx, 1, Integer::sum);
    }

    void receiveOne() {
        received++;
    }

    int givenTo(int toIdx) {
        return givenToCount.getOrDefault(toIdx, 0);
    }
}

class Solution {
    public int solution(String[] friends, String[] gifts) {
        int n = friends.length;

        // 이름 -> 인덱스
        Map<String, Integer> idx = new HashMap<>();
        // 인덱스 -> Friend
        List<Friend> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            idx.put(friends[i], i);
            list.add(new Friend(friends[i]));
        }

        // gifts 처리
        for (String g : gifts) {
            int sp = g.indexOf(' ');
            String from = g.substring(0, sp);
            String to = g.substring(sp + 1);

            int fromIdx = idx.get(from);
            int toIdx = idx.get(to);

            list.get(fromIdx).giveTo(toIdx);
            list.get(toIdx).receiveOne();
        }

        // 다음 달 수령 계산 (모든 쌍 비교)
        for (int i = 0; i < n; i++) {
            Friend fi = list.get(i);
            for (int j = i + 1; j < n; j++) {
                Friend fj = list.get(j);

                int ij = fi.givenTo(j);
                int ji = fj.givenTo(i);

                if (ij > ji) {
                    fi.nextReceived++;
                } else if (ji > ij) {
                    fj.nextReceived++;
                } else {
                    int gi = fi.giftIndex();
                    int gj = fj.giftIndex();

                    if (gi > gj) fi.nextReceived++;
                    else if (gj > gi) fj.nextReceived++;
                    // 같으면 아무도 받지 않음
                }
            }
        }

        int answer = 0;
        for (Friend f : list) {
            answer = Math.max(answer, f.nextReceived);
        }
        return answer;
    }
}
