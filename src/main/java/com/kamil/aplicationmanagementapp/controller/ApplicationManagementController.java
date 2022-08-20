package com.kamil.aplicationmanagementapp.controller;


import com.kamil.aplicationmanagementapp.dto.*;
import com.kamil.aplicationmanagementapp.entity.Application;
import com.kamil.aplicationmanagementapp.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class ApplicationManagementController {

    private final ApplicationService applicationService;


    @PostMapping("/create")
    public Application createApplication(@RequestBody CreateApplicationDTO createApplicationDTO) {
       return this.applicationService.createApplication(createApplicationDTO);
    }

    @PutMapping("/update/{id}")
    public void updateApplication(@PathVariable Long id, @RequestBody UpdateApplicationDTO updateApplicationDto) {
        this.applicationService.updateApplication(id, updateApplicationDto);
    }

    @PutMapping("/reject/{id}")
    public Application rejectApplication(@PathVariable Long id, @RequestBody RejectApplicationDTO rejectApplicationDto) {
      return  this.applicationService.rejectStatusApplication(id, rejectApplicationDto);
    }

    @PutMapping("/delete/{id}")
    public void deleteApplication(@PathVariable Long id, @RequestBody DeleteApplicationDTO deleteApplicationDTO) {
        this.applicationService.deleteApplication(id, deleteApplicationDTO);
    }

    @PutMapping("/verify/{id}")
    public Application verifyStatusApplication(@PathVariable Long id) {
        return this.applicationService.verifyStatusApplication(id);
    }

    @PutMapping("/accept/{id}")
    public Application acceptApplication(@PathVariable Long id) {
       return this.applicationService.acceptStatusApplication(id);
    }

    @PutMapping("/publish/{id}")
    public Application publishApplication(@PathVariable Long id) {
        return this.applicationService.publishStatusApplication(id);
    }
}
