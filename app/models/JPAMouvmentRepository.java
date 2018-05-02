package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAMouvmentRepository implements MouvmentRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAMouvmentRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Mouvment> add(Mouvment mouvment) {
        return supplyAsync(() -> wrap(em -> insert(em, mouvment)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Mouvment>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Mouvment>> listByVolunteer(Long idVolunteer) {
        return supplyAsync(() -> wrap(em -> listByVolunteer(em, idVolunteer)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Mouvment>> listByNgo(Long idNgo) {
        return supplyAsync(() -> wrap(em -> listByNgo(em, idNgo)), executionContext);
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Mouvment insert(EntityManager em, Mouvment mouvment) {
        em.persist(mouvment);
        return mouvment;
    }

    private Stream<Mouvment> list(EntityManager em) {
        List<Mouvment> mouvments = em.createQuery("select p from Mouvment p", Mouvment.class).getResultList();
        return mouvments.stream();
    }

    private Stream<Mouvment> listByVolunteer(EntityManager em, Long idVolunteer) {
        List<Mouvment> mouvments = em.createQuery("select p from Mouvment p where p.volunteer.id = :idVolunteer", Mouvment.class).setParameter("idVolunteer", idVolunteer).getResultList();
        return mouvments.stream();
    }

    private Stream<Mouvment> listByNgo(EntityManager em, Long idNgo) {
        List<Mouvment> mouvments = em.createQuery("select p from Mouvment p where p.ngo.id = :idNgo", Mouvment.class).setParameter("idNgo", idNgo).getResultList();
        return mouvments.stream();
    }

}