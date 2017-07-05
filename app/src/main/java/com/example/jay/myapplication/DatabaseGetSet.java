package com.example.jay.myapplication;

public class DatabaseGetSet {

    //型號
    private String type;
    //型號名稱
    private String typeName;
    //相片檔名
    private String imageName;
    //相片Url
    private String pathUrl;
    //縮圖Url
    private String thumbPathUrl;

    public DatabaseGetSet() {

    }

    public DatabaseGetSet(String type, String typeName, String imageName, String pathUrl, String thumbPathUrl) {
        //this(type, typeName, imageName, pathUrl);
        this.type = type;
        this.typeName = typeName;
        this.imageName = imageName;
        this.pathUrl = pathUrl;
        this.thumbPathUrl = thumbPathUrl;
    }

//    public DatabaseGetSet(String type, String typeName, String imageName, String pathUrl) {
//        this(type, typeName, imageName);
//        this.pathUrl = pathUrl;
//    }
//
//    public DatabaseGetSet(String type, String typeName, String imageName) {
//        this(typeName,"");
//        this.type = type;
//        this.imageName = imageName;
//        this.thumbPathUrl = "";
//    }
//
//    public DatabaseGetSet(String typeName, String pathUrl) {
//        this.type = "";
//        this.typeName = typeName;
//        this.imageName = "";
//        this.pathUrl = pathUrl;
//        this.thumbPathUrl = "";
//    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public void setThumbPathUrl(String thumbPathUrl) {
        this.thumbPathUrl = thumbPathUrl;
    }


    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public String getThumbPathUrl() {
        return thumbPathUrl;
    }

}
