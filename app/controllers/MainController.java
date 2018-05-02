package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.analytic;
import views.html.index;

public class MainController extends Controller {

    public Result index() {
        return ok(index.render());
    }

    public Result analytics() {
        return ok(analytic.render());
    }


}
