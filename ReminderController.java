package com.jobtracker.controllers;

import com.jobtracker.models.JobApplication;
import com.jobtracker.models.Reminder;
import com.jobtracker.models.User;
import com.jobtracker.repositories.JobApplicationRepository;
import com.jobtracker.repositories.ReminderRepository;
import com.jobtracker.repositories.UserRepository;
import com.jobtracker.security.services.UserDetailsImpl;
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
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Reminder>> getAllReminders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<User> user = userRepository.findById(userDetails.getId());
        if (user.isPresent()) {
            List<Reminder> reminders = reminderRepository.findByJobApplicationUser(user.get());
            return ResponseEntity.ok(reminders);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Reminder> reminder = reminderRepository.findById(id);
        if (reminder.isPresent() && reminder.get().getJobApplication().getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.ok(reminder.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @PostMapping("/job/{jobId}")
    public ResponseEntity<Reminder> createReminder(@PathVariable Long jobId, @Valid @RequestBody Reminder reminder, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(jobId);
        if (jobApplication.isPresent() && jobApplication.get().getUser().getId().equals(userDetails.getId())) {
            reminder.setJobApplication(jobApplication.get());
            Reminder savedReminder = reminderRepository.save(reminder);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReminder);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable Long id, @Valid @RequestBody Reminder reminder, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Reminder> existingReminder = reminderRepository.findById(id);
        if (existingReminder.isPresent() && existingReminder.get().getJobApplication().getUser().getId().equals(userDetails.getId())) {
            reminder.setId(id);
            reminder.setJobApplication(existingReminder.get().getJobApplication());
            Reminder updatedReminder = reminderRepository.save(reminder);
            return ResponseEntity.ok(updatedReminder);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<Reminder> reminder = reminderRepository.findById(id);
        if (reminder.isPresent() && reminder.get().getJobApplication().getUser().getId().equals(userDetails.getId())) {
            reminderRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
