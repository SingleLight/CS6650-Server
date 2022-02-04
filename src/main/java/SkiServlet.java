import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SkiServlet", value = "/SkiServlet")
public class SkiServlet extends HttpServlet {

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

    if (!isGetUrlValid(urlParts)) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else {
      response.setStatus(HttpServletResponse.SC_OK);

      response.getWriter().write("It works!");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }

  private boolean isGetUrlValid(String[] urlParts) {
    int length = urlParts.length;
    if (length == 1) {
      return lengthOneUrlValidation(urlParts);
    } else if (length == 7) {
      return lengthSevenUrlValidation(urlParts);
    } else if (length == 3) {
      return lengthThreeUrlValidation(urlParts);
    }
    return false;
  }

  private boolean lengthOneUrlValidation(String[] urlParts) {
    return urlParts[0].equals("resorts") || urlParts[0].equals("statistics");
  }

  private boolean lengthSevenUrlValidation(String[] urlParts) {
    if (urlParts[0].equals("resorts") && urlParts[2].equals("seasons") && urlParts[4].equals(
        "day") && urlParts[6].equals("skiers")) {
      try {
        Integer.parseInt(urlParts[1]);
        Integer.parseInt(urlParts[3]);
        Integer.parseInt(urlParts[5]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean lengthThreeUrlValidation(String[] urlParts) {
    if (urlParts[0].equals("resorts") && urlParts[2].equals("seasons")) {
      try {
        Integer.parseInt(urlParts[1]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    } else if (urlParts[0].equals("skiers") && urlParts[2].equals("vertical")) {
      try {
        Integer.parseInt(urlParts[1]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean lengthEightUrlValidation(String[] urlParts) {
    if (urlParts[0].equals("skiers") && urlParts[2].equals("seasons") && urlParts[4].equals(
        "day") && urlParts[6].equals("skiers")) {
      try {
        Integer.parseInt(urlParts[1]);
        Integer.parseInt(urlParts[3]);
        Integer.parseInt(urlParts[5]);
        Integer.parseInt(urlParts[7]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }
}
