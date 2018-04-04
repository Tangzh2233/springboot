package com.edu.something.mq.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author Tangzhihao
 * @date 2017/10/17
 */

public class MessageListenerImpl implements MessageListenerConcurrently{

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt ext:list) {
                doConsume(ext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public void doConsume(MessageExt ext){
        System.out.println("具体业务逻辑处理");
    }
}
