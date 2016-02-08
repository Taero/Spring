package pl.spring.demo.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String bookList(Map<String, Object> params) {
        final List<BookTo> allBooks = bookService.findAllBooks();
        params.put("books", allBooks);
        return "bookList";
    }
    
    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.GET)
	public String deleteBook(@PathVariable(value = "id") Long id, Map<String, Object> params) {
    	BookTo deletedBook = bookService.removeBookAndGetRemoved(id);
    	if (deletedBook == null) {
    		deletedBook = new BookTo(id, "Nie znaleziono książki", "");
    		return "bookNotDeleted";
    	} else {
    		params.put("deletedBook", deletedBook);
    		return "bookDeleted";
    	}
    }
    
    @RequestMapping(value = "/add")
 	public String addBook(@RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("author") String author, Map<String, Object> params) {
    	bookService.addBook(new BookTo(id, title, author));
//    	http://localhost:9721/workshop/add?id=10&title=W pustyni i w puszczy&author=Henryk Sienkiewicz
     	return "redirect:/books";
     }

    @RequestMapping(value = "/change")
 	public String changeBook(@RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("author") String author, Map<String, Object> params) {
    	bookService.changeBook(new BookTo(id, title, author));
     	return "redirect:/books";
     }
}
