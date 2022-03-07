import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SkiServlet", value = "/SkiServlet")
public class SkiServlet extends HttpServlet {

  private final Gson gson = new Gson();
  private final JsonMessage message = new JsonMessage("Error occurred in request");
  private final ConnectionFactory factory = new ConnectionFactory();
  private Connection connection;

  @Override
  public void init() throws ServletException {
    super.init();
    factory.setHost("44.233.74.178");
    factory.setUsername("user");
    factory.setPassword("user");
    try {
      connection = factory.newConnection();
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");

    String urlPath = request.getPathInfo();
    if (urlPath == null || urlPath.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write("missing parameters");
      return;
    }
    String[] urlParts = urlPath.split("/");
    isGetUrlValid(urlParts, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("application/json");
    String urlPath = request.getPathInfo();

    if (urlPath == null || urlPath.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write("missing parameters");
      return;
    }

    String[] urlParts = urlPath.split("/");

    isPostUrlValid(urlParts, request, response);

  }

  private boolean isGetUrlValid(String[] urlParts, HttpServletResponse response)
      throws IOException {
    int length = urlParts.length - 1;
    switch (length) {
      case 1:
        return lengthOneUrlValidation(urlParts, response);
      case 3:
        return lengthThreeUrlValidation(urlParts, response);
      case 7:
        return lengthSevenUrlValidation(urlParts, response);
      case 8:
        return lengthEightUrlValidation(urlParts, response);
    }
    return false;
  }

  private boolean isPostUrlValid(String[] urlParts, HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    int length = urlParts.length - 1;
    switch (length) {
      case 3:
        return postLengthThreeUrlValidation(urlParts, request, response);
      case 8:
        return postLengthEightUrlValidation(urlParts, request, response);
    }
    return false;
  }

  private boolean lengthOneUrlValidation(String[] urlParts,
      HttpServletResponse response) throws IOException {
    if (urlParts[1].equals("resorts")) {
      JsonResort resortOne = new JsonResort("Ian", 12);
      JsonResort resortTwo = new JsonResort("Lift", 13);
      JsonResort[] resortList = new JsonResort[]{resortOne, resortTwo};
      JsonResorts jsonResorts = new JsonResorts(resortList);
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().print(gson.toJson(jsonResorts));
      return true;
    }
    if (urlParts[1].equals("statistics")) {
      JsonStatistic jsonStatistic = new JsonStatistic("/resorts", "GET", 11, 198);
      JsonStatistic[] jsonStatisticsList = new JsonStatistic[]{jsonStatistic};
      JsonStatistics jsonStatistics = new JsonStatistics(jsonStatisticsList);
      response.getWriter().print(gson.toJson(jsonStatistics));
      response.setStatus(HttpServletResponse.SC_OK);
      return true;
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    response.getWriter().print(message);
    return false;
  }

  private boolean lengthSevenUrlValidation(String[] urlParts, HttpServletResponse response)
      throws IOException {
    if (urlParts[1].equals("resorts") && urlParts[3].equals("seasons") && urlParts[5].equals("day")
        && urlParts[7].equals("skiers")) {
      try {
        Integer.parseInt(urlParts[2]);
        Integer.parseInt(urlParts[4]);
        Integer.parseInt(urlParts[6]);
        response.setStatus(HttpServletResponse.SC_OK);
        JsonSkiers jsonSkiers = new JsonSkiers("Mission Ridge", 300);
        response.getWriter().print(gson.toJson(jsonSkiers));
        return true;
      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print(gson.toJson(message));
        return false;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.getWriter().print(gson.toJson(message));
    return false;
  }

  private boolean lengthThreeUrlValidation(String[] urlParts,
      HttpServletResponse response) throws IOException {
    if (urlParts[1].equals("resorts") && urlParts[3].equals("seasons")) {
      try {
        Integer.parseInt(urlParts[2]);
        JsonSeasons jsonSeasons = new JsonSeasons(new String[]{"spring", "fall"});
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(gson.toJson(jsonSeasons));
        return true;
      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print(gson.toJson(message));
        return false;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (urlParts[1].equals("skiers") && urlParts[3].equals("vertical")) {
      try {
        Integer.parseInt(urlParts[2]);
        JsonResort jsonResort = new JsonResort("SP", 13);
        JsonResorts jsonResorts = new JsonResorts(new JsonResort[]{jsonResort});
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(gson.toJson(jsonResorts));
        return true;
      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print(gson.toJson(message));
        return false;
      }
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.getWriter().print(gson.toJson(message));
    return false;
  }

  private boolean lengthEightUrlValidation(String[] urlParts, HttpServletResponse response)
      throws IOException {
    if (urlParts[1].equals("skiers") && urlParts[3].equals("seasons") && urlParts[5].equals("days")
        && urlParts[7].equals("skiers")) {
      try {
        Integer.parseInt(urlParts[2]);
//        Integer.parseInt(urlParts[4]);
//        int day = Integer.parseInt(urlParts[6]);
        Integer.parseInt(urlParts[8]);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(12345);
//        if (day < 1 || day > 366) {
//          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//          response.getWriter().print(gson.toJson(message));
//          return false;
//        }
        return true;
      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print(gson.toJson(message));
        return false;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.getWriter().print(gson.toJson(message));
    return false;
  }

  private boolean postLengthThreeUrlValidation(String[] urlParts, HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    if (urlParts[1].equals("resorts") && urlParts[3].equals("seasons")) {
      try {
        Integer.parseInt(urlParts[2]);
        JsonYear jsonYear = gson.fromJson(request.getReader(), JsonYear.class);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().print(gson.toJson(new JsonMessage("Success")));
        return true;
      } catch (NumberFormatException | JsonParseException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print(gson.toJson(message));
        return false;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.getWriter().print(gson.toJson(message));
    return false;
  }

  private boolean postLengthEightUrlValidation(String[] urlParts, HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    if (urlParts[1].equals("skiers") && urlParts[3].equals("seasons") && urlParts[5].equals("day")
        && urlParts[7].equals("skiers")) {
      try {
        int resortID = Integer.parseInt(urlParts[2]);
//        Integer.parseInt(urlParts[4]);
//        int day = Integer.parseInt(urlParts[6]);
        int skierID = Integer.parseInt(urlParts[8]);
//        if (day < 1 || day > 366) {
//          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//          response.getWriter().print(gson.toJson(message));
//          return false;
//        }
        JsonLift jsonLift = gson.fromJson(request.getReader(), JsonLift.class);
        response.setStatus(HttpServletResponse.SC_CREATED);
        SendToRabbitMQ.sendToQueue(gson.toJson(jsonLift), connection);
        response.getWriter().print(gson.toJson(new JsonMessage("Success")));
        return true;
      } catch (NumberFormatException | JsonParseException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().print(gson.toJson(message));
        return false;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.getWriter().print(gson.toJson(message));
    return false;
  }

}
