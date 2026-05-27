package com.yupi.yupao.Tools;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.yupao.model.domain.User;
import com.yupi.yupao.model.vo.UserVO;
import com.yupi.yupao.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMatchTools {

    private final UserService userService;
    private final Gson gson = new Gson();
    
    @Tool(description = "获取指定用户的标签列表。用于了解用户的兴趣、技能等特征。")
    public List<String> getCurrentUserTags(
        @ToolParam(description = "用户ID") Long userId
    ) {
        User user = userService.getById(userId);
        if (user == null || StringUtils.isBlank(user.getTags())) {
            return Collections.emptyList();
        }
        return gson.fromJson(user.getTags(), new TypeToken<List<String>>() {}.getType());
    }
    
    @Tool(description = "获取所有候选用户的基本信息（排除指定用户）。用于交友匹配场景，返回所有可供匹配的用户列表。")
    public List<CandidateUser> getAllCandidateUsers(
        @ToolParam(description = "要排除的用户ID（当前用户）") Long excludeUserId
    ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "username", "tags");
        queryWrapper.ne("id", excludeUserId);
        queryWrapper.isNotNull("tags");
        List<User> allUsers = userService.list(queryWrapper);
        
        if (CollectionUtils.isEmpty(allUsers)) {
            return Collections.emptyList();
        }
        
        return allUsers.stream()
            .map(user -> {
                List<String> tags = StringUtils.isNotBlank(user.getTags()) 
                    ? gson.fromJson(user.getTags(), new TypeToken<List<String>>() {}.getType())
                    : Collections.emptyList();
                return new CandidateUser(user.getId(), user.getUsername(), tags);
            })
            .collect(Collectors.toList());
    }
    
    @Tool(description = "根据用户ID列表查询完整的用户信息。返回包含用户名、头像、标签等完整信息的用户列表。")
    public List<UserVO> getUserByIds(
        @ToolParam(description = "用户ID列表") List<Long> userIds
    ) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIds);
        List<User> users = userService.list(queryWrapper);
        
        return users.stream()
            .map(user -> {
                UserVO vo = new UserVO();
                BeanUtils.copyProperties(user, vo);
                return vo;
            })
            .collect(Collectors.toList());
    }
    
    public record CandidateUser(Long id, String username, List<String> tags) {}
}
