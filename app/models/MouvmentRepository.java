package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JPAMouvmentRepository.class)
public interface MouvmentRepository {

    CompletionStage<Mouvment> add(Mouvment mouvment);
    CompletionStage<Stream<Mouvment>> list();
    CompletionStage<Stream<Mouvment>> listByVolunteer(Long idVolonteer);
    CompletionStage<Stream<Mouvment>> listByNgo(Long idNgo);

}
