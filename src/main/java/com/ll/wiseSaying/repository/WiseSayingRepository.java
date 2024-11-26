package com.ll.wiseSaying.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ll.wiseSaying.entity.WiseSaying;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Slf4j
public class WiseSayingRepository {

    private final ObjectMapper objectMapper;
    private final ArrayList<WiseSaying> wiseSayings;
    private int lastId;

    public WiseSayingRepository() {
        this.wiseSayings = new ArrayList<>();
        this.lastId = 0;
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }


    public void add(WiseSaying wiseSaying) {
        wiseSaying.setId(++lastId);
        wiseSayings.add(wiseSaying);

        try {
            String json = objectMapper.writeValueAsString(wiseSaying);
            Files.writeString(Paths.get("db/wiseSaying/" + lastId + ".json"), json);
            saveLastId();
        } catch (IOException e) {
            log.info("파일 생성중 오류발생.");
        }
    }

    public void saveLastId() {
        try {
            Files.writeString(Paths.get("db/wiseSaying/lastId.txt"), String.valueOf(lastId));

        } catch (IOException e) {
            log.info("마지막 아이디파일 저장중 오류발생.");
        }
    }

    public ArrayList<WiseSaying> findAll() {
        return wiseSayings;
    }

    public boolean delete(int id) {

        try {
            Files.deleteIfExists(Paths.get("db/wiseSaying/" + id + ".json"));
        } catch (IOException e) {
            log.info("파일 삭제중 오류 발생.");
        }

        try {
            if (wiseSayings.get(id - 1) != null) {
                wiseSayings.set(id - 1, null);
            }

        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    public WiseSaying findById(int id) {
        return wiseSayings.get(id-1);
    }

    public void modify(WiseSaying wiseSaying) {
        wiseSayings.set(wiseSaying.getId()-1, wiseSaying);
        try {
            String json = objectMapper.writeValueAsString(wiseSaying);
            Files.writeString(Paths.get("db/wiseSaying/" + wiseSaying.getId() + ".json"), json);
        } catch (IOException e) {
            log.info("파일 수정 중 오류발생.");
        }

    }

    public void build() {
        try {
            String json = objectMapper.writeValueAsString(wiseSayings);
            Files.writeString(Paths.get("db/wiseSaying/data.json"), json);
        } catch (IOException e) {
            log.info("data.json파일 빌드중 오류발생.");
        }


    }



}

