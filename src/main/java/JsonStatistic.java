public class JsonStatistic {
  public String URL;
  public String operation;
  int mean;
  int max;

  public JsonStatistic(String URL, String operation, int mean, int max) {
    this.URL = URL;
    this.operation = operation;
    this.mean = mean;
    this.max = max;
  }
}
