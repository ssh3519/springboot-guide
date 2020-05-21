package com.ssh.scheduletask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class ScheduleTaskApplicationTests {
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Test
    void demo1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            return 1;
        }, threadPoolExecutor).thenApply(a -> {
//            int i = 1/0;
            return a + 1;
        }).handle((r,t)->{
            if (t != null){
                return 110;
            }
            return r;
        });
        System.out.println(completableFuture.get());
    }
    //并行
    @Test
    void demo2() {
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> "resultA",threadPoolExecutor);
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> "resultB",threadPoolExecutor);
        cfA.thenAcceptBoth(cfB, (resultA, resultB) -> {
            System.out.println(resultA+resultB);
        });
        cfA.thenCombine(cfB, (resultA, resultB) -> "result A + B");
        cfA.runAfterBoth(cfB, () -> {});
    }

    //领取多个任务
    @Test
    void demo3() {
        CompletableFuture cfA = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture cfB = CompletableFuture.supplyAsync(() -> 123);
        CompletableFuture cfC = CompletableFuture.supplyAsync(() -> "resultC");
        CompletableFuture<Void> future1 = CompletableFuture.allOf(cfA, cfB, cfC);
        CompletableFuture<Object> future2 = CompletableFuture.anyOf(cfA, cfB, cfC);
        Object join = future2.join();
        System.out.println(join);
    }

    @Test
    void demo4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
             int i = 1/0;
            return "1";
        }, threadPoolExecutor).whenComplete((r, t) -> {
            System.out.println(r);
            System.out.println(t);
        });
        String s = completableFuture.get();
        System.out.println(s);
    }
}
