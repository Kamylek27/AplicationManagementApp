package com.kamil.aplicationmanagementapp.service;

import com.kamil.aplicationmanagementapp.exception.WrongStatusException;
import com.kamil.aplicationmanagementapp.dto.*;
import com.kamil.aplicationmanagementapp.entity.HistoryOfApplication;
import com.kamil.aplicationmanagementapp.entity.Application;
import com.kamil.aplicationmanagementapp.entity.Status;
import com.kamil.aplicationmanagementapp.repository.HistoryOfApplicationRepository;
import com.kamil.aplicationmanagementapp.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final HistoryOfApplicationRepository historyOfApplicationRepository;


    public Application createApplication(CreateApplicationDTO createApplication) {
        Application application = new Application();

        application.setName(createApplication.getName());
        application.setDescription(createApplication.getDescription());
        application.setStatus(Status.CREATED);

        HistoryOfApplication historyOfApplication = createHistory(application);

        historyOfApplicationRepository.save(historyOfApplication);
        return applicationRepository.save(application);

    }

    public void deleteApplication(Long id, DeleteApplicationDTO deleteApplication) {
        Application application = applicationRepository.findById(id).stream().filter(m -> m.getStatus().equals(Status.CREATED))
                .findAny().orElseThrow(WrongStatusException::new);

        HistoryOfApplication historyOfApplication = createHistory(application);
        historyOfApplication.setCurrentStatus(Status.DELETED);

        application.setReasonOfRejection(deleteApplication.getReasonOfDelete());
        application.setStatus(Status.DELETED);

        historyOfApplicationRepository.save(historyOfApplication);
        applicationRepository.save(application);
    }

    public void updateApplication(Long id, UpdateApplicationDTO updateApplication) {
        Application application = applicationRepository.findById(id).stream().filter(m -> m.getStatus().equals(Status.CREATED)
                || m.getStatus().equals(Status.VERIFIED)).findAny().orElseThrow(WrongStatusException::new);

        application.setDescription(updateApplication.getDescription());
        applicationRepository.save(application);
    }

    public Application verifyStatusApplication(Long id) {
        Application application = applicationRepository.findById(id).stream().filter(m -> m.getStatus().equals(Status.CREATED))
                .findAny().orElseThrow(WrongStatusException::new);

        HistoryOfApplication historyOfApplication = createHistory(application);
        historyOfApplication.setCurrentStatus(Status.VERIFIED);

        application.setStatus(Status.VERIFIED);

        historyOfApplicationRepository.save(historyOfApplication);
        return applicationRepository.save(application);

    }

    public Application acceptStatusApplication(Long id) {
        Application application = applicationRepository.findById(id).stream().filter(m -> m.getStatus().equals(Status.VERIFIED))
                .findAny().orElseThrow(WrongStatusException::new);

        HistoryOfApplication historyOfApplication = createHistory(application);
        historyOfApplication.setCurrentStatus(Status.ACCEPTED);
        application.setStatus(Status.ACCEPTED);

        historyOfApplicationRepository.save(historyOfApplication);
        return applicationRepository.save(application);

    }

    public Application publishStatusApplication(Long id) {
        Application application = applicationRepository.findById(id).stream().filter(m -> m.getStatus().equals(Status.ACCEPTED))
                .findAny().orElseThrow(WrongStatusException::new);

        HistoryOfApplication historyOfApplication = createHistory(application);
        historyOfApplication.setCurrentStatus(Status.PUBLISH);

        application.setStatus(Status.PUBLISH);
        UUID uuid = UUID.randomUUID();
        application.setUuid(uuid);

        historyOfApplicationRepository.save(historyOfApplication);
        return applicationRepository.save(application);

    }

    public Application rejectStatusApplication(Long id, RejectApplicationDTO rejectApplicationDto) {
        Application application = applicationRepository.findById(id).stream().filter(m -> m.getStatus().equals(Status.VERIFIED)
                        || m.getStatus().equals(Status.ACCEPTED))
                .findAny().orElseThrow(WrongStatusException::new);

        HistoryOfApplication historyOfApplication = createHistory(application);
        historyOfApplication.setCurrentStatus(Status.REJECTED);

        application.setReasonOfRejection(rejectApplicationDto.getReasonOfReject());
        application.setStatus(Status.REJECTED);

        historyOfApplicationRepository.save(historyOfApplication);
        return applicationRepository.save(application);

    }


    private HistoryOfApplication createHistory(Application application) {
        HistoryOfApplication historyOfApplication = new HistoryOfApplication();
        historyOfApplication.setApplication(application);
        historyOfApplication.setPreviousStatus(application.getStatus());
        historyOfApplication.setDateOfChange(LocalDateTime.now().withNano(0));
        historyOfApplication.setName(application.getName());
        return historyOfApplication;
    }

}
