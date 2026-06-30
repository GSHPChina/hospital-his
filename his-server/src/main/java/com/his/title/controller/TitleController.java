package com.his.title.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.result.R;
import com.his.title.entity.TitleApplication;
import com.his.title.entity.TitleLevel;
import com.his.title.service.TitleApplicationService;
import com.his.title.service.TitleLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/title")
@RequiredArgsConstructor
public class TitleController {

    private final TitleLevelService titleLevelService;
    private final TitleApplicationService titleApplicationService;

    @GetMapping("/levels")
    public R<List<TitleLevel>> listLevels() {
        return R.ok(titleLevelService.list());
    }

    @GetMapping("/applications")
    public R<List<TitleApplication>> listApplications(@RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<TitleApplication> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(TitleApplication::getStatus, status);
        }
        wrapper.orderByDesc(TitleApplication::getCreateTime);
        return R.ok(titleApplicationService.list(wrapper));
    }

    @GetMapping("/applications/{id}")
    public R<TitleApplication> getApplication(@PathVariable Long id) {
        return R.ok(titleApplicationService.getById(id));
    }

    @PostMapping("/applications")
    public R<Void> addApplication(@RequestBody TitleApplication application) {
        application.setStatus(1);
        titleApplicationService.save(application);
        return R.ok();
    }

    @PutMapping("/applications/{id}/submit")
    public R<Void> submit(@PathVariable Long id) {
        titleApplicationService.submit(id);
        return R.ok();
    }

    @PutMapping("/applications/{id}/dept-review")
    public R<Void> deptReview(@PathVariable Long id,
                              @RequestParam String opinion,
                              @RequestParam boolean pass) {
        titleApplicationService.deptReview(id, opinion, pass);
        return R.ok();
    }

    @PutMapping("/applications/{id}/hr-review")
    public R<Void> hrReview(@PathVariable Long id,
                            @RequestParam String opinion,
                            @RequestParam boolean pass) {
        titleApplicationService.hrReview(id, opinion, pass);
        return R.ok();
    }

    @PutMapping("/applications/{id}/committee-review")
    public R<Void> committeeReview(@PathVariable Long id,
                                   @RequestParam String opinion,
                                   @RequestParam boolean pass,
                                   @RequestParam int score) {
        titleApplicationService.committeeReview(id, opinion, pass, score);
        return R.ok();
    }
}
