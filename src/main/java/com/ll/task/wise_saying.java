package com.ll.task;

import java.util.Scanner;

public class wise_saying {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==명언 앱==");

        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();
            if (input.equals("종료")) {
                break;
            }
        }

    }


}
