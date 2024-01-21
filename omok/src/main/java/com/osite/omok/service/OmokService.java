package com.osite.omok.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import com.osite.omok.dto.OmokRoomDTO;
import com.osite.omok.dto.TransferMessage;
import com.osite.omok.entity.OmokOrder;
import com.osite.omok.entity.OmokSetting;
import com.osite.omok.entity.UserTable;
import com.osite.omok.repository.OmokOrderRepository;
import com.osite.omok.repository.OmokSettingRepository;
import com.osite.omok.repository.UserTableRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OmokService {

	// key : uuid
	// value : OmokRoomDTO
	private Map<String, OmokRoomDTO> omokRooms;
	private final OmokSettingRepository omokSettingRepository;
	private final UserTableRepository userTableRepository;
	private final OmokOrderRepository omokOrderRepository;
	
	// @PostConstruct는 아마도 하나의 고유한 map을 만들기 위해서 사용한것으로 추정
	// 그로인해 방을 매핑하여 사람-방을 연결할 예정으로 추정
	@PostConstruct
	private void init() {
		omokRooms = new LinkedHashMap<>();
	}
	
	// 모든 방 목록을 list로 반환
	public List<OmokSetting> findAbleToEnterOmokRoom() {
//		List<OmokSetting> roomList = omokSettingRepository.findBywhiteStone(null);
		
		// 현재 서버가 돌아가기 시작한 이후부터 생성된 오목 방들의 uuid를 가져오기
		Set<String> roomIDs = omokRooms.keySet();
		
		List<OmokSetting> omokSettings = new ArrayList<OmokSetting>();
		
		for (String roomID : roomIDs) {
			
			OmokSetting omokSetting = omokSettingRepository.findByroomID(roomID);
			if (omokSetting.getWhiteStone() == null) {
				System.out.println("백돌 null 찾음");
				omokSettings.add(omokSetting);
			} else {
				System.out.println("백돌 null 아닌거 찾음");
			}
		}
		
		return omokSettings;
	}
	
	// 처음 방에 입장할때 방을 만들 필요가 있음
	public OmokSetting createRoom(OmokSetting omokSetting
			, String username) {
		// uuid : 중복되지않는 고유한 문자열 생성
		String randomUUID = UUID.randomUUID().toString(); 
		System.out.println(randomUUID);
		
		Optional<UserTable> optionalUser = userTableRepository.findByusername(username);
		UserTable user = optionalUser.get();
		
		// 방을 만든 사람이 흑돌유저가 됩니다.
		omokSetting.setRoomID(randomUUID);
		omokSetting.setBlackStone(user);
		
		OmokRoomDTO omokRoomDTO = OmokRoomDTO.builder()
				.roomID(randomUUID)
				.name(omokSetting.getRoomName())
				.build();
		omokRooms.put(randomUUID, omokRoomDTO);
		
		omokSettingRepository.save(omokSetting);
		return omokSetting;
	}
	
	// uuid로 생성된 문자열을 토대로 생성된 방을 찾아 들어가는 함수
	public OmokRoomDTO findRoomByID(String roomID) {
		System.out.println("오목룸s의 길이");
		System.out.println(omokRooms.size());
		OmokRoomDTO findRoom = omokRooms.get(roomID);
		System.out.println(findRoom.toString());
		return findRoom;
	}

	public String enterOmokPlayer(String roomID, Authentication authentication) {
		// 입장을 허용하면 true
		// 입장을 불허하면 false를 리턴
		Optional<UserTable> optionalUser = userTableRepository.findByusername(authentication.getName());
		UserTable user = optionalUser.get();
		
		OmokSetting omokSetting = omokSettingRepository.findByroomID(roomID);
		
		if (omokSetting.getBlackStone().equals(user)) {
			System.out.println("흑돌유저로 허용");
			return "black";
		} else if (omokSetting.getWhiteStone()==(null)) {
			System.out.println("백돌유저로 허용");
			omokSetting.setWhiteStone(user);
			
			omokSettingRepository.save(omokSetting);
			// 백돌유저를 허용했으니 입장
			return "white";
		} else {
			return null;
		}
		
	}
	
	/**
	 * uuid로 된 방 id를 갖는 데이터를 omoksetting에서 삭제한다 
	 * */
	public void deleteRoomByID(String roomID) {
		// TODO Auto-generated method stub
		omokSettingRepository.deleteByroomID(roomID);
	}

	public void placedStone(TransferMessage transferMessage) {
		OmokOrder order = new OmokOrder();
		
		OmokSetting setting = omokSettingRepository.findByroomID(transferMessage.getRoomID());
		List<OmokOrder> orders = omokOrderRepository.findBygameNum(setting);
		UserTable user = userTableRepository.findByuserNum(transferMessage.getSender());
		String[] line = transferMessage.getMessage().split(",");
		
		
		order.setGameNum(setting);
		order.setUserNum(user);
		
		order.setXLine(Integer.parseInt(line[0]));
		order.setYLine(Integer.parseInt(line[1]));
		order.setProcedure(orders.size()+1);
		
		System.out.println("수순 :"+ order.toString());
		
		omokOrderRepository.save(order);
	}
	
	public OmokRoomDTO quitRoom(WebSocketSession session, CloseStatus status) {

		OmokRoomDTO omokRoom = null;
		String roomID = null;
//		String sessionID = session.getId();
		
//		System.out.println(sessionID);
		
		System.out.println(omokRooms.size());
		
		Set<String> keys = omokRooms.keySet();
		
		// 2중 for문 : session을 가지고 roomDto를 찾아내는 과정
		Loop:
		for (String key : keys) {
			OmokRoomDTO room = omokRooms.get(key);
			
			Set<WebSocketSession> sessions = room.getSessions();
			
			for (WebSocketSession targetSession : sessions) {
				// 같은 session을 찾기 
				if (targetSession.getId().equals(session.getId())) {

					omokRoom = room;
					roomID = room.getRoomID();
					// 2중 반복문을 한번에 종료
					break Loop;
				}
			}
		}
		// 백돌 유저가 입장하기 전이었다면
		// 혼자 두고있던 order삭제, 들어가있던 setting을 삭제
		if (omokRoom.getSessions().size() == 1) {
			OmokSetting targetOmokSetting = omokSettingRepository.findByroomID(roomID);
			
			omokOrderRepository.deleteBygameNum(targetOmokSetting);
			omokSettingRepository.deleteByroomID(roomID);
		}
		
		// 평범하게 사용자가 종료한 상황
		if(status.getReason() == null) {
			// 해당 세션 지우기 + 세션 갯수로 구분?해서 승패 지정?
			omokRoom.getSessions().remove(session);
			// 내가 갖고있는 데이터 : 
			// roomID, session.getPrincipal(), omokRoom
			
			// 남은 세션이 1개인 경우   <--- 여기서 시작하면 됨
			if (omokRoom.getSessions().size() == 1) {
				System.out.println("남은 사용자에게 승리메세지를 보내고 승자패자를 기록하면 됨");
			} else if (omokRoom.getSessions().size() == 0) {
				// 남은 세션이 0개인 경우 // 세션이 하나만 있었던 경우
//				omokSettingRepository  
				System.out.println("남은 사용자가 없고 세션 지웠으니까 끝나는듯?");
			}
			
		} else {
			System.out.println("서버가 중단한 경우");
		}
		
		
		return omokRoom;
	}
	
	public UserTable findEnemyPlayer(String username, String roomID) {
		// TODO Auto-generated method stub
		Optional<UserTable> optionalUserTable = userTableRepository.findByusername(username);
		UserTable user = optionalUserTable.get();
		
		OmokSetting omokSetting = omokSettingRepository.findByroomID(roomID);
		if(omokSetting.getBlackStone().equals(user)) {
			return omokSetting.getWhiteStone();
		} else{
			return omokSetting.getBlackStone();
		}
	}
	
	
	
}