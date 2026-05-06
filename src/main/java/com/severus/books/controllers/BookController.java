package com.severus.books.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.severus.books.models.BooksEntity;

@RestController
@RequestMapping("/api/books/")
public class BookController {
      
     private final List<BooksEntity> allBooks = new ArrayList<>();

     public BookController(){
      intializeBooks();
     }

     private void intializeBooks (){
      allBooks.addAll(List.of(
            new BooksEntity("The Great Gatsby", "F. Scott Fitzgerald", "Classic"),
            new BooksEntity("To Kill a Mockingbird", "Harper Lee", "Classic"),
            new BooksEntity("1984", "George Orwell", "Dystopian"),
            new BooksEntity("Clean Code", "Robert C. Martin", "Programming"),
            new BooksEntity("The Pragmatic Programmer", "Andrew Hunt", "Programming"),
            new BooksEntity("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy"),
            new BooksEntity("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new BooksEntity("Atomic Habits", "James Clear", "Self-Help"),
            new BooksEntity("The Alchemist", "Paulo Coelho", "Fiction"),
            new BooksEntity("Sapiens", "Yuval Noah Harari", "History")
      ));
     }
      @GetMapping
      public List<BooksEntity> getAllBooks(){
            return allBooks;
      }

      @GetMapping("{name:[a-zA-Z0-9].*}")
      public BooksEntity getBookWithName (@PathVariable String name){
            return allBooks.stream()
            .filter(book -> name.equalsIgnoreCase(book.getName())).findFirst().orElse(null);
      }

      @GetMapping("books-with-category")
      public List<BooksEntity> filterBooksWithCategory (@RequestParam String category){
            return allBooks.stream().filter(book -> category.equalsIgnoreCase(book.getCategory())).toList();
      }

      @PostMapping("newbook")
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
      public String deleteBook ( @PathVariable String name){
            allBooks.removeIf(book-> book.getName().equalsIgnoreCase(name));
            return"Book Has Been Deleted successfully ";
      }

      

}
