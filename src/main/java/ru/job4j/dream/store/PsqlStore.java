package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private static final Logger LOGGER = LogManager.getLogger();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("db.properties"))
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> cans = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cans.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return cans;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    @Override
    public Post findPostByID(int id) {
        Post rslPost = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT name FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            ps.execute();
            var rslSet = ps.getResultSet();
            if (rslSet.next()) {
                rslPost = new Post(id, rslSet.getString(1));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rslPost;
    }

    @Override
    public Candidate findCandidateByID(int id) {
        Candidate rslCandidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "SELECT name, photo_id FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            ps.execute();
            var rslSet = ps.getResultSet();
            if (rslSet.next()) {
                rslCandidate = new Candidate(id, rslSet.getString("name"),
                        Integer.parseInt(rslSet.getString("photo_id")));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rslCandidate;
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return post;
    }

    private void updatePost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE post SET name = ? WHERE id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return candidate;
    }

    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE post SET name = ? WHERE id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public int registerPhotoID(int candidateID) {
        int rslID = 0;
        if (candidateID != 0) {
            var photoID = findCandidateByID(candidateID).getPhotoID();
            if (photoID != 0) {
                rslID = photoID;
            } else {
                try (Connection cn = pool.getConnection();
                     PreparedStatement ps =  cn.prepareStatement(
                             "INSERT INTO photo DEFAULT VALUES",
                             PreparedStatement.RETURN_GENERATED_KEYS)
                ) {
                    ps.execute();
                    try (ResultSet id = ps.getGeneratedKeys()) {
                        if (id.next()) {
                            rslID = id.getInt(1);
                            registerPhotoIDInCandidate(candidateID, rslID);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }

        return rslID;
    }

    private void registerPhotoIDInCandidate(int candidateID, int photoID) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE candidate SET photo_id = ? WHERE id = ?")
        ) {
            ps.setInt(1, photoID);
            ps.setInt(2, candidateID);
            ps.execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteCandidateByID(int candidateID) {
        var photoID = findCandidateByID(candidateID).getPhotoID();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "DELETE FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, candidateID);
            ps.execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        deletePhotoID(photoID);
        deletePhotoFromDisc(photoID);

    }
    private void deletePhotoID(int photoID) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "DELETE FROM photo WHERE id = ?")
        ) {
            ps.setInt(1, photoID);
            ps.execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void deletePhotoFromDisc(int photoID) {
        File file = new File(String.format("images%sphoto_%s.png", File.separator, photoID));
        file.delete();
    }
}
