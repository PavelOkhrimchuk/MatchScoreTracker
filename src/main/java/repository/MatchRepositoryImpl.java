package repository;

import lombok.Setter;
import model.Match;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;
@Setter
public class MatchRepositoryImpl implements MatchRepository {

    @Override
    public List<Match> findByPlayerName(String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT m FROM Match m JOIN m.player1 p1 JOIN m.player2 p2 WHERE p1.name = :name OR p2.name = :name";
            Query<Match> query = session.createQuery(hql, Match.class);
            query.setParameter("name", playerName);
            return query.list();
        }
    }

    @Override
    public void save(Match entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Match> findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Match match = session.get(Match.class, id);
            return Optional.ofNullable(match);
        }
    }

    @Override
    public List<Match> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Match", Match.class).list();
        }
    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Match match = session.get(Match.class, id);
            if (match != null) {
                session.remove(match);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
