
var userNum;
var gameNum;
var board;
const MAX_ROW = 15;
const MAX_COL = 15;   
var intersections;
// 오목 수순
var orders;
// 오목 게임 세팅
var setting;
// 댓글 리스트
var comments;
// 둔 순서
var procedure = 0;

// JQuery
$(document).ready(function(){
	
	// jquery를 이용해 id값으로 input태그의 값 가져오기
	gameNum = $("#gameNum").val();
	userNum = $("#userNum").val();
	board = document.getElementById('stone_board');
	intersections = [];
	
	// 돌 생성
	for (let i = 1; i <= MAX_ROW; i++) {
		for (let j = 1; j <= MAX_COL; j++) {
			const intersection = document.createElement('div');
			intersection.classList.add('intersection');
			intersection.id = 'intersection';
			intersection.dataset.row = i;
			intersection.dataset.col = j;
			
			if(procedure < orders.length &&
				orders[procedure].xline == j &&
				orders[procedure].yline == i){
				
				if(orders[procedure].userNum.userNum == setting.blackStone.userNum){
					// intersection.setAttribute("style", "background-color: black; color : white;")
					intersection.classList.add('black_stone');
				} else{
					intersection.classList.add('white_stone');
				}
				procedure++;
				var textNode = document.createTextNode(procedure);
				intersection.appendChild(textNode);
			}
			board.appendChild(intersection);
			intersections.push(intersection);
		}
	}
	
});

function saveComment(){
		
	let historyCommentDTO = {
		comment : $("#comment").val(),
		gameNumGameNum : gameNum,
		userNumUserNum : userNum
	};
	 
	$.ajax({
	    type : "POST",            // HTTP method type(GET, POST) 형식이다.
	    url : "/save/history/comment",      // 컨트롤러에서 대기중인 URL 주소이다.
	    data : historyCommentDTO,            // Json 형식의 데이터이다.
	    success : function(){ // 비동기통신의 성공일경우 success콜백으로 들어옵니다. 'res'는 응답받은 데이터이다.
	        alert("저장완료")
	        location.reload();
	    },
	    error : function(XMLHttpRequest, textStatus, errorThrown){ // 비동기 통신이 실패할경우 error 콜백으로 들어옵니다.
	        alert("저장 실패")
	    }
	});
}

function deleteComment(commentNum){
	
	let historyCommentDTO = {
		historyCommentNum : commentNum
	};
	 
	$.ajax({
	    type : "POST",
	    url : "/delete/history/comment",
	    data : historyCommentDTO,
	    success : function(){
	        alert("삭제완료")
	        location.reload();
	    },
	    error : function(XMLHttpRequest, textStatus, errorThrown){
	        alert("삭제 실패")
	    }
	});
	
}

function updeteOn(commentNum){
	$(".updateBlock").hide();
	$("#updateBlock_"+commentNum).show();
}

function updateOff(){
	$(".updateBlock").hide();
}

function updateComment(commentNum){
	var updateText = $("#updateText_"+commentNum).val();
	
	let historyCommentDTO = {
		historyCommentNum : commentNum,
		userNumUserNum : userNum,
		gameNumGameNum : gameNum,
		comment : updateText
	};
	 
	$.ajax({
	    type : "POST",
	    url : "/update/history/comment",
	    data : historyCommentDTO,
	    success : function(){
	        alert("수정완료")
	        location.reload();
	    },
	    error : function(XMLHttpRequest, textStatus, errorThrown){
	        alert("수정 실패")
	    }
	});
	
	
}