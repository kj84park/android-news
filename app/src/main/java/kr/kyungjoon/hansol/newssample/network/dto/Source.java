package kr.kyungjoon.hansol.newssample.network.dto;

import java.io.Serializable;

/**
 * Created by HANSOL on 2018-03-18.
 */

public class Source implements Serializable {

    private final String id;
    private final String name;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
