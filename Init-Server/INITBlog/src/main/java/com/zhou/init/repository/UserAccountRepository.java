package com.zhou.init.repository;

import com.zhou.init.search.UserAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface UserAccountRepository extends ElasticsearchRepository<UserAccount, Integer> {

    /**
     * 查询用户名并按粉丝排序
     * @param name
     * @return
     */
    List<UserAccount> findByNicknameOrderByLikesDesc(String name);

}
