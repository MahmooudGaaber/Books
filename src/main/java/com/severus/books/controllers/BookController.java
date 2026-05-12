package com.severus.books.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.severus.books.models.BooksEntity;
import io.swagger.v3.oas.annotations.Operation;
import com.severus.books.dto.*;
import com.severus.books.errors.BookNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/books/")
@Validated
public class BookController {
      
     private final List<BooksEntity> allBooks = new ArrayList<>();

     public BookController(){
       intializeBooks();
     }

     private void intializeBooks (){
      allBooks.addAll(List.of(
            new BooksEntity(1,"The Great Gatsby", "F. Scott Fitzgerald", "Classic", 0),
            new BooksEntity(2,"To Kill a Mockingbird", "Harper Lee", "Classic", 0),
            new BooksEntity(3,"1984", "George Orwell", "Dystopian",0 ),
            new BooksEntity(4,"Clean Code", "Robert C. Martin", "Programming", 0),
            new BooksEntity(5,"The Pragmatic Programmer", "Andrew Hunt", "Programming",0 ),
            new BooksEntity(6,"Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 0),
            new BooksEntity(7,"The Hobbit", "J.R.R. Tolkien", "Fantasy",0 ),
            new BooksEntity(8,"Atomic Habits", "James Clear", "Self-Help", 0),
            new BooksEntity(9,"The Alchemist", "Paulo Coelho", "Fiction",0 ),
            new BooksEntity(10,"Sapiens", "Yuval Noah Harari", "History",0 )
      ));
     }
      @GetMapping
      @Operation(tags = {"V1"})
      @ResponseStatus(HttpStatus.OK)
      public List<BooksEntity> getAllBooks(){
            return allBooks;
      }

      @GetMapping("{name:[a-zA-Z0-9].*}")
      @Operation(tags = {"V1"})
      @ResponseStatus(HttpStatus.OK)
      public BooksEntity getBookWithName (@PathVariable String name){
            return allBooks.stream()
            .filter(book -> name.equalsIgnoreCase(book.getName())).findFirst().orElse(null);
      }

      @GetMapping("books-with-category")
      @Operation(tags = {"V1"})
      @ResponseStatus(HttpStatus.OK)
      public List<BooksEntity> filterBooksWithCategory (@RequestParam String category){
            return allBooks.stream().filter(book -> category.equalsIgnoreCase(book.getCategory())).toList();
      }

      @PostMapping("newbook")
      @Operation(tags = {"V1"})
      @ResponseStatus(HttpStatus.CREATED)
      public String addNewBook (@RequestBody BooksEntity newBook){
            boolean isNewBook = allBooks.stream()
            .noneMatch(book-> 
                  book.getName().equalsIgnoreCase(newBook.getName())
            );
            if(isNewBook){
                  allBooks.add(newBook);
                  return "your new book added successfully ";
            }
            return "This book is already exist";
      }

      @PutMapping("{name:[a-zA-Z0-9].*}")
      @Operation(tags = {"V1"})
      @ResponseStatus(HttpStatus.OK)
      public String  updateBook (@PathVariable String name , @RequestBody BooksEntity updatedBook){
            for (BooksEntity books : allBooks){
                  if(books.getName().equalsIgnoreCase(name )){
                        books.setName(updatedBook.getName());
                        books.setAuthor(updatedBook.getAuthor());
                        books.setCategory(updatedBook.getCategory());
                        // books = updatedBook;
                        return "The Book Been Updated";
                  }
            }
            return "The Book Not Found";
      }

      @DeleteMapping("{name:[a-zA-Z0-9].*}")
      @Operation(tags = {"V1"})
      @ResponseStatus(HttpStatus.NOT_FOUND)
      public String deleteBook ( @PathVariable String name){
            allBooks.removeIf(book-> book.getName().equalsIgnoreCase(name));
            return"Book Has Been Deleted successfully ";
      }

      @GetMapping("id/{bookId}")
      @Operation(tags = {"V2"})
      @ResponseStatus(HttpStatus.OK)
      public BooksEntity getBookWitId(@PathVariable @Min(value = 1) long bookId){
      return allBooks.stream().filter(book -> book.getId() == bookId).findFirst().orElseThrow(
            ()-> new BookNotFoundException("Book With This id is not found "+ bookId)
      );
      }

      @DeleteMapping("id/{id}")
      @Operation(tags = {"V2"})
      @ResponseStatus(HttpStatus.NOT_FOUND)
      public void deleteBookWithId (@PathVariable @Min(value = 1) long id){
            allBooks.stream().filter(book -> book.getId() == id).findFirst().orElseThrow(
                  ()-> new BookNotFoundException("Book With This id is not found "+ id)
            );
            allBooks.removeIf(book-> book.getId() == id);
      }

      @PostMapping("newbook-with-dto")
      @Operation(tags = {"V2"})
      @ResponseStatus(HttpStatus.CREATED)
      public String createBookWithDTO ( @Valid @RequestBody BooksRequest newBook){
            long id;
            id =  allBooks.isEmpty() ? 1 : allBooks.get(allBooks.size() -1 ).getId() + 1 ;
            BooksEntity book = convertFromRequestToEntity(id , newBook);
            allBooks.add(book);
            return "The New Book Done Added ";
      }


      @PutMapping("updated-with-dto/{id}")
      @Operation(tags = {"V2"})
      @ResponseStatus(HttpStatus.OK)
      public String updateBookWithDto (
            @PathVariable @Min(value = 1) long id ,
            @Valid @RequestBody BooksRequest updatedBook) {

                  if(id > allBooks.size()){
                        return "Book Out Of Range";
                  }

            for(int i = 0 ; i < allBooks.size() ; i++){
                  if (allBooks.get(i).getId() == id) {
                        convertFromRequestToEntity(id , updatedBook);
                        return "Book Updated ";
                  }
            }

            return "Book Not Found";
      }


      private BooksEntity convertFromRequestToEntity ( long id ,BooksRequest booksRequest)
      {
            return new BooksEntity(id, booksRequest.getName(), booksRequest.getAuthor(), booksRequest.getCategory(), booksRequest.getRating());
      }

     
      
}
