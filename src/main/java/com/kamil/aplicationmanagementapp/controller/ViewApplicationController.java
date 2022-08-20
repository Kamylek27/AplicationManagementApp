package com.kamil.aplicationmanagementapp.controller;

import com.kamil.aplicationmanagementapp.dto.APIResponse;
import com.kamil.aplicationmanagementapp.entity.Application;
import com.kamil.aplicationmanagementapp.service.ViewApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pagination")
public class ViewApplicationController {

    private final ViewApplicationService viewApplicationService;

    @GetMapping()
    public APIResponse<Page<Application>> getPaginationApplicationsByStatusAndName() {
        Page<Application> allApplications = viewApplicationService.findApplicationsWithPaginationByStatusAndName();
        return new APIResponse<>(allApplications.getSize(), allApplications);
    }

    @GetMapping("/{offset}/{pageSize}")
    public APIResponse<Page<Application>> getPaginationApplications(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Application> allApplications = viewApplicationService.findApplicationsWithPagination(offset, pageSize);
        return new APIResponse<>(allApplications.getSize(), allApplications);
    }
}

