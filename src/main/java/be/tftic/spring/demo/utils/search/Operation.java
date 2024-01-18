package be.tftic.spring.demo.utils.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Operation {
    EQUAL("eq"),
    NOT_EQUAL("neq"),
    GREATER("gt"),
    GREATER_EQUAL("gte"),
    LESSER("ls"),
    LESSER_EQUAL("lse"),
    IN("in"),
    AFTER("aft"),
    BEFORE("bef");

    private final String symbol;

    public static Operation fromSymbol(String symbol) {
      return switch (symbol){
          case "eq" -> EQUAL;
          case "neq" -> NOT_EQUAL;
          case "gt" -> GREATER;
          case "gte" -> GREATER_EQUAL;
          case "lse" -> LESSER_EQUAL;
          case "ls" -> LESSER;
          case "in" -> IN;
          case "bef" -> BEFORE;
          case "aft" -> AFTER;
          default -> throw new IllegalArgumentException("Not a valid symbol");
      };
    };

}
