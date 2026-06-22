package com.project.secondBrain.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ArchiveProcessingService {
    @Async
    public void processArchive(Long archiveId)
    {
        System.out.println("Processing archive" + archiveId);

        try{
            Thread.sleep(4000);

        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }

        System.out.println("Finished proecessing archive" + archiveId);
    }
}
