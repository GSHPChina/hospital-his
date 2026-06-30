package com.his.research.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.common.exception.BusinessException;
import com.his.common.result.R;
import com.his.research.entity.ResearchAchievement;
import com.his.research.entity.ResearchMember;
import com.his.research.entity.ResearchPaper;
import com.his.research.entity.ResearchProject;
import com.his.research.mapper.ResearchAchievementMapper;
import com.his.research.mapper.ResearchMemberMapper;
import com.his.research.service.ResearchPaperService;
import com.his.research.service.ResearchProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/research")
@RequiredArgsConstructor
public class ResearchController {

    private final ResearchProjectService projectService;
    private final ResearchPaperService paperService;
    private final ResearchMemberMapper memberMapper;
    private final ResearchAchievementMapper achievementMapper;

    // ==================== 项目管理 ====================

    @GetMapping("/projects")
    public R<List<ResearchProject>> listProjects(@RequestParam(required = false) String type,
                                                 @RequestParam(required = false) Integer status) {
        LambdaQueryWrapper<ResearchProject> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(ResearchProject::getType, type);
        }
        if (status != null) {
            wrapper.eq(ResearchProject::getStatus, status);
        }
        wrapper.orderByDesc(ResearchProject::getCreateTime);
        return R.ok(projectService.list(wrapper));
    }

    @PostMapping("/projects")
    public R<Void> addProject(@RequestBody ResearchProject project) {
        project.setProjectNo("PRJ" + IdUtil.getSnowflakeNextIdStr());
        project.setStatus(0);
        projectService.save(project);
        return R.ok();
    }

    @PutMapping("/projects/{id}")
    public R<Void> updateProject(@PathVariable Long id, @RequestBody ResearchProject project) {
        project.setId(id);
        projectService.updateById(project);
        return R.ok();
    }

    @PutMapping("/projects/{id}/status")
    public R<Void> updateProjectStatus(@PathVariable Long id, @RequestParam Integer status) {
        ResearchProject project = projectService.getById(id);
        if (project == null) {
            throw new BusinessException("科研项目不存在");
        }
        project.setStatus(status);
        projectService.updateById(project);
        return R.ok();
    }

    // ==================== 论文管理 ====================

    @GetMapping("/papers")
    public R<List<ResearchPaper>> listPapers(@RequestParam(required = false) String level) {
        LambdaQueryWrapper<ResearchPaper> wrapper = new LambdaQueryWrapper<>();
        if (level != null) {
            wrapper.eq(ResearchPaper::getLevel, level);
        }
        wrapper.orderByDesc(ResearchPaper::getCreateTime);
        return R.ok(paperService.list(wrapper));
    }

    @PostMapping("/papers")
    public R<Void> addPaper(@RequestBody ResearchPaper paper) {
        paper.setStatus(0);
        paperService.save(paper);
        return R.ok();
    }

    // ==================== 成果管理 ====================

    @GetMapping("/achievements")
    public R<List<ResearchAchievement>> listAchievements(@RequestParam(required = false) String type) {
        LambdaQueryWrapper<ResearchAchievement> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(ResearchAchievement::getType, type);
        }
        wrapper.orderByDesc(ResearchAchievement::getCreateTime);
        return R.ok(achievementMapper.selectList(wrapper));
    }

    @PostMapping("/achievements")
    public R<Void> addAchievement(@RequestBody ResearchAchievement achievement) {
        achievementMapper.insert(achievement);
        return R.ok();
    }
}
