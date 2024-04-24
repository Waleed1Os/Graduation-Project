package com.graduationproject.project.configuration;

import java.util.Arrays;

// import org.apache.catalina.Context;
// import org.apache.catalina.connector.Connector;
// import org.apache.tomcat.util.descriptor.web.SecurityCollection;
// import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
// import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
// import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// // import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfigurationSource;
// // import org.springframework.web.servlet.config.annotation.CorsRegistry;
// // import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ServerConiguration
//  implements WebMvcConfigurer
 {


  // @Override
  // public void addCorsMappings(@NonNull CorsRegistry registry) {
  //     registry.addMapping("/**")
  //             .allowedOrigins("http://localhost:4200")
  //             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
  //             .allowedHeaders("*")
  //             .allowCredentials(true);
  // }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  // Allow all origins
      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD")); // Allow all standard methods
      configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL)); // Allow all headers
      configuration.setAllowCredentials(true); // Allow credentials
      configuration.addExposedHeader(CorsConfiguration.ALL); // Expose all headers
      configuration.setMaxAge(3600L); // How long the response from a pre-flight request can be cached by clients
  
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration); // Apply this configuration to all paths
      return source;
  }
// @Bean
//   public ServletWebServerFactory servletContainer() {
//     TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//       @Override
//       protected void postProcessContext(Context context) {
//         SecurityConstraint securityConstraint = new SecurityConstraint();
//         securityConstraint.setUserConstraint("CONFIDENTIAL");
//         SecurityCollection collection = new SecurityCollection();
//         collection.addPattern("/*");
//         securityConstraint.addCollection(collection);
//         context.addConstraint(securityConstraint);
//       }
//     };
//     tomcat.addAdditionalTomcatConnectors(getHttpConnector());
//     return tomcat;
//   }

  // private Connector getHttpConnector() {
  //   var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
  //   connector.setScheme("http");
  //   connector.setPort(8080);
  //   connector.setSecure(false);
  //   connector.setRedirectPort(8443);
  //   return connector;
  // }
}
