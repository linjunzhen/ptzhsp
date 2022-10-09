function getRandom() {
    var result = "";

    $.ajax({
        url: "/login/genRandom",
        type: "get",
        cache: false,
        data: {},
        timeout: 60000,
        async: false,
        crossDomain: !(document.all),
        success: function(randomrespon) {
            result = randomrespon;
        },
        error: function(randomrespon) {
            alert(JSON.stringify(randomrespon));
            result = "";
        }
    });

    return result;
}


function sign(randomrespon) {
    var result = "";
    $.ajax({
        url: "http://127.0.0.1:56007/Cert/Seclib/SOF_SignData",
        type: "post",
        dataType: "json",
        cache: false,
        data: {
            InDataBase64: randomrespon
        },
        timeout: 60000,
        async: false,
        crossDomain: !(document.all),
        success: function(signResponse) {
            result =  signResponse;
        },
        error: function(signResponse) {
            alert(JSON.stringify(signResponse));
            result = "";
        }
    });
    return result;
}


function auth(cert,orginData,signData) {
    var result = "";

    $.ajax({
        url: "/login/auth",
        type: "post",
        cache: false,
        data: {
            CertData: cert,
            SignData: signData,
            OrginData: orginData
        },
        timeout: 60000,
        async: false,
        crossDomain: !(document.all),
        success: function(randomrespon) {
            result = randomrespon;
        },
        error: function(randomrespon) {
            result = "错误:" + JSON.stringify(randomrespon);
        }
    });

    return result;
}


function getCert() {
    var result = "";
    $.ajax({
        url: "http://127.0.0.1:56007/Cert/Seclib/SOF_ExportUserCert",
        type: "post",
        dataType: "json",
        cache: false,
        timeout: 60000,
        async: false,
        crossDomain: !(document.all),
        success: function(signResponse) {
            result =  signResponse;
        },
        error: function(signResponse) {
            result = "";
        }
    });
    return result;
}