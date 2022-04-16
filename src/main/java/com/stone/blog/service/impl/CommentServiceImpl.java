package com.stone.blog.service.impl;

import com.stone.blog.dao.CommentMapper;
import com.stone.blog.po.Comment;
import com.stone.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.findByBlogId(blogId);//一级评论
        for(Comment comment : comments){ //每个一级评论下的子评论
            List<Comment> sub = listCommentsByParentComment(comment);
            comment.setReplyComments(sub);
        }
        return comments;

    }

    @Override
    public List<Comment> listCommentsByParentComment(Comment parentComment) {//某个评论的子评论
        List<Comment> subComments = new ArrayList<>();
        Queue<Comment> queue = new LinkedList<>();
        queue.offer(parentComment);
        while(!queue.isEmpty()){
            Comment cur = queue.poll();
            List<Comment> curSub = commentMapper.findByParentCommentId(cur.getId());
            for(Comment sub : curSub){
                queue.offer(sub);
            }
            if(!cur.equals(parentComment))subComments.add(cur);
        }
        return subComments;
    }


    @Override
    public List<Comment> listComments() { //list comments on background
        return commentMapper.listComments();
    }

    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    //删除评论及所有的子评论
    @Override
    public void deleteCommentAndChildren(Comment comment) {
        List<Comment> subComment = listCommentsByParentComment(comment);
        for(int i = subComment.size() - 1; i >= 0; i--){ //从后往前
            Comment sub = subComment.get(i);
            commentMapper.deleteByCommentId(sub.getId());
        }
        commentMapper.deleteByCommentId(comment.getId());
    }

    @Override
    public Comment findById(Long id) {
        return commentMapper.findByCommentId(id);
    }




    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

       /* for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }*/
       return;
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        /*tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                recursively(reply);
            }
        }*/
        return;
    }


    /*return eachComment(comments);*/
     /*Comment temp = new Comment();
        BeanUtils.copyProperties(comment, temp);
        List<Comment> replies = temp.();
        for(Comment reply1 : replies) {
            recursively(reply1);
        }
        temp.setReplyComments(tempReplys);
        tempReplys = new ArrayList<>();
        return temp.getReplyComments();*/

}
