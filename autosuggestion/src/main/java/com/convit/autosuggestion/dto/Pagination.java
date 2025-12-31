package com.convit.autosuggestion.dto;

public record Pagination(int page,
                         int size,
                         long totalElements,
                         int totalPages) {
}