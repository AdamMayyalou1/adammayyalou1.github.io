import tester.*;

interface IEntertainment {
  // compute the total price of this Entertainment
  double totalPrice();

  // computes the minutes of entertainment of this IEntertainment
  int duration();

  // produce a String that shows the name and price of this IEntertainment
  String format();

  // is this IEntertainment the same as that one?
  boolean sameEntertainment(IEntertainment that);

  // is this IEntertainment the same as that Magazine?
  boolean sameMagazine(Magazine that);

  // is this IEntertainment the same as that TVSeries?
  boolean sameTVSeries(TVSeries that);

  // is this IEntertainment the same as that Podcast?
  boolean samePodcast(Podcast that);
}

abstract class AEntertainment implements IEntertainment {
  String name; // name of the entertainment
  double price; // represents price per issue
  int installments; // number of episodes in this entertainment

  /* TEMPLATE
  FIELDS
  ... this.name ...                    -- String
  ... this.price ...                    -- double
  ... this.installments ...                     -- int
  METHODS
  ... this.totalPrice() ...                    -- double
  ... this.duration() ...        -- int
  ... this.format() ... -- String
  ... this.sameEntertainment(IEntertainment that) ... -- boolean
  ... this.sameMagazine(Magazine that) ... -- boolean
  ... this.sameTVSeries(TVSeries that) ... -- boolean
  ... this.samePodcast(Podcast that) ... -- boolean
  */
  
  // general constructor for a form of entertainment
  public AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }

  // compute the total price of this IEntertainment
  public double totalPrice() {
    return this.price * this.installments;
  }

  // computes the minutes of entertainment of this IEntertainment
  public int duration() {
    return 50 * this.installments;
  }

  // produce a String that shows the name and price of this IEntertainment
  public String format() {
    return this.name + ", " + this.price + ".";
  }

  /* TEMPLATE
  FIELDS
  ... this.name ...                    -- String
  ... this.price ...                    -- double
  ... this.installments ...                     -- int
  METHODS
  ... this.totalPrice() ...                    -- double
  ... this.duration() ...        -- int
  ... this.format() ... -- String
  ... this.sameEntertainment(IEntertainment that) ... -- boolean
  ... this.sameMagazine(Magazine that) ... -- boolean
  ... this.sameTVSeries(TVSeries that) ... -- boolean
  ... this.samePodcast(Podcast that) ... -- boolean
  PARAMETERS
  ... that -- IEntertainment
  METHODS ON PARAMETERS
  ... that.sameMagazine(Magazine that) ... -- boolean
  ... that.sameTVSeries(TVSeries that) ... -- boolean
  ... that.samePodcast(Podcast that) ... -- boolean
  */
  // is this IEntertainment the same as that one?
  abstract public boolean sameEntertainment(IEntertainment that);

  // are the IEntertainments the same Magazine?
  public boolean sameMagazine(Magazine that) {
    return false;
  }

  // are the IEntertainments the same TVSeries?
  public boolean sameTVSeries(TVSeries that) {
    return false;
  }

  // are the IEntertainments the same Podcast?
  public boolean samePodcast(Podcast that) {
    return false;
  }
}

class Magazine extends AEntertainment {
  String genre;
  int pages;

  /* TEMPLATE
  FIELDS
  ... this.name ...                    -- String
  ... this.price ...                    -- double
  ... this.installments ...                     -- int
  ... this.genre ...                     -- String
  ... this.pages ...                    -- int
  METHODS
  ... this.totalPrice() ...                    -- double
  ... this.duration() ...        -- int
  ... this.format() ... -- String
  ... this.sameEntertainment(IEntertainment that) ... -- boolean
  ... this.sameMagazine(Magazine that) ... -- boolean
  ... this.sameTVSeries(TVSeries that) ... -- boolean
  ... this.samePodcast(Podcast that) ... -- boolean
*/
  
  // constructor for Magazine
  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.genre = genre;
    this.pages = pages;
  }

  // computes the minutes of entertainment of this Magazine, (includes all
  // installments)
  public int duration() {
    return 5 * this.pages * this.installments;
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    /* TEMPLATE
    FIELDS
    ... this.name ...                    -- String
    ... this.price ...                    -- double
    ... this.installments ...                     -- int
    ... this.genre ...                            -- String
    ... this.pages ...                           -- int
    METHODS
    ... this.totalPrice() ...                    -- double
    ... this.duration() ...        -- int
    ... this.format() ... -- String
    ... this.sameEntertainment(IEntertainment that) ... -- boolean
    ... this.sameMagazine(Magazine that) ... -- boolean
    ... this.sameTVSeries(TVSeries that) ... -- boolean
    ... this.samePodcast(Podcast that) ... -- boolean
    PARAMETERS
    ... that -- IEntertainment
    METHODS ON PARAMETERS
    ... that.sameMagazine(Magazine that) ... -- boolean
    */
    return that.sameMagazine(this);
  }

  // is this Magazine the same as that Magazine?
  public boolean sameMagazine(Magazine that) {
    return this.name.equals(that.name) && this.price == that.price
        && this.installments == that.installments && this.genre.equals(that.genre)
        && this.pages == that.pages;
  }
}

class TVSeries extends AEntertainment {
  String corporation;

  /* TEMPLATE
  FIELDS
  ... this.name ...                    -- String
  ... this.price ...                    -- double
  ... this.installments ...                     -- int
  ... this.corporation ...                     -- String
  METHODS
  ... this.totalPrice() ...                    -- double
  ... this.duration() ...        -- int
  ... this.format() ... -- String
  ... this.sameEntertainment(IEntertainment that) ... -- boolean
  ... this.sameMagazine(Magazine that) ... -- boolean
  ... this.sameTVSeries(TVSeries that) ... -- boolean
  ... this.samePodcast(Podcast that) ... -- boolean
*/
  
  // constructor for TVSeries
  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  // is this TVSeries the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    /* TEMPLATE
    FIELDS
    ... this.name ...                    -- String
    ... this.price ...                    -- double
    ... this.installments ...                     -- int
    ... this.corporation ...                            -- String
    METHODS
    ... this.totalPrice() ...                    -- double
    ... this.duration() ...        -- int
    ... this.format() ... -- String
    ... this.sameEntertainment(IEntertainment that) ... -- boolean
    ... this.sameMagazine(Magazine that) ... -- boolean
    ... this.sameTVSeries(TVSeries that) ... -- boolean
    ... this.samePodcast(Podcast that) ... -- boolean
    PARAMETERS
    ... that -- IEntertainment
    METHODS ON PARAMETERS
    ... that.sameTVSeries(TVSeries that) ... -- boolean
    */
    return that.sameTVSeries(this);
  }

  // is this TVSeries the same as that TVSeries?
  public boolean sameTVSeries(TVSeries that) {
    return this.name.equals(that.name) && this.price == that.price
        && this.installments == that.installments && this.corporation.equals(that.corporation);
  }
}

class Podcast extends AEntertainment {

  /* TEMPLATE
  FIELDS
  ... this.name ...                    -- String
  ... this.price ...                    -- double
  ... this.installments ...                     -- int
  METHODS
  ... this.totalPrice() ...                    -- double
  ... this.duration() ...        -- int
  ... this.format() ... -- String
  ... this.sameEntertainment(IEntertainment that) ... -- boolean
  ... this.sameMagazine(Magazine that) ... -- boolean
  ... this.sameTVSeries(TVSeries that) ... -- boolean
  ... this.samePodcast(Podcast that) ... -- boolean
*/
  
  // constructor for Podcast
  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  // is this Podcast the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    /* TEMPLATE
    FIELDS
    ... this.name ...                    -- String
    ... this.price ...                    -- double
    ... this.installments ...                     -- int
    METHODS
    ... this.totalPrice() ...                    -- double
    ... this.duration() ...        -- int
    ... this.format() ... -- String
    ... this.sameEntertainment(IEntertainment that) ... -- boolean
    ... this.sameMagazine(Magazine that) ... -- boolean
    ... this.sameTVSeries(TVSeries that) ... -- boolean
    ... this.samePodcast(Podcast that) ... -- boolean
    PARAMETERS
    ... that -- IEntertainment
    METHODS ON PARAMETERS
    ... that.samePodcast(Podcast that) ... -- boolean
    */
    return that.samePodcast(this);
  }

  // is this Podcast the same as that Podcast?
  public boolean samePodcast(Podcast that) {
    return this.name.equals(that.name) && this.price == that.price
        && this.installments == that.installments;
  }
}

class ExamplesEntertainment {
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment theNews = new Magazine("The News", 4.95, "News", 57, 5);
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment theAdamsFamily = new TVSeries("The Adam's Family", 6.00, 10, "Hulu");
  IEntertainment serial = new Podcast("Serial", 0.0, 8);
  IEntertainment truth = new Podcast("Truth", 2.0, 9);

  Magazine rollingStone2 = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  Magazine theNews2 = new Magazine("The News", 4.95, "News", 57, 5);
  TVSeries houseOfCards2 = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  TVSeries theAdamsFamily2 = new TVSeries("The Adam's Family", 6.00, 10, "Hulu");
  Podcast serial2 = new Podcast("Serial", 0.0, 8);
  Podcast truth2 = new Podcast("Truth", 2.0, 9);

  // testing total price method
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001)
        && t.checkInexact(this.theNews.totalPrice(), 4.95 * 5, .0001)
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001)
        && t.checkInexact(this.theAdamsFamily.totalPrice(), 6.00 * 10, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.truth.totalPrice(), 2.0 * 9, .0001);
  }

  // testing duration method for all forms of entertainment
  boolean testDuration(Tester t) {
    return t.checkExpect(this.rollingStone.duration(), 3600)
        && t.checkExpect(this.theNews.duration(), 1425)
        && t.checkExpect(this.houseOfCards.duration(), 650)
        && t.checkExpect(this.theAdamsFamily.duration(), 500)
        && t.checkExpect(this.serial.duration(), 400) && t.checkExpect(this.truth.duration(), 450);
  }

  // testing format method for all forms of entertainment
  boolean testFormat(Tester t) {
    return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.")
        && t.checkExpect(this.theNews.format(), "The News, 4.95.")
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
        && t.checkExpect(this.theAdamsFamily.format(), "The Adam's Family, 6.0.")
        && t.checkExpect(this.serial.format(), "Serial, 0.0.")
        && t.checkExpect(this.truth.format(), "Truth, 2.0.");
  }

  // testing sameEntertainment method for all forms of entertainment
  boolean testSameEntertainment(Tester t) {
    return t.checkExpect(this.rollingStone.sameEntertainment(theNews), false)
        && t.checkExpect(this.theNews.sameEntertainment(theNews), true)
        && t.checkExpect(this.houseOfCards.sameEntertainment(houseOfCards), true)
        && t.checkExpect(this.theAdamsFamily.sameEntertainment(serial), false)
        && t.checkExpect(this.serial.sameEntertainment(serial), true)
        && t.checkExpect(this.truth.sameEntertainment(serial), false);
  }

  // testing sameMagazine method for all forms of entertainment
  boolean testSameMagazine(Tester t) {
    return t.checkExpect(this.rollingStone2.sameMagazine(theNews2), false)
        && t.checkExpect(this.theNews2.sameMagazine(theNews2), true)
        && t.checkExpect(this.houseOfCards2.sameMagazine(rollingStone2), false)
        && t.checkExpect(this.theAdamsFamily2.sameMagazine(rollingStone2), false)
        && t.checkExpect(this.serial2.sameMagazine(theNews2), false)
        && t.checkExpect(this.truth2.sameMagazine(theNews2), false);
  }

  // testing sameMagazine method for all forms of entertainment
  boolean testSameTVSeries(Tester t) {
    return t.checkExpect(this.rollingStone2.sameTVSeries(houseOfCards2), false)
        && t.checkExpect(this.theNews2.sameTVSeries(theAdamsFamily2), false)
        && t.checkExpect(this.houseOfCards2.sameTVSeries(houseOfCards2), true)
        && t.checkExpect(this.theAdamsFamily2.sameTVSeries(houseOfCards2), false)
        && t.checkExpect(this.serial2.sameTVSeries(theAdamsFamily2), false)
        && t.checkExpect(this.truth2.sameTVSeries(theAdamsFamily2), false);
  }

  // testing sameMagazine method for all forms of entertainment
  boolean testSamePodcast(Tester t) {
    return t.checkExpect(this.rollingStone2.samePodcast(truth2), false)
        && t.checkExpect(this.theNews2.samePodcast(truth2), false)
        && t.checkExpect(this.houseOfCards2.samePodcast(truth2), false)
        && t.checkExpect(this.theAdamsFamily2.samePodcast(serial2), false)
        && t.checkExpect(this.serial2.samePodcast(serial2), true)
        && t.checkExpect(this.truth2.samePodcast(serial2), false);
  }
}