package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.service.ThreadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/threading")
public class ThreadingController {

    private final ThreadingService threadingService;

    public ThreadingController(ThreadingService threadingService) {
        this.threadingService = threadingService;
    }

    // Async processing endpoint
    @PostMapping("/async/process/{studentId}")
    public ResponseEntity<?> processStudentAsync(@PathVariable Long studentId) {
        CompletableFuture<String> future = threadingService.processStudentDataAsync(studentId);
        return ResponseEntity.ok("Processing started for student " + studentId + ". Check status later.");
    }

    // Batch async processing
    @PostMapping("/async/batch")
    public ResponseEntity<?> processBatchAsync() {
        List<CompletableFuture<String>> futures = threadingService.processBatchStudentsAsync();
        return ResponseEntity.ok("Batch processing started for " + futures.size() + " students");
    }

    // Custom thread processing
    @PostMapping("/custom-thread")
    public ResponseEntity<String> processWithCustomThread(@RequestBody Map<String, String> request) {
        String task = request.get("task");
        String result = threadingService.processWithCustomThread(task);
        return ResponseEntity.ok(result);
    }

    // ExecutorService processing
    @PostMapping("/executor")
    public ResponseEntity<List<String>> processWithExecutor(@RequestBody List<String> tasks) {
        List<String> results = threadingService.processWithExecutorService(tasks);
        return ResponseEntity.ok(results);
    }

    // Synchronization demo
    @PostMapping("/sync-demo")
    public ResponseEntity<String> demonstrateSynchronization() {
        String result = threadingService.demonstrateSynchronization();
        return ResponseEntity.ok(result);
    }

    // Get processing statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getProcessingStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("processedCount", threadingService.getProcessedCount());
        stats.put("activeThreads", Thread.activeCount());
        stats.put("currentThread", Thread.currentThread().getName());
        return ResponseEntity.ok(stats);
    }
}
