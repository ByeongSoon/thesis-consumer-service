package demo.consumerservice.controller;

import demo.consumerservice.ifs.CrudInterface;
import demo.consumerservice.model.network.ConsumerApiRequest;
import demo.consumerservice.model.network.ConsumerApiResponse;
import demo.consumerservice.model.network.Header;
import demo.consumerservice.sevice.ConsumerApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerApiController implements CrudInterface<ConsumerApiResponse, ConsumerApiRequest> {

    @Autowired(required = false)
    private ConsumerApiLogicService consumerApiLogicService;

    @Override
    @PostMapping("")
    public Header<ConsumerApiResponse> create(@RequestBody Header<ConsumerApiRequest> request) {
        return consumerApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<ConsumerApiResponse> read(@PathVariable Long id) {
        return consumerApiLogicService.read(id);
    }

    @GetMapping("/login")
    public Header login(@RequestBody Header<ConsumerApiRequest> request) {
        return consumerApiLogicService.login(request);
    }

    @Override
    @PutMapping("")
    public Header<ConsumerApiResponse> update(@RequestBody Header<ConsumerApiRequest> request) {
        return consumerApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return consumerApiLogicService.delete(id);
    }

    @Override
    @GetMapping("")
    public Header<List<ConsumerApiResponse>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        return consumerApiLogicService.search(pageable);
    }

}
