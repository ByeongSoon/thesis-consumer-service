package demo.consumerservice.sevice;

import demo.consumerservice.ifs.CrudInterface;
import demo.consumerservice.model.entity.Consumer;
import demo.consumerservice.model.network.ConsumerApiRequest;
import demo.consumerservice.model.network.ConsumerApiResponse;
import demo.consumerservice.model.network.Header;
import demo.consumerservice.model.network.Pagination;
import demo.consumerservice.repository.ConsumerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConsumerApiLogicService implements CrudInterface<ConsumerApiResponse,ConsumerApiRequest> {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Override
    @Transactional
    public Header<ConsumerApiResponse> create(Header<ConsumerApiRequest> request) {

        ConsumerApiRequest body = request.getData();

        Consumer consumer = Consumer.builder()
                .account(body.getAccount())
                .password(body.getPassword())
                .email(body.getEmail())
                .phoneNumber(body.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();

        return Header.OK(response(consumerRepository.save(consumer)));

    }

    @Override
    @Transactional
    public Header<ConsumerApiResponse> read(Long id) {

        return consumerRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Transactional
    public Header login(Header<ConsumerApiRequest> request) {

        ConsumerApiRequest body = request.getData();
        return consumerRepository.findByAccountAndPassword(body.getAccount(), body.getPassword())
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("회원정보 없음"));

    }

    @Override
    @Transactional
    public Header<ConsumerApiResponse> update(Header<ConsumerApiRequest> request) {

        ConsumerApiRequest body = request.getData();

        return consumerRepository.findById(body.getId())
                .map( consumer -> consumer
                        .setAccount(body.getAccount())
                        .setPassword(body.getPassword())
                        .setEmail(body.getEmail())
                        .setPhoneNumber(body.getPhoneNumber())
                        .setUpdatedAt(LocalDateTime.now()))
                .map(consumer -> consumerRepository.save(consumer))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    @Transactional
    public Header delete(Long id) {

        return consumerRepository.findById(id)
                .map(consumer -> {
                    consumerRepository.delete(consumer);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("delete ERROR"));

    }

    @Override
    @Transactional
    public Header<List<ConsumerApiResponse>> search(Pageable pageable) {

        Page<Consumer> consumers = consumerRepository.findAll(pageable);

        List<ConsumerApiResponse> consumerApiResponseList = consumers.stream()
                .map(consumer -> {
                    System.out.println(consumer.toString());
                    return consumer;
                })
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPage(consumers.getTotalPages())
                .totalElements(consumers.getTotalElements())
                .currentPage(consumers.getNumber())
                .currentElements(consumers.getNumberOfElements())
                .build();

        return Header.OK(consumerApiResponseList,pagination);

    }

    public ConsumerApiResponse response(Consumer consumer) {

        return ConsumerApiResponse.builder()
                .id(consumer.getId())
                .account(consumer.getAccount())
                .password(consumer.getPassword())
                .email(consumer.getEmail())
                .phoneNumber(consumer.getPhoneNumber())
                .createdAt(consumer.getCreatedAt())
                .updatedAt(consumer.getUpdatedAt())
                .build();

    }

}
