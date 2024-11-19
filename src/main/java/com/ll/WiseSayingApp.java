package com.ll;

import java.util.ArrayList;
import java.util.Scanner;

class WiseSaying {

    int regis;
    String content;
    String author;

    WiseSaying(int regis, String content, String author) {
        this.regis = regis;
        this.content = content;
        this.author = author;
    }




}

public class WiseSayingApp {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<WiseSaying> customer = new ArrayList<>();
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
                WiseSaying wiseObject =  new WiseSaying(regis, wiseSaying, author);
                customer.add(wiseObject);
            } else if (input.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("------------------------");
                for (int i = customer.size() - 1; i >= 0; i--) {
                    WiseSaying saying = customer.get(i);
                    System.out.println(saying.regis + " / " + saying.content + " / " + saying.author);
                }


            }

        }

    }





}
