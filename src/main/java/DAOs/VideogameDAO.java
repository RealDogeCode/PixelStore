package DAOs;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import Models.Tag;
import Models.Videogame;

public class VideogameDAO implements GenericDAO<Videogame, Integer> {

    private final DataSource ds;

    public VideogameDAO(DataSource ds) {
        this.ds = ds;
    }

    private Videogame map(ResultSet rs) throws SQLException {
        Videogame v = new Videogame();
        v.setId(rs.getInt("id"));
        v.setTitle(rs.getString("title"));
        v.setDescription(rs.getString("description"));
        v.setPublisherId(rs.getInt("publisher_id"));
        v.setPrice(rs.getDouble("price"));
        v.setDiscountPercentage(rs.getInt("discount_percentage"));
        v.setBannerUrl(rs.getString("banner_url"));
        v.setCreatedAt(rs.getTimestamp("created_at"));
        v.setUpdatedAt(rs.getTimestamp("updated_at"));
        v.setPublisherName(rs.getString("company_name"));
        return v;
    }

    public List<Videogame> search(String query) throws SQLException {
        if (query == null || query.isBlank()) {
            return findAll();
        }

        String sql =
            "SELECT v.*, p.company_name, t.id AS tag_id, t.name AS tag_name " +
            "FROM videogames v " +
            "JOIN publishers p ON v.publisher_id = p.user_id " +
            "LEFT JOIN videogame_tags vt ON vt.videogame_id = v.id " +
            "LEFT JOIN tags t ON t.id = vt.tag_id " +
            "WHERE LOWER(v.title) LIKE ? " +
            "ORDER BY v.created_at DESC";

        Map<Integer, Videogame> games = new LinkedHashMap<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + query.toLowerCase() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Videogame game = games.get(id);


                    if (game == null) {

                        game = map(rs);
                        game.setTags(new ArrayList<>());

                        games.put(id, game);
                    }


                    int tagId = rs.getInt("tag_id");


                    if (!rs.wasNull()) {

                        Tag tag = new Tag();

                        tag.setId(tagId);
                        tag.setName(rs.getString("tag_name"));

                        game.getTags().add(tag);
                    }
                }
            }
        }
        return new ArrayList<>(games.values());
    }
    
    @Override
    public Videogame create(Videogame v) throws SQLException {
        String sql = "INSERT INTO videogames (title, description, publisher_id, price, discount_percentage, banner_url, created_at) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getTitle());
            ps.setString(2, v.getDescription());
            ps.setInt(3, v.getPublisherId());
            ps.setDouble(4, v.getPrice());
            ps.setInt(5, v.getDiscountPercentage());
            ps.setString(6, v.getBannerUrl());
            ps.setTimestamp(7, v.getCreatedAt());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setId(rs.getInt(1));
                }
            }
        }

        return v;
    }

    @Override
    public Videogame update(Videogame v) throws SQLException {
        String sql = "UPDATE videogames SET title=?, description=?, publisher_id=?, price=?, discount_percentage=?, banner_url=?, updated_at=NOW() WHERE id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getTitle());
            ps.setString(2, v.getDescription());
            ps.setInt(3, v.getPublisherId());
            ps.setDouble(4, v.getPrice());
            ps.setInt(5, v.getDiscountPercentage());
            ps.setString(6, v.getBannerUrl());
            ps.setInt(7, v.getId());

            ps.executeUpdate();
        }

        return v;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM videogames WHERE id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Videogame findById(Integer id) throws SQLException {
        String sql = "SELECT v.*, p.company_name FROM videogames v " +
                     "JOIN publishers p ON v.publisher_id = p.user_id " +
                     "WHERE v.id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Videogame> findAll() throws SQLException {
        String sql =
            "SELECT v.*, p.company_name, t.id AS tag_id, t.name AS tag_name " +
            "FROM videogames v " +
            "JOIN publishers p ON v.publisher_id = p.user_id " +
            "LEFT JOIN videogame_tags vt ON vt.videogame_id = v.id " +
            "LEFT JOIN tags t ON t.id = vt.tag_id";

        Map<Integer, Videogame> games = new LinkedHashMap<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");

                Videogame game = games.get(id);

                if (game == null) {
                    game = map(rs);
                    game.setTags(new ArrayList<>());
                    games.put(id, game);
                }

                int tagId = rs.getInt("tag_id");

                if (!rs.wasNull()) {
                    Tag tag = new Tag();
                    tag.setId(tagId);
                    tag.setName(rs.getString("tag_name"));
                    game.getTags().add(tag);
                }
            }
        }

        return new ArrayList<>(games.values());
    }

    public List<Videogame> findByPublisher(Integer publisherId) throws SQLException {
        String sql =
            "SELECT v.*, p.company_name, t.id AS tag_id, t.name AS tag_name " +
            "FROM videogames v " +
            "JOIN publishers p ON v.publisher_id = p.user_id " +
            "LEFT JOIN videogame_tags vt ON vt.videogame_id = v.id " +
            "LEFT JOIN tags t ON t.id = vt.tag_id " +
            "WHERE v.publisher_id=? " +
            "ORDER BY v.created_at DESC";

        Map<Integer, Videogame> games = new LinkedHashMap<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, publisherId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");

                    Videogame game = games.get(id);

                    if (game == null) {
                        game = map(rs);
                        game.setTags(new ArrayList<>());
                        games.put(id, game);
                    }

                    int tagId = rs.getInt("tag_id");

                    if (!rs.wasNull()) {
                        Tag tag = new Tag();
                        tag.setId(tagId);
                        tag.setName(rs.getString("tag_name"));
                        game.getTags().add(tag);
                    }
                }
            }
        }

        return new ArrayList<>(games.values());
    }

    public List<Tag> findAllTags() throws SQLException {
        String sql = "SELECT * FROM tags ORDER BY name";

        List<Tag> tags = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getInt("id"));
                tag.setName(rs.getString("name"));
                tags.add(tag);
            }
        }

        return tags;
    }

    public void addTag(int gameId, int tagId) throws SQLException {
        String sql = "INSERT INTO videogame_tags (videogame_id, tag_id) VALUES (?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gameId);
            ps.setInt(2, tagId);
            ps.executeUpdate();
        }
    }

    public void deleteTags(int gameId) throws SQLException {
        String sql = "DELETE FROM videogame_tags WHERE videogame_id=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gameId);
            ps.executeUpdate();
        }
    }
}