package com.yupi.yupao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.yupao.model.domain.Team;
import com.yupi.yupao.model.domain.UserTeam;
import com.yupi.yupao.service.TeamService;
import com.yupi.yupao.service.UserTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MyApplicationTest {
    @Autowired
    private  TeamService teamService;
    @Autowired
    private  UserTeamService userTeamService;

    @Test
    void test1() {
        // 1.查询所有队伍
        List<Team> teamList = teamService.list();
        for (Team team : teamList) {
            // 获取队伍id和创建人id
            long teamId = team.getId();
            long userId = team.getUserId();
            // 查询用户队伍表，判断是否已经有关联关系
            QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teamId", teamId);
            queryWrapper.eq("userId", userId);
            long count = userTeamService.count(queryWrapper);
            if (count > 0) {
                // 已经有关系
                continue;
            }
            // 创建关系
            UserTeam userTeam = new UserTeam();
            userTeam.setTeamId(teamId);
            userTeam.setUserId(userId);
            userTeam.setCreateTime(new Date());
            userTeam.setUpdateTime(new Date());
            userTeam.setJoinTime(new Date());
            boolean result = userTeamService.save(userTeam);
        }

//    @Test
//    void testDigest() throws NoSuchAlgorithmException {
//        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
//        System.out.println(newPassword);
//    }
//
//    @Test
//    void contextLoads() {
//
//    }
//
//    @Test
//    void create_user_team_relation() {
//        // 1.查询所有队伍
//        List<Team> teamList = teamService.list();
//        for (Team team : teamList) {
//            // 获取队伍id和创建人id
//            long teamId = team.getId();
//            long userId = team.getUserId();
//            // 查询用户队伍表，判断是否已经有关联关系
//            QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("teamId", teamId);
//            queryWrapper.eq("userId", userId);
//            long count = userTeamService.count(queryWrapper);
//            if (count > 0){
//                // 已经有关系
//                continue;
//            }
//            // 创建关系
//            UserTeam userTeam = new UserTeam();
//            userTeam.setTeamId(teamId);
//            userTeam.setUserId(userId);
//            userTeam.setCreateTime(new Date());
//            userTeam.setUpdateTime(new Date());
//            userTeam.setJoinTime(new Date());
//            boolean result = userTeamService.save(userTeam);
//
//
//        }
//    }

    }
}
