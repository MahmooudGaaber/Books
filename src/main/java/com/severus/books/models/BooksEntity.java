package com.severus.books.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooksEntity {
      private String name;
      private String author;
      private String category;
}
