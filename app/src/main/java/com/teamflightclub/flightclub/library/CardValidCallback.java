package com.teamflightclub.flightclub.library;

public interface CardValidCallback {
  /**
   * called when data entry is complete and the card is valid
   * @param card the validated card
   */
  void cardValid(com.teamflightclub.flightclub.library.CreditCard card);
}
