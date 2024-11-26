package com.ll;

import com.ll.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingController wiseSayingController;


    public App() {
        scanner = new Scanner(System.in);
        wiseSayingController = new WiseSayingController(scanner);
    }



    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();
            if (input.equals("종료")) {
                System.out.println("프로그램이 종료됩니다.");
                break;
            } else if (input.equals("등록")) {
                wiseSayingController.actionAdd();
            } else if (input.equals(("목록"))) {
                wiseSayingController.actionList();
            } else if (input.startsWith("삭제?id=")) {
                wiseSayingController.actionDelete(input);
            } else if (input.startsWith("수정?id=")) {
                wiseSayingController.actionModify(input);
            }
        }

    }




}
