package com.ll;

import java.util.ArrayList;
import java.util.Scanner;

class Information {

    int id;
    String content;
    String author;

    Information(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

}

public class WiseSayingApp {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Information> obj = new ArrayList<>();
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
                obj.add(new Information(regis, wiseSaying, author));
            } else if (input.equals("목록")) {

                System.out.println("번호 / 작가 / 명언");
                System.out.println("------------------------");
                for (int i = obj.size() - 1; i >= 0; i--) {
                    if (obj.get(i) != null) {
                        System.out.println(obj.get(i).id + " / " + obj.get(i).content + " / " + obj.get(i).author);
                    }

                }
            } else if (input.startsWith("삭제?id=")) {

                int deleteNum = Integer.parseInt(input.split("=") [1] );

                try {
                    if (deleteNum > obj.size() || deleteNum <= 0) {
                        throw new IllegalArgumentException(deleteNum + "번 명언은 존재하지 않습니다.");
                    } else if (obj.get(deleteNum-1) == null){
                        throw new IllegalArgumentException(deleteNum + "번 명언은 존재하지 않습니다.");
                    } else {
                        obj.set(deleteNum -1, null);
                        System.out.println(deleteNum + "번 명언이 삭제되었습니다.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            }

        }

    }


}
