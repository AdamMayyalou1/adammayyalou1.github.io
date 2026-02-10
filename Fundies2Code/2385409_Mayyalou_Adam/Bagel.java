import tester.*;

class BagelRecipe {
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  /* TEMPLATE
  FIELDS
  ... this.flour ...                    -- double
  ... this.water ...                    -- double
  ... this.yeast ...                     -- double
  ... this.salt ... -- double
  ... this.malt ... -- double
  METHODS
  ... this.sameRecipe(BagelRecipe that) ... -- boolean
  */
  
  // Constructor that takes in the weight of flour, water, yeast, salt, and malt
  public BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    if (flour < 0 || water < 0 || yeast < 0 || salt < 0 || malt < 0 ) {
      throw new IllegalArgumentException("Ingredients cannot be a negative value.");
    }
    else if (flour != water) {
      throw new IllegalArgumentException("Flour and water must be equal in weight.");
    }
    else if (yeast != malt) {
      throw new IllegalArgumentException("Yeast and malt must be equal in weight.");
    }
    else if (salt + yeast != flour / 20) {
      throw new IllegalArgumentException(
          "Salt and yeast combined must be 1/20th the weight of the flour.");
    }
    this.flour = flour;
    this.water = water;
    this.yeast = yeast;
    this.salt = salt;
    this.malt = malt;
  }

  // Constructor that only takes in the weights of flour and yeast
  public BagelRecipe(double flour, double yeast) {
    if (flour < 0 || yeast < 0) {
      throw new IllegalArgumentException("Ingredients cannot be a negative value.");
    }
    this.flour = flour;
    this.water = flour;
    this.yeast = yeast;
    this.salt = (flour / 20) - yeast;
    this.malt = yeast;
  }

  // Constructor that takes flour in cups, while yeast and salt are teaspoons
  public BagelRecipe(double flourCups, double yeastTeaspoons, double saltTeaspoons) {
    if (flourCups < 0 || yeastTeaspoons < 0 || saltTeaspoons < 0) {
      throw new IllegalArgumentException("Ingredients cannot be a negative value.");
    }
    double flourWeight = flourCups * 4.25;
    double waterWeight = flourWeight;
    double yeastWeight = yeastTeaspoons * (5.0 / 48);
    double saltWeight = saltTeaspoons * (10.0 / 48);
    double maltWeight = yeastWeight;

    //System.out.println("Calculation1: " + (saltWeight + yeastWeight));
    //System.out.println("Calculation2: " + flourWeight / 20);
    if ((saltWeight + yeastWeight) - (flourWeight / 20) > 0.001) {
      throw new IllegalArgumentException(
          "Salt and yeast combined must be 1/20th the weight of the flour.");
    }
    this.flour = flourWeight;
    this.water = waterWeight;
    this.yeast = yeastWeight;
    this.salt = saltWeight;
    this.malt = maltWeight;
  }

  //checks if two recipes share the same ingredients within 0.001 ounces
  public boolean sameRecipe(BagelRecipe that) {
    return Math.abs(this.flour - that.flour) < 0.001 && Math.abs(this.water - that.water) < 0.001
        && Math.abs(this.yeast - that.yeast) < 0.001 && Math.abs(this.salt - that.salt) < 0.001
        && Math.abs(this.malt - that.malt) < 0.001;
  }
}

class ExamplesBagelRecipe {
  BagelRecipe recipe1 = new BagelRecipe(20.0, 20.0, 1.0, 0.0, 1.0);
  BagelRecipe recipe2 = new BagelRecipe(40.0, 40.0, 2.0, 0.0, 2.0);
  BagelRecipe recipe3 = new BagelRecipe(20.0, 1.0);
  BagelRecipe recipe4 = new BagelRecipe(10.0, 5.0);
  BagelRecipe recipe5 = new BagelRecipe(4.0, 4.08, 2.04);
  BagelRecipe recipe6 = new BagelRecipe(8.0, 8.16, 4.08);

  // Test for BagelRecipe where flour and water are not equal
  boolean testBagelRecipe1(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Flour and water must be equal in weight."), "BagelRecipe",
        20.0, 10.0, 1.0, 0.0, 1.0);
  }

  //Test for BagelRecipe where yeast and malt are not equal
  boolean testBagelRecipe2(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Yeast and malt must be equal in weight."), "BagelRecipe",
        20.0, 20.0, 1.0, 1.0, 0.0);
  }

  //Test for BagelRecipe where salt and yeast are not 1/20th
  //the weight of the flour using the constructor that
  //takes in flour, water, yeast, salt, and malt
  boolean testBagelRecipe3(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException(
            "Salt and yeast combined must be 1/20th the weight of the flour."), "BagelRecipe",
        20.0, 20.0, 1.0, 1.0, 1.0);
  }
  
  //Test for BagelRecipe where salt and yeast are not 1/20th
  //the weight of the flour using the constructor that
  //takes in flour, yeast, and salt in cups and teaspoons respectively
  boolean testBagelRecipe4(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException(
            "Salt and yeast combined must be 1/20th the weight of the flour."), "BagelRecipe",
        4.0, 4.08, 4.04);
  }
  
  //Test for BagelRecipe where an ingredient is negative in the first constructor
  boolean testBagelRecipe5(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Ingredients cannot be a negative value."), "BagelRecipe",
        0.0, 0.0, 1.0, 5.0, -3.0);
  }
  
  //Test for BagelRecipe where an ingredient is negative in the second constructor
  boolean testBagelRecipe6(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Ingredients cannot be a negative value."), "BagelRecipe",
        -5.3, -3.0);
  }
  
  //Test for BagelRecipe where an ingredient is negative in the third constructor
  boolean testBagelRecipe7(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Ingredients cannot be a negative value."), "BagelRecipe",
        1.0, 5.0, -3.0);
  }
  
  //Test for sameRecipe which checks if two recipes share the same
  //ingredients within 0.001 ounces
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(recipe1.sameRecipe(recipe1), true)
        && t.checkExpect(recipe2.sameRecipe(recipe3), false)
        && t.checkExpect(recipe3.sameRecipe(recipe3), true)
        && t.checkExpect(recipe4.sameRecipe(recipe5), false)
        && t.checkExpect(recipe5.sameRecipe(recipe1), false)
        && t.checkExpect(recipe6.sameRecipe(recipe6), true);
  }
}
