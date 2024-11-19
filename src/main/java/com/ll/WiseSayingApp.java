package com.ll;

import java.util.Scanner;

public class WiseSayingApp {

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==명언 앱==");

        int regis = 0;

        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();

            if (input.equals("종료")) {
                break;
            } else if (input.equals("등록")) {

                System.out.print("명언 : ");
                String wiseSaying = scanner.nextLine();
                System.out.print("작가 : ");
                String author = scanner.nextLine();
                regis ++;
                System.out.println(regis + "번 명언이 등록되었습니다.");
            }



        }

    }





}
