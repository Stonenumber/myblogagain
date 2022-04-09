package com.stone.blog.dao;

import com.stone.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

public interface BlogMapper{

    List<Blog> findTop(Pageable pageable);

    //blog的title或者是content包含第一个参数 query
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Modifying
    @Query("update Blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by function('date_format', b.updateTime, '%Y') desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

    @Query("select b from Blog b where b.privately = false")
    Page<Blog> findBlogsByPrivately(Pageable pageable);

    void deleteById(Long id);
}
