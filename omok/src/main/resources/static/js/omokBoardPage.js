/**
 * 
 */
 
// JQuery
$(document).ready(function(){
 
	// jquery를 이용해 id값으로 input태그의 값 가져오기
	const roomID = $("#roomID").val();
	const userNum = $("#userNum").val();
	const board = document.getElementById('stone_board');
	const ROWS = 15;
	const COLS = 15;
	const intersections = [];
	const myStone = $("#stone").val();
	const webSocket = new WebSocket("ws://" + location.host + "/ws/omok");
	
	// // 돌 생성
	for (let i = 0; i < ROWS; i++) {
		for (let j = 0; j < COLS; j++) {
			const intersection = document.createElement('span');
			intersection.classList.add('intersection');
			intersection.id = 'intersection';
			intersection.setAttribute('onmouseout', "this.style.backgroundColor=''")
			intersection.dataset.row = i;
			intersection.dataset.col = j;
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
		/* console.log(typeof transferMessage); */
		  
		if(transferMessage.type == "PLACE"){
			/* console.log(transferMessage.xLine); */
			/* console.log(transferMessage.yLine); */
			placeStone(transferMessage.xLine, transferMessage.yLine);
			turnOver();
		} else if(transferMessage.type == "ENTER"){
			let msgArea = document.querySelector('.msgArea');
			let newMsg = document.createElement('div');
			newMsg.innerText=transferMessage.message;
			msgArea.append(newMsg);
		}
	};
	  
	function sendMsg(){
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
	  
	function enterRoom(webSocket){
		
		/* console.log(roomID); */
		/* console.log(userNum); */
		var enterMessage = {
			"type" : "ENTER",
			"roomID" : roomID,
			"sender" : userNum
		};
		
		// json 형태로 변형하여 전송
		// 필수는 아닐거로 생각됨  
		webSocket.send(JSON.stringify(enterMessage));
	}
	  
	// 돌 색 바꾸기
	function placeStone(xLine, yLine){
		var stones = document.getElementsByClassName('intersection');
		for(var i=0;i<stones.length;i++){
			if( Number(stones[i].dataset.col)+1 == xLine && Number(stones[i].dataset.row)+1==yLine){
			stones[i].style.backgroundColor=$('#turn').val();
			stones[i].setAttribute('onmouseout', "");
			stones[i].setAttribute('id', "placed");
			stones[i].setAttribute('class', "placed");
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
		
	// 돌 위에 마우스를 올렸을때 색 변경 
	$(".intersection").mouseover(function(e){
		/* console.log(e); */
		/* console.log(this); */
		/* console.log(this.dataset.col); */
		/* console.log(Number(this.dataset.col)+1); */
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
		var xLine = Number(this.dataset.col)+1;
		var yLine = Number(this.dataset.row)+1;
			
			
		var placeMessage={
			"type" : "PLACE",
			"roomID":roomID,
			"sender":userNum,
			"message":(Number(this.dataset.col)+1).toString()+ ","+ (Number(this.dataset.row)+1).toString(),
			"xLine":xLine,
			"yLine":yLine
		};
		/* console.log(Number(this.dataset.col)+1);
		console.log(Number(this.dataset.row)+1); */
			
		if(myStone==turn){
			webSocket.send(JSON.stringify(placeMessage));
		}else{
			console.log("내 턴이 아니구나!!")
		}
			
	});
});
	