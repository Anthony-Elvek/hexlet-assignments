package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Book;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> index() {
        return bookRepository.findAll().stream()
                .map(bookMapper::map)
                .toList();
    }

    public BookDTO create(BookCreateDTO bookCreateDTO) {
        var book = bookMapper.map(bookCreateDTO);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO get(Long id) {
        return bookMapper.map(findAuthor(id));
    }

    public BookDTO update(BookUpdateDTO bookData, Long id) {
        var author = findAuthor(id);
        bookMapper.update(bookData, author);
        bookRepository.save(author);
        return bookMapper.map(author);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private Book findAuthor(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Author id = %d is not found", id))
        );
    }
    // END
}
