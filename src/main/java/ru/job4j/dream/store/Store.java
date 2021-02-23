package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    void savePost(Post post);
    Collection<Post> findAllPosts();
    Post findPostByID(int id);
    void saveCandidate(Candidate candidate);
    Collection<Candidate> findAllCandidates();
    Candidate findCandidateByID(int id);
    void deleteCandidateByID(int candidateID);
    int registerPhotoID(int candidateID);
    void saveUser(User user);
    User findUserByID(int id);
    User findUserByEmail(String email);
    Collection<City> getAllCities();

}
