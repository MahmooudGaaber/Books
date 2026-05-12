package com.severus.books.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooksRequest {

      @Size(min = 1 , max=30)
      @NotEmpty
      private String name;

      @Size(min = 1 , max=40)
      @NotEmpty
      private String author;

      @Size(min = 1 , max=30)
      @NotEmpty
      private String category;

      @Min(value = 1)
      @Max(value = 9)
      private int rating;
}
