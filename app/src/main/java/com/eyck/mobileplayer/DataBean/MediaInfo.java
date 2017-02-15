package com.eyck.mobileplayer.DataBean;

import java.io.Serializable;

/**
 * Created by Eyck on 2017/2/10.
 */

public class MediaInfo implements Serializable{

    String name ;
    long duration ;
    long size ;
    String data ;
    String artist ;

    public MediaInfo() {
        this(null,0,0,null,null);
    }

    public MediaInfo(String name, long duration, long size, String data) {
        this(name,duration,size,data,null);
    }

    public MediaInfo(String name, long duration, long size, String data, String artist) {
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.data = data;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "MediaInfo{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
