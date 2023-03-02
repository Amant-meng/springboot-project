package com.ym.springbootproject.moudle.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author yangmeng
 * @Date 2023/3/2 11:43
 */
@Slf4j
public class KafkaListenerJob implements Runnable {

    @Resource
    private KafkaConsumerListener kafkaConsumerListener;

    public KafkaListenerJob(KafkaConsumerListener kafkaConsumerListener){
        this.kafkaConsumerListener = kafkaConsumerListener;
    }

    @Override
    public void run() {
        log.info("kafka消息监听任务开启...");
        while (true){
            ConsumerRecords<String,String> records = KafkaConfig.kafkaConsumer.poll(5000);
            log.info("开始消费信息......records:{}",records.toString());
            if(records !=null && records.count()>0){
                for (ConsumerRecord<String,String> record:records){
                    try {
                        String jsonString = record.value();
                        log.info("获取到kafka消费信息：{}",jsonString);
                        kafkaConsumerListener.listen(record);
                    }catch (Exception e){
                        log.error("消息消费异常",e.getMessage());
                    }
                }
            }
        }
    }

}
