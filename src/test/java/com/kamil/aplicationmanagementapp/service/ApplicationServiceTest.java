package com.kamil.aplicationmanagementapp.service;

import com.kamil.aplicationmanagementapp.exception.WrongStatusException;
import com.kamil.aplicationmanagementapp.dto.CreateApplicationDTO;
import com.kamil.aplicationmanagementapp.dto.DeleteApplicationDTO;
import com.kamil.aplicationmanagementapp.dto.RejectApplicationDTO;
import com.kamil.aplicationmanagementapp.dto.UpdateApplicationDTO;
import com.kamil.aplicationmanagementapp.entity.Application;
import com.kamil.aplicationmanagementapp.entity.Status;
import com.kamil.aplicationmanagementapp.repository.ApplicationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationService applicationService;

    Application applicationCreated;
    Application applicationDeleted;
    Application applicationVerified;
    Application applicationAccepted;
    Application applicationRejected;


    @BeforeEach
    void setUp() {
        applicationCreated = new Application(1L, "Test", "TestDescription", null, null, Status.CREATED);
        applicationDeleted = new Application(2L, "Test", "TestDescription", null, null, Status.DELETED);
        applicationVerified = new Application(3L, "Test", "TestDescription", null, null, Status.VERIFIED);
        applicationRejected = new Application(4L, "Test", "TestDescription", null, null, Status.REJECTED);
        applicationAccepted = new Application(5L, "Test", "TestDescription", null, null, Status.ACCEPTED);


        applicationRepository.save(applicationCreated);
        applicationRepository.save(applicationDeleted);
        applicationRepository.save(applicationVerified);
        applicationRepository.save(applicationRejected);
        applicationRepository.save(applicationAccepted);
    }

    @Test
    @DisplayName("Should create new application")
    public void createApplication() {

        CreateApplicationDTO createApplicationDTO = new CreateApplicationDTO();
        createApplicationDTO.setName("Test");
        createApplicationDTO.setDescription("TestDescription");

        Application createApplication = applicationService.createApplication(createApplicationDTO);
        Application application = applicationRepository.findById(1L).orElseThrow(WrongStatusException::new);

        assertEquals(createApplication.getName(), application.getName());
        assertEquals(application.getDescription(), createApplication.getDescription());
    }

    @Test
    @DisplayName("Application should change status from created to deleted")
    public void deleteApplication() {

        DeleteApplicationDTO deleteApplicationDTO = new DeleteApplicationDTO();
        deleteApplicationDTO.setReasonOfDelete("TestDelte");

        applicationService.deleteApplication(1L, deleteApplicationDTO);
        Application application = applicationRepository.findById(1L).orElseThrow(WrongStatusException::new);

        assertEquals(application.getStatus(), Status.DELETED);
        assertEquals(application.getReasonOfRejection(), deleteApplicationDTO.getReasonOfDelete());

    }

    @Test
    @DisplayName("Application should change description only for created and verified")
    public void updateApplication() {
        UpdateApplicationDTO updateApplicationDTO = new UpdateApplicationDTO();
        updateApplicationDTO.setDescription("NewDescription");

        applicationService.updateApplication(1L, updateApplicationDTO);
        applicationService.updateApplication(3L, updateApplicationDTO);
        Application applicationCreated = applicationRepository.findById(1L).orElseThrow(WrongStatusException::new);
        Application applicationVerified = applicationRepository.findById(3L).orElseThrow(WrongStatusException::new);

        assertEquals(applicationCreated.getDescription(), updateApplicationDTO.getDescription());
        assertEquals(applicationVerified.getDescription(), updateApplicationDTO.getDescription());


    }


    @Test
    @DisplayName("Application should change status from created to verified")
    public void verifyStatusApplication() {
        Application applicationVerify = applicationService.verifyStatusApplication(1L);
        Application application = applicationRepository.findById(1L).orElseThrow(WrongStatusException::new);

        assertEquals(application.getStatus(), Status.VERIFIED);

    }


    @Test
    @DisplayName("Application should change status from verified to rejected")
    public void rejectVerifiedStatusApplication() {
        RejectApplicationDTO rejectApplicationDTO = new RejectApplicationDTO();
        rejectApplicationDTO.setReasonOfReject("ReasonTestOfReject");

        Application rejectApplication = applicationService.rejectStatusApplication(3L, rejectApplicationDTO);
        Application application = applicationRepository.findById(3L).orElseThrow(WrongStatusException::new);

        assertEquals(application.getStatus(), Status.REJECTED);
        assertEquals(application.getReasonOfRejection(), rejectApplication.getReasonOfRejection());

    }


    @Test
    @DisplayName("Application should change status from verified to accepted")
    public void acceptStatusApplication() {
        Application applicationAccept = applicationService.acceptStatusApplication(3L);
        Application application = applicationRepository.findById(3L).orElseThrow(WrongStatusException::new);

        assertEquals(application.getStatus(), Status.ACCEPTED);

    }


    @Test
    @DisplayName("Application should change status from accepted to rejected")
    public void rejectAcceptedStatusApplication() {
        RejectApplicationDTO rejectApplicationDTO = new RejectApplicationDTO();
        rejectApplicationDTO.setReasonOfReject("ReasonTestOfReject");
        Application applicationReject = applicationService.rejectStatusApplication(5L, rejectApplicationDTO);
        Application application = applicationRepository.findById(5L).orElseThrow(WrongStatusException::new);

        assertEquals(application.getStatus(), Status.REJECTED);
        assertEquals(application.getReasonOfRejection(), applicationReject.getReasonOfRejection());

    }

    @Test
    @DisplayName("Application should change status from accepted to publish")
    public void publishStatusApplication() {

        Application applicationStatus = applicationService.publishStatusApplication(5L);
        Application application = applicationRepository.findById(5L).orElseThrow(WrongStatusException::new);


        assertNotNull(application.getUuid());
        assertEquals(application.getStatus(), Status.PUBLISH);
    }
}
