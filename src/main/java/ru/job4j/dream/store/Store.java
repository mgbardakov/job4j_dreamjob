package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;

public interface Store {
    void savePost(Post post);
    void saveCandidate(Candidate candidate);
    Collection<Post> findAllPosts();
    Post findPostByID(int id);
    Collection<Candidate> findAllCandidates();
    Candidate findCandidateByID(int id);
    int registerPhotoID(int candidateID);
    void deleteCandidateByID(int candidateID);
}
