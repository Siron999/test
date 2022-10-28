package com.example.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("test")
    public Flux<CustomData> test() {
        List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14));
        System.out.println("Main" + Thread.currentThread().getName());
        Flux.fromIterable(ids).flatMap((x) -> {
            return getData(x);
        }).subscribe();
        System.out.println("Whats up");
        return null;
    }

    private Mono<CustomData> getData(Integer id) {
        return Mono.fromSupplier(() -> {
            System.out.println(Thread.currentThread().getName());
            return new CustomData(id, "Name" + id);
        }).log().subscribeOn(Schedulers.parallel());

    }
}
