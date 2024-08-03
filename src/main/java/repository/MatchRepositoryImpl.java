package repository;

import model.Match;
import model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl implements MatchRepository {

    private Session session;

    public MatchRepositoryImpl(Session session) {
        this.session = session;
    }


    @Override
    public List<Match> findByPlayerName(String playerName) {
        String hql = "SELECT m FROM Match m JOIN m.player1 p1 JOIN m.player2 p2 WHERE p1.name = :name OR p2.name = :name";
        Query<Match> query = session.createQuery(hql, Match.class);
        query.setParameter("name", playerName);
        return query.list();
    }

    @Override
    public void save(Match entity) {
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();

    }

    @Override
    public Optional<Match> findById(Integer id) {
        Match match = session.get(Match.class, id);
        return Optional.ofNullable(match);
    }

    @Override
    public List<Match> findAll() {
        return session.createQuery("FROM Match", Match.class).list();
    }

    @Override
    public void delete(Integer id) {

        Transaction transaction = session.beginTransaction();
        Match match = session.get(Match.class, id);
        if (match != null) {
            session.remove(match);
        }
        transaction.commit();

    }
}
