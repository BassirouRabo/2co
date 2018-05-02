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

public class JPANgoRepository implements NgoRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPANgoRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Ngo> add(Ngo ngo) {
        return supplyAsync(() -> wrap(em -> insert(em, ngo)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Ngo>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Optional<Ngo>> get(Long id) {
        return supplyAsync(() -> wrap(em -> find(em, id)), executionContext);
    }

    @Override
    public CompletionStage<Optional<Ngo>> change(Ngo ngo) {
        return supplyAsync(() -> wrap(em -> update(em, ngo)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Ngo insert(EntityManager em, Ngo ngo) {
        em.persist(ngo);
        return ngo;
    }

    private Stream<Ngo> list(EntityManager em) {
        List<Ngo> ngos = em.createQuery("select p from Ngo p", Ngo.class).getResultList();
        return ngos.stream();
    }

    private Optional<Ngo> find(EntityManager em, Long id) {
        Ngo ngo;
        try {
            ngo =  em.find(Ngo.class, id);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(ngo);
    }

    private Optional<Ngo> update(EntityManager em, Ngo ngo) {
        Ngo iEntity;
        try {
            iEntity = em.merge(ngo);
            em.flush();
        } catch (PersistenceException e) {
            return Optional.empty();
        }
        return Optional.of(iEntity);
    }
    
}

