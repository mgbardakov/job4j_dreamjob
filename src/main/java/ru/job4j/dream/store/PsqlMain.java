package ru.job4j.dream.store;

import ru.job4j.dream.exception.ObjectNotFoundException;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) throws ObjectNotFoundException {
        Store store = PsqlStore.instOf();
        store.savePost(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        var post = store.findPostByID(1);
        System.out.println(post.getId() + " " + post.getName());
        store.saveCandidate(new Candidate(0, "Java Junior"));
        for (Candidate can : store.findAllCandidates()) {
            System.out.println(can.getId() + " " + can.getName());
        }
        var can = store.findCandidateByID(1);
        System.out.println(can.getId() + " " + can.getName());
    }
}