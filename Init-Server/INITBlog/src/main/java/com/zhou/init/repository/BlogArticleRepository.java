package com.zhou.init.repository;

import com.zhou.init.search.BlogArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface BlogArticleRepository extends ElasticsearchRepository<BlogArticle, Integer>{

}
