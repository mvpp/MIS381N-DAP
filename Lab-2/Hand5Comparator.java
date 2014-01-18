import java.util.Comparator;
import java.util.Arrays;

enum Suit {
  HEART, CLUB, SPADE, DIAMOND
}

enum Value {
  ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}

class Card {
  Suit suit;
  Value value;
  Card( Suit s, Value v) {
    suit = s;
    value = v;
  }
}

class CardCompare implements Comparator<Card> {
  private CardCompare() { };
  static public final CardCompare compareObject = new CardCompare();
  public int compare(Card s1, Card s2) {
    return s1.value.ordinal() - s2.value.ordinal();
  }
}

class Hand5 {
  Card cards[] = new Card[5];

  Hand5( Card a, Card b, Card c, Card d, Card e) {
    cards[0] = a;
    cards[1] = b;
    cards[2] = c;
    cards[3] = d;
    cards[4] = e;
    Arrays.sort(cards, 
        // ascending sort
        CardCompare.compareObject
    );
  }

  boolean is4OfAKind() {
    return (cards[0].value == cards[1].value && cards[1].value == cards[2].value && cards[2].value == cards[3].value) 
      || (cards[1].value == cards[2].value && cards[2].value == cards[3].value && cards[3].value == cards[4].value);
  }

  boolean isFlush() {
    return cards[0].suit == cards[1].suit && cards[1].suit == cards[2].suit 
        && cards[2].suit == cards[3].suit && cards[3].suit == cards[4].suit;
  }

  boolean isStraight() {
    return cards[0].value.ordinal() == cards[1].value.ordinal() - 1 
        && cards[1].value.ordinal() == cards[2].value.ordinal() - 1 
        && cards[2].value.ordinal() == cards[3].value.ordinal() - 1
        && cards[3].value.ordinal() == cards[4].value.ordinal() - 1
        || cards[0].value == Value.TWO && cards[1].value == Value.THREE 
        && cards[2].value == Value.FOUR && cards[3].value == Value.FIVE
        && cards[4].value == Value.ACE;
  }
  
  boolean isStraightFlush() {
    return isFlush() && isStraight();
  }
  
  boolean isTwoPairs() {
  return !is4OfAKind() && !is3OfAKind() && (cards[0].value.ordinal() == cards[1].value.ordinal() 
                       && cards[2].value.ordinal() == cards[3].value.ordinal()
                       || cards[1].value.ordinal() == cards[2].value.ordinal()
                       && cards[3].value.ordinal() == cards[4].value.ordinal()
                       || cards[0].value.ordinal() == cards[1].value.ordinal()
                       && cards[3].value.ordinal() == cards[4].value.ordinal());
  }
  
  boolean isOnePair() {
    return !is4OfAKind() && !is3OfAKind() && !isTwoPairs() 
      &&(cards[0].value.ordinal() == cards[1].value.ordinal() 
      || cards[1].value.ordinal() == cards[2].value.ordinal()
      || cards[2].value.ordinal() == cards[3].value.ordinal()
      || cards[3].value.ordinal() == cards[4].value.ordinal());
  }

  boolean is3OfAKind() {
  return !is4OfAKind() && (cards[0].value == cards[1].value && cards[1].value == cards[2].value
    || cards[1].value == cards[2].value && cards[2].value == cards[3].value
    || cards[2].value == cards[3].value && cards[3].value == cards[4].value);
  }
  
  boolean isFullHouse() {
  return cards[0].value == cards[1].value && cards[2].value == cards[3].value
    && cards[3].value == cards[4].value 
    || cards[0].value == cards[1].value && cards[1].value == cards[2].value
    && cards[3].value == cards[4].value;  
  }
}

class Hand5Comparator implements Comparator<Hand5>{
  static public final Hand5Comparator compareObject = new Hand5Comparator();
  public int compare1(Hand5 h1, Hand5 h2) {
      return compare(h1, h2);
  }
  
  private static int secondCheck( boolean b0, boolean b1, Hand5 h0, Hand5 h1 ) {
      if ( !b0 && b1 ) {
        return 1;
      } else if ( b0 && !b1 ) {
        return -1;
      } else if (b0 && b1) { 
        int v0 = h0.cards[1].value.ordinal();
        int v1 = h1.cards[1].value.ordinal();
        return v1 - v0;
      }
      return 0;
    }
  
  private static int thirdCheck( boolean b0, boolean b1, Hand5 h0, Hand5 h1 ) {
      if ( !b0 && b1 ) {
        return 1;
      } else if ( b0 && !b1 ) {
        return -1;
      } else if (b0 && b1) { 
        int v0 = h0.cards[2].value.ordinal();
        int v1 = h1.cards[2].value.ordinal();
        return v1 - v0;
      }
      return 0;
    }
  
  private static int fourthCheck( boolean b0, boolean b1, Hand5 h0, Hand5 h1 ) {
      if ( !b0 && b1 ) {
        return 1;
      } else if ( b0 && !b1 ) {
        return -1;
      } else if (b0 && b1) { 
        int v0 = h0.cards[3].value.ordinal();
        int v1 = h1.cards[3].value.ordinal();
        return v1 - v0;
      }
      return 0;
    }

  private static int compare4OfAKind(Hand5 h0, Hand5 h1) {
      boolean b0 = h0.is4OfAKind();
      boolean b1 = h1.is4OfAKind();
      return thirdCheck( b0, b1, h0, h1 );
    }
  
  private static int compare3OfAKind(Hand5 h0, Hand5 h1) {
    boolean b0 = h0.is3OfAKind();
    boolean b1 = h1.is3OfAKind();
    return thirdCheck( b0, b1, h0, h1 );
  }
  
  private static int compareFullHouse(Hand5 h0, Hand5 h1) {
  boolean b0 = h0.isFullHouse();
    boolean b1 = h1.isFullHouse();
    return thirdCheck( b0, b1, h0, h1 );
  }

  private static int compareStraightFlush(Hand5 h0, Hand5 h1) {
    boolean b0 = h0.isStraightFlush();
    boolean b1 = h1.isStraightFlush();
    if ( !b0 && b1 ) {
        return 1;
    } else if ( b0 && !b1 ) {
        return -1;
    } else if ( b0 && b1 ) {
      return compareStraight( h0, h1 );
    } else {
      return 0;
    }
  }

  private static int compareFlush(Hand5 h0, Hand5 h1) {
     boolean b0 = h0.isFlush();
     boolean b1 = h1.isFlush();
     if ( !b0 && b1 ) {
       return 1;
     } else if ( b0 && !b1 ) {
       return -1;
     } else if ( b0 && b1 ) {
       return compareOne(h0, h1);
     } else {
       return 0;
     }
   }

  private static int compareStraight(Hand5 h0, Hand5 h1) {
     boolean b0 = h0.isStraight();
     boolean b1 = h1.isStraight();
     if ( !b0 && b1 ) {
       return 1;
     } else if ( b0 && !b1 ) {
       return -1;
     } else if ( b0 && b1 ) {
       int v0 = h0.cards[0].value.ordinal();
       int v1 = h1.cards[0].value.ordinal();
       // special ace, 2, 3 straight; watch out for confusion with 2,3,4
       if ( v0 == Value.TWO.ordinal() && h0.cards[4].value.ordinal() == Value.ACE.ordinal() ) {
         if ( v1 == Value.TWO.ordinal() && h1.cards[4].value.ordinal() == Value.ACE.ordinal() ) {
           return 0;
         } else {
           return 1;
         }
       } else if ( v1 == Value.TWO.ordinal() && h1.cards[4].value.ordinal() == Value.ACE.ordinal() ) {
         return -1;
       }
       return h1.cards[4].value.ordinal() - h0.cards[4].value.ordinal();
     }
     return 0;
 }

  private static int compareTwoPairs(Hand5 h0, Hand5 h1) {
      boolean b0 = h0.isTwoPairs();
      boolean b1 = h1.isTwoPairs();
      if ( !b0 && b1 ) {
        return 1;
      } else if ( b0 && !b1 ) {
        return -1;
      } else if ( b0 && b1 ) {
        // check if tied on the pair - second & fourth card is the common card
        if ( fourthCheck(b0, b1, h0, h1) != 0) {
          return fourthCheck(b0, b1, h0, h1);
        } else {
          // tied on fourth card (the bigger pair)
          return secondCheck(b0, b1, h0, h1);
        } 
      } 
      return 0;
    }
  
  private static int compareOnePair(Hand5 h0, Hand5 h1) {
    boolean b0 = h0.isOnePair();
    boolean b1 = h1.isOnePair();
    int i = 0;
    int j = 0;
    int pair0A = 0;
    int pair0B = 0;
    int pair1A = 0;
    int pair1B = 0;
    if ( !b0 && b1 ) {
      return 1;
    } else if ( b0 && !b1 ) {
      return -1;
    } else if ( b0 && b1 ) {
      for(i = 0; i < 4; i++){
        pair0A = h0.cards[i].value.ordinal();
        pair0B = h0.cards[i+1].value.ordinal();
        if ( pair0A == pair0B ) break;
      }
  	  for(j = 0; j < 4; j++){
  		pair1A = h1.cards[j].value.ordinal();
  		pair1B = h1.cards[j+1].value.ordinal();
  		if ( pair1A == pair1B ) break;
  	  }
  	  return pair1A - pair0A;
    } else {
        return compareOne(h0, h1);
    } 
  }

  private static int compareOne(Hand5 h0, Hand5 h1) {
    for ( int i = h0.cards.length-1; i >= 0; i-- ) {
      int v0 = h0.cards[i].value.ordinal();
      int v1 = h1.cards[i].value.ordinal();
      if ( v0 != v1 ) {
        return v1 - v0;
      }
    }
    return 0;
  }

  public int compare(Hand5 h0, Hand5 h1) {
    int cmp;
    // use short circuit evaluation
    if ( (0 != ( cmp = compareStraightFlush(h0,h1) ) )
          || (0 != ( cmp = compare4OfAKind(h0,h1) ) )
          || (0 != ( cmp = compareFullHouse(h0,h1) ) )
          || (0 != ( cmp = compareFlush(h0,h1) ) )
          || (0 != ( cmp = compareStraight(h0,h1) ) )
          || (0 != ( cmp = compare3OfAKind(h0,h1) ) )
          || (0 != ( cmp = compareTwoPairs(h0,h1) ) )
          || (0 != ( cmp = compareOnePair(h0,h1) ) )
          || (0 != ( cmp = compareOne (h0,h1) ) ) ) {
    }
    return cmp;
  }

  public static Hand5 pick5( Card[] sevenCards, int i, int j ) {
    int k = 0;
    int n = 0;
    Card cards[] = new Card[5];
  for(k = 0; k < 7; k++){
    if(k == i || k == j){
      continue;
    }
    cards[n++] = sevenCards[k];
  }
  Hand5 result = new Hand5(cards[0], cards[1], cards[2],cards[3], cards[4]);
    return result;
  }
 
  // returns 21 hands of 5 cards made up from the seven cards
  // provided
  public static Hand5[] compute7Choose5( Card[] sevenCards ) {
    Hand5[] result = new Hand5[21]; // 7 choose 2 is 21
    int k = 0;
    for ( int i = 0 ; i < 7; i++ ) {
      for ( int j = i+1; j < 7; j++ ) {
        Hand5 aHand = pick5( sevenCards, i, j );
        result[k++] = aHand;
      }
    }
    return result;
  }
  
  public static Hand5 best5OutOf7( Card[] sevenCards ) {
      if ( sevenCards.length != 7 ) {
        System.out.println("Error: need 7 cards input to best5OutOf7");
        return null;
      }

      Hand5[] all5Hands = compute7Choose5( sevenCards );
      Arrays.sort(all5Hands, new Hand5Comparator() );

      return all5Hands[0];
  }
}
