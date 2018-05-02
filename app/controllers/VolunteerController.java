package controllers;



import models.MouvmentRepository;
import models.Volunteer;
import models.VolunteerRepository;
import play.Logger;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.mouvments_volunteer;
import views.html.volunteer;
import views.html.volunteers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class VolunteerController extends Controller {

    private final FormFactory formFactory;
    private final VolunteerRepository volunteerRepository;
    private final MouvmentRepository mouvmentRepository;
    private final HttpExecutionContext ec;

    @Inject
    public VolunteerController(FormFactory formFactory, VolunteerRepository volunteerRepository, MouvmentRepository mouvmentRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.volunteerRepository = volunteerRepository;
        this.mouvmentRepository = mouvmentRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> reads() {
        return volunteerRepository.list().thenApplyAsync(volunteerStream -> {
            return ok(volunteers.render(volunteerStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> read(Long id) {
        return mouvmentRepository.listByVolunteer(id).thenApplyAsync(mouvmentStream -> {
            return ok(volunteer.render(mouvmentStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> create() {
        Volunteer volunteer = formFactory.form(Volunteer.class).bindFromRequest().get();
        volunteer.setWallet(0L);
        return volunteerRepository.add(volunteer).thenApplyAsync(p -> {
            return redirect(controllers.routes.VolunteerController.reads());
        }, ec.current());
    }


    public Result update(Long id) {
        return ok(index.render());
    }
}
