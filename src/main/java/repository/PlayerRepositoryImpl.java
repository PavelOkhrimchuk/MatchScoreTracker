package repository;

import model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {

    private Session session;

    public PlayerRepositoryImpl(Session session) {
        this.session = session;
    }


    @Override
    public void save(Player entity) {
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();

    }

    @Override
    public Optional<Player> findById(Integer id) {
        Player player = session.get(Player.class, id);
        return Optional.ofNullable(player);
    }

    @Override
    public List<Player> findAll() {
        return session.createQuery("FROM Player", Player.class).list();
    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = session.beginTransaction();
        Player player = session.get(Player.class, id);
        if (player != null) {
            session.remove(player);
        }
        transaction.commit();
    }

    @Override
    public Optional<Player> findByName(String name) {
        String hql = "FROM Player WHERE name = :name";
        Query<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("name", name);
        return query.uniqueResultOptional();

    }
}
