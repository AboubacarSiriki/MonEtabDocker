package ci.digitalacademy.monetab.config;

import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.SchoolService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SetupVerificationFilter extends OncePerRequestFilter {

    private final AppSettingService appSettingService;
    private final SchoolService schoolService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String path = request.getRequestURI();

            // Vérifier si l'utilisateur accède à une page qui n'est ni la configuration ni l'ajout d'école
            if (!path.equals("/") && !path.equals("/AddSchool") && !path.startsWith("/login")) {
                boolean isAppConfigured = !appSettingService.findAll().isEmpty();
                boolean isSchoolConfigured = !schoolService.findAll().isEmpty();

                // Si les paramètres ne sont pas configurés, rediriger l'utilisateur
                if (!isAppConfigured) {
                    response.sendRedirect("/");
                    return;
                } else if (!isSchoolConfigured) {
                    response.sendRedirect("/AddSchool");
                    return;
                }
            }

            // Continuer avec la chaîne de filtres
            filterChain.doFilter(request, response);
        }
}
