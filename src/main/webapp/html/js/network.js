/**
 * Created with JetBrains WebStorm.
 * User: peijiqiu
 * Date: 13-8-13
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
// ajax超时统一设置为6s
$.ajaxSetup({
    timeout: 6000
});
function sendGetRequest(fnCallback, protocolObject) {
    var ERROR_TAG = "error";
    $.ajax({
    	// 192.168.5.66:8080
        //url : 'http://localhost/api/tracker.do',
		url : '../tracker.do',
        type : 'POST',
        data : JSON.encode(protocolObject),
        dataType : 'json',
        jsonp : 'callback',
        contentType : 'application/json;charset=UTF-8',

        success : function(response) {
            var bStatus = false;
            if(response == undefined || response == null) {
                response = {};
                response[ERROR_TAG] = "no response from server";
            } else if(response.error == undefined) {
                bStatus = true;
            } else  {
                bStatus = false;
            }

            if(typeof fnCallback === 'function') {
                fnCallback(bStatus, response);
            }
        },

        error : function(oXHRequest, nTextStatus, oErrorThrownv) {

            if(typeof fnCallback === 'function') {
                fnCallback(false, {error : nTextStatus});
            }
        }
    })
}

JSON = {
    encode : function(input) {
        if (!input)
            return 'null';
        switch (input.constructor) {
            case String:
                return '"' + input + '"';
            case Number:
                return input.toString();
            case Boolean:
                return input.toString();
            case Array:
                var buf = [];
                for (i in input)
                    buf.push(JSON.encode(input[i]));
                return '[' + buf.join(', ') + ']';
            case Object:
                var buf = [];
                for (k in input)
// buf.push(k + ' : ' + JSON.encode(input[k]));
                    buf.push('"' + k + '"' + ' : ' + JSON.encode(input[k]));
                return '{ ' + buf.join(', ') + '} ';
            default:
                return 'null';
        }
    }
};