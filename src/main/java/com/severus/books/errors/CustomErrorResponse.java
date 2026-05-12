package com.severus.books.errors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomErrorResponse {

      private int errorStatus;
      private String errorMessage;
      private long errorTimeStamp;
}
