package com.his.title.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.common.exception.BusinessException;
import com.his.title.entity.TitleApplication;
import com.his.title.entity.TitleReview;
import com.his.title.mapper.TitleApplicationMapper;
import com.his.title.service.TitleApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TitleApplicationServiceImpl extends ServiceImpl<TitleApplicationMapper, TitleApplication>
        implements TitleApplicationService {

    private final com.his.title.mapper.TitleReviewMapper titleReviewMapper;

    // status: 1=待提交, 2=已提交, 3=科室审核, 4=人事审核, 5=评审委员会, 6=已通过, 7=已驳回

    @Override
    @Transactional
    public void submit(Long id) {
        TitleApplication app = getById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        if (app.getStatus() != 1) {
            throw new BusinessException("当前状态不允许提交");
        }
        app.setStatus(2);
        updateById(app);
    }

    @Override
    @Transactional
    public void deptReview(Long id, String opinion, boolean pass) {
        TitleApplication app = getById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        if (app.getStatus() != 2) {
            throw new BusinessException("当前状态不允许科室审核");
        }
        app.setDeptOpinion(opinion);
        if (pass) {
            app.setStatus(3);
        } else {
            app.setStatus(7);
        }
        updateById(app);

        TitleReview review = new TitleReview();
        review.setApplicationId(id);
        review.setReviewType("dept");
        review.setOpinion(opinion);
        review.setResult(pass ? "通过" : "驳回");
        review.setReviewTime(LocalDateTime.now());
        titleReviewMapper.insert(review);
    }

    @Override
    @Transactional
    public void hrReview(Long id, String opinion, boolean pass) {
        TitleApplication app = getById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        if (app.getStatus() != 3) {
            throw new BusinessException("当前状态不允许人事审核");
        }
        app.setHrOpinion(opinion);
        if (pass) {
            app.setStatus(4);
        } else {
            app.setStatus(7);
        }
        updateById(app);

        TitleReview review = new TitleReview();
        review.setApplicationId(id);
        review.setReviewType("hr");
        review.setOpinion(opinion);
        review.setResult(pass ? "通过" : "驳回");
        review.setReviewTime(LocalDateTime.now());
        titleReviewMapper.insert(review);
    }

    @Override
    @Transactional
    public void committeeReview(Long id, String opinion, boolean pass, int score) {
        TitleApplication app = getById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        if (app.getStatus() != 4) {
            throw new BusinessException("当前状态不允许评审委员会审核");
        }
        app.setCommitteeOpinion(opinion);
        if (pass) {
            app.setStatus(6);
        } else {
            app.setStatus(7);
        }
        updateById(app);

        TitleReview review = new TitleReview();
        review.setApplicationId(id);
        review.setReviewType("committee");
        review.setOpinion(opinion);
        review.setScore(score);
        review.setResult(pass ? "通过" : "驳回");
        review.setReviewTime(LocalDateTime.now());
        titleReviewMapper.insert(review);
    }
}
