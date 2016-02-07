package pl.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.repository.BookRepository;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookTo> findAllBooks() {
        return BookMapper.map2To(bookRepository.findAll());
    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
        return BookMapper.map2To(bookRepository.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return BookMapper.map2To(bookRepository.findBookByAuthor(author));
    }

    @Override
    @Transactional(readOnly = false)
    public BookTo saveBook(BookTo book) {
        BookEntity entity = BookMapper.map(book);
        entity = bookRepository.save(entity);
        return BookMapper.map(entity);
    }

	@Override
    @Transactional(readOnly = false)
	public void removeBook(BookTo bookTo) {
		bookRepository.delete(BookMapper.map(bookTo));
	}

	@Override
    @Transactional(readOnly = false)
	public BookTo removeBookAndGetRemoved(long id) {
        List<BookTo> allBooks = findAllBooks();
        
		if (!allBooks.isEmpty()) {
			for (BookTo book : allBooks) {
				if (book.getId() == id) {
					removeBook(book);	
					return book;
				}
			}
		}		
		return null;
	}

	@Override
    @Transactional(readOnly = false)
	public BookTo addBook(BookTo book) {
		if (book.getAuthors().length() > 0 && book.getTitle().length() > 0 && book.getId() != null) {
			return saveBook(book);
		}
		return null;
	}

	@Override
    @Transactional(readOnly = false)
	public BookTo changeBook(BookTo book) {
		if (book.getAuthors().length() > 0 && book.getTitle().length() > 0 && book.getId() != null) {
			BookTo bookToChange = BookMapper.map(bookRepository.findOne(book.getId()));
			if (bookToChange != null) {
				bookToChange.setAuthors(book.getAuthors());
				bookToChange.setTitle(book.getTitle());
				return saveBook(bookToChange);
			}
		}
		return null;
	}
	
}
