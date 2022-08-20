package com.kamil.aplicationmanagementapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryOfApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status currentStatus;
    @Enumerated(EnumType.STRING)
    private Status previousStatus;
    private LocalDateTime dateOfChange;
    @ManyToOne
    private Application application;

}
