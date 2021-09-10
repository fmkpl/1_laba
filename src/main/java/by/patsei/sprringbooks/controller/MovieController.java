package by.patsei.sprringbooks.controller;

import by.patsei.sprringbooks.forms.MovieForm;
import by.patsei.sprringbooks.model.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MovieController {
    private static List<Movie> movies = new ArrayList<Movie>();

    static {
        movies.add(new Movie("Transformers (2007)", "USA"));
        movies.add(new Movie("Chernobyl", "England"));
    }
    //
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        return modelAndView;
    }

    @RequestMapping(value = {"/allmovies"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movielist");
        model.addAttribute("movies", movies);
        return modelAndView;
    }

    @RequestMapping(value = {"/addmovie"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addmovie");
        MovieForm movieForm = new MovieForm();
        model.addAttribute("movieform", movieForm);

        return modelAndView;
    }

    // @PostMapping("/addbook")
    //GetMapping("/")
    @RequestMapping(value = {"/addmovie"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, @ModelAttribute("movieform") MovieForm movieForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movielist");
        String title = movieForm.getTitle();
        String country = movieForm.getCountry();
        if (title != null && title.length() > 0 //
                && country != null && country.length() > 0) {
            Movie newMovie = new Movie(title, country);
            movies.add(newMovie);
            model.addAttribute("movies",movies);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addmovie");
        return modelAndView;
    }
}
