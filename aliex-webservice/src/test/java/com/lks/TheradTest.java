package com.lks;

import com.lks.thread.file.FileDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/8/10 21:47
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WebServiceApplication.class)
public class TheradTest {

   @Autowired
   private FileDataService fileDataService;


   @Test
   public void test(){
      fileDataService.excelWrite();
   }
}
