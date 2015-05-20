package com.unistrong.tracker.dao.sql;

public class SpotDaoSql {

		public static String sql1 = 
				"select s2.receive_date as day," +
				"       sum(s2.f_stayed) as stop," + 
				"       sum(s2.f_distance) as distance" + 
				"  from (select from_unixtime(s.f_receive / 1000, '%Y%c%d') as receive_date," + 
				"               s.f_stayed," + 
				"               s.f_distance" + 
				"          from us_spot s" + 
				"         where s.f_device_sn = :deviceSn" + 
				"           and s.f_receive >= :begin" + 
				"           and s.f_receive <= :end) as s2" + 
				" group by s2.receive_date";

		public static String sql2 = 
				"select case" +
				"         when s.f_speed between 0 and 30 then" + 
				"          '10-30km'" + 
				"         when s.f_speed between 31 and 60 then" + 
				"          '30-60km'" + 
				"         when s.f_speed between 61 and 100 then" + 
				"          '60-100km'" + 
				"         when s.f_speed between 101 and 9999 then" + 
				"          '大于100'" + 
				"         else" + 
				"          'other'" + 
				"       end as grade," + 
				"       sum(s.f_distance) as percent" + 
				"  from us_spot s" + 
				" where s.f_device_sn = :deviceSn" + 
				"   and s.f_receive >= :begin" + 
				"   and s.f_receive <= :end" + 
				" group by grade";

}
