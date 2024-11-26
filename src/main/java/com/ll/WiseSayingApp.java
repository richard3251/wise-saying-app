package com.ll;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
class Information {

    private int id;
    private String content;
    private String author;

    public Information() {
    }

    Information(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }



    // 객체에서 JSON 파일로 저장
    public void saveToFile() throws IOException {

        Path directoryPath = Paths.get("db/wiseSaying");
        if (Files.notExists(directoryPath)) {
        Files.createDirectories(directoryPath);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // .json파일 이쁘게 저장.

        String jsonContent = objectMapper.writeValueAsString(this);
        Files.writeString(Paths.get("db/wiseSaying/" + id + ".json"), jsonContent);

    }

    // JSON 파일에서 객체로 변환해서 가져오기
    public static Information loadFromFile(int id) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Path path = Paths.get("db/wiseSaying/" + id + ".json");
        return objectMapper.readValue(path.toFile(), Information.class);

    }

}


@Slf4j
public class WiseSayingApp {
    private List<Information> wiseSayings = new ArrayList<>();
    private int id = 0;

    public void run() {
        loadLastId();
        loadRegisterList();

        Scanner scanner = new Scanner(System.in);
        System.out.println("==명언 앱==");

        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();

            if (input.equals("종료")) {
                saveLastId();
                break;
            } else if (input.equals("등록")) {
                register(scanner);
            } else if (input.equals("목록")) {
                registerList();
            } else if (input.startsWith("삭제?id=")) {
                delete(input, scanner);
            } else if (input.startsWith("수정?id=")) {
                update(input, scanner);
            } else if (input.equals("빌드")) {
                build();
            }

        }

    }


    // 등록/파일등록
    private void register(Scanner scanner) {

        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        id++;
        Information info = new Information(id, content, author);
        wiseSayings.add(info);

        try {
            info.saveToFile();
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다.");
        }

        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    // 목록
    private void registerList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------------");
        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            Information info = wiseSayings.get(i);

            if (wiseSayings.get(i) != null) {
                System.out.println(wiseSayings.get(i).getId() + " / " + wiseSayings.get(i).getContent() + " / " + wiseSayings.get(i).getAuthor());
            }

        }
    }

    // 삭제/파일삭제
    private void delete(String input, Scanner scanner) {

        int deleteNum = Integer.parseInt(input.split("=") [1] );

        try {
            if (deleteNum > wiseSayings.size() || deleteNum <= 0) {
                throw new IllegalArgumentException(deleteNum + "번 명언은 존재하지 않습니다.");
            } else if (wiseSayings.get(deleteNum-1) == null){
                throw new IllegalArgumentException(deleteNum + "번 명언은 존재하지 않습니다.");
            } else {
                wiseSayings.set(deleteNum -1, null);
                try {
                    Files.deleteIfExists(Paths.get("db/wiseSaying/" + deleteNum + ".json"));
                    System.out.println(deleteNum + "번 명언이 삭제 되었습니다.");
                } catch (IOException e) {
                    System.out.println("파일 삭제 중 오류가 발생 했습니다.");
                }


            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    // 수정
    private void update(String input, Scanner scanner ) {

        int updateNum = Integer.parseInt(input.split("=") [1]);

        try {
            if (updateNum < 1 || updateNum > wiseSayings.size()) {
                throw new IllegalArgumentException(updateNum + "번 명언은 존재하지 않습니다.");
            } else if (wiseSayings.get(updateNum-1) == null) {
                throw new IllegalArgumentException(updateNum + "번 명언은 존재하지 않습니다.");
            } else {


                System.out.println("명언(기존) : " + wiseSayings.get(updateNum-1).getContent());
                System.out.print("명언 : ");
                wiseSayings.get(updateNum-1).setContent(scanner.nextLine());

                System.out.println("작가(기존) : " + wiseSayings.get(updateNum-1).getAuthor());
                System.out.print("작가 : ");
                wiseSayings.get(updateNum-1).setAuthor(scanner.nextLine());

                Information info = wiseSayings.get(updateNum-1);

                try {
                    info.saveToFile();
                } catch (IOException e) {
                    System.out.println("파일 수정 중 오류가 발생했습니다.");
                }

            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 모두 등록
    private void build() {

        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String jsonContent = objectMapper.writeValueAsString(wiseSayings);
            Files.writeString(Paths.get("db/wiseSaying/data.json"), jsonContent);
        } catch (IOException e) {
            log.error("전체 파일 저장 중 오류가 발생했습니다.");
        } finally {
            log.info("data.json 파일의 내용이 갱신되었습니다.");
        }

    }

    // 마지막id 불러오기
    private void loadLastId() {
        try {
            String idstr = Files.readString(Paths.get("db/wiseSaying/lastId.txt"));
            id = Integer.parseInt(idstr);
        } catch (IOException e) {
            id = 0;
        }

    }

    // 저장된 목록들 불러온뒤 wiseSayings객체에 다시 담기
    private void loadRegisterList() {
        for (int i = 1; i <= id; i++) {
            try {
                Information info = Information.loadFromFile(i);
                wiseSayings.add(info);
            } catch (IOException e) {
                log.error(i + "번 명언을 불러오는 중 오류가 발생했습니다.", e);
            }
        }

    }

    // 종료 전 마지막id 저장
    private void saveLastId() {
        try {
            Files.writeString(Paths.get("db/wiseSaying/lastId.txt"), String.valueOf(id));
        } catch (IOException e) {
            System.out.println("lastId 저장중 오류가 발생했습니다.");
        }

    }






}
