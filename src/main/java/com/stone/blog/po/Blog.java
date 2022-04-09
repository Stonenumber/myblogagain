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
    private Type type;
    private String firstPicture;
    private String description;
    private User user;
    private String content;
    private Integer appreciation; //点赞
    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private String tagIds;

    private Date createTime;
    private Date updateTime;

    private boolean shareStatement;
    private boolean commentable;
    private boolean published;
    private boolean recommend;
    private boolean admin;
    private boolean privately;


    public boolean isAdmin() {
        return admin;
    }

    public boolean isPrivately() {
        return privately;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isRecommend() {
        return recommend;
    }


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


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentable +
                ", published=" + published +
                ", recommend=" + recommend +
                ", admin=" + admin +
                ", privately=" + privately +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

