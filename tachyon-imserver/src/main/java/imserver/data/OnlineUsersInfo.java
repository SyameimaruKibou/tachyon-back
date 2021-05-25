package imserver.data;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OnlineUsersInfo {
    // user和group映射
    final private Map<Long,Long> userGroup;
    // group内user的在线情况
    final private Map<Long,Set<Long>> groupUsers;
    // user和session映射
    private final Map<Long,UUID> userSession;
    // session和user映射
    private final Map<UUID,Long> sessionUser;
    public OnlineUsersInfo() {
        userSession = new HashMap<>();
        userGroup = new HashMap<>();
        sessionUser = new HashMap<>();
        groupUsers = new HashMap<>();
    }

    public Long getGroup(Long userId) {
        return userGroup.get(userId);
    }

    public UUID getSessionInfo(Long userId) { return userSession.get(userId); }

    public void putGroup(Long userId, Long groupId) {
        // 如果 映射关系中未包括这样的关系，那么加入关系
        if (!userGroup.containsKey(userId)){
            userGroup.put(userId, groupId);
        }

        // 如果 在线状态中还未包括这个group,那么新建一个set容器存储在线人数状态
        if (!groupUsers.containsKey(groupId)) {
            groupUsers.put(groupId, new HashSet<>());
        }
        groupUsers.get(groupId).add(userId);
    }

    public void putSession(Long userId, UUID client) {
        sessionUser.put(client, userId);
        userSession.put(userId, client);
    }

    public boolean isUserInfoChanged(Long userId, UUID sessionId) {
        if (userGroup.containsKey(userId) && userSession.containsKey(userId))
            return (userSession.get(userId).toString()).equals(sessionId.toString());
        else {
            // 如果原本未包括该userId信息，那么也返回false
            return false;
        }
    }

    public Set<Long> getOnlineUsersByGroupId(Long groupId) {
        return groupUsers.get(groupId);
    }

    public Set<Long> getOnLineUsersBySessionId(UUID sessionId) {
        return groupUsers.get(userGroup.get(sessionUser.get(sessionId)));
    }

    public void userLeave(UUID client) {
        Long userId = sessionUser.get(client);
        // 删除user和session对应关系
        userSession.remove(userId);
        sessionUser.remove(client);
        // 退出在线状态
        groupUsers.get(userGroup.get(userId)).remove(userId);
    }
}
