package com.sduwh.match.service.matchinfo;

import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.model.to.MatchInfoTO;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */
public interface MatchInfoService extends BaseService<MatchInfo,Integer> {
    List<MatchInfo> selectAll();
    public int createMatchInfoByTO(MatchInfoTO to);
    public int updateMatchInfoByTO(MatchInfoTO to);
}