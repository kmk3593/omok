var webSocket;
var placedStoneSound; 
var enterPlayerSound; 
var clockSecondsSound;
var roomID;
var userNum;
var board;
const MAX_ROW = 15;
const MAX_COL = 15;   
var intersections;
var myStone
var playerNumber;
var countDownLimit = 30;
var countDown = countDownLimit;
let timerOn = setInterval(playTimer, 1000)

// JQuery
$(document).ready(function(){
	
	// jquery를 이용해 id값으로 input태그의 값 가져오기
	roomID = $("#roomID").val();
	userNum = $("#userNum").val();
	board = document.getElementById('stone_board');
	intersections = []; // 필요는 없음
	myStone = $("#stone").val();
	webSocket = new WebSocket("ws://" + location.host + "/ws/omok");
	clockSecondsSound = new Audio("/sound_file/clockSecondsSound.mp3");
	placedStoneSound = new Audio("/sound_file/placedStoneSound.mp3");
	enterPlayerSound = new Audio("/sound_file/enterPlayerSound.mp3");
	
	timer = document.querySelector("#timer");
	
	
	// 돌 생성
	for (let i = 1; i <= MAX_ROW; i++) {
		for (let j = 1; j <= MAX_COL; j++) {
			const intersection = document.createElement('div');
			intersection.classList.add('intersection');
			intersection.id = 'intersection';
			intersection.setAttribute('onmouseout', "this.style.backgroundColor=''")
			intersection.dataset.row = i;
			intersection.dataset.col = j;
			
			var textNode = document.createTextNode("10");
			intersection.appendChild(textNode);
			intersection.setAttribute("style", "color: white;")
			
			board.appendChild(intersection);
			intersections.push(intersection);
		}
	}
		
	// websocket이 처음 열렸을때 실행되는 코드
	webSocket.onopen = () =>{
		enterRoom(webSocket);
	};
	
	// websocket에서 메세지 수신시 실행되는 코드
	webSocket.onmessage = (e) =>{
		/* console.log(typeof e.data);
		console.log(e.data); */
		var transferMessage = JSON.parse(e.data);
		  
		/* console.log("수신메세지 : ", transferMessage); */
		/* console.log(transferMessage.type); */
		/* console.log(transferMessage.message); */
		  
		if(transferMessage.type == "PLACE"){
			placeStone(transferMessage.xLine, transferMessage.yLine);
			var turn = $("#turn").val();
			placedStoneSound.play();
			if(checkWinner(Number(transferMessage.xLine)-1, Number(transferMessage.yLine)-1, turn)){
				if(myStone == $("#turn").val()){
					sendWinMessage();
				}
				gameWin();
			} else{
				turnOver();
				timerReload();
			}
		} else if(transferMessage.type == "ENTER"){
			let msgArea = document.querySelector('.msgArea');
			let newMsg = document.createElement('div');
			newMsg.innerText=transferMessage.message;
			msgArea.append(newMsg);
			enterPlayerSound.play();
			if(playerNumberCheck()== 2){
				clearInterval(timerOn);
				timerOn = setInterval(playTimer, 1000);
			}else{
				clearInterval(timerOn);
			}
		} else if(transferMessage.type == "TALK"){
			let msgArea = document.querySelector('.msgArea');
			let newMsg = document.createElement('div');
			newMsg.innerText=transferMessage.message;
			msgArea.append(newMsg);
		} else if(transferMessage.type == "QUIT"){
			sendWinMessage();
			gameWin();
		} else if(transferMessage.type == "GIVEUP"){
			console.log(transferMessage);
			if(transferMessage.sender == userNum){
				gameLose();
			} else{
				gameWin();
			}
		}
	};
		
	// 돌 위에 마우스를 올렸을때 색 변경 
	$(".intersection").mouseover(function(e){
		/* console.log(e); */
		/* console.log(this); */
		/* console.log(this.dataset.col); */
		/* console.log(this.dataset.col); */
		/* console.log(myStone) */
		
		/* console.log(this.className); */
		// if문을 만든 이유: 처음엔 class 이름이 intersection 이었던 태그의 클래스를
		// 착수하며 placed로 바꿔서 그런지 마우스를 올리면 한번은 색이 변하는것으로 추정됨
		if(this.className == "intersection"){
			this.style.backgroundColor=myStone;
		}
	});
		
	// 돌 착수
	$(".intersection").click(function(e){
		var turn = $("#turn").val();
		var xLine = this.dataset.col;
		var yLine = this.dataset.row;
			
			
		var placeMessage={
			"type" : "PLACE",
			"roomID":roomID,
			"sender":userNum,
			"message":(this.dataset.col).toString()+ ","+ (this.dataset.row).toString(),
			"xLine":xLine,
			"yLine":yLine
		};
			
		if(myStone==turn){
			webSocket.send(JSON.stringify(placeMessage));
		}else{
			// console.log("내 턴이 아니구나!!")
		}
			
	});
	
});

function enterRoom(webSocket){
	var enterMessage = {
		"type" : "ENTER",
		"roomID" : roomID,
		"sender" : userNum
	};
	
	// json 형태로 변형하여 전송
	// 필수는 아닐거로 생각됨  
	webSocket.send(JSON.stringify(enterMessage));
}

// 착수 한 돌의 색깔 바꾸기
function placeStone(xLine, yLine){
	var stones = document.getElementsByClassName('intersection');
	var turn = $("#turn").val();
	for(var i=0;i<stones.length;i++){
		if( Number(stones[i].dataset.col) == xLine && Number(stones[i].dataset.row)==yLine){
		stones[i].style.backgroundColor=$('#turn').val();
		stones[i].setAttribute('onmouseout', "");
		stones[i].setAttribute('id', turn);
		stones[i].setAttribute('class', turn+"_stone");
		break;
		}
	};
	// stones를 반환하면 처음부터 다시 리빌딩 할 여지도 있기에 남겨둠
	return stones;
}

/** input태그 id값이 turn인 태그의 값이 블랙이면 화이트로 값 변경<br>
*	값이 화이트면 블랙으로 변경<br>
* by woobin
*/
function turnOver(){
	var turn = $("#turn").val();
	if(turn=="black"){
		turnInput = document.getElementById("turn");
		turnInput.value = "white";
	} else if(turn=="white"){
		turnInput = document.getElementById("turn");
		turnInput.value = "black";
	}
}

function sendMessage(){
		let content=document.querySelector('.content').value;
		var talkMessage={
			"type" : "TALK",
			"roomID":roomID,
			"sender":userNum,
			"message":content
		};
		
		// 아래와 같이 쓰는 방법도 존재
		// "roomID":[[${room.roomID}]],
		// "sender":[[${userNum}]],
		
		webSocket.send(JSON.stringify(talkMessage));
}

function inputEnterKey(e){	
	if(e.key === "Enter"){
		sendMessage();
	}
}

/** 방금 놓은 돌로 승리했는지 구분하는 함수
 * col == xLine (int)
 * row == yLine (int)
 * turn == 방금 놓은 돌의 색깔 (string)
 * by Woobin
 */
function checkWinner(xLine, yLine, turn){
	var board = convertDoubleList(MAX_COL, MAX_ROW, intersections);
	
	// 가로, 세로, 우상향, 우하향 방향 순으로 체크
	if(checkDirection(board, xLine, yLine, turn, 1, 0) ||
		checkDirection(board, xLine, yLine, turn, 0, 1)||
		checkDirection(board, xLine, yLine, turn, 1, 1) ||
		checkDirection(board, xLine, yLine, turn, 1, -1)
		) {
        return true;
    } else{
		return false;
	}
}

/** (최대 X축)*(최대 Y축)로 이루어진
* 1차원 배열intersections를 2중배열로 반환하는 함수
* by Woobin
*/
function convertDoubleList(max_col, max_row, intersections){
	var board = [];
	for(var row=0; row<max_row; row++){
		var newRow = [];
		for(var col=0; col<max_col; col++){
			var position = row*max_row + col;
			newRow.push(intersections[position]);
		}
		board.push(newRow);
	}
	return board;
}

/** 방향별로 체크하는 함수
*/
function checkDirection(board, col, row, player, colIncrement, rowIncrement) {
    var consecutiveStones = 1; // 실제 색깔인 돌의 갯수
    const maxConsecutiveStones = 5; // 승리하는 갯수
    col = Number(col);
    row = Number(row);
    
    // 정방향으로 i를 최대 4회 반복 할 예정
    for(var i = 1;i<maxConsecutiveStones; i++){
		var findCol = colIncrement * i + col;
		var findRow = rowIncrement * i + row;
		
		if(
		  findCol >= 0 &&
		  findCol < board[0].length &&
          findRow >= 0 &&
          findRow < board.length){
			// 이하 본문
			if(board[findRow][findCol].getAttribute('id') === player){
				// 찾는 돌이 맞으면 +1시키기
				consecutiveStones++;
			} else{
				break;
			}
		}
    }
    
    
    // 반대향으로 i를 최대 4회 반복 할 예정
    for(var i = 1;i<maxConsecutiveStones; i++){
		var findCol = colIncrement * (-i) + col;
		var findRow = rowIncrement * (-i) + row;
		
		if(
		  findCol >= 0 &&
		  findCol < board[0].length &&
          findRow >= 0 &&
          findRow < board.length){
			// 이하 본문
			if(board[findRow][findCol].getAttribute('id') === player){
				consecutiveStones++;
			} else{
				break;
			}
		}
    }
    
	if (consecutiveStones === maxConsecutiveStones) {
		console.log("consecutiveStones : ", consecutiveStones)
		return true;
    }
	
    return false;
}

// 엔터로 받을때 ajax로 확인
function playerNumberCheck(){
	
	var returnNum;
	
	var transferMessage = { 
		"roomID": roomID
		}
	
	$.ajax({
		type : 'get',           // 타입 (get, post, put 등등)
		url : '/omok/player/check' , // 요청할 서버url    
		async : false,            // 비동기화 여부 (default : true)    
		headers : {              // Http header      
			"Content-Type" : "application/json",      
			"X-HTTP-Method-Override" : "POST"    
		},    
		dataType : 'json',       // 데이터 타입 (html, xml, json, text 등등)    
		data : 
			transferMessage
		,    
		
		success : function(result) { // 결과 성공 콜백함수
			returnNum = result;
		},    
		error : function(request, status, error) { // 결과 에러 콜백함수        
		console.log(error)    
		}
	})
	
	return returnNum;
}


function playTimer(){
	
	makeCountDownClock(countDown)
	
	if(countDown == 0){
		// 시간 제한을 다하면 알림
		clearInterval(timerOn);
		// 턴 사라지고 승리라고 알리고 항복으로 websocket 보내고
		var turn = $("#turn").val();
		console.log(turn)
		console.log(myStone)
		if(turn == myStone){
			sendGiveUpMessage();
		}
		
		alert("시간 종료")
	}		
}

function makeCountDownClock(sec){
	document.getElementById("timer")
    	.innerHTML =
    	'<div>' + sec + '<span>Seconds</span></div>';
    clockSecondsSound.play();
		countDown--;
}

function timerReload(){
	// 돌을 놓으면 타이머를 재시작
	clearInterval(timerOn);
	countDown = countDownLimit;
	timerOn = setInterval(playTimer, 1000);	
}

function sendGiveUpMessage(){
	var giveUpMessage={
			"type" : "GIVEUP",
			"roomID":roomID,
			"sender":userNum
		};
		
	webSocket.send(JSON.stringify(giveUpMessage));
}

function sendWinMessage(){
	var winMessage={
			"type" : "WIN",
			"roomID":roomID,
			"sender":userNum
		};
		
	webSocket.send(JSON.stringify(winMessage));
}

function gameWin(){
	alert($("#turn").val() + " win")
	turnInput = document.getElementById("turn");
	turnInput.value = "";
	clearInterval(timerOn)
}

function gameLose(){
	alert("lose")
	turnInput = document.getElementById("turn");
	turnInput.value = "";
	clearInterval(timerOn)
}