<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/sockjs-0.3.0.min.js"></script>
<script type="text/javascript">
var websocket;
    /* if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080${pageContext.request.contextPath}/webSocketServer.json");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080${pageContext.request.contextPath}/webSocketServer.json");
    } else {
        websocket = new SockJS("http://localhost:8080${pageContext.request.contextPath}/sockjs/webSocketServer.json");
    } */
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:9080/webSocketServer.json");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:9080/webSocketServer.json");
    } else {
        websocket = new SockJS("http://localhost:9080/sockjs/webSocketServer.json");
    }
    websocket.onopen = function (evnt) {
    	console.log("websocket connection success ... ...");
    };
    websocket.onmessage = function (evnt) {
        console.log("accept msg = "+evnt.data);
        //alert(evnt.data);
        /* $.messager.show({
			title:'My Title',
			msg:evnt.data,
			timeout:10000,
			showType:'show'
		}); */
    };
    websocket.onerror = function (evnt) {
    	console.log("websocket connection error ... ...");
    };
    websocket.onclose = function (evnt) {
    	console.log("websocket connection close ... ...");
    }
    window.close=function(){
    	websocket.onclose();
    }


</script>
</head>
<body>
<h4>welcome to ... ...</h4>
</body>
</html>