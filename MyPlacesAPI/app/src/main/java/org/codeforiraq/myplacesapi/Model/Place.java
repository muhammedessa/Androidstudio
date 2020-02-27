package org.codeforiraq.myplacesapi.Model;

public class Place {
    private int id;
    private String title,platitude ,plongitude;

    public Place() { }

    public Place(int id, String title, String platitude, String plongitude) {
        this.id = id;
        this.title = title;
        this.platitude = platitude;
        this.plongitude = plongitude;
    }
    public Place(String title, String platitude, String plongitude) {
        this.title = title;
        this.platitude = platitude;
        this.plongitude = plongitude;
    }


    public int getId() {   return id;  }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatitude() {
        return platitude;
    }

    public void setPlatitude(String platitude) {
        this.platitude = platitude;
    }

    public String getPlongitude() {
        return plongitude;
    }

    public void setPlongitude(String plongitude) {
        this.plongitude = plongitude;
    }
}
