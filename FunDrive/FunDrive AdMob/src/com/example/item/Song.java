package com.example.item;


public class Song
{

    private String artist;
    private String duration;
    private long id;
    private String path;
    private String title;

    public Song(long l, String s, String s1, String s2, String s3)
    {
        id = l;
        title = s;
        artist = s1;
        duration = s2;
        path = s3;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getDuration()
    {
        return duration;
    }

    public long getID()
    {
        return id;
    }

    public String getPath()
    {
        return path;
    }

    public String getTitle()
    {
        return title;
    }
}
