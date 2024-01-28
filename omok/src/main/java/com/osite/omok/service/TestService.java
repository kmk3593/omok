package com.osite.omok.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.Thymeleaf;

import com.osite.omok.entity.Board;
import com.osite.omok.entity.UserTable;
import com.osite.omok.mapper.TestMapper;
import com.osite.omok.repository.UserTableRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	@Autowired
	private TestMapper testMapper;
	
	@Autowired
	private UserTableRepository userTableRepository;
	
	String apiKey = "b3d13b51d101291ffb41547e4f562fcf";
    String Url = "https://api.kakaobrain.com/v1/inference/kogpt/generation";
	
	public UserTable testMapper(String username) {
		UserTable userTable = testMapper.testByID(username);
		System.out.println("mapper 테스트");
		System.out.println(userTable);
		return userTable;
	}
	
	public void mapperInsert() {
		
		UserTable user = userTableRepository.findByuserNum(1);
		
		Board board = new Board();
		board.setTitle("제목");
		board.setText("제목");
		board.setWriteDateTime(LocalDateTime.now());
		board.setWriter(user);
		
		testMapper.insertTest(board);
	}
	
	public void mapperSelect() {
		
		UserTable user = userTableRepository.findByuserNum(1);
		
		Board board = new Board();
		board.setTitle("제목");
		board.setText("제목");
		board.setWriteDateTime(LocalDateTime.now());
		board.setWriter(user);
		System.out.println("board : "+ board.toString());
		System.out.println("user : "+ user.toString());
		
		List<Board> board2 = testMapper.selectBoard(board);
//		Board board2 = testMapper.selectBoard(board);
		System.out.println("끝 : "+board2.toString());
	}
	
	
	
//	public void KoGPT() {
//		
//		try {
//			String api = "b3d13b51d101291ffb41547e4f562fcf";
//            // KoGPT API 엔드포인트 URL
//            String apiUrl = "https://api.kakaobrain.com/v1/inference/kogpt/generation"+"/"+api;
//
//            // KoGPT API에 보낼 데이터
//            String requestData = "{\"input\":\"안녕하세요, 대화 내용을 입력하세요.\"}";
//
//            // HTTP POST 요청 보내기
//            URL url = new URL(apiUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setDoOutput(true);
//
//            connection.getOutputStream().write(requestData.getBytes());
//
//            // 응답 읽기
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            StringBuilder response = new StringBuilder();
//
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//
//            // API 응답 출력
//            System.out.println("API 응답: " + response.toString());
//
//            // 연결 종료
//            connection.disconnect();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
	
	public void testGPT() {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // KoGPT API 엔드포인트 URL
            String apiUrl = Url;

            // KoGPT API에 보낼 데이터
            String requestData = "{\"input\":\"안녕하세요, 대화 내용을 입력하세요.\"}";

            // HTTP POST 요청 설정
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", apiKey); // 여기에 발급받은 API 키를 넣어주세요
            httpPost.setEntity(new StringEntity(requestData));

            // HTTP 요청 보내기
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // API 응답 읽기
                org.apache.http.HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String responseString = EntityUtils.toString(entity);
                    System.out.println("API 응답: " + responseString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String practice(String data) throws Exception {
		if (data == "asd") {
			return "qwe";
		} else {
			throw new Exception();
		}
	}
}
