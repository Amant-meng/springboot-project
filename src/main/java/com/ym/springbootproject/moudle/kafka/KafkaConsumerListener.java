package com.ym.springbootproject.moudle.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;


/**
 * @Description TODO kafka消费监听
 * @Author yangmeng
 * @Date 2023/3/2 11:43
 */
@Slf4j
@Service
public class KafkaConsumerListener {


    /**
     * 监听kafka消费消息，进行业务处理
     * @param record
     * @throws Exception
     */
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        try {
            String jsonString = record.value();
            log.info("监听到消费信息进行业务处理开始...");
            //具体业务

        }catch (Exception e){
            throw new Exception(this.getClass().getName()+"");
        }
    }
}
