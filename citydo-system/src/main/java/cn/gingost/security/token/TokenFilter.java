package cn.gingost.security.token;

import cn.gingost.security.domain.JwtProperties;
import cn.gingost.security.domain.OnlineUser;
import cn.gingost.security.service.OnlineUserService;
import cn.gingost.utils.RedisUtils;
import cn.gingost.utils.SpringContextHolder;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author /
 */
@Slf4j
@AllArgsConstructor
public class TokenFilter extends GenericFilterBean {

   private final TokenProvider tokenProvider;

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      String token = resolveToken(httpServletRequest);
      if (!StringUtils.isEmpty(token)){
         String requestRri = httpServletRequest.getRequestURI();
         OnlineUser onlineUser = null;
         JwtProperties properties = SpringContextHolder.getBean(JwtProperties.class);
         try {
            RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
            onlineUser = (OnlineUser) redisUtils.get(properties.getOnlineKey().concat(token));
         } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
         }
         if (onlineUser != null && StringUtils.hasText(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Token 续期
            tokenProvider.checkRenewal(token);
            log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestRri);
         } else {
            log.debug("no valid JWT token found, uri: {}", requestRri);
         }
      }

      filterChain.doFilter(servletRequest, servletResponse);
   }

   private String resolveToken(HttpServletRequest request) {
      JwtProperties properties = SpringContextHolder.getBean(JwtProperties.class);
      String bearerToken = request.getHeader(properties.getHeader());
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
         return bearerToken.substring(7);
      }
      return null;
   }
}
