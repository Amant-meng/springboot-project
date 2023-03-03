package com.ym.springbootproject.moudle.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Properties;

/**
 * @Description TODO kafka消费配置
 * @Author yangmeng
 * @Date 2023/3/2 11:19
 */
@Configuration
@Data
@Slf4j
public class KafkaConfig {

    @Value("${kafka.bootstrap-servers}")
    private String kafkaServer;
    @Value("${kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.consumer.topic-name}")
    private String topicName;
    @Value("${kafka.consumer.auto-commit-interval}")
    private String auto_commit_interval;
    @Value("${kafka.consumer.enable-auto-commit}")
    private String enable_auto_commit;
    @Value("${kafka.consumer.auto-offset-reset}")
    private String auto_offset_reset;
    @Value("${kafka.consumer.key-deserializer}")
    private String key_deserializer;
    @Value("${kafka.consumer.value-deserializer}")
    private String value_deserializer;

    @Resource
    private KafkaConsumerListener kafkaConsumerListener;

    public static KafkaConsumer<String,String> kafkaConsumer;

    /**
     * 加载kafka消息配置
     */
    @Bean
    public void loadKafkaConfig(){
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaServer);
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,auto_commit_interval);
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enable_auto_commit);
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,auto_offset_reset);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,key_deserializer);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,value_deserializer);

        kafkaConsumer = new KafkaConsumer<String, String>(prop);
        kafkaConsumer.subscribe(Collections.singletonList(topicName));
        log.info("kafka消息订阅成功！kafka 配置：{}",prop.toString());
        KafkaListenerJob kafkaListenerJob = new KafkaListenerJob(kafkaConsumerListener);
        Thread thread = new Thread(kafkaListenerJob);
        thread.start();

    }

}
