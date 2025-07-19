package com.jobtracker.controllers;

import com.jobtracker.models.JobApplication;
import com.jobtracker.models.User;
import com.jobtracker.repositories.UserRepository;
import com.jobtracker.security.services.UserDetailsImpl;
import com.jobtracker.services.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isPresent()) {
            List<JobApplication> applications = jobApplicationService.findAllByUser(user.get());
            return ResponseEntity.ok(applications);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<JobApplication> jobApplication = jobApplicationService.findById(id);
        if (jobApplication.isPresent() && jobApplication.get().getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.ok(jobApplication.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<JobApplication> createJobApplication(@Valid @RequestBody JobApplication jobApplication, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isPresent()) {
            jobApplication.setUser(user.get());
            JobApplication savedApplication = jobApplicationService.save(jobApplication);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateJobApplication(@PathVariable Long id, @Valid @RequestBody JobApplication jobApplication, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<JobApplication> existingJobApplication = jobApplicationService.findById(id);
        if (existingJobApplication.isPresent() && existingJobApplication.get().getUser().getId().equals(userDetails.getId())) {
            jobApplication.setId(id);
            jobApplication.setUser(existingJobApplication.get().getUser());
            
            if (jobApplication.getReminders() == null) {
                jobApplication.setReminders(existingJobApplication.get().getReminders());
            }
            if (jobApplication.getInterviews() == null) {
                jobApplication.setInterviews(existingJobApplication.get().getInterviews());
            }
            if (jobApplication.getNotes() == null) {
                jobApplication.setNotes(existingJobApplication.get().getNotes());
            }
            
            JobApplication updatedApplication = jobApplicationService.save(jobApplication);
            return ResponseEntity.ok(updatedApplication);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<JobApplication> jobApplication = jobApplicationService.findById(id);
        if (jobApplication.isPresent() && jobApplication.get().getUser().getId().equals(userDetails.getId())) {
            jobApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobApplication>> getJobApplicationsByStatus(@PathVariable String status, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isPresent()) {
            try {
                JobApplication.ApplicationStatus applicationStatus = JobApplication.ApplicationStatus.valueOf(status.toUpperCase());
                List<JobApplication> applications = jobApplicationService.findByUserAndStatus(user.get(), applicationStatus);
                return ResponseEntity.ok(applications);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
