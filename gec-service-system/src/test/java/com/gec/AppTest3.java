package com.gec;

import com.gec.system.ServiceApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApp.class)
public class AppTest3 {

    @Test
   public void exception1(){
       int i  =0;
       int c = 10/i; //除数不能为0
       System.out.println("c = " + c);
   }
}
