package com.zhou.init.service.Impl;

import com.zhou.init.dto.PageBean;
import com.zhou.init.mapper.AccAttentionMapper;
import com.zhou.init.mapper.ArtTagMapper;
import com.zhou.init.mapper.BlogArticleMapper;
import com.zhou.init.mapper.UserAccountMapper;
import com.zhou.init.repository.BlogArticleRepository;
import com.zhou.init.repository.UserAccountRepository;
import com.zhou.init.search.BlogArticle;
import com.zhou.init.search.SearchType;
import com.zhou.init.search.UserAccount;
import com.zhou.init.service.SearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 搜索
 * @author ZHOU
 * @create 2019-04-11 22:33
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Autowired
    private ArtTagMapper artTagMapper;

    @Autowired
    private AccAttentionMapper accAttentionMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private BlogArticleRepository blogArticleRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void createIndex(Class indexClass) {
        template.createIndex(indexClass);
    }

    @Override
    public void deleteIndex(Class indexClass) {
        template.deleteIndex(indexClass);
    }

    @Override
    public void addArticle(Integer articleId) {
        com.zhou.init.pojo.BlogArticle article = blogArticleMapper.selectByID(articleId);

        // 封装博客信息
        BlogArticle search = new BlogArticle();
            search.setId(article.getId());
            search.setTitle(article.getTitle());
            search.setContentShort(article.getContentShort());
            search.setCover(article.getCover());
            search.setPageViews(article.getPageViews());
            search.setLikes(article.getLikes());
            search.setSearchType(SearchType.ARTICLE);
            search.setTag(artTagMapper.selectByName(articleId));
            search.setUid(blogArticleMapper.selectById(articleId));
        blogArticleRepository.save(search);
    }

    @Override
    public void addUser(Integer uid) {
        Map map = userAccountMapper.selectById(uid);
        UserAccount userAccount = new UserAccount();
        userAccount.setId(Integer.parseInt(map.get("id").toString()));
        userAccount.setNickname(map.get("nickname").toString());
        userAccount.setHportrait(map.get("hportrait").toString());
        userAccount.setSearchType(SearchType.USER);
        userAccount.setFollow(accAttentionMapper.countFollow(uid));
        userAccount.setLikes(accAttentionMapper.countLike(uid));
        userAccountRepository.save(userAccount);
    }


    @Override
    public PageBean search(String queryName, String type, Integer page, Integer size) {

        page--;

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 结果过滤
        // queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "title"}, null));
        // 添加查询条件
        if(type.equals(SearchType.All.getKey())) {
            queryBuilder.withQuery(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", queryName))
                    .should(QueryBuilders.matchPhraseQuery("contentShort", queryName))
                    .should(QueryBuilders.matchPhraseQuery("tag", queryName)));
        }
        if(type.equals(SearchType.TAG.getKey())){
            queryBuilder.withQuery(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("tag", queryName)));
        }

        // should(QueryBuilders.matchQuery("xm","好的"))//分词后匹配
        //.should(QueryBuilders.matchParaseQuery("addr","钱江路"))//匹配完整词
        //.should(QueryBuilders.termQuery("status",0))//完全匹配
        //.should(QueryBuilders.termsQuery("keyword",string[]))//多关键字匹配
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("likes").order(SortOrder.DESC));

        // 分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        Page<BlogArticle> result = blogArticleRepository.search(queryBuilder.build());
        // 总数量
        long totalElements = result.getTotalElements();
        // 总页数
        int totalPages = result.getTotalPages();
        // 分页数据
        List<BlogArticle> content = result.getContent();

        // 封装分页数据
        PageBean pageBean = new PageBean();
        pageBean.setLists(content);
        pageBean.setPage(++page);
        pageBean.setSize(size);
        pageBean.setTotalElement(totalElements);
        pageBean.setTotalPage(totalPages);
        return pageBean;
    }

    @Override
    public PageBean search(String queryName, Integer page, Integer size) {

        page--;

        List<UserAccount> lists = userAccountRepository.findByNicknameOrderByLikesDesc(queryName);
        for (UserAccount userAccount : lists) {
            System.out.println(userAccount.toString());
        }
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加查询条件

        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhrasePrefixQuery("nickname", queryName)));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("likes").order(SortOrder.DESC));
        // 分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        Page<UserAccount> result = userAccountRepository.search(queryBuilder.build());
        // 总数量
        long totalElements = result.getTotalElements();
        // 总页数
        int totalPages = result.getTotalPages();
        // 分页数据
        List<UserAccount> content = result.getContent();

        // 封装分页数据
        PageBean pageBean = new PageBean();
        pageBean.setLists(content);
        pageBean.setPage(++page);
        pageBean.setSize(size);
        pageBean.setTotalElement(totalElements);
        pageBean.setTotalPage(totalPages);
        return pageBean;
    }
}
