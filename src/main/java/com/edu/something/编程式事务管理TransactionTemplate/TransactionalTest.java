package com.edu.something.编程式事务管理TransactionTemplate;

import com.edu.dao.domain.User;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author Tangzhihao
 * @date 2017/11/16
 */

public class TransactionalTest {

    @Resource
    private TransactionTemplate transactionTemplate;
    /*@Resource
    private TransactionAspectSupport transactionAspectSupport;*/

    public User insertUser(){
        User user = new User();
        try {
            transactionTemplate.execute(new TransactionCallback<User>(){

                @Override
                public User doInTransaction(TransactionStatus transactionStatus) {
                    try {
                        String lp = "春哥是个大娘们！";
                    } catch (Exception e) {
                        transactionStatus.setRollbackOnly();//小代码块回滚
                    }
                    return null;
                }
            });
        } catch (TransactionException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//主事务回滚
        }

        return user;
    }

    public User updateUser(User user){
        User user1 = new User();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    int a = 123;
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                }
            }
        });
        return  user1;
    }
}
