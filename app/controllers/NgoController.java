package controllers;



import models.MouvmentRepository;
import models.Ngo;
import models.NgoRepository;
import models.VolunteerRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ngo;
import views.html.ngos;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class NgoController extends Controller {

    private final FormFactory formFactory;
    private final NgoRepository ngoRepository;
    private final MouvmentRepository mouvmentRepository;
    private final VolunteerRepository volunteerRepository;

    private final HttpExecutionContext ec;

    @Inject
    public NgoController(FormFactory formFactory, NgoRepository ngoRepository, MouvmentRepository mouvmentRepository, VolunteerRepository volunteerRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.ngoRepository = ngoRepository;
        this.mouvmentRepository = mouvmentRepository;
        this.volunteerRepository = volunteerRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> reads() {
        return ngoRepository.list().thenApplyAsync(ngoStream -> {
            return ok(ngos.render(ngoStream.collect(Collectors.toList())));
        }, ec.current());
    }


    public CompletionStage<Result> read (Long id) {
        return mouvmentRepository.listByNgo(id).thenApplyAsync(mouvmentStream -> {
            return ok(ngo.render(mouvmentStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> create() {
        Ngo ngo = formFactory.form(Ngo.class).bindFromRequest().get();
        ngo.setStatus(0L);
        ngo.setWallet(0L);
        return ngoRepository.add(ngo).thenApplyAsync(p -> {
            return redirect(controllers.routes.NgoController.reads());
        }, ec.current());
    }


    public CompletionStage<Result> update(Long id) {
        return ngoRepository.get(id).thenApplyAsync(ngoObjectGet -> {
            if (!ngoObjectGet.isPresent()) {
                return redirect(controllers.routes.NgoController.read(id));
            }
            Ngo ngo = ngoObjectGet.get();
            ngo.setStatus(1L);
            ngoRepository.change(ngo).thenApplyAsync(rubriqueObjectUpdate -> {
                return redirect(controllers.routes.NgoController.reads());
            }, ec.current());
            return redirect(controllers.routes.NgoController.reads());
        }, ec.current());
    }
}
