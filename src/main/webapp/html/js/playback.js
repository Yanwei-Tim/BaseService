/**
 * Created by peijiqiu on 13-9-29.
 */
$(document).ready(function() {
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
    L.Control.measureControl().addTo(map);


    //get user_id ,token,begin,end,sn from cookie
    var user_id = $.cookie('user_id');
    var token = $.cookie('token');
    var begin = $.cookie('trace_begin');
    var end = $.cookie('trace_end');
    var sn = $.cookie('trace_sn');
    var markerTrace = {};
    var traceArray = [];
    var protocolObject = {};
    protocolObject.user_id = user_id;
    protocolObject.token = token;
    var protocol = {};
    var protocolArray = [];
    protocol.cmd = 14;
    protocol.device_sn = sn;
    protocol.begin = begin;
    protocol.end = end;
    protocolArray.push(protocol);
    protocolObject.protocol = protocolArray;

    sendGetRequest(function(bstatus,oResult) {
        if(bstatus) {
            if(oResult.protocol[0].ret == 1) {
                var traces = oResult.protocol[0].track;
                if($.isArray(traces)) {
                    if(traces.length == 0) {
                        alert('所选时间范围内无轨迹点，请重新查询');
                    } else {
                        if(oResult.protocol[0].ret == 1) {
                            markerTrace.type = "Feature";
                            markerTrace.geometry = {};
                            markerTrace.geometry.type = "MultiPoint";
                            markerTrace.geometry.coordinates = [];
                            markerTrace.properties = {};
                            markerTrace.properties.time = [];
                            for(var i in traces) {
                                //judge direction
                                var direction = judgeDir(traces[i].direction);
                                var markerImgUrl = 'img/marker-'+direction+'.png';
                                markerTrace.geometry.coordinates.push([traces[i].lng,traces[i].lat]);
                                markerTrace.properties.time.push(traces[i].receive);
                                var myIcon = L.icon({
                                    iconUrl: markerImgUrl,
                                    iconSize: [40, 40],
                                    popupAnchor: [0,-8]
                                });
                                var marker = L.marker([traces[i].lat,traces[i].lng], {icon: myIcon}).addTo(map);
                                marker.bindPopup("<p>"+changeTimeFormat(traces[i].receive)+','+traces[i].lng+','+traces[i].lat+"</p>").openPopup();
                            }
                            markerTrace.geometry.coordinates.reverse();
                            markerTrace.properties.time.reverse();

                            if(traceArray == '') {
                                traceArray.push(markerTrace);
                                demoTracks = [markerTrace];

                                playback = new L.Playback(map,demoTracks);
                            } else {
                                traceArray.push(markerTrace);
                            }
                        } else {
                            alert(oResult.protocol[0].desc);
                        }
                    }
                }
            } else {
                alert(oResult.protocol[0].desc);
            }
        }
    },protocolObject);

    /**
     * judge direction
     * @param fAngle
     */
    function judgeDir(fAngle) {
        var dirNum = (fAngle/45).toFixed(0);
        var dirction;
        switch (dirNum){
            case '0':
                dirction = 0;
                break;
            case '1' :
                dirction = 1;
                break;
            case '2' :
                dirction = 2;
                break;
            case '3' :
                dirction = 3;
                break;
            case '4' :
                dirction = 4;
                break;
            case '5' :
                dirction = 5;
                break;
            case '6' :
                dirction = 6;
                break;
            case '7' :
                dirction = 7;
                break;
            case '8' :
                dirction = 0;
                break;
            default :
                dirction = 9;
                break;
        }
        return dirction;
    }
});