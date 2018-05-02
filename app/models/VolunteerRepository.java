package models;

import com.google.inject.ImplementedBy;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JPAVolunteerRepository.class)
public interface VolunteerRepository {

    CompletionStage<Volunteer> add(Volunteer volunteer);
    CompletionStage<Stream<Volunteer>> list();
    CompletionStage<Optional<Volunteer>> get(Long id);
}
