package pl.spring.demo.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class BookRestService {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/books-by-title", method = RequestMethod.GET)
	public List<BookTo> findBooksByTitle(@RequestParam("titlePrefix") String titlePrefix) {
		return bookService.findBooksByTitle(titlePrefix);
	}

	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public BookTo saveBook(@RequestBody BookTo book) {
		return bookService.saveBook(book);
	}
/*
 * ten kod został dodany przez Andrzeja L.
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public BookTo saveBook() {
		return bookService.findBooksByTitle("").get(0);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public BookTo deleteBook(@PathVariable(value = "id") Long id, Map<String, Object> params) {
		return bookService.removeBookAndGetRemoved(id);
	}*/
}
