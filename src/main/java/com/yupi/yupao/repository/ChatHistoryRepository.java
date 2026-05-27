package com.yupi.yupao.repository;

import java.util.List;

public interface ChatHistoryRepository {
    /*
    * type  业务类型 chat service pdf
    * chatId  会话id
    * */
    void save(String type,String chatId);

    /**
     * 获取会话id列表
     * @param type 业务类型  chat service pdf
     * @return 会话id列表
     */
    List<String> getCharIds(String type);

}
