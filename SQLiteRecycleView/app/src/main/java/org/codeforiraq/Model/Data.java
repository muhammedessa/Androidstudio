package org.codeforiraq.Model;

public class Data {

    private int id;
    private String name;
    private String lname;
    private String timeStamp;
    private String description;
    private String age;



    public Data() {
    }

    public Data(int id, String name, String lname, String timeStamp, String description, String age) {
        this.id = id;
        this.name = name;
        this.lname = lname;
        this.timeStamp = timeStamp;
        this.description = description;
        this.age = age;
    }






    public Data(int id,String name, String lname, String description, String age) {
        this.id = id;
        this.name = name;
        this.lname = lname;
        this.description = description;
        this.age = age;
    }

    public Data( String name, String lname, String description, String age) {

        this.name = name;
        this.lname = lname;
        this.description = description;
        this.age = age;
    }




    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
