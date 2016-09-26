package com.uninorte.pokemongogo;

/**
 * Created by admin on 26/09/16.
 */
public class Pokemon {
    private String id;
    private String name;
    private String ImgFront;

    public Pokemon(String id, String name, String imgFront) {
        this.id = id;
        this.name = name;
        ImgFront = imgFront;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgFront() {
        return ImgFront;
    }

    public void setImgFront(String imgFront) {
        ImgFront = imgFront;
    }
}
