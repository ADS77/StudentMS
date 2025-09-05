package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ThreadingService {
    private final StudentRepository studentRepository;
    private final FileService fileService;

    public ThreadingService(StudentRepository studentRepository, FileService fileService) {
        this.studentRepository = studentRepository;
        this.fileService = fileService;
    }
    // Thread-safe counter for demo
    private final AtomicInteger processedCount = new AtomicInteger(0);
    // ExecutorService for manual thread management
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    // This Async method runs in separate thread
    @Async
    public CompletableFuture<String> processStudentDataAsync(Long studentId) {
        try {
            System.out.println("Processing student " + studentId + " on thread: " + Thread.currentThread().getName());
            Thread.sleep(2000);
            Optional<Student> student = studentRepository.findById(studentId);
            if (student.isPresent()) {
                processedCount.incrementAndGet();
                return CompletableFuture.completedFuture("Processed student: " + student.get().getName());
            } else {
                return CompletableFuture.completedFuture("Student not found");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Processing interrupted");
        }
    }

    // This method will be processed in batch with multiple threads
    public List<CompletableFuture<String>> processBatchStudentsAsync() {
        List<Student> students = studentRepository.findAll();
        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (Student student : students) {
            futures.add(processStudentDataAsync(student.getId()));
        }

        return futures;
    }

    // This time we are creating threads manually
    public String processWithCustomThread(String task) {
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder result = new StringBuilder();
        Thread customThread = new Thread(() -> {
            try {
                System.out.println("Custom thread processing: " + task);
                Thread.sleep(1000);
                result.append("Task '").append(task).append("' completed by thread: ").append(Thread.currentThread().getName());
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                result.append("Task interrupted");
                latch.countDown();
            }
        }, "CustomStudentProcessor");
        customThread.start();
        try {
            latch.await(5, TimeUnit.SECONDS); // Wait 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Processing timeout";
        }
        return result.toString();
    }


    public List<String> processWithExecutorService(List<String> tasks) {
        List<Future<String>> futures = new ArrayList<>();

        for (String task : tasks) {
            Future<String> future = executorService.submit(() -> {
                try {
                    Thread.sleep(500);
                    return "Processed: " + task + " by " + Thread.currentThread().getName();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return "Task interrupted: " + task;
                }
            });
            futures.add(future);
        }
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                results.add(future.get(3, TimeUnit.SECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                results.add("Task failed: " + e.getMessage());
            }
        }
        return results;
    }

    //This is a scheduled task and runs periodically in every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void scheduledStudentCheck() {
        System.out.println("Scheduled check running on thread: " + Thread.currentThread().getName());
        long studentCount = studentRepository.count();
        System.out.println("Current student count: " + studentCount);
        System.out.println("Processed count since startup: " + processedCount.get());
    }


    //And the following method explains thread synchronization
    private final Object lock = new Object();
    private int synchronizedCounter = 0;
    public String demonstrateSynchronization() {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    synchronized (lock) { // incrementing counter  within  a synchronized block
                        synchronizedCounter++;
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
        // Waiting for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Synchronization test interrupted";
            }
        }
        return "Synchronization test completed. Final counter value: " + synchronizedCounter;
    }


    public int getProcessedCount() {
        return processedCount.get();
    }


}
