package com.bootcamp19.helloworld.exception;

public class AnalysisNotFoundException extends RuntimeException {
    public AnalysisNotFoundException(Long id) {
        super("Data dengan Id: ," + id + "tidak ditemukan");
    }
}
