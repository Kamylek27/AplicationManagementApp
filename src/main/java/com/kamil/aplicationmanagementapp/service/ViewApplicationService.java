package com.kamil.aplicationmanagementapp.service;

import com.kamil.aplicationmanagementapp.entity.Application;
import com.kamil.aplicationmanagementapp.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ViewApplicationService {
    private final static int OFFSET = 0;
    private final static int PAGESIZE = 10;
    private final ApplicationRepository applicationRepository;

    public Page<Application> findApplicationsWithPagination(int offset, int pageSize) {
        return applicationRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<Application> findApplicationsWithPaginationByStatusAndName() {
        applicationRepository.findAll(PageRequest.of(OFFSET, PAGESIZE).withSort(Sort.by("Status").and(Sort.by("Name")))).forEach(System.out::println);
        return applicationRepository.findAll(PageRequest.of(OFFSET, PAGESIZE).withSort(Sort.by("Status").and(Sort.by("Name"))));
    }

}
