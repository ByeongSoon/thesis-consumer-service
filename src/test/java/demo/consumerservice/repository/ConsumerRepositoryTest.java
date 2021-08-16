package demo.consumerservice.repository;

import demo.consumerservice.ConsumerServiceApplicationTests;
import demo.consumerservice.model.entity.Consumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class ConsumerRepositoryTest extends ConsumerServiceApplicationTests {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Test
    @Transactional
    public void create() {

        Consumer consumer = Consumer.builder()
                .account("Test01")
                .password("1234")
                .email("Test01@aaa.com")
                .phoneNumber("010-0000-0001")
                .createdAt(LocalDateTime.now())
                .build();

        Consumer newConsumer = consumerRepository.save(consumer);

        Assertions.assertNotNull(newConsumer);

    }

    @Test
    @Transactional
    public void read() {

        Optional<Consumer> consumer = consumerRepository.findById(1003L);

        if (consumer.isPresent()) {
            System.out.println(consumer);
        }

        Assertions.assertNotNull(consumer);
    }

    @Test
    @Transactional
    public void update() {

        Optional<Consumer> consumer = consumerRepository.findById(1003L);

        Assertions.assertNotNull(consumer);

        consumer.ifPresent(selectConsumer -> {
                    selectConsumer.setPassword("4321")
                            .setEmail("Test01@bbb.com")
                            .setPhoneNumber("010-0001-0001")
                            .setUpdatedAt(LocalDateTime.now());

                    consumerRepository.save(selectConsumer);
                });

    }

    @Test
    @Transactional
    public void delete() {

        Optional<Consumer> consumer = consumerRepository.findById(1003L);

        Assertions.assertTrue(consumer.isPresent());

        consumer.ifPresent(selectConsumer -> {
            consumerRepository.delete(selectConsumer);
        });

        Optional<Consumer> deleteConsumer = consumerRepository.findById(1003L);

        if (deleteConsumer.isPresent()) {
            System.out.println("데이터 존재");
        } else {
            System.out.println("데이터 삭제, 데이터 없음");
        }


    }


}
