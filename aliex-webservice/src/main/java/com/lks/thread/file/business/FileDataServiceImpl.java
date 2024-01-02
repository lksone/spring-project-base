package com.lks.thread.file.business;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.lks.thread.file.FileDataService;
import com.lks.thread.file.domin.VoteRecord;
import com.lks.thread.file.repo.VoteRecordRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/12/29 15:24
 */
@Service
@Slf4j
public class FileDataServiceImpl implements FileDataService {

    private final VoteRecordRepo voteRecordRepo;

    private final EntityManager entityManager;

    private final static String PATH = "C:\\Users\\Administrator\\Desktop\\ss\\";

    @Autowired
    public FileDataServiceImpl(VoteRecordRepo voteRecordRepo, EntityManager entityManager) {
        this.voteRecordRepo = voteRecordRepo;
        this.entityManager = entityManager;
    }

    @Override
    public void excelWrite() {
        Stream<VoteRecord> byAll = voteRecordRepo.findByAll();
        List<VoteRecord> list = new LinkedList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        byAll.forEach(c -> {
            //解除强引用，避免数据过大
            entityManager.detach(c);
            list.add(c);
            if (atomicInteger.get() == 10000) {
                String name = PATH + "aaa" + System.currentTimeMillis() + ".xlsx";
                EasyExcel.write(name, VoteRecord.class).sheet(1).doWrite(list);
            }
            atomicInteger.addAndGet(1);
        });
    }
}
