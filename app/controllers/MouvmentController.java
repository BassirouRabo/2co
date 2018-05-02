package controllers;

import models.Mouvment;
import models.MouvmentRepository;
import models.Mouvment;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.mouvments;
import views.html.mouvments_ngo;
import views.html.mouvments_volunteer;

import javax.inject.Inject;
import java.util.Date;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class MouvmentController extends Controller {

    private final FormFactory formFactory;
    private final MouvmentRepository mouvmentRepository;
    private final HttpExecutionContext ec;

    @Inject
    public MouvmentController(FormFactory formFactory, MouvmentRepository mouvmentRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.mouvmentRepository = mouvmentRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> reads() {
        return mouvmentRepository.list().thenApplyAsync(mouvmentStream -> {
            return ok(mouvments.render(mouvmentStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> create() {
        Mouvment mouvment = formFactory.form(Mouvment.class).bindFromRequest().get();
        mouvment.setDate(new Date());
        return mouvmentRepository.add(mouvment).thenApplyAsync(p -> {
            return redirect(controllers.routes.MouvmentController.reads());
        }, ec.current());
    }
    
    public CompletionStage<Result> readsByVolunteers (Long idMouvments) {
        return mouvmentRepository.listByVolunteer(idMouvments).thenApplyAsync(mouvmentStream -> {
            return ok(mouvments_volunteer.render(mouvmentStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> readsByNgos (Long idNgos) {
        return mouvmentRepository.listByNgo(idNgos).thenApplyAsync(mouvmentStream -> {
            return ok(mouvments_ngo.render(mouvmentStream.collect(Collectors.toList())));
        }, ec.current());
    }
}
