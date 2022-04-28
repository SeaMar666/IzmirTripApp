package com.example.izmirtripapp.Search;

public class Place {
    private String placeDisc;
    private String placeName;
    private String placeImage;

    public Place(){

    }

    public Place(String placeDisc, String placeName, String placeImage) {
        this.placeDisc = placeDisc;
        this.placeName = placeName;
        this.placeImage = placeImage;
    }

    public String getPlaceDisc() {
        return placeDisc;
    }

    public void setPlaceDisc(String placeDisc) {
        this.placeDisc = placeDisc;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }
}
