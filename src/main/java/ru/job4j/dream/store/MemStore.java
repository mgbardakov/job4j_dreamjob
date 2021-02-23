package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {

    private static final MemStore INST = new MemStore();

    private static final AtomicInteger POST_ID = new AtomicInteger(1);

    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(1);

    private static final AtomicInteger USER_ID = new AtomicInteger(1);

    private static final AtomicInteger PHOTO_ID = new AtomicInteger(1);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private MemStore() {
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.getAndIncrement());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.getAndIncrement());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Post findPostByID(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findCandidateByID(int id) {
        return candidates.get(id);
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public int registerPhotoID(int candidateID) {
        var rsl = PHOTO_ID.getAndIncrement();
        candidates.get(candidateID).setPhotoID(rsl);
        return rsl;
    }

    @Override
    public void deleteCandidateByID(int candidateID) {
        candidates.remove(candidateID);
    }

    @Override
    public void saveUser(User user) {
        users.put(USER_ID.getAndIncrement(), user);
    }

    @Override
    public User findUserByID(int id) {
        return users.get(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return users.values().stream().filter(x -> x.getEmail()
                .equals(email)).findAny().orElse(null);
    }

    @Override
    public Collection<City> getAllCities() {
        return null;
    }
}