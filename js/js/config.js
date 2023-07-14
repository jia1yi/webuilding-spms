var configPre = {
    ajaxPath: "http://10.60.254.254:9902",
}
function parseTime(time, pattern) {
    if (arguments.length === 0 || !time) {
      return null
    }
    const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
      date = time
    } else {
      if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
        time = parseInt(time)
      } else if (typeof time === 'string') {
        time = time.replace(new RegExp(/-/gm), '/');
      }
      if ((typeof time === 'number') && (time.toString().length === 10)) {
        time = time * 1000
      }
      date = new Date(time)
    }
    const formatObj = {
      y: date.getFullYear(),
      m: date.getMonth() + 1,
      d: date.getDate(),
      h: date.getHours(),
      i: date.getMinutes(),
      s: date.getSeconds(),
      a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
      let value = formatObj[key]
      // Note: getDay() returns 0 on Sunday
      if (key === 'a') {
        return ['日', '一', '二', '三', '四', '五', '六'][value]
      }
      if (result.length > 0 && value < 10) {
        value = '0' + value
      }
      return value || 0
    })
    return time_str
}

 
    function encrypt(text) {
        return CryptoJS.AES.encrypt(text, CryptoJS.enc.Utf8.parse(key), {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        }).toString()
    }

    // 解密
    function decrypt1(text,key) {
        let decrypted = CryptoJS.AES.decrypt(text, CryptoJS.enc.Utf8.parse(key), {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8)
    }
    
   
    

// 门禁状态
var mjjl = configPre.ajaxPath + '/iot/MJ/Get_MJ_Open_Trend'


// 查询控制机状态
var  machineState = configPre.ajaxPath + '/api/third/getparkingstatus'
// 停车场车位数量
var  parinkNum = configPre.ajaxPath + '/api/third/parkingplace'
// 车辆记录
var   carLog = configPre.ajaxPath + '/api/third/getparkinglog'
//  打包后删除 uis.json  相关的信息

var   equipmentstatus = configPre.ajaxPath+'/recordInfo/';
var   toExecls = configPre.ajaxPath+'/getparkingInfo/toExecl';


var b64map = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var b64pad = "=";
var BI_RM = "0123456789abcdefghijklmnopqrstuvwxyz";
 function int2char(n) {
    return BI_RM.charAt(n);
}

//RSA每次加密117bytes，需要辅助方法判断字符串截取位置

	  function hexToBytes(hex) {
	        for (var bytes = [], c = 0; c < hex.length; c += 2)
	            bytes.push(parseInt(hex.substr(c, 2), 16));
	        return bytes;
	    }
	    
	    function bytesToHex(bytes) {
	        for (var hex = [], i = 0; i < bytes.length; i++) {
	            hex.push((bytes[i] >>> 4).toString(16));
	            hex.push((bytes[i] & 0xF).toString(16));
	        }
	        return hex.join("");
	    }
	    
	    JSEncrypt.prototype.encryptLong = function (string) {
	        var k = this.getKey();
	        try {
	            var lt = "";
	            var ct = "";
	            //1.获取字符串截取点
	            var bytes = new Array();
	            bytes.push(0);
	            var byteNo = 0;
	            var len, c;
	            len = string.length;
	            var temp = 0;
	            for (var i = 0; i < len; i++) {
	                c = string.charCodeAt(i);
	                if (c >= 0x010000 && c <= 0x10FFFF) {
	                    byteNo += 4;
	                } else if (c >= 0x000800 && c <= 0x00FFFF) {
	                    byteNo += 3;
	                } else if (c >= 0x000080 && c <= 0x0007FF) {
	                    byteNo += 2;
	                } else {
	                    byteNo += 1;
	                }
	                if ((byteNo % 117) >= 114 || (byteNo % 117) == 0) {
	                    if (byteNo - temp >= 114) {
	                        bytes.push(i);
	                        temp = byteNo;
	                    }
	                }
	            }
	            //2.截取字符串并分段加密
	            if (bytes.length > 1) {
	                for (var i = 0; i < bytes.length - 1; i++) {
	                    var str;
	                    if (i == 0) {
	                        str = string.substring(0, bytes[i + 1] + 1);
	                    } else {
	                        str = string.substring(bytes[i] + 1, bytes[i + 1] + 1);
	                    }
	                    var t1 = k.encrypt(str);
	                    ct += t1;
	                }
	                ;
	                if (bytes[bytes.length - 1] != string.length - 1) {
	                    var lastStr = string.substring(bytes[bytes.length - 1] + 1);
	                    ct += k.encrypt(lastStr);
	                }
	                return hex2b64(ct);
	            }
	            var t = k.encrypt(string);
	            var y = hex2b64(t);
	            return y;
	        } catch (ex) {
	            return false;
	        }
	    };
	    
	     JSEncrypt.prototype.decryptLong = function (string) {
	        var k = this.getKey();
	        var MAX_DECRYPT_BLOCK = 128;
	        try {
	            var ct = "";
	            var t1;
	            var bufTmp;
	            var hexTmp;
	            var str = b64tohex(string);
	            var buf = hexToBytes(str);
	            var inputLen = buf.length;
	            //开始长度
	            var offSet = 0;
	            //结束长度
	            var endOffSet = MAX_DECRYPT_BLOCK;
	
	            //分段加密
	            while (inputLen - offSet > 0) {
	                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                    bufTmp = buf.slice(offSet, endOffSet);
	                    hexTmp = bytesToHex(bufTmp);
	                    t1 = k.decrypt(hexTmp);
	                    ct += t1;
	                    
	                } else {
	                    bufTmp = buf.slice(offSet, inputLen);
	                    hexTmp = bytesToHex(bufTmp);
	                    t1 = k.decrypt(hexTmp);
	                    ct += t1;
	                 
	                }
	                offSet += MAX_DECRYPT_BLOCK;
	                endOffSet += MAX_DECRYPT_BLOCK;
	            }
	            return ct;
	        } catch (ex) {
	            return ex;
	        }
	    };
	    
		//RSA加密
		function encryptRSA(data){
			var encrypt = new JSEncrypt();
			encrypt.setPublicKey("这里是公钥");
			var encryptPwd = encrypt.encryptLong(data);
			return encryptPwd;
			
		}
function b64tohex(s) {
    var ret = "";
    var i;
    var k = 0; // b64 state, 0-3
    var slop = 0;
    for (i = 0; i < s.length; ++i) {
        if (s.charAt(i) == b64pad) {
            break;
        }
        var v = b64map.indexOf(s.charAt(i));
        if (v < 0) {
            continue;
        }
        if (k == 0) {
            ret += int2char(v >> 2);
            slop = v & 3;
            k = 1;
        }
        else if (k == 1) {
            ret += int2char((slop << 2) | (v >> 4));
            slop = v & 0xf;
            k = 2;
        }
        else if (k == 2) {
            ret += int2char(slop);
            ret += int2char(v >> 2);
            slop = v & 3;
            k = 3;
        }
        else {
            ret += int2char((slop << 2) | (v >> 4));
            ret += int2char(v & 0xf);
            k = 0;
        }
    }
    if (k == 1) {
        ret += int2char(slop << 2);
    }
    return ret;
}		
		
function hex2b64(h) {
    var i;
    var c;
    var ret = "";
    for (i = 0; i + 3 <= h.length; i += 3) {
        c = parseInt(h.substring(i, i + 3), 16);
        ret += b64map.charAt(c >> 6) + b64map.charAt(c & 63);
    }
    if (i + 1 == h.length) {
        c = parseInt(h.substring(i, i + 1), 16);
        ret += b64map.charAt(c << 2);
    }
    else if (i + 2 == h.length) {
        c = parseInt(h.substring(i, i + 2), 16);
        ret += b64map.charAt(c >> 2) + b64map.charAt((c & 3) << 4);
    }
    while ((ret.length & 3) > 0) {
        ret += b64pad;
    }
    return ret;
}
		
// The error decryption code
JSEncrypt.prototype.decryptLong1 = function(string) {
  var k = this.getKey();
  var maxLength = ((k.n.bitLength()+7)>>3);
  // var maxLength = 128;
  try {
    var string = b64tohex(string);
    var ct = "";
    if (string.length > maxLength) {
      var lt = string.match(/.{1,128}/g);
      lt.forEach(function(entry) {
        var t1 = k.decrypt(entry);
        ct += t1;
      });
    }
    var y = k.decrypt(b64tohex(string));
    return y;
  } catch (ex) {
    return false;
  }
};