package com.searchit.animestreams;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.searchit.animestreams.Anime;

import java.util.List;

@Dao
public interface AnimeDao {
    @Query("Select * from anime order by id DESC")
    List<Anime> getAnimeList();
    @Insert
    void insertAnime(Anime anime);
    @Update
    void updateAnime(Anime anime);
    @Delete
    void deleteAnime(Anime anime);
    @Query("DELETE FROM anime WHERE episodeNo = :episodeNo and name= :name")
    void deleteAnimeByNameAndEpisodeNo(String name, String episodeNo);
    @Query("SELECT * FROM anime where name = :name and episodeNo= :episodeNo")
    Anime getAnimeByNameAndEpisodeNo(String name, String episodeNo);

    //recent changes
    @Query("SELECT * FROM anime where name = :name and episodeNo= :episodeNo")
    int checkAnimeByNameAndEpisodeNo(String name, String episodeNo);



}
