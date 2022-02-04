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
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("Bad Url");
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().write("It works!");
    }
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

    if (!isPostUrlValid(urlParts)) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("Bad Url");
    } else {
      response.setStatus(HttpServletResponse.SC_CREATED);
      response.getWriter().write("It works!");
    }
  }

  private boolean isGetUrlValid(String[] urlParts) {
    int length = urlParts.length - 1;
    switch (length) {
      case 1:
        return lengthOneUrlValidation(urlParts);
      case 3:
        return lengthThreeUrlValidation(urlParts);
      case 7:
        return lengthSevenUrlValidation(urlParts);
      case 8:
        return lengthEightUrlValidation(urlParts);
    }
    return false;
  }

  private boolean isPostUrlValid(String[] urlParts) {
    int length = urlParts.length - 1;
    switch (length) {
      case 3:
        return postLengthThreeUrlValidation(urlParts);
      case 8:
        return postLengthEightUrlValidation(urlParts);
    }
    return false;
  }

  private boolean lengthOneUrlValidation(String[] urlParts) {
    return urlParts[1].equals("resorts") || urlParts[1].equals("statistics");
  }

  private boolean lengthSevenUrlValidation(String[] urlParts) {
    if (urlParts[1].equals("resorts") && urlParts[3].equals("seasons") && urlParts[5].equals("day")
        && urlParts[7].equals("skiers")) {
      try {
        Integer.parseInt(urlParts[2]);
        Integer.parseInt(urlParts[4]);
        Integer.parseInt(urlParts[6]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean lengthThreeUrlValidation(String[] urlParts) {
    if (urlParts[1].equals("resorts") && urlParts[3].equals("seasons")) {
      try {
        Integer.parseInt(urlParts[2]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    } else if (urlParts[1].equals("skiers") && urlParts[3].equals("vertical")) {
      try {
        Integer.parseInt(urlParts[2]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean lengthEightUrlValidation(String[] urlParts) {
    if (urlParts[1].equals("skiers") && urlParts[3].equals("seasons") && urlParts[5].equals("day")
        && urlParts[7].equals("skiers")) {
      try {
        Integer.parseInt(urlParts[2]);
        Integer.parseInt(urlParts[4]);
        Integer.parseInt(urlParts[6]);
        Integer.parseInt(urlParts[8]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean postLengthThreeUrlValidation(String[] urlParts) {
    if (urlParts[1].equals("resorts") && urlParts[3].equals("seasons")) {
      try {
        Integer.parseInt(urlParts[2]);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean postLengthEightUrlValidation(String[] urlParts) {
    return lengthEightUrlValidation(urlParts);
  }
}
