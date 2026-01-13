package healthcareab.project.healthcare_booking_app.filters;

import healthcareab.project.healthcare_booking_app.services.CustomUserDetailsService;
import healthcareab.project.healthcare_booking_app.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     Kör inte JWT-filter på /auth/** endpoints (login/register/logout/check).
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path != null && (
                path.equals("/auth/login") ||
                        path.equals("/auth/register") ||
                        path.equals("/auth/logout")
        );
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Hämta token från header (Bearer) eller cookie (jwt).
        String jwt = resolveToken(request);

        //Ingen token -> fortsätt som anonymous.
        if (!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        //Om user redan är autentiserad -> fortsätt.
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrahera username från token.
            String username = jwtUtil.extractUsername(jwt);

            // Saknas username -> fortsätt som anonymous.
            if (!StringUtils.hasText(username)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Ladda userDetails från databasen.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validera token (signature + expiration + user match).
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Skapa Authentication och sätt i SecurityContext.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException e) {
            // Blockera inte direkt här, låt SecurityConfig returnera 401/403 vid skyddade endpoints.
            logger.warn("JWT validation failed: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        // Fortsätt filterkedjan.
        filterChain.doFilter(request, response);
    }

    /**
     * Försök först Authorization header (Bearer), annars cookie "jwt".
     */
    private String resolveToken(HttpServletRequest request) {

        // Authorization: Bearer <token>
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }

        // Cookie: jwt=<token>
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName()) && StringUtils.hasText(cookie.getValue())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
