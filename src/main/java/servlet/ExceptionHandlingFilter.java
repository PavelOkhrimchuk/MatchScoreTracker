package servlet;


import exception.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {

    private static final Logger logger = Logger.getLogger(ExceptionHandlingFilter.class.getName());


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {
            chain.doFilter(request, response);
        } catch (MatchNotFoundException | MatchScoreNotFoundException | InvalidMatchIdException |
                 PlayerNotFoundException | DuplicatePlayerException | InvalidPlayerNameException e) {
            logger.warning(e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            httpRequest.getRequestDispatcher("/error-page.jsp").forward(request, response);
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            httpRequest.getRequestDispatcher("/error-page.jsp").forward(request, response);
        }
    }
}
