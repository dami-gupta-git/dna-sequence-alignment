package alignment.controllers;

import alignment.model.AlignmentQuery;
import alignment.model.QueryMatch;
import alignment.services.AlignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * REST controller object
 */
@RestController
@CrossOrigin("*")
public class AlignmentRestController {

    // Executor service for task threads
    ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    AlignmentService alignmentService;

    @RequestMapping("/")
    public String home() {
        return "You have reached the Alignment App";
    }

    /**
     * Asynchronous implementation of query alignment request
     *
     * @param query
     * @return
     * @exception ExecutionException
     * @exception InterruptedException
     */
    @PostMapping("/alignAsync")
    public QueryMatch doAlignAsync(@RequestBody AlignmentQuery query) throws ExecutionException, InterruptedException {
        Future<QueryMatch> completableFuture = doAlignTask(query); QueryMatch match = completableFuture.get();
        return match;
    }

    /**
     * Alignment task
     *
     * @param query
     * @return
     */
    private CompletableFuture<QueryMatch> doAlignTask(AlignmentQuery query) {
        CompletableFuture<QueryMatch> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            QueryMatch match = alignmentService.doAlignment(query.getSequence()); completableFuture.complete(match);
            return match;
        }); return completableFuture;
    }

    /**
     * Vanilla implementation of alignment query
     *
     * @param query
     * @return
     */
    @PostMapping("/align")
    public QueryMatch alignmentPost(@RequestBody AlignmentQuery query) {
        try {
            QueryMatch match = alignmentService.doAlignment(query.getSequence()); return match;

        } catch (Exception e) {
            e.printStackTrace();
        } return null;
    }
}


