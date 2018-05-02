package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAVolunteerRepository implements VolunteerRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAVolunteerRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Volunteer> add(Volunteer volunteer) {
        return supplyAsync(() -> wrap(em -> insert(em, volunteer)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Volunteer>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Optional<Volunteer>> get(Long id) {
        return supplyAsync(() -> wrap(em -> find(em, id)), executionContext);
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Volunteer insert(EntityManager em, Volunteer volunteer) {
        em.persist(volunteer);
        return volunteer;
    }

    private Stream<Volunteer> list(EntityManager em) {
        List<Volunteer> volunteers = em.createQuery("select p from Volunteer p", Volunteer.class).getResultList();
        return volunteers.stream();
    }

    private Optional<Volunteer> find(EntityManager em, Long id) {
        Volunteer volunteer;
        try {
            volunteer =  em.find(Volunteer.class, id);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(volunteer);
    }


}

