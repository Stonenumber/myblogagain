package com.stone.blog.po;


import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Blog {

    private Long id;
    private String title;
    private String flag;
    private Integer views;
    private String firstPicture;
    private String description;
    private String content;
    private Long appreciation; //点赞
    private String tagIds;
    private long typeId;
    private long userId;

    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private User user;
    private Type type;

    private Date createTime;
    private Date updateTime;

    private boolean shareStatement;
    private boolean commentable;
    private boolean published;
    private boolean recommend;
    private boolean admin;
    private boolean privately;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if(tags.isEmpty()) return tagIds;
        StringBuffer ids = new StringBuffer();
        for(Tag tag : tags){
            ids.append("," + tag.getId());
        }
        return ids.toString().substring(1);
    }

}

