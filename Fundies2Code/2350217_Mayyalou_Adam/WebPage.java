//

import tester.Tester;

// represents a list that either holds items or is empty
interface ILoItem {
  
  int totalImageSize();

  int textLength();

  String images();
}

// Represents an empty list of items
class MtLoItem implements ILoItem {
  MtLoItem() {
  }

  // An empty list does not have a size
  public int totalImageSize() {
    return 0;
  }

  // An empty list does not have a text length
  public int textLength() {
    return 0;
  }

  // An empty list does not have any images to get the names of
  public String images() {
    return "";
  }
}

// represents a non-empty list of items
class ConsLoItem implements ILoItem {
  IItem first;
  ILoItem rest;

  // constructor
  ConsLoItem(IItem first, ILoItem rest) {
    this.first = first;
    this.rest = rest;
  }

  // computes the total size of all images in the list
  public int totalImageSize() {
    return this.first.totalImageSize() + rest.totalImageSize();
  }

  // computes the text length of all relevant components
  public int textLength() {
    return this.first.textLength() + rest.textLength();
  }

  // returns the names of all images in the list
  public String images() {
    String firstImages = first.images();
    String restImages = rest.images();
    if (firstImages.isEmpty()) {
      return restImages;
    }
    else if (restImages.isEmpty()) {
      return firstImages;
    }
    else {
      return firstImages + ", " + restImages;
    }
  }
}

// represents an item which is either a text, image, or link
interface IItem {
  int totalImageSize();

  int textLength();

  String images();
}

// represents a text Item
class Text implements IItem {
  String contents;

  Text(String contents) {
    this.contents = contents;
  }

  // a text item does not have a size
  public int totalImageSize() {
    return 0;
  }

  // returns the length of the contents of the text
  public int textLength() {
    return this.contents.length();
  }

  // text does not contain an image
  public String images() {
    return "";
  }
}

// Represents an image Item
class Image implements IItem {
  String fileName;
  int size;
  String fileType;

  // constructor
  Image(String fileName, int size, String fileType) {
    this.fileName = fileName;
    this.size = size;
    this.fileType = fileType;
  }

  // returns the size of the image
  public int totalImageSize() {
    return this.size;
  }

  // returns the text length using the file name and type
  public int textLength() {
    return this.fileName.length() + this.fileType.length();
  }

  // returns the file name and type
  public String images() {
    return this.fileName + "." + this.fileType;
  }
}

// represents a link Item
class Link implements IItem {
  String name;
  WebPage page;

  Link(String name, WebPage page) {
    this.name = name;
    this.page = page;
  }

  // returns the size of the page within the link
  public int totalImageSize() {
    return this.page.totalImageSize();
  }

  // returns the text length using the link's name and page
  public int textLength() {
    return this.name.length() + this.page.textLength();
  }

  // returns the images within the link
  public String images() {
    return this.page.images();
  }
}

// represents a web page
class WebPage {
  String title;
  String url;
  ILoItem items;

  // constructor
  WebPage(String title, String url, ILoItem items) {
    this.title = title;
    this.url = url;
    this.items = items;
  }

  // returns the total size of all images in the webPage and related links
  int totalImageSize() {
    return this.items.totalImageSize();
  }

  // returns the text length using the contents of texts,
  // file names and types, link labels, and web page titles
  int textLength() {
    return this.title.length() + this.items.textLength();
  }

  // stub for images
  String images() {
    return this.items.images();
  }
}

// examples and tests for WebPage methods
class ExamplesWebPage {
  WebPage htdp = new WebPage("HtDP", "htdp.org", new ConsLoItem(new Text("How to Design Programs"),
      new ConsLoItem(new Image("htdp", 4300, "tiff"), new MtLoItem())));

  WebPage ood = new WebPage("OOD", "ccs.neu.edu/OOD", new ConsLoItem(new Text("Stay classy, Java"),
      new ConsLoItem(new Link("Back to the Future", htdp), new MtLoItem())));

  WebPage fundiesWP = new WebPage("Fundies II", "ccs.neu.edu/Fundies2",
      new ConsLoItem(new Text("Home sweet home"),
          new ConsLoItem(new Image("wvh-lab", 400, "png"),
              new ConsLoItem(new Text("The staff"),
                  new ConsLoItem(new Image("profs", 240, "jpeg"),
                      new ConsLoItem(new Link("A Look Back", htdp),
                          new ConsLoItem(new Link("A Look Ahead", ood), new MtLoItem())))))));
  
  WebPage htdpEmpty = new WebPage("HtDP", "htdp.org", new MtLoItem());

  // test the method totalImageSize for WebPages
  boolean testTotalImageSizeWebPage(Tester t) {
    return t.checkExpect(this.fundiesWP.totalImageSize(), 9240)
        && t.checkExpect(this.htdpEmpty.totalImageSize(), 0);
  }

  // test the method totalImageSize for lists of items
  boolean testTotalImageSizeList(Tester t) {
    return t.checkExpect(
        new ConsLoItem(new Image("htdp", 4300, "tiff"), new MtLoItem()).totalImageSize(), 4300)
        && t.checkExpect(new MtLoItem().totalImageSize(), 0);
  }

  // test the method totalImageSize for items
  boolean testTotalImageSizeItem(Tester t) {
    return t.checkExpect(new Image("htdp", 4300, "tiff").totalImageSize(), 4300)
        && t.checkExpect(new Text("Home sweet home").totalImageSize(), 0)
        && t.checkExpect(new Link("A Look Back", htdp).totalImageSize(), 4300);
  }

  // test the method textLength for WebPages
  boolean testTextLengthWebPage(Tester t) {
    return t.checkExpect(this.fundiesWP.textLength(), 182)
        && t.checkExpect(this.htdpEmpty.textLength(), 4);
  }

  // test the method textLength for lists of items
  boolean testTextLengthList(Tester t) {
    return t.checkExpect(
        new ConsLoItem(new Image("htdp", 4300, "tiff"), new MtLoItem()).textLength(), 8)
        && t.checkExpect(new MtLoItem().textLength(), 0);
  }

  // test the method textLength for items
  boolean testTextLengthItem(Tester t) {
    return t.checkExpect(new Image("htdp", 4300, "tiff").textLength(), 8)
        && t.checkExpect(new Text("Home sweet home").textLength(), 15)
        && t.checkExpect(new Link("A Look Back", htdp).textLength(), 45);
  }

  // test the method images for WebPages
  boolean testImagesWebPage(Tester t) {
    return t.checkExpect(this.fundiesWP.images(), "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff")
        && t.checkExpect(this.htdpEmpty.images(), "");
  }

  // test the method images for lists of items
  boolean testImagesList(Tester t) {
    return t.checkExpect(
        new ConsLoItem(new Image("htdp", 4300, "tiff"), new MtLoItem()).images(),
        "htdp.tiff") && t.checkExpect(new MtLoItem().images(), "");
  }

  // test the method images for items
  boolean testImagesItem(Tester t) {
    return t.checkExpect(new Image("htdp", 4300, "tiff").images(), "htdp.tiff")
        && t.checkExpect(new Text("Home sweet home").images(), "")
        && t.checkExpect(new Link("A Look Back", htdp).images(), "htdp.tiff");
  }
}