/**
 * Created with JetBrains WebStorm.
 * User: peijiqiu
 * Date: 13-4-22
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */

    var md5Pwd = function (sPwd) {
        return $.md5(sPwd.toUpperCase()).toUpperCase();
    };

    /**
     * 检查输入日期是否合理
     * @param oBeginTime
     * @param oEndTime
     * @return {Boolean}
     */
    var validateDate = function (oBeginTime, oEndTime) {
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
        return true;
    };

    /**
     * 把长整型的时间转换为如：2012/10/01 下午3:5的格式
     * @param nMiliSeconds
     * @return {String}
     */
    var changeTimeFormat = function (nMiliSeconds){
        if(nMiliSeconds != null) {
            var date = new Date();
            date.setTime(nMiliSeconds);
            var day = date.getDate();
            var month = date.getMonth() + 1;
            var year = date.getFullYear();
            var hour = date.getHours();
            var min = date.getMinutes();
            var second = date.getSeconds();

            var hour_ = hour.toString();
            var min_ = min.toString();
            var second_ = second.toString();
            if(hour_.length == 1) {
                hour_ = '0'+hour_;
            }
            if(min_.length == 1) {
                min_ = '0'+min_;
            }
            if(second_.length == 1) {
                second_ = '0'+second_;
            }
            return(year+'-'+month+'-'+day+' '+hour_+':'+min_+':'+second_);
        } else {
            return('无');
        }

    };

