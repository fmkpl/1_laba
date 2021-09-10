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
        movies.add(new Movie("Triple nine","USA"));
    }
    //
    // Вводится (inject) из application.properties.
    @Value("${error.del}")
    private String errorDel;
    @Value("${error.edit}")
    private String errorEdit;
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

    //GET ALL MOVIES
    @RequestMapping(value = {"/allmovies"}, method = RequestMethod.GET)
    public ModelAndView movieList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movielist");
        model.addAttribute("movies", movies);
        return modelAndView;
    }

    //ADD MOVIE
    @RequestMapping(value = {"/addmovie"}, method = RequestMethod.GET)
    public ModelAndView showAddMoviePage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addmovie");
        MovieForm movieForm = new MovieForm();
        model.addAttribute("movieform", movieForm);

        return modelAndView;
    }

    // @PostMapping("/addbook")
    //GetMapping("/")
    @RequestMapping(value = {"/addmovie"}, method = RequestMethod.POST)
    public ModelAndView saveMovie(Model model, @ModelAttribute("movieform") MovieForm movieForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movielist");
        String title = movieForm.getTitle();
        String country = movieForm.getCountry();
        if (title != null && title.length() > 0 && country != null && country.length() > 0) {
            Movie newMovie = new Movie(title, country);
            movies.add(newMovie);
            model.addAttribute("movies",movies);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addmovie");
        return modelAndView;
    }

    //DELETE MOVIE
    @RequestMapping(value = {"/deletemovie"}, method = RequestMethod.GET)
    public ModelAndView showDeleteMoviePage(Model model) {
        ModelAndView modelAndView = new ModelAndView("deletemovie");
        MovieForm movieForm = new MovieForm();
        model.addAttribute("movieform", movieForm);

        return modelAndView;
    }

    @RequestMapping(value = {"/deletemovie"}, method = RequestMethod.POST)
    public ModelAndView deleteMovie(Model model, @ModelAttribute("movieform") MovieForm movieForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movielist");
        String title = movieForm.getTitle();
        String country = movieForm.getCountry();
        if (title != null && title.length() > 0 && country != null && country.length() > 0) {
            Iterator<Movie> movieIterator = movies.iterator();
            while(movieIterator.hasNext()) {
                Movie nextMovie = movieIterator.next();
                if(nextMovie.title.equals(title) && nextMovie.country.equals(country)) {
                    movieIterator.remove();
                }
            }
            model.addAttribute("movies",movies);
            return modelAndView;
        }
        model.addAttribute("errorDel", errorDel);
        modelAndView.setViewName("deletemovie");
        return modelAndView;
    }

    //EDIT MOVIE
    @RequestMapping(value = {"/editmovie"}, method = RequestMethod.GET)
    public ModelAndView showEditMoviePage(Model model) {
        ModelAndView modelAndView = new ModelAndView("editmovie");
        MovieForm movieForm = new MovieForm();
        model.addAttribute("movieform", movieForm);
        return modelAndView;
    }

    @RequestMapping(value = {"/editmovie"}, method = RequestMethod.POST)
    public ModelAndView editMovie(Model model, @ModelAttribute("movieform") MovieForm movieForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("movielist");
        String title = movieForm.getTitle();
        String country = movieForm.getCountry();
        String newTitle = movieForm.getNewTitle();
        String newCountry = movieForm.getNewCountry();
        if (title != null && title.length() > 0 && country != null && country.length() > 0) {
            Iterator<Movie> movieIterator = movies.iterator();
            while(movieIterator.hasNext()) {
                Movie nextMovie = movieIterator.next();
                if(nextMovie.title.equals(title) && nextMovie.country.equals(country)) {
                    nextMovie.title = newTitle;
                    nextMovie.country = newCountry;
                }
            }
            model.addAttribute("movies",movies);
            return modelAndView;
        }
        model.addAttribute("errorEdit", errorEdit);
        modelAndView.setViewName("editmovie");
        return modelAndView;
    }
}
