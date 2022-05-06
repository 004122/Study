function askVerifyCode() {
    get('http://localhost:8080/api/auth/verify-code', {
        email: $("#email").val()
    }, function (data) {
        alert(data.reason)
    })
}

function register() {
    post('http://localhost:8080/api/auth/register', {
        verify: $("#verify").val(),
        username: $("#username").val(),
        password: $("#password").val(),
        email: $("#email").val(),
    }, function (data) {
        if (data.code === 200) {
            window.location = "/login.html"
        } else {
            alert(data.reason)
        }
    })
}

function login() {
    post('http://localhost:8080/api/auth/login', {
        username: $("#username").val(),
        password: $("#password").val()
    }, function (data) {
        if (data.code === 200) {
            window.location = "/index.html"
        } else {
            alert(data.reason)
        }
    })
}

function initUserInfo() {
    get('http://localhost:8080/api/user/info', function (data) {
        if (data.code === 200) {
            alert("登录成功,欢迎" + data.data.username + "进入图书管理系统！")
        } else {
            alert(data.reason)
            window.location = "/login.html"
        }
    })
}

function logout() {
    get('http://localhost:8080/api/auth/logout', function (data) {
        if (data.code === 200) {
            window.location = "/login.html"
        }
    })
}

function get(url, data,success){
    $.ajax({
        type: "get",
        data:data,
        url: url,
        async: true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function post(url, data, success){
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}