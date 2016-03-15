package web.controller;

import com.sun.org.apache.xpath.internal.operations.Quo;
import model.Quote;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Leon on 3/7/2016.
 */

@RestController
@EnableWebMvc
public class QuoteController {
    List<Quote> quoteList = new ArrayList<Quote>();

    private final AtomicLong counter = new AtomicLong();

    @PostConstruct
    public void init() {
        quoteList.add(new Quote(counter.incrementAndGet(), "Bine faci, bine gasesti!"));
        quoteList.add(new Quote(counter.incrementAndGet(), "Minciuna are picioare scurte."));
        quoteList.add(new Quote(counter.incrementAndGet(), "Nu lasa pe maine ce poti face azi."));
        quoteList.add(new Quote(counter.incrementAndGet(), "Dai un ban dar stai in fata."));
        quoteList.add(new Quote(counter.incrementAndGet(), "Ai, n-ai mingea, dai la poarta."));
        quoteList.add(new Quote(counter.incrementAndGet(), "Prost sa fii, noroc ca esti... uh... sa ai."));
    }

    @RequestMapping(value="/quote", method = RequestMethod.GET)
    public ResponseEntity<Quote> quote() {
        System.out.println("Fetching random quote");
        Random randomGenerator = new Random();
        Integer randomIndex = randomGenerator.nextInt(quoteList.size());
        return new ResponseEntity<Quote>(quoteList.get(randomIndex), HttpStatus.OK);
    }

    @RequestMapping(value="/quote/{id}", method = RequestMethod.GET)
    public ResponseEntity<Quote> addQuote(@PathVariable Integer id) {
        System.out.println("Fetching quote with id " + id);
        return new ResponseEntity<Quote>(quoteList.get(id), HttpStatus.CREATED);
    }

    @RequestMapping(value="/quote", method = RequestMethod.POST)
    public ResponseEntity<Quote> addQuote(@RequestBody Quote quote, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating quote " + quote.getContent());
        quoteList.add(quote);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/quote/{id}").buildAndExpand(quote.getId()).toUri());
        return new ResponseEntity<Quote>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/quote/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Quote> updateQuote(@PathVariable("id") long id, @RequestBody Quote quote) {
        System.out.println("Updating quote " + id);
        Quote currentQuote = quoteList.get((int) id);
        if(currentQuote == null) {
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND);
        }
        quoteList.set((int) id, quote);
        return new ResponseEntity<Quote>(currentQuote, HttpStatus.OK);
    }

    @RequestMapping(value="/quote/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Quote> deleteUser(@PathVariable int id) {
        System.out.println("Deleting quote with id: " + id);
        Quote currentQuote = quoteList.get((int) id);
        if(currentQuote == null) {
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND);
        }
        quoteList.remove(id);
        return new ResponseEntity<Quote>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/quote/", method = RequestMethod.DELETE)
    public ResponseEntity<Quote> deleteAllUsers() {
        System.out.println("Deleting all quotes...");
        quoteList.clear();
        return new ResponseEntity<Quote>(HttpStatus.NO_CONTENT);
    }

}
/*@Controller
public class QuoteController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/quote", method = RequestMethod.GET)
    public Quote quote(ModelAndView model) {
        model.addObject("quote", "test quote");
        return new Quote(counter.incrementAndGet(), "What you seed is what you get");
    }
}*/
