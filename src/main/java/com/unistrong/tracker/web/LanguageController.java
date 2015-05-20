package com.unistrong.tracker.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unistrong.tracker.handle.PositionHandle;
import com.unistrong.tracker.model.GeoCoordinate;
import com.unistrong.tracker.model.Position;
import com.unistrong.tracker.service.UserDeviceService;
import com.unistrong.tracker.service.cache.UserCache;
import com.unistrong.tracker.util.GeoMapUtil;
import com.unistrong.tracker.util.Language;
import com.unistrong.tracker.util.UsConstants;

@Controller
@RequestMapping({"/language"})
public class LanguageController {
	@Resource
	  private UserCache userCache;
	  @Resource
	  private PositionHandle positionHandle;
	  @Resource
	  private UserDeviceService userDeviceService;
	  
	  @RequestMapping({"/change.do"})
	  public Map<String, Object> change(HttpServletRequest request, String language, Long user_id)
	  {
	    Map<String, Object> rs = new HashMap();
	    Language lag = Language.valueOf(language);
	    if (lag != null)
	    {
	      this.userCache.setUserLanguage(String.valueOf(user_id), language);
	      rs.put("ret", Integer.valueOf(1));
	    }
	    else
	    {
	      rs.put("ret", Integer.valueOf(2));
	      rs.put("desc", UsConstants.getI18nMessage(UsConstants.PARAM_ERROR));
	    }
	    return rs;
	  }
	  
	  @RequestMapping({"/country.do"})
	  public Map<String, Object> country(HttpServletRequest request, Long user_id)
	  {
	    Map<String, Object> rs = new HashMap();
	    Map map = this.positionHandle.getLasts(user_id+"", null, null,"-1","-1");
	    List<Position> ps = (List)map.get("statuss");
	    boolean inchina = true;
	    if ((ps != null) && (ps.size() == 1)) {
	      inchina = GeoMapUtil.IsInsideChinaByGoogle(new GeoCoordinate(((Position)ps.get(0)).getLng().doubleValue(), ((Position)ps.get(0)).getLat().doubleValue()));
	    } else {
	      for (Position p : ps)
	      {
	        inchina = GeoMapUtil.IsInsideChina(new GeoCoordinate(p.getLng().doubleValue(), p.getLat().doubleValue()));
	        if (!inchina) {
	          break;
	        }
	      }
	    }
	    rs.put("allinchina", Boolean.valueOf(inchina));
	    rs.put("ret", Integer.valueOf(1));
	    return rs;
	  }
	  
	  
	  
	  @RequestMapping({"/suport.do"})
	  public Map<String, Object> suport(HttpServletRequest request)
	  {
	    Map<String, Object> rs = new HashMap();
	    rs.put("ret", Integer.valueOf(1));
	    rs.put("language", Language.values());
	    return rs;
	  }
}
