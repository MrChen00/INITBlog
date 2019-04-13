package com.zhou.elasticsearch.repository;

import com.zhou.elasticsearch.pojo.BlogArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BlogArticleRepository extends ElasticsearchRepository<BlogArticle, Integer>{

    List<BlogArticle> findByTitleOrContentShort(String name, String content);

}
