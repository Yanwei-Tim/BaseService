/**
 * Created with JetBrains WebStorm.
 * User: peijiqiu
 * Date: 13-8-13
 * Time: 上午9:11
 * To change this template use File | Settings | File Templates.
 */
    var user = {};
    var traces = [];
    var lastPage = 0;
    var totalNum = 0;
    var demoTracks;
    var n = 1;

    $(document).ready(function() {
//        //user login
        $('#loginModal').modal({
            keyboard: false,
            backdrop : 'static'
        });

        $('#loginBtn').click(function() {
            var name = $('#inputName').val();
            var ts  = Math.floor(Math.random()*100);
            var password = md5Pwd(md5Pwd($('#inputPassword').val()));
            var pwd = md5Pwd(password +ts);
            var protocolObject = {};
            var protocol = {};
            var protocolArray = [];
            protocol.cmd = 3;
            protocol.username = name;
            protocol.pwd = pwd;
            protocol.ts = ts;
            protocolArray.push(protocol);
            protocolObject.protocol = protocolArray;
            sendGetRequest(function(bStatus,oResult){
                if(bStatus) {
                    if(oResult.protocol[0].ret == 1) {
                        user.user_id = oResult.protocol[0].user_id;
                        user.token = oResult.protocol[0].token;
                        $.cookie("user_id", user.user_id);
                        $.cookie("token",user.token);
                        //获取设备列表
                        var protocolObject = {};
                        protocolObject.user_id = user.user_id;
                        protocolObject.token = user.token;
                        var protocol = {};
                        var protocolArray = [];
                        protocol.cmd = 11;
                        protocolArray.push(protocol);
                        protocolObject.protocol = protocolArray;

                        sendGetRequest(function(bstatus,oResult) {
                            if(bstatus) {
                                if(oResult.protocol[0].ret == 1) {
                                    var devices = oResult.protocol[0].devices;
                                    if($.isArray(devices)){
                                        var deviceStr = '';
                                        for(var i in devices) {
                                            deviceStr += '<option id="'+devices[i].sn+'">'+devices[i].sn+'-'+devices[i].name+'</option>'
                                        }
                                        $('#deviceList').append($(deviceStr))
                                    }
                                    $('#loginModal').modal('hide')
                                } else {
                                    alert(oResult.protocol[0].desc);
                                }
                            }
                        },protocolObject);
                    } else {
                        alert(oResult.protocol[0].desc);
                    }
                } else {
                    alert(oResult.error);
                }
            },protocolObject);


        });
        var $loading = $('.loadingPic');

        //绑定时间控件,所有绑定datetimepicker的控件class为date
        $('.date').datetimepicker({
            controlType: 'select'
        });

        //set default time today0:0:0 to current time
        var currDate = getDateTime(new Date().getTime());
        var iniDateBegin = currDate.year+'-'+currDate.month+'-'+currDate.day+' '+'00:00';
        var iniDateEnd = currDate.year+'-'+currDate.month+'-'+currDate.day+' '+currDate.hour+':'+currDate.min;
        $('#beginTime').datepicker("setDate", iniDateBegin);
        $('#endTime').datepicker("setDate", iniDateEnd);

        //change time period
        $('input[type=radio]').click(function() {
            var id = this.id.split('-')[1];
            var changeDate = getDateTime(new Date().setDate(new Date().getDate()-id));
            var begin = changeDate.year+'-'+changeDate.month+'-'+changeDate.day+' '+'00:00';
            var end = changeDate.year+'-'+changeDate.month+'-'+changeDate.day+' '+'23:59';
            $('#beginTime').datepicker("setDate", begin);
            $('#endTime').datepicker("setDate", end);
        });

        //creates a new map for trace playback
        //load map
        var map = new L.Map('map');

        var basemapURL = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
        var basemapLayer = new L.TileLayer(basemapURL, {
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a>',
            maxZoom : 19
        });

        // centers map and default zoom level
        map.setView([39.85, 108.47], 4);

        // adds the background layer to the map
        map.addLayer(basemapLayer);

        $('#playBackWrap').hide();

        //create a new map for upload position points
        var mapUpload = new L.Map('map_upload');
        var basemapLayer1 = new L.TileLayer(basemapURL, {
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a>',
            maxZoom : 19
        });
        mapUpload.setView([39.85, 108.47], 4);
        mapUpload.addLayer(basemapLayer1);
        $('#uploadWrap').hide();


        /*=============show history points as table format==============*/
        $('#search').on('click',function() {
            var beginTimePicker = $('#beginTime').datetimepicker('getDate');
            var endTimePicker = $('#endTime').datetimepicker('getDate');

            if(validateDate(beginTimePicker,endTimePicker)) {
                var beginDate = beginTimePicker.getTime();
                var endDate = endTimePicker.getTime();
                var sn = $("select#deviceList option:selected").attr("id");
                if(sn == '') {
                    alert('请输入要查询的设备IMIE号');
                    return;
                }

                var protocolObject = {};
                protocolObject.user_id = user.user_id;
                protocolObject.token = user.token;
                var protocol = {};
                var protocolArray = [];
                protocol.cmd = 14;
                protocol.device_sn = sn;
                protocol.begin = beginDate;
                protocol.end = endDate;
                protocolArray.push(protocol);
                protocolObject.protocol = protocolArray;
                sendGetRequest(function(bstatus,oResult) {
                    if(bstatus) {
                        if(oResult.protocol[0].ret == 1) {
                            traces = oResult.protocol[0].track;
                            if($.isArray(traces)) {
                                if(traces.length == 0) {
                                    alert('所选时间范围内无轨迹点，请重新查询');
                                } else {
                                    n = 1;
                                    lastPage = 0;
                                    totalNum = traces.length;
                                    $loading.fadeIn();
                                    appendTraceOnTable(traces,1);
                                    $loading.hide();
                                    $('.content-wrap').fadeIn();
                                    addPagination(totalNum,1);
                                }
                            }
                        } else {
                            alert(oResult.protocol[0].desc);
                        }
                    }
                },protocolObject)
            }
        });

        //翻页
        $('body').delegate(".gopage","click",function() {
            var pgNum = $(this).attr('id');
            $('ul#page li').removeClass('active');
            $(this).parent().addClass('active');
            $loading.fadeIn();
            appendTraceOnTable(traces,pgNum);
            $loading.hide();
            $('.content-wrap').fadeIn();
        });

        //后翻页
        $("body").delegate( "#next", "click", function() {
            appendTraceOnTable(traces,lastPage+1);
            addPagination(totalNum-1000*n,lastPage+1);
            n = n+1;
        });

        //前翻页
        $("body").delegate("#pre",'click',function() {
            appendTraceOnTable(traces,lastPage-19);
            addPagination(totalNum+1000*(n-1),lastPage-19);
            n = n-1;
        });


        /*============ trace playback =============*/
        $('#showmap').click(function() {
            var beginTimePicker = $('#beginTime').datetimepicker('getDate');
            var endTimePicker = $('#endTime').datetimepicker('getDate');
            if(validateDate(beginTimePicker,endTimePicker)) {
                var begin = beginTimePicker.getTime();
                var end = endTimePicker.getTime();
                var sn = $("select#deviceList option:selected").attr("id");
                $.cookie("trace_begin", begin);
                $.cookie("trace_end",end);
                $.cookie("trace_sn",sn);
                window.open('playback.html');
            }
        });

        /*============ upload location points ============*/
        var markers = {};
        var pointNum = 1;
        var currentTime = '';

        $('#upload').click(function() {
            $('.content-wrap,.date-wrap').hide();
            var sn = $("select#deviceList option:selected").attr("id");
            $('#currentSn').html(sn);
            $('#uploadWrap').fadeIn();

            //get device current status
            var protocolObject = {};
            protocolObject.user_id = user.user_id;
            protocolObject.token = user.token;
            var protocol = {};
            var protocolArray = [];
            protocol.cmd = 10;

            protocolArray.push(protocol);
            protocolObject.protocol = protocolArray;

            sendGetRequest(function(bStatus,oResult) {
                if(bStatus) {
                    if(oResult.protocol[0].ret == 1) {
                        var devStatus = oResult.protocol[0].statuss;
                        var sn = $("select#deviceList option:selected").attr("id");
                        for(var i in devStatus) {
                            if(devStatus[i].deviceSn == sn) {
                                currentTime = devStatus[i].receive;
                                $('#currentTime').html(changeTimeFormat(currentTime));
                            }
                        }
                    } else {
                        alert(oResult.protocol[0].ret);
                    }
                } else {
                    alert(oResult.error);
                }
            },protocolObject)
        });

        $('#back').click(function() {
            $('#uploadWrap').hide();
            $('.date-wrap').fadeIn();
        });

        mapUpload.on('click', onMapClick);
        function onMapClick(e) {
            var icon = L.icon({
                iconUrl: "img/marker.png",
                iconSize: [30, 30]
            });
            var marker = L.marker([e.latlng.lat, e.latlng.lng],{icon:icon}).addTo(mapUpload);
            markers[pointNum] = {};
            markers[pointNum].point = {};
            markers[pointNum].point['marker'] = marker;
            if($('#normal').is(':checked')) {
                markers[pointNum].point['receive'] = currentTime + 60000*pointNum;
            } else {
                markers[pointNum].point['receive'] = currentTime - 60000*pointNum;
            }

            $('#point_list').append('' +
                '<li>' +
                'Point'+pointNum+':<br/>' +
                'lng:<input type="text" style="width: 100px" class="lng'+pointNum+'">' +' lat:<input type="text" style="width: 100px" class="lat'+pointNum+'">' +
                '<br />Time:<input type="text" class="date" style="width: 200px">' +
                '</li>');
            $('.lng'+pointNum+'').val(e.latlng.lng.toFixed(6));
            $('.lat'+pointNum+'').val(e.latlng.lat.toFixed(6));
            pointNum ++;
            $('.date').datetimepicker();
        }

        //clear markers and records
        $('#clear_point').click(function() {
            $('#point_list').html('');
            for(var i in markers) {
                mapUpload.removeLayer(markers[i].point.marker);
            }
            markers = {};
            pointNum = 1;
        });

        //normal upload location points
        $('#normal_upload').click(function() {
            if(!$('#point_list').text().trim()) {
                alert('请绘制所要上传的位置点');
            } else {
                //normal upload position point
                var sn = $("select#deviceList option:selected").attr("id");
                $('#point_list li').each(function() {
                    var lng = $(this).find('input').eq(0).val();
                    var lat = $(this).find('input').eq(1).val();
                    var datePicker = $(this).find('input').eq(2).datetimepicker('getDate');
                    if(datePicker == null) {
                        alert('请选择上传时间')
                    } else {
                        var time = datePicker.getTime();
                        var protocolObject = {};
                        protocolObject.user_id = user.user_id;
                        protocolObject.token = user.token;
                        var protocol = {};
                        var protocolArray = [];
                        protocol.cmd = 17;
                        protocol.device_sn = sn;
                        protocol.lng = lng;
                        protocol.lat = lat;
                        protocol.time = time;
                        protocol.battery = '';
                        protocol.flow = '';
                        protocol.mode = 'A';
                        protocol.direction = '';
                        protocol.speed = '';
                        protocol.info = '模拟上传';
                        protocolArray.push(protocol);
                        protocolObject.protocol = protocolArray;

                        sendGetRequest(function(bStatus, oResult) {
                            if(bStatus) {
                                if(oResult.protocol[0].ret == 1) {
                                    alert('上传成功');
                                } else {
                                    alert(oResult.protocol[0].desc);
                                    flag = false;
                                }
                            } else {
                                alert(oResult.error)
                            }
                        }, protocolObject)
                    }
                });
            }

        });

        //delay_upload location points
        $('#delay_upload').click(function() {
            if(!$('#point_list').text().trim()) {
                alert('请绘制所要上传的位置点');
            } else {
                //normal upload position point
                var sn = $("select#deviceList option:selected").attr("id");
                var flag = true;
                for(var i in markers) {
                    var protocolObject = {};
                    protocolObject.user_id = user.user_id;
                    protocolObject.token = user.token;
                    var protocol = {};
                    var protocolArray = [];
                    protocol.cmd = 17;
                    protocol.device_sn = sn;
                    protocol.lng = markers[i].point.marker.getLatLng().lng;
                    protocol.lat = markers[i].point.marker.getLatLng().lat;
                    protocol.time = markers[i].point.receive;
                    protocol.battery = '';
                    protocol.flow = '';
                    protocol.mode = 'A';
                    protocol.direction = '';
                    protocol.speed = '';
                    protocol.info = '模拟上传';
                    protocolArray.push(protocol);
                    protocolObject.protocol = protocolArray;

                    sendGetRequest(function(bStatus, oResult) {
                        if(bStatus) {
                            if(oResult.protocol[0].ret == 1) {
                                alert('上传成功');
                            } else {
                                alert(oResult.protocol[0].desc);
                                flag = false;
                            }
                        } else {
                            alert(oResult.error)
                        }
                    }, protocolObject)
                }
            }
        });

        $('#upalarm').click(function() {
            //上传告警点
            if(!$('#point_list').text().trim()) {
                alert('请绘制所要上传的告警位置点');
            } else {
                var type = $('#alarm-type').val()
                //normal upload position point
                var sn = $("select#deviceList option:selected").attr("id");
                var flag = true;
                for(var i in markers) {
                    var protocolObject = {};
                    protocolObject.user_id = user.user_id;
                    protocolObject.token = user.token;
                    var protocol = {};
                    var protocolArray = [];
                    protocol.cmd = 21;
                    protocol.device_sn = sn;
                    protocol.lng = markers[i].point.marker.getLatLng().lng;
                    protocol.lat = markers[i].point.marker.getLatLng().lat;
                    protocol.mode = 1;
                    protocol.info = '模拟上传告警点';
                    protocol.type = type,
                    protocol.time = new Date().getTime(),
                    protocolArray.push(protocol);
                    protocolObject.protocol = protocolArray;

                    sendGetRequest(function(bStatus, oResult) {
                        if(bStatus) {
                            if(oResult.protocol[0].ret == 1) {
                                alert('上传成功');
                            } else {
                                alert(oResult.protocol[0].desc);
                                flag = false;
                            }
                        } else {
                            alert(oResult.error)
                        }
                    }, protocolObject)
                }
            }

        })
    });

    /**
     * 自定义md5加密
     * @param sPwd
     * @returns {string}
     */
    function md5Pwd(sPwd) {
        return $.md5(sPwd.toUpperCase()).toUpperCase();
    }

    /**
     * 检查输入日期是否合理
     * @param oBeginTime
     * @param oEndTime
     * @return {Boolean}
     */
    function validateDate(oBeginTime, oEndTime) {
        if(oBeginTime == null) {
            alert('请选择开始时间');
            return false
        }

        if(oEndTime == null) {
            alert('请选择结束时间');
            return false;
        }

        var beginDate = oBeginTime.getTime();
        var endDate = oEndTime.getTime();
        var todayDate = new Date();


        if(endDate > todayDate) {
            alert('所选结束日期已超过当日，请重新选择');
            return false;
        }

        if(beginDate > endDate) {
            alert('结束日期需大于开始日期，请重新选择');
            return false;
        }

        if((endDate - beginDate) > 259200000) {
            alert('只能查询三天内的轨迹数据，请重新选择');
            return false;
        }
        return true;
    }

    /**
     * 把长整型的时间转换为如：2012/10/01 下午3:5的格式
     * @param nMiliSeconds
     * @return {String}
     */
    function changeTimeFormat(nMiliSeconds){
        if(nMiliSeconds != null) {
            var date = new Date();
            date.setTime(nMiliSeconds);
            var day = date.getDate();
            var month = date.getMonth() + 1;
            var year = date.getFullYear();
            var hour = date.getHours();
            var min = date.getMinutes();
            var second = date.getSeconds();
            return(year+'-'+month+'-'+day+' '+hour+':'+min+':'+second);
        } else {
            return('无');
        }
    }

    /**
     * get DateTime
     * @param nMiliSeconds
     * @returns {*}
     */
    function getDateTime(nMiliSeconds) {
        if(nMiliSeconds != null) {
            var date = new Date();
            date.setTime(nMiliSeconds);
            var day = date.getDate();
            var month = date.getMonth() + 1;
            var year = date.getFullYear();
            var hour = date.getHours();
            var min = date.getMinutes();
            var second = date.getSeconds();
            var _hour = parseInt(hour);
            var _min = parseInt(min);
            var _second = parseInt(second);
            if(_hour.length == 1) {
                _hour = '0'+_hour;
            }
            if(_min.length == 1) {
                _min = '0'+_min;
            }
            if(_second.length == 1) {
                _second = '0'+_second;
            }

            var dateObj = {};
            dateObj.year = year;
            dateObj.month = month;
            dateObj.day = day;
            dateObj.hour = _hour;
            dateObj.min = _min;
            dateObj.second = _second;
            return dateObj;
        } else {
            return null;
        }
    }

    /**
     * 改变时间段，以天为单位
     * @param nDayNum
     * @returns {{}}
     */
    function changeTimePeriod(nDayNum) {
        var eMilisecond = new Date().setDate(new Date().getDate()-nDayNum);
        var date = {};
        date.begin = changeTimeFormat(new Date(eMilisecond).setHours(0,0,0,0));
        date.end = changeTimeFormat(new Date(eMilisecond).setHours(23,59,0,0));
        return date;
    }

    /**
     * 将历史轨迹点显示在table中
     * @param oTraceArray
     * @param nPageNum
     */
    function appendTraceOnTable(oTraceArray,nPageNum) {
        var $traceTable = $('table#posTable tbody');
        $traceTable.html('');
        var startNum = (nPageNum-1)*100;
        var traceStr = '';
        var endNum=(startNum+100 > oTraceArray.length)? oTraceArray.length:startNum+100;
        for(var i = startNum;i < endNum;i++) {
            var stayed = oTraceArray[i].stayed;
            var h = parseInt(stayed/3600);
            var m = parseInt(stayed/60) > 60 ? parseInt(stayed/60)%60:parseInt(stayed/60);
            var s = stayed % 60;
            traceStr += '<tr>' +
                '<td>'+(i+1)+'</td>' +
                '<td>'+oTraceArray[i].lng+'</td>' +
                '<td>'+oTraceArray[i].lat+'</td>' +
                '<td>'+changeTimeFormat(oTraceArray[i].receive)+'</td>' +
                '<td>'+changeTimeFormat(oTraceArray[i].systime)+'</td>' +
                '<td>'+stayed+'('+h+'时'+m+'分'+s+'秒'+')</td>' +
                '<td>'+oTraceArray[i].mode+'</td>' +
                '<td>'+oTraceArray[i].speed+'</td>' +
                '<td>'+oTraceArray[i].distance+'</td>'+
                '</tr>';
        }
        $traceTable.append(traceStr);
    }

    /**
    * 添加pagination
    * @param nRecondNum
    * @param nBeginPage
    */
    function addPagination(nRecondNum,nBeginPage) {
        var $page = $('#page');
        $page.html('');
        var pageNumStr = '';
        var pageNum = Math.ceil(nRecondNum/100);
        if(nBeginPage >1) {
            $page.append('<li><a href="#" id="pre">«</a></li>');
        }
        if(pageNum > 10) {
            for(var i = nBeginPage;i < nBeginPage+10;i++) {
                pageNumStr += '<li><a href="#" class="gopage" id="'+i+'">'+i+'</a></li>';
            }
            $page.append($(pageNumStr));
            $page.append('<li><a href="#" id="next">»</a></li>');
            lastPage = nBeginPage+9;
        } else {
            for(var i = nBeginPage;i < pageNum+nBeginPage; i++) {
                pageNumStr += '<li><a href="#" class="gopage" id="'+i+'">'+i+'</a></li>';
            }
            $page.append($(pageNumStr));
            lastPage = nBeginPage+9;
        }

        $('ul#page li').removeClass('active');
        var id = lastPage-9;
        $('#'+id+'').parent().addClass('active');
    }
