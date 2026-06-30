package com.his.title.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.title.entity.TitleApplication;

public interface TitleApplicationService extends IService<TitleApplication> {

    void submit(Long id);

    void deptReview(Long id, String opinion, boolean pass);

    void hrReview(Long id, String opinion, boolean pass);

    void committeeReview(Long id, String opinion, boolean pass, int score);
}
