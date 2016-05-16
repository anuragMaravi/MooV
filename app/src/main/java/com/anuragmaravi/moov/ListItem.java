package com.anuragmaravi.moov;

/**
 * Created by RISHABH on 18-Apr-16.
 */
public class ListItem {
    private String title;
    private String vote_average;
    private String popularity;
    private String poster_path;
    private String movie_id;
    private String release_date;

    //Credits Data
    private String credits_name;
    private String credits_profile_path;
    private String credits_character;
    private String actor_id;
    private String actor_image_paths;

    //

    private String upcoming_movie_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getCredits_name() {
        return credits_name;
    }

    public void setCredits_name(String credits_name) {
        this.credits_name = credits_name;
    }

    public String getCredits_profile_path() {
        return credits_profile_path;
    }

    public void setCredits_profile_path(String credits_profile_path) {
        this.credits_profile_path = credits_profile_path;
    }

    public String getCredits_character() {
        return credits_character;
    }

    public void setCredits_character(String credits_character) {
        this.credits_character = credits_character;
    }

    public String getUpcoming_movie_id() {
        return upcoming_movie_id;
    }

    public void setUpcoming_movie_id(String upcoming_movie_id) {
        this.upcoming_movie_id = upcoming_movie_id;
    }

    public String getActor_id() {
        return actor_id;
    }

    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }

    public String getActor_image_paths() {
        return actor_image_paths;
    }

    public void setActor_image_paths(String actor_image_paths) {
        this.actor_image_paths = actor_image_paths;
    }
}

