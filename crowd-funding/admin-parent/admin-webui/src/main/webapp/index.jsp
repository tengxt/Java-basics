<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
    <!-- http://localhost:8080/admin-webui/test/ssm.html -->
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
</head>
<body>
    <a href="test/ssm.html">测试页面</a>
    <br/>
    <br/>

    <button id="btn1">Send [5,8,12] One </button>
    <br/>
    <br/>

    <button id="btn2">Send [5,8,12] Two </button>
    <br/>
    <br/>

    <button id="btn3">Send [5,8,12] Three </button>
    <br/>
    <br/>

    <button id="btn4">Test Compose Object</button>
    <br/>
    <br/>

    <button id="btn5">Test ResultEntity</button>


    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                $.ajax({
                    "url": "send/array/one.html",
                    "type": "post",
                    "data": {
                        array: [5, 8, 12]
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn2").click(function () {
                $.ajax({
                    "url": "send/array/two.html",
                    "type": "post",
                    "data": {
                        "array[0]":5,
                        "array[1]":8,
                        "array[2]":12
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn3").click(function () {
                //
                var array = [5,8,12];
                console.log(array.length); // 5

                // 把 JSON 数组转换为 JSON 字符串
                var requestBody = JSON.stringify(array);
                console.log(array.length); // "['5','8','12']"

                $.ajax({
                    "url": "send/array/three.html",
                    "type": "post",
                    "data": requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn4").click(function () {
                var student = {
                    "name":"Tom",
                    "id":21,
                    "address":{
                        "province":"浙江",
                        "city":"宁波"
                    },
                    "subjects":[
                        {
                            "subjectName":"Java",
                            "score":96
                        },
                        {
                            "subjectName":"Data Struct",
                            "score":93
                        }
                    ],
                    "map":{
                        "key1":"value1",
                        "key2":"value2"
                    }
                };
                // 把 JSON 数组转换为 JSON 字符串
                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/array/object.html",
                    "type": "post",
                    "data": requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            });

            $("#btn5").click(function () {
                var student = {
                    "name":"Tom",
                    "id":21,
                    "address":{
                        "province":"浙江",
                        "city":"宁波"
                    },
                    "subjects":[
                        {
                            "subjectName":"Java",
                            "score":96
                        },
                        {
                            "subjectName":"Data Struct",
                            "score":93
                        }
                    ],
                    "map":{
                        "key1":"value1",
                        "key2":"value2"
                    }
                };
                // 把 JSON 数组转换为 JSON 字符串
                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/array/object.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType": "json",
                    "success": function (response) {
                        console.log(response);
                    },
                    "error": function (response) {
                        console.log(response);
                    }
                })
            });
        });
    </script>
</body>
</html>
