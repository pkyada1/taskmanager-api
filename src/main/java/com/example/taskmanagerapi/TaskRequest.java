package com.example.taskmanagerapi;

public record TaskRequest(
    String title,
    String description,
    String dueDate
) {}

