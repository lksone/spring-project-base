package com.lks.thread.file.repo;

import com.lks.thread.file.domin.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

/**
 * @author lks
 * @description jpa读取数据库内容
 * @e-mail 1056224715@qq.com
 * @date 2023/12/29 15:29
 */
@Repository
public interface VoteRecordRepo extends JpaRepository<VoteRecord, Long> {

    /**
     * 流式查询
     *
     * @return
     */
    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1000"))
    Stream<VoteRecord> findByAll();
}
