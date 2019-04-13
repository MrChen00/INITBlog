package com.zhou.elasticsearch.repository;

import com.zhou.elasticsearch.pojo.UserAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author: ZHOU
 * @Date: 2019/4/11 15:56
 */
public interface UserAccountRepository extends ElasticsearchRepository<UserAccount, Integer> {

    /**
     * 查询用户名并按粉丝排序
     * @param name
     * @return
     */
    List<UserAccount> findByNicknameOrderByLikesDesc(String name);

}
