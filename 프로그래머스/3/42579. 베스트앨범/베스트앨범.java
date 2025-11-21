import java.util.*;

class SongStat {
    private final int key;
    private final int playCount;

    public SongStat(int key, int playCount) {
        this.key = key;
        this.playCount = playCount;
    }

    public int key() {
        return key;
    }

    public int playCount() {
        return playCount;
    }

    public boolean isHigherPriorityThan(SongStat other) {
        if (this.playCount != other.playCount) {
            return this.playCount > other.playCount;
        }
        return this.key < other.key;
    }
}

class GenreStat {
    private SongStat bestSong;
    private SongStat secondBestSong;
    private int playCountSum;

    private GenreStat(SongStat bestSong) {
        this.bestSong = bestSong;
        this.secondBestSong = null;
        this.playCountSum = bestSong.playCount();
    }

    public static GenreStat initiate(SongStat firstSong) {
        return new GenreStat(firstSong);
    }

    public void addSong(SongStat newSong) {
        playCountSum += newSong.playCount();

        if (newSong.isHigherPriorityThan(bestSong)) {
            secondBestSong = bestSong;
            bestSong = newSong;
        } else if (secondBestSong == null || newSong.isHigherPriorityThan(secondBestSong)) {
            secondBestSong = newSong;
        }
    }

    public int totalPlays() {
        return playCountSum;
    }

    public List<Integer> topSongKeys() {
        List<Integer> keys = new ArrayList<>(2);
        keys.add(bestSong.key());
        if (secondBestSong != null) {
            keys.add(secondBestSong.key());
        }
        return keys;
    }
}

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        Map<String, GenreStat> genreStats = new HashMap<>();

        for (int i = 0; i < genres.length; i++) {
            String genre = genres[i];
            SongStat song = new SongStat(i, plays[i]);

            genreStats.compute(genre, (g, stat) -> {
                if (stat == null) {
                    return GenreStat.initiate(song);
                }
                stat.addSong(song);
                return stat;
            });
        }

        List<Map.Entry<String, GenreStat>> sortedGenres =
                new ArrayList<>(genreStats.entrySet());

        sortedGenres.sort(
                Comparator
                        .comparingInt((Map.Entry<String, GenreStat> e) -> e.getValue().totalPlays())
                        .reversed()
        );

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<String, GenreStat> entry : sortedGenres) {
            result.addAll(entry.getValue().topSongKeys());
        }

        int[] answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }
}
