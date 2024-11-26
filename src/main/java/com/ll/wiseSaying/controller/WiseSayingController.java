package com.ll.wiseSaying.controller;

import com.ll.wiseSaying.entity.WiseSaying;
import com.ll.wiseSaying.service.WiseSayingService;

import java.util.ArrayList;
import java.util.Scanner;

public class WiseSayingController {

    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner) {
        this.wiseSayingService = new WiseSayingService();
        this.scanner = scanner;
    }


    public void actionAdd() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = wiseSayingService.add(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("--------------------------");

        ArrayList<WiseSaying> wiseSayings = wiseSayingService.showList();

        try {
            wiseSayings.reversed().forEach(e -> System.out.println(e.getId() + " / " + e.getContent() + " / " + e.getAuthor()));
        } catch (RuntimeException e) {
            // 목록 출력하는것 때문에 의도적으로 비워둠
        }
    }

    public void actionDelete(String input) {
        int id = Integer.parseInt(input.substring(6));
        boolean tf = wiseSayingService.delete(id);

        if (tf) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }



    }

    public void actionModify(String input) {
        int id = Integer.parseInt(input.substring(6));

        try {
            WiseSaying wiseSaying = wiseSayingService.findById(id);
            if (wiseSaying == null) {
                throw new NullPointerException();
            } else {
                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = scanner.nextLine();

                wiseSayingService.modify(new WiseSaying(id, content, author));
            }

        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
















    }



}

