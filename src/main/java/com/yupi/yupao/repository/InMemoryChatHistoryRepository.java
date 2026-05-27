package com.yupi.yupao.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class InMemoryChatHistoryRepository implements ChatHistoryRepository {
    private final Map<String,List<String>> charHistory = new HashMap<>();
    @Override
    public void save(String type, String chatId) {
        List<String> chatIds = charHistory.computeIfAbsent(type, k -> new ArrayList<>());
        if (chatIds.contains(chatId)) {
            return;
        }
        chatIds.add(chatId);
    }

    @Override
    public List<String> getCharIds(String type) {
        return charHistory.getOrDefault(type, List.of());
    }
}
