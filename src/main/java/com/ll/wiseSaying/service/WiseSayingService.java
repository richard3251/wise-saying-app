package com.ll.wiseSaying.service;

import com.ll.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.repository.WiseSayingRepository;

import java.util.ArrayList;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }


    public WiseSaying add(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(0, content, author);
        wiseSayingRepository.add(wiseSaying);

        return wiseSaying;
    }

    public ArrayList<WiseSaying> showList() {
        return  wiseSayingRepository.findAll();

    }

    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void modify(WiseSaying wiseSaying) {
        wiseSayingRepository.modify(wiseSaying);
    }



}

