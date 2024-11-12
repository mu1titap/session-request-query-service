package com.multitab.bookingScheduleQuery.messagequeue;
import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    // 멘토링 생성 완료
    @Bean
    public ConsumerFactory<String, MentoringAddAfterOutDto> mentoringConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092, localhost:39092, localhost:49092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-bookingSchedule-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(MentoringAddAfterOutDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MentoringAddAfterOutDto> mentoringAddAfterDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, MentoringAddAfterOutDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mentoringConsumerFactory());
        return factory;
    }

    // 멘토링 세션 참가 등록 완료
    @Bean
    public ConsumerFactory<String, AfterSessionUserOutDto> sessionUserConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092, localhost:39092, localhost:49092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-bookingSchedule-query-service");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(AfterSessionUserOutDto.class, false));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AfterSessionUserOutDto> afterSessionUserOutDtoListener() {
        ConcurrentKafkaListenerContainerFactory<String, AfterSessionUserOutDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(sessionUserConsumerFactory());
        return factory;
    }

}