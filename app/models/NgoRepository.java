package models;

import com.google.inject.ImplementedBy;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JPANgoRepository.class)
public interface NgoRepository {
    CompletionStage<Ngo> add(Ngo ngo);
    CompletionStage<Stream<Ngo>> list();
    CompletionStage<Optional<Ngo>> get(Long id);
    CompletionStage<Optional<Ngo>> change(Ngo ngo);
}
