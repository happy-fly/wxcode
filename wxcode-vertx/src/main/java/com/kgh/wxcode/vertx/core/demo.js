console.log("1");
$.ajax({
    "url": "/hello",
    "type": "post",
    "dataType": "json",
    "success": function (val) {
        console.log("2");
    }
});
console.log("3");