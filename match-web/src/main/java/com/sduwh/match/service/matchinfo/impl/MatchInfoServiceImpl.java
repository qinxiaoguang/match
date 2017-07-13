package com.sduwh.match.service.matchinfo.impl;

import com.sduwh.match.dao.MatchInfoMapper;
import com.sduwh.match.model.entity.MatchInfo;

import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.to.MatchInfoTO;
import com.sduwh.match.model.wrapper.MatchInfoWrapper;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import com.sduwh.match.service.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-6-28.
 */
@Service
public class MatchInfoServiceImpl implements MatchInfoService {

    @Autowired
    MatchInfoMapper matchInfoMapper;
    @Autowired
    StageService stageService;

    private static final int UPDATE = 1;
    private static final int INSERT = 0;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return matchInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MatchInfo record) {
        return matchInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(MatchInfo record) {
        return matchInfoMapper.insertSelective(record);
    }

    @Override
    public MatchInfo selectByPrimaryKey(Integer id) {
        return matchInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MatchInfo record) {
        return matchInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MatchInfo record) {
        return matchInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MatchInfo> selectAll() {
        return matchInfoMapper.selectAll();
    }

    /**
     * 虽然说是通过TO来创建一个数据，但是首先要把这个对象转换为MatchInfo对象
     */
    @Override
    public int createMatchInfoByTO(MatchInfoTO to) {
        return matchInfoMapper.insert(TO2MatchInfo(to,INSERT));
    }

    @Override
    public int updateMatchInfoByTO(MatchInfoTO to) {
        return matchInfoMapper.updateByPrimaryKey(TO2MatchInfo(to,UPDATE));
    }

    /** 将MatchInfoTO转换为MatchInfo*/
    public MatchInfo TO2MatchInfo(MatchInfoTO to,int flag){
        MatchInfo info = new MatchInfo();
        info.setId(to.getId());
        info.setCreateTime(new Timestamp(System.currentTimeMillis()));
        info.setLeaderInNum(to.getLeaderInNum());
        info.setLeaderNum(to.getLeaderNum());
        info.setLevel(to.getLevel());
        info.setMemberInNum(to.getMemberInNum());
        info.setMemberNum(to.getMemberNum());
        info.setName(to.getName());
        info.setTeacherInNum(to.getTeacherInNum());
        info.setTeacherNum(to.getTeacherNum());
        info.setType1(to.getType1());
        info.setType2(to.getType2());

        List<Stage> stages = new ArrayList<>();
        List<Integer> isChoose = to.getIsChoose();
        List<Timestamp> startTime = to.getStartTime();
        List<Timestamp> endTime = to.getEndTime();
        List<String> toGetStages = to.getStageName();

        if(flag == INSERT){
            for(int i=0; i<isChoose.size(); i++){
                if (isChoose.get(i)!=0){
                    //说明有选择
                    Stage stage = new Stage();
                    stage.setEndTime(endTime.get(i));
                    stage.setStartTime(startTime.get(i));
                    stage.setStageFlag(isChoose.get(i));
                    stage.setName(toGetStages.get(i));
                    //这个地方就需要插入
                    stageService.insert(stage);
                    //这个是插入id  stage.getId();
                    stages.add(stage);
                }
            }
        }else if(flag == UPDATE){
            //如果是更新，则应该调用方法进行更新
            for(int i=0; i<isChoose.size(); i++){
                Stage stage = new Stage();
                stage.setId(isChoose.get(i));
                stage.setEndTime(endTime.get(i));
                stage.setStartTime(startTime.get(i));
                stage.setStageFlag(isChoose.get(i));
                stage.setName(toGetStages.get(i));
                //这个地方就需要插入
                stageService.updateByPrimaryKey(stage);
                //这个是插入id  stage.getId();
                stages.add(stage);
            }
        }
        info.setAllStage(stages.stream().map(e->e.getId().toString()).collect(Collectors.joining(",")));
        //System.out.println(info);
        return info;
    }

    public static void main(String[] args){
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
