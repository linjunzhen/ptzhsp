(function (W) {
    var suwellAes = function () {
        this.uuid = function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        };
        this.getAesString = function (data, key, iv, addCookie) {
            var key = CryptoJS.enc.Utf8.parse(key);
            var iv = CryptoJS.enc.Utf8.parse(iv);
            var url = data + "&_&" + this.getCookie() + "&_&";
            console.log("最终加密url : " + url)
            var encrypted = CryptoJS.AES.encrypt(addCookie ? url : data + "", key,
                {
                    iv: iv,
                    mode: CryptoJS.mode.CBC,
                    padding: CryptoJS.pad.ZeroPadding
                });
            return encrypted.toString();    //返回的是base64格式的密文
        }
        this.getAesNotCookieString = function (data, key, iv) {
            return this.getAesString(data, key, iv, false)
        }
        this.getDAesString = function (encrypted, key, iv) {
            var key = CryptoJS.enc.Utf8.parse(key);
            var iv = CryptoJS.enc.Utf8.parse(iv);
            var decrypted = CryptoJS.AES.decrypt(encrypted, key,
                {
                    iv: iv,
                    mode: CryptoJS.mode.CBC,
                    padding: CryptoJS.pad.ZeroPadding
                });
            return decrypted.toString(CryptoJS.enc.Utf8);
        }
        this.getCookie = function () {
            var arr1 = document.cookie.split("; ");
            for (var i = 0; i < arr1.length; i++) {
                var arr2 = arr1[i].split("=");
                if (arr2[0] == 'suwellCookie') {
                    return decodeURI(arr2[1]);
                }
            }
        }
        this.setCookie = function () {
            var key = 'suwellCookie';
            if (!this.getCookie(key)) {
                W.document.cookie = key + "=" + this.uuid();
            }
            console.log(this.getCookie())
        }
        this.setCookie();
        return this;
    }
    W.suwellAes = new suwellAes();

})(window)
