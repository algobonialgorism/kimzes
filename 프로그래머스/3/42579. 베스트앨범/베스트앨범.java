import java.util.*;

class GenreStat {
    private int bestIndex;        // 가장 많이 재생된 곡의 인덱스
    private int bestPlayCount;    // 그 곡의 재생 수

    private int secondIndex;      // 두 번째로 많이 재생된 곡의 인덱스 (없으면 -1)
    private int secondPlayCount;  // 그 곡의 재생 수 (없으면 -1)

    private int playCountSum;     // 장르 전체 재생 수 합

    public GenreStat(int index, int playCount) {
        this.bestIndex = index;
        this.bestPlayCount = playCount;

        this.secondIndex = -1;
        this.secondPlayCount = -1;

        this.playCountSum = playCount;
    }

    public void addSong(int index, int playCount) {
        playCountSum += playCount;

        // 1순위 후보 갱신 조건:
        // - 재생 수가 더 크거나
        // - 재생 수가 같고, 인덱스가 더 작은 경우
        if (playCount > bestPlayCount ||
            (playCount == bestPlayCount && index < bestIndex)) {

            // 기존 1순위를 2순위로 내린다.
            secondIndex = bestIndex;
            secondPlayCount = bestPlayCount;

            bestIndex = index;
            bestPlayCount = playCount;
        }
        // 2순위 후보 갱신 조건:
        // - 아직 2순위가 없거나
        // - 재생 수가 더 크거나
        // - 재생 수가 같고, 인덱스가 더 작은 경우
        else if (secondIndex == -1 ||
                 playCount > secondPlayCount ||
                 (playCount == secondPlayCount && index < secondIndex)) {

            secondIndex = index;
            secondPlayCount = playCount;
        }
    }

    public int totalPlays() {
        return playCountSum;
    }

    // 장르에서 뽑힌 상위 곡들의 인덱스를 외부 리스트에 직접 추가
    public void appendTopSongKeysTo(List<Integer> target) {
        target.add(bestIndex);
        if (secondIndex != -1) {
            target.add(secondIndex);
        }
    }
}

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        Map<String, GenreStat> genreStats = new HashMap<>();

        // 1. 장르별 통계(총 재생 수, 상위 1, 2곡) 계산
        for (int i = 0; i < genres.length; i++) {
            String genre = genres[i];
            int playCount = plays[i];

            GenreStat stat = genreStats.get(genre);
            if (stat == null) {
                genreStats.put(genre, new GenreStat(i, playCount));
            } else {
                stat.addSong(i, playCount);
            }
        }

        // 2. 장르들을 총 재생 수 기준으로 내림차순 정렬
        List<String> sortedGenres = new ArrayList<>(genreStats.keySet());
        sortedGenres.sort((g1, g2) -> 
            Integer.compare(
                genreStats.get(g2).totalPlays(),
                genreStats.get(g1).totalPlays()
            )
        );

        // 3. 각 장르에서 상위 2곡씩 인덱스 수집
        List<Integer> result = new ArrayList<>(2 * genreStats.size());
        for (String genre : sortedGenres) {
            genreStats.get(genre).appendTopSongKeysTo(result);
        }

        // 4. List<Integer> -> int[] 변환
        int[] answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }
}
