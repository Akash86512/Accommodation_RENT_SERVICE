package com.example.akashkumar.room;

public class SortDistance implements Comparable<SortDistance> {


    int Distance;
   UploadData uploadData;

    public SortDistance(int distance, UploadData uploadData) {
        Distance = distance;
        this.uploadData = uploadData;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }

    public UploadData getUploadData() {
        return uploadData;
    }

    public void setUploadData(UploadData uploadData) {
        this.uploadData = uploadData;
    }

    @Override
    public int compareTo(SortDistance o) {
        if(Distance==o.Distance)
            return 0;
        else if(Distance>o.Distance)
            return 1;
        else
            return -1;
    }


}
